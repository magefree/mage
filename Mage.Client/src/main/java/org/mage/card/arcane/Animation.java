package org.mage.card.arcane;

import mage.cards.MageCard;
import mage.util.ThreadUtils;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

public abstract class Animation {

    private static final boolean ENABLED = true;
    private static final long MS_TO_NANO = 1000000;

    private static final long TRANSFORM_CARD_DURATION_MS = 600;
    private static final long CARD_SHOW_HIDE_DURATION_MS = 250;
    private static final long CARD_TAP_DURATION_MS = 300;

    private static final long TARGET_MILLIS_PER_FRAME = 30;
    private static final long MAX_MILLIS_PER_FRAME = 100;

    private static CardPanel enlargedCardPanel;
    private static CardPanel enlargedAnimationPanel;
    private static final Object enlargeLock = new Object();

    /**
     * Global registry of all running animations with a non-null target.
     * This registry is used to cancel running animations for a target when
     * a new animation is scheduled. Example: tapping a permanent that is still
     * fading-in will cancel the fade animation and start the tap animation.
     *
     * Note: Animations register themselves during construction and deregister
     * when cleanup() gets called.
     */
    private static final ConcurrentMap<UUID, Set<Animation>> activeByGameId = new ConcurrentHashMap<>();
    private static final ConcurrentMap<Object, Animation> activeByTarget = new ConcurrentHashMap<>();

    /**
     * List of animations to add/schedule. Can be accessesd thread-safe.
     */
    private static final ConcurrentLinkedQueue<Animation> pendingAdd = new ConcurrentLinkedQueue<>();

    /**
     * Shared Swing timer that processes ticks on the EDT. All animation callbacks must
     * be run from the animation timer.
     */
    private static final Timer timer =  new Timer((int)TARGET_MILLIS_PER_FRAME, timeTick());

    /**
     * List of currently running animations. Private to the timer tick handler!
     */
    private static final List<Animation> animations = new ArrayList<>();

    private Object target;
    private UUID gameId;
    private final long duration; // relative in ms
    private final long delay; // relative in ms
    private long lastProcessTime; // in ns
    private long scheduledAtTime; // in ns
    private long startedAtTime; // in ns

    enum State {
        Starting,
        Running,
        Finished,
        Canceled,
    };
    private State state = State.Starting;

    private static ActionListener timeTick() {
        return e -> {
            // Move all pending animations to the animations list that
            // is safe to iterate on the EDT.
            animations.addAll(pendingAdd);
            pendingAdd.clear();

            List<Animation> pendingRemove = new ArrayList<>();

            final long time = System.nanoTime();
            for (Animation animation : animations) {
                State state = animation.process(time);
                if (state == State.Finished) {
                    pendingRemove.add(animation);
                }
            }

            // Remove all finished animations
            animations.removeAll(pendingRemove);
            pendingRemove.forEach(Animation::cleanup);

            // Stop the timer if not needed
            if (animations.isEmpty())
                timer.stop();
        };
    }

    public Animation(UUID gameId, Object target, long duration) {
        this(gameId, target, duration, 0);
    }

    public Animation(UUID gameId, Object target, long duration, long delay) {
        this.target = target;
        this.gameId = gameId;
        this.duration = duration * MS_TO_NANO;
        this.delay = delay * MS_TO_NANO;

        if (!ENABLED || duration <= 0) {
            UI.invokeLater(() -> {
                start();
                update(1f);
                end();
            });

            // We never added this animation to any list, so we can just return
            return;
        }

        cancelRunningAnimation(this.target);
        if (this.target != null) {
            activeByTarget.put(this.target, this);
        }
        if (this.gameId != null) {
            activeByGameId.computeIfAbsent(this.gameId, ignore -> new HashSet<>()).add(this);
        }

        pendingAdd.add(this);
        if (!timer.isRunning())
            timer.start();
    }

    /**
     * Returns the animation state [0,1] for the time in ms.
     * @param time Current time in nanoseconds
     * @return The state/percentage of the animation
     */
    public float getPercentageForTime(long time) {
        return (float)Math.min(duration, Math.max(0, time - startedAtTime)) / (float)duration;
    }

    /**
     * Process a single animation tick by advancing the animations state and percentage.
     * @param time System time in nanoseconds
     * @return State of the animation
     */
    private State process(long time) {
        if (state == State.Starting) {
            if (scheduledAtTime == 0) {
                scheduledAtTime = time + delay;
            }

            if (scheduledAtTime <= time) {
                startedAtTime = time;
                start();
                state = State.Running;
            }
        } else {
            if (time - startedAtTime >= duration || state == State.Canceled) {
                update(1f);
                end();
                state = State.Finished;
            } else {
                // To smooth animations while the EDT is busy, we
                // limit time "jumps" to MAX_MILLIS_PER_FRAME, but we
                // still end animations on time, to not stress the EDT even more.
                long timeElapsed = time - lastProcessTime;
                long smoothedTime = lastProcessTime + Math.min(timeElapsed, MAX_MILLIS_PER_FRAME * MS_TO_NANO);
                update(getPercentageForTime(smoothedTime));
            }
        }

        lastProcessTime = time;
        return state;
    }

    protected abstract void update(float percentage);

    protected void cancel() {
        // The animation timer + process will do the cleanup
        // safely on the EDT for us.
        state = State.Canceled;
        cleanup();
    }

    protected void start() {
    }

    protected void end() {
    }

    private void cleanup() {
        if (target != null) {
            activeByTarget.remove(target);
        }
        target = null;

        if (gameId != null) {
            Set<Animation> byGameId = activeByGameId.getOrDefault(gameId, null);
            if (byGameId != null) {
                byGameId.remove(this);
                if (byGameId.isEmpty()) {
                    activeByGameId.remove(gameId);
                }
            }
        }
        gameId = null;
    }

    /**
     * Cancel all running animations associated with the given game id.
     *
     * @param gameId Game identifier.
     */
    public static void cancelRunningAnimations(UUID gameId) {
        ThreadUtils.ensureRunInGUISwingThread();

        Set<Animation> animations = new HashSet<>(activeByGameId.getOrDefault(gameId, Collections.emptySet()));

        // We need to iterate over a copy because the animations
        // remove themselves from the original set.
        for (Animation animation : animations) {
            animation.cancel();
        }
    }

    /**
     * Cancel running animations for the given target.
     *
     * Canceling a running animation directly calls the end/finish
     * function (jumping to percentage 1.0) and marks the animation as complete.
     *
     * @param target Target key of the animation to cancel.
     */
    private static void cancelRunningAnimation(Object target) {
        ThreadUtils.ensureRunInGUISwingThread();

        if (target == null)
            return;

        Animation running = activeByTarget.getOrDefault(target, null);
        if (running != null) {
            running.cancel();
        }
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

        CompletableFuture<Void> done = new CompletableFuture<Void>();

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

    public static void moveCardToPlay(final int startX, final int startY, final int startWidth, final int endX, final int endY,
                                      final int endWidth, final CardPanel cardToAnimate, final CardPanel placeholder, final JLayeredPane layeredPane,
                                      final int speed) {
        CardPanel cardPanel = (CardPanel) cardToAnimate.getMainPanel();
        MageCard mainPanel = cardToAnimate.getTopPanelRef();

        UI.invokeLater(() -> {
            final int startHeight = Math.round(startWidth * CardPanel.ASPECT_RATIO);
            final int endHeight = Math.round(endWidth * CardPanel.ASPECT_RATIO);
            final float a = 2f;
            final float sqrta = (float) Math.sqrt(1 / a);

            mainPanel.setCardBounds(startX, startY, startWidth, startHeight);
            cardPanel.setAnimationPanel(true);
            Container parent = mainPanel.getParent();
            if (parent != null && !parent.equals(layeredPane)) {
                layeredPane.add(mainPanel);
                layeredPane.setLayer(mainPanel, JLayeredPane.MODAL_LAYER);
            }

            new Animation(cardPanel.gameId, cardToAnimate, 700) {
                @Override
                protected void update(float percentage) {
                    float percent = percentage;
                    if (placeholder != null && !placeholder.isShowing()) {
                        cancel();
                        return;
                    }
                    int currentX = startX + Math.round((endX - startX + endWidth / 2f) * percent);
                    int currentY = startY + Math.round((endY - startY + endHeight / 2f) * percent);
                    int currentWidth, currentHeight;
                    int midWidth = Math.max(200, endWidth * 2);
                    int midHeight = Math.round(midWidth * CardPanel.ASPECT_RATIO);
                    if (percent <= 0.5f) {
                        percent = percent * 2;
                        float pp = sqrta * (1 - percent);
                        percent = 1 - a * pp * pp;
                        currentWidth = startWidth + Math.round((midWidth - startWidth) * percent);
                        currentHeight = startHeight + Math.round((midHeight - startHeight) * percent);
                    } else {
                        percent = (percent - 0.5f) * 2;
                        float pp = sqrta * percent;
                        percent = a * pp * pp;
                        currentWidth = midWidth + Math.round((endWidth - midWidth) * percent);
                        currentHeight = midHeight + Math.round((endHeight - midHeight) * percent);
                    }
                    currentX -= currentWidth / 2;
                    currentY -= currentHeight / 2;
                    mainPanel.setCardBounds(currentX, currentY, currentWidth, currentHeight);
                }

                @Override
                protected void end() {
                    EventQueue.invokeLater(() -> {
                        if (placeholder != null) {
                            placeholder.setDisplayEnabled(true);
                            placeholder.transferResources(cardPanel);
                        }
                        mainPanel.setVisible(false);
                        mainPanel.repaint();
                        layeredPane.remove(mainPanel);
                    });
                }
            };
        });
    }

    public static void moveCard(final int startX, final int startY, final int startWidth, final int endX, final int endY,
                                final int endWidth, final MageCard cardToAnimate, final CardPanel placeholder, final JLayeredPane layeredPane,
                                final int speed) {
        CardPanel cardPanel = (CardPanel) cardToAnimate.getMainPanel();
        MageCard mainPanel = cardToAnimate.getTopPanelRef();

        UI.invokeLater(() -> {
            final int startHeight = Math.round(startWidth * CardPanel.ASPECT_RATIO);
            final int endHeight = Math.round(endWidth * CardPanel.ASPECT_RATIO);

            mainPanel.setCardBounds(startX, startY, startWidth, startHeight);
            cardPanel.setAnimationPanel(true);
            Container parent = mainPanel.getParent();
            if (parent != null && !parent.equals(layeredPane)) {
                layeredPane.add(mainPanel);
                layeredPane.setLayer(mainPanel, JLayeredPane.MODAL_LAYER);
            }

            new Animation(cardPanel.gameId, cardToAnimate, speed) {
                @Override
                protected void update(float percentage) {
                    int currentX = startX + Math.round((endX - startX) * percentage);
                    int currentY = startY + Math.round((endY - startY) * percentage);
                    int currentWidth = startWidth + Math.round((endWidth - startWidth) * percentage);
                    int currentHeight = startHeight + Math.round((endHeight - startHeight) * percentage);
                    mainPanel.setCardBounds(currentX, currentY, currentWidth, currentHeight);
                }

                @Override
                protected void end() {
                    EventQueue.invokeLater(() -> {
                        if (placeholder != null) {
                            placeholder.setDisplayEnabled(true);
                            placeholder.transferResources(cardPanel);
                        }
                        mainPanel.setVisible(false);
                        mainPanel.repaint();
                        layeredPane.remove(mainPanel);
                    });
                }
            };
        });
    }

    public static void shrinkCard() {
        final CardPanel overPanel, animationPanel;
        synchronized (enlargeLock) {
            overPanel = Animation.enlargedCardPanel;
            animationPanel = Animation.enlargedAnimationPanel;
            if (animationPanel == null) {
                return;
            }
            Animation.enlargedCardPanel = null;
            Animation.enlargedAnimationPanel = null;
        }

        animationPanel.setAnimationPanel(true);
        final JLayeredPane layeredPane = SwingUtilities.getRootPane(overPanel).getLayeredPane();
        layeredPane.setLayer(animationPanel, JLayeredPane.MODAL_LAYER);

        final int startWidth = animationPanel.getCardWidth();
        final int startHeight = Math.round(startWidth * CardPanel.ASPECT_RATIO);
        final int endWidth = overPanel.getCardWidth();
        final int endHeight = Math.round(endWidth * CardPanel.ASPECT_RATIO);

        new Animation(animationPanel.gameId, animationPanel, 200) {
            @Override
            protected void update(float percentage) {
                int currentWidth = startWidth + Math.round((endWidth - startWidth) * percentage);
                int currentHeight = startHeight + Math.round((endHeight - startHeight) * percentage);
                Point startPos = SwingUtilities.convertPoint(overPanel.getParent(), overPanel.getCardLocation().getCardPoint(), layeredPane);
                int centerX = startPos.x + Math.round(endWidth / 2f);
                int centerY = startPos.y + Math.round(endHeight / 2f);
                int currentX = Math.max(0, centerX - Math.round(currentWidth / 2f));
                currentX = Math.min(currentX, layeredPane.getWidth() - currentWidth);
                int currentY = Math.max(0, centerY - Math.round(currentHeight / 2f));
                currentY = Math.min(currentY, layeredPane.getHeight() - currentHeight);
                animationPanel.setTappedAngle(overPanel.getTappedAngle() * percentage);
                animationPanel.setCardBounds(currentX, currentY, currentWidth, currentHeight);
            }

            @Override
            protected void end() {
                animationPanel.setVisible(false);
                animationPanel.repaint();
                EventQueue.invokeLater(() -> layeredPane.remove(animationPanel));
            }
        };
    }

    public static boolean isShowingEnlargedCard() {
        synchronized (enlargeLock) {
            return enlargedAnimationPanel != null;
        }
    }

    /**
     * Schedule a linear alpha-fade animation for a card.
     *
     * @param card The card to animate
     * @param durationMs Duration in milliseconds
     * @param from Starting alpha value
     * @param to Destination alpha value
     * @return A future object that gets completed when the animation is done
     */
    static private CompletableFuture<Void> linearFade(final MageCard card, long durationMs, float from, float to) {
        if (card == null || from == to)
            return CompletableFuture.completedFuture(null);

        CompletableFuture<Void> done = new CompletableFuture<Void>();

        UUID gameId = null;
        if (card.getMainPanel() != null) {
            gameId = ((CardPanel)card.getMainPanel()).gameId;
        }
        new Animation(gameId, card, durationMs) {
            @Override
            protected void start() {
                card.setAlpha(from);
                card.repaint();
            }

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
