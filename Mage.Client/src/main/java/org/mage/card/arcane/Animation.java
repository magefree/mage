package org.mage.card.arcane;

import mage.cards.MageCard;
import mage.util.ThreadUtils;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * GUI: card animations (tap, transform, fade in/out)
 * <p>
 * All animations are processed by a single Swing timer in the Swing thread (EDT), so animation callbacks can change
 * GUI components directly. The whole API (create, cancel) is for the Swing thread only.
 * <p>
 * Rules:
 * - one animation per target (example: a tap animation cancels an unfinished fade in animation of the same card);
 * - a cancelled animation jumps to the final state (update(1f) + end()) on the next tick;
 * - all animations of a game are cancelled on the game's close (see cancelRunningAnimations);
 * - a busy Swing thread (example: big game update) does not break an animation: a time jump is limited per frame,
 * so the animation continues from the last visible state and ends a little later (but never later than x2 duration).
 */
public abstract class Animation {

    private static final boolean ENABLED = true;
    private static final long MS_TO_NANO = 1000000;

    private static final long TRANSFORM_CARD_DURATION_MS = 600;
    private static final long CARD_SHOW_HIDE_DURATION_MS = 250;
    private static final long CARD_TAP_DURATION_MS = 300;

    private static final long TARGET_MILLIS_PER_FRAME = 30;
    private static final long MAX_MILLIS_PER_FRAME = 100; // max time jump per frame on a busy swing thread
    private static final int MAX_DURATION_MULTIPLIER_ON_BUSY = 2; // max total time of an animation on a busy swing thread

    /**
     * Running and scheduled animations by target (one animation per target, a new one cancels the old one)
     * and by game (to cancel all on the game close). Swing thread only.
     */
    private static final Map<Object, Animation> activeByTarget = new HashMap<>();
    private static final Map<UUID, Set<Animation>> activeByGameId = new HashMap<>();

    /**
     * New animations, moved to the processing list on the next tick (so a new animation can be created
     * safely inside a callback of another animation). Swing thread only.
     */
    private static final List<Animation> pendingAdd = new ArrayList<>();

    /**
     * Animations in processing. Timer tick only.
     */
    private static final List<Animation> animations = new ArrayList<>();

    /**
     * Shared Swing timer, runs while there are animations to process
     */
    private static final javax.swing.Timer timer;

    static {
        // init in static block, not in field: javac 8 fails on a field lambda with "variable e might not have been initialized"
        // in the constructor's early return
        timer = new javax.swing.Timer((int) TARGET_MILLIS_PER_FRAME, e -> processTick());
    }

    private enum State {
        SCHEDULED, // waits a delay or the first tick
        RUNNING,
        CANCELLED, // must jump to the final state on the next tick
        FINISHED
    }

    private final Object target;
    private final UUID gameId;
    private final long duration; // in ns
    private final long delay; // in ns
    private long scheduledAtTime = 0; // in ns, the first tick after it starts the animation
    private long startedAtTime = 0; // in ns
    private long lastProcessTime = 0; // in ns, real time of the last tick
    private long animationTime = 0; // in ns, smoothed elapsed time of the animation (limited jumps per frame)
    private State state = State.SCHEDULED;

    /**
     * @param gameId   game to cancel the animation on close (null for non game cards)
     * @param target   object to keep one animation per target (null to allow multiple animations)
     * @param duration in ms
     */
    public Animation(UUID gameId, Object target, long duration) {
        this(gameId, target, duration, 0);
    }

    /**
     * @param gameId   game to cancel the animation on close (null for non game cards)
     * @param target   object to keep one animation per target (null to allow multiple animations)
     * @param duration in ms
     * @param delay    in ms
     */
    public Animation(UUID gameId, Object target, long duration, long delay) {
        this.target = target;
        this.gameId = gameId;
        this.duration = duration * MS_TO_NANO;
        this.delay = delay * MS_TO_NANO;

        if (!ENABLED || duration <= 0) {
            // no animation - jump to the final state
            UI.invokeLater(() -> {
                start();
                update(1f);
                end();
            });
            return;
        }

        ThreadUtils.ensureRunInGUISwingThread();

        // one animation per target
        if (this.target != null) {
            Animation running = activeByTarget.get(this.target);
            if (running != null) {
                running.cancel();
            }
            activeByTarget.put(this.target, this);
        }
        if (this.gameId != null) {
            activeByGameId.computeIfAbsent(this.gameId, k -> new HashSet<>()).add(this);
        }

        pendingAdd.add(this);
        timer.start(); // start on a running timer is ignored
    }

    private static void processTick() {
        // new animations
        animations.addAll(pendingAdd);
        pendingAdd.clear();

        // process
        long time = System.nanoTime();
        List<Animation> finished = new ArrayList<>();
        for (Animation animation : animations) {
            if (animation.process(time) == State.FINISHED) {
                finished.add(animation);
            }
        }
        animations.removeAll(finished);
        finished.forEach(Animation::unregister);

        // stop the timer if nothing to do (new animations can be created inside the callbacks, so check pending too)
        if (animations.isEmpty() && pendingAdd.isEmpty()) {
            timer.stop();
        }
    }

    /**
     * Process a single tick
     *
     * @param time current time in ns
     * @return new state
     */
    private State process(long time) {
        switch (state) {
            case SCHEDULED: {
                if (scheduledAtTime == 0) {
                    scheduledAtTime = time + delay;
                }
                if (scheduledAtTime <= time) {
                    startedAtTime = time;
                    lastProcessTime = time;
                    state = State.RUNNING;
                    start();
                    update(0f);
                }
                break;
            }
            case RUNNING: {
                // smooth: a busy swing thread can delay a tick for a long time, so limit a time jump per frame
                // (the animation continues from the last visible state and ends a little later)
                // but never run too long (fast games must not collect animations)
                long realElapsed = time - startedAtTime;
                long frameTime = Math.min(time - lastProcessTime, MAX_MILLIS_PER_FRAME * MS_TO_NANO);
                animationTime += frameTime;
                lastProcessTime = time;
                if (animationTime >= duration || realElapsed >= duration * MAX_DURATION_MULTIPLIER_ON_BUSY) {
                    finish();
                } else {
                    update((float) animationTime / (float) duration);
                }
                break;
            }
            case CANCELLED: {
                if (startedAtTime == 0) {
                    // never started - must call start too, so start/end calls are always balanced
                    start();
                }
                finish();
                break;
            }
            default: {
                break;
            }
        }
        return state;
    }

    private void finish() {
        state = State.FINISHED;
        update(1f);
        end();
    }

    /**
     * Cancel the animation. It will jump to the final state on the next tick (update(1f) + end()).
     * Swing thread only.
     */
    protected void cancel() {
        if (state == State.FINISHED || state == State.CANCELLED) {
            return;
        }
        state = State.CANCELLED;
        unregister(); // free the target for a new animation immediately
    }

    private void unregister() {
        if (target != null && activeByTarget.get(target) == this) {
            activeByTarget.remove(target);
        }
        if (gameId != null) {
            Set<Animation> gameAnimations = activeByGameId.get(gameId);
            if (gameAnimations != null) {
                gameAnimations.remove(this);
                if (gameAnimations.isEmpty()) {
                    activeByGameId.remove(gameId);
                }
            }
        }
    }

    /**
     * Cancel all animations of the game (on game close). Swing thread only.
     */
    public static void cancelRunningAnimations(UUID gameId) {
        ThreadUtils.ensureRunInGUISwingThread();
        Set<Animation> gameAnimations = activeByGameId.get(gameId);
        if (gameAnimations != null) {
            // cancel removes from the original set
            new ArrayList<>(gameAnimations).forEach(Animation::cancel);
        }
    }

    /**
     * Called once before the first update
     */
    protected void start() {
    }

    /**
     * Called on each frame
     *
     * @param percentage 0..1
     */
    protected abstract void update(float percentage);

    /**
     * Called once after the last update (percentage 1.0), on a normal finish and on a cancel
     */
    protected void end() {
    }

    public static void tapCardToggle(final CardPanel source, final boolean tapped, final boolean flipped) {
        CardPanel mainPanel = source;
        MageCard parentPanel = mainPanel.getTopPanelRef();

        new Animation(mainPanel.gameId, parentPanel, CARD_TAP_DURATION_MS) {
            @Override
            protected void start() {
                parentPanel.onBeginAnimation();
            }

            @Override
            protected void update(float percentage) {
                if (tapped) {
                    mainPanel.setTappedAngle(CardPanel.TAPPED_ANGLE * percentage);
                    // reverse movement if untapping
                    if (!mainPanel.isTapped()) {
                        mainPanel.setTappedAngle(CardPanel.TAPPED_ANGLE - mainPanel.getTappedAngle());
                    }
                }
                if (flipped) {
                    mainPanel.setFlippedAngle(CardPanel.FLIPPED_ANGLE * percentage);
                    if (!mainPanel.isFlipped()) {
                        mainPanel.setFlippedAngle(CardPanel.FLIPPED_ANGLE - mainPanel.getFlippedAngle());
                    }
                }
                parentPanel.repaint();
            }

            @Override
            protected void end() {
                if (tapped) {
                    mainPanel.setTappedAngle(mainPanel.isTapped() ? CardPanel.TAPPED_ANGLE : 0);
                }
                if (flipped) {
                    mainPanel.setFlippedAngle(mainPanel.isFlipped() ? CardPanel.FLIPPED_ANGLE : 0);
                }
                parentPanel.onEndAnimation();
                parentPanel.repaint();
            }
        };
    }

    public static CompletableFuture<Void> transformCard(final CardPanel source) {
        CardPanel mainPanel = source;
        MageCard parentPanel = mainPanel.getTopPanelRef();

        CompletableFuture<Void> done = new CompletableFuture<>();

        new Animation(mainPanel.gameId, parentPanel, TRANSFORM_CARD_DURATION_MS) {
            private boolean state = false;

            @Override
            protected void start() {
                parentPanel.onBeginAnimation();
            }

            @Override
            protected void update(float percentage) {
                double p = percentage * 2;
                if (percentage > 0.5) {
                    if (!state) {
                        parentPanel.toggleTransformed();
                    }
                    state = true;
                    p = (p - 0.5) * 2;
                }
                if (!state) {
                    mainPanel.transformAngle = Math.max(0.01, 1 - p);
                } else {
                    mainPanel.transformAngle = Math.max(0.01, p - 1);
                }
                parentPanel.repaint();
            }

            @Override
            protected void end() {
                if (!state) {
                    parentPanel.toggleTransformed();
                }
                state = true;
                mainPanel.transformAngle = 1;

                parentPanel.onEndAnimation();
                parentPanel.repaint();
                done.complete(null);
            }
        };

        return done;
    }

    /**
     * Linear alpha fade of a card
     *
     * @return future, completed after the last frame (in the swing thread)
     */
    private static CompletableFuture<Void> linearFade(final MageCard card, long durationMs, float from, float to) {
        if (card == null || from == to) {
            return CompletableFuture.completedFuture(null);
        }

        CompletableFuture<Void> done = new CompletableFuture<>();

        UUID gameId = null;
        if (card.getMainPanel() instanceof CardPanel) {
            gameId = ((CardPanel) card.getMainPanel()).gameId;
        }
        new Animation(gameId, card, durationMs) {
            @Override
            protected void update(float percentage) {
                card.setAlpha(from + percentage * (to - from));
                card.repaint();
            }

            @Override
            protected void end() {
                card.setAlpha(to);
                card.repaint();
                done.complete(null);
            }
        };

        return done;
    }

    public static CompletableFuture<Void> showCard(final MageCard card) {
        return linearFade(card, CARD_SHOW_HIDE_DURATION_MS, 0f, 1f);
    }

    public static CompletableFuture<Void> hideCard(final MageCard card) {
        return linearFade(card, CARD_SHOW_HIDE_DURATION_MS, 1f, 0f);
    }
}
