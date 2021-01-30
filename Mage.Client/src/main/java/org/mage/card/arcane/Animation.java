package org.mage.card.arcane;

import mage.cards.MageCard;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Animation {

    private static final boolean ENABLED = true;

    private static final long TARGET_MILLIS_PER_FRAME = 30;

    private static final Timer timer = new Timer("Animation", true);

    private static CardPanel enlargedCardPanel;
    private static CardPanel enlargedAnimationPanel;
    private static final Object enlargeLock = new Object();

    private final TimerTask timerTask;
    private FrameTimer frameTimer;
    private long elapsed;

    public Animation(final long duration) {
        this(duration, 0);
    }

    public Animation(final long duration, long delay) {
        if (!ENABLED) {
            UI.invokeLater(() -> {
                start();
                //update(1.0f);
                end();
            });
            return;
        }

        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (frameTimer == null) {
                    start();
                    frameTimer = new FrameTimer();
                }
                elapsed += frameTimer.getTimeSinceLastFrame();
                if (elapsed >= duration) {
                    cancel();
                    elapsed = duration;
                }
                update(elapsed / (float) duration);
                if (elapsed == duration) {
                    end();
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, delay, TARGET_MILLIS_PER_FRAME);
    }

    protected abstract void update(float percentage);

    protected void cancel() {
        timerTask.cancel();
        end();
    }

    protected void start() {
    }

    protected void end() {
    }

    /**
     * Uses averaging of the time between the past few frames to provide smooth
     * animation.
     */
    private static class FrameTimer {

        private static final int SAMPLES = 6;
        private static final long MAX_FRAME = 100; // Max time for one frame, to weed out spikes.

        private final long[] samples = new long[SAMPLES];
        private int sampleIndex;

        public FrameTimer() {
            long currentTime = System.currentTimeMillis();
            for (int i = SAMPLES - 1; i >= 0; i--) {
                samples[i] = currentTime - (SAMPLES - i) * TARGET_MILLIS_PER_FRAME;
            }
        }

        public long getTimeSinceLastFrame() {
            long currentTime = System.currentTimeMillis();

            int id = sampleIndex - 1;
            if (id < 0) {
                id += SAMPLES;
            }

            long timeSinceLastSample = currentTime - samples[id];

            // If the slice was too big, advance all the previous times by the diff.
            if (timeSinceLastSample > MAX_FRAME) {
                long diff = timeSinceLastSample - MAX_FRAME;
                for (int i = 0; i < SAMPLES; i++) {
                    samples[i] += diff;
                }
            }

            long timeSinceOldestSample = currentTime - samples[sampleIndex];
            samples[sampleIndex] = currentTime;
            sampleIndex = (sampleIndex + 1) % SAMPLES;

            return timeSinceOldestSample / (long) SAMPLES;
        }
    }

    public static void tapCardToggle(final CardPanel source, final boolean tapped, final boolean flipped) {
        CardPanel mainPanel = source;
        MageCard parentPanel = mainPanel.getTopPanelRef();

        new Animation(300) {
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

    public static void transformCard(final CardPanel source) {

        CardPanel mainPanel = source;
        MageCard parentPanel = mainPanel.getTopPanelRef();

        new Animation(600) {
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
            }
        };
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

            new Animation(700) {
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

            new Animation(speed) {
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

        new Animation(200) {
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

    public static void showCard(final MageCard card, int count) {
        if (count == 0) {
            return;
        }
        new Animation(600 / count) {
            @Override
            protected void start() {
            }

            @Override
            protected void update(float percentage) {
                float alpha = percentage;
                card.setAlpha(alpha);
                card.repaint();
            }

            @Override
            protected void end() {
                card.setAlpha(1.f);
            }
        };
    }

    public static void hideCard(final MageCard card, int count) {
        if (count == 0) {
            return;
        }
        new Animation(600 / count) {
            @Override
            protected void start() {
            }

            @Override
            protected void update(float percentage) {
                float alpha = 1 - percentage;
                card.setAlpha(alpha);
                card.repaint();
            }

            @Override
            protected void end() {
                card.setAlpha(0f);
            }
        };
    }
}
