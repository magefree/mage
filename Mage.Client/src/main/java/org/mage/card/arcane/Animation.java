package org.mage.card.arcane;

import mage.cards.MagePermanent;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Animation {
    private static final long TARGET_MILLIS_PER_FRAME = 30;

    private static Timer timer = new Timer("Animation", true);

    private static CardPanel enlargedCardPanel;
    private static CardPanel enlargedAnimationPanel;
    private static final Object enlargeLock = new Object();

    private TimerTask timerTask;
    private FrameTimer frameTimer;
    private long elapsed;

    public Animation (final long duration) {
        this(duration, 0);
    }

    public Animation (final long duration, long delay) {
        timerTask = new TimerTask() {
            @Override
            public void run () {
                if (frameTimer == null) {
                    start();
                    frameTimer = new FrameTimer();
                }
                elapsed += frameTimer.getTimeSinceLastFrame();
                if (elapsed >= duration) {
                    cancel();
                    elapsed = duration;
                }
                update(elapsed / (float)duration);
                if (elapsed == duration) {
                    end();
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, delay, TARGET_MILLIS_PER_FRAME);
    }

    protected abstract void update (float percentage);

    protected void cancel () {
        timerTask.cancel();
        end();
    }

    protected void start () {
    }

    protected void end () {
    }

    /**
     * Uses averaging of the time between the past few frames to provide smooth animation.
     */
    private static class FrameTimer {
        private static final int SAMPLES = 6;
        private static final long MAX_FRAME = 100; // Max time for one frame, to weed out spikes.

        private long samples[] = new long[SAMPLES];
        private int sampleIndex;

        public FrameTimer () {
            long currentTime = System.currentTimeMillis();
            for (int i = SAMPLES - 1; i >= 0; i--) {
                samples[i] = currentTime - (SAMPLES - i) * TARGET_MILLIS_PER_FRAME;
            }
        }

        public long getTimeSinceLastFrame () {
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

            return timeSinceOldestSample / (long)SAMPLES;
        }
    }

    public static void tapCardToggle (final CardPanel panel, final MagePermanent parent, final boolean tapped, final boolean flipped) {
        new Animation(300) {
            @Override
            protected void start () {
                parent.onBeginAnimation();
            }

            @Override
            protected void update (float percentage) {
                if (tapped) {
                    panel.tappedAngle = CardPanel.TAPPED_ANGLE * percentage;
                    // reverse movement if untapping
                    if (!panel.isTapped()) {
                        panel.tappedAngle = CardPanel.TAPPED_ANGLE - panel.tappedAngle;
                    }
                }
                if (flipped) {
                    panel.flippedAngle = CardPanel.FLIPPED_ANGLE * percentage;
                    if (!panel.isFlipped()) {
                        panel.flippedAngle = CardPanel.FLIPPED_ANGLE - panel.flippedAngle;
                    }
                }
                panel.repaint();
            }

            @Override
            protected void end () {
                if (tapped) {
                    panel.tappedAngle = panel.isTapped() ? CardPanel.TAPPED_ANGLE : 0;
                }
                if (flipped) {
                    panel.flippedAngle = panel.isFlipped() ? CardPanel.FLIPPED_ANGLE : 0;
                }
                parent.onEndAnimation();
                parent.repaint();
            }
        };
    }

    public static void transformCard (final CardPanel panel, final MagePermanent parent, final boolean transformed) {

        new Animation(1200) {
            private boolean state = false;

            @Override
            protected void start () {
                parent.onBeginAnimation();
            }

            @Override
            protected void update (float percentage) {
                double p = percentage * 2;
                if (percentage > 0.5) {
                    if (!state) {
                        parent.toggleTransformed();
                    }
                    state = true;
                    p = (p - 0.5) * 2;
                }
                if (!state) {
                    panel.transformAngle = Math.max(0.01, 1 - p);
                } else {
                    panel.transformAngle = Math.max(0.01, p - 1);
                }
                panel.repaint();
            }

            @Override
            protected void end () {
                parent.onEndAnimation();
                parent.repaint();
            }
        };
    }

    public static void moveCardToPlay (final int startX, final int startY, final int startWidth, final int endX, final int endY,
        final int endWidth, final CardPanel animationPanel, final CardPanel placeholder, final JLayeredPane layeredPane,
        final int speed) {
        UI.invokeLater(new Runnable() {
            @Override
            public void run () {
                final int startHeight = Math.round(startWidth * CardPanel.ASPECT_RATIO);
                final int endHeight = Math.round(endWidth * CardPanel.ASPECT_RATIO);
                final float a = 2f;
                final float sqrta = (float)Math.sqrt(1 / a);

                animationPanel.setCardBounds(startX, startY, startWidth, startHeight);
                animationPanel.setAnimationPanel(true);
                Container parent = animationPanel.getParent();
                if (parent != null && !parent.equals(layeredPane)) {
                    layeredPane.add(animationPanel);
                    layeredPane.setLayer(animationPanel, JLayeredPane.MODAL_LAYER);
                }

                new Animation(700) {
                    @Override
                    protected void update (float percentage) {
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
                        animationPanel.setCardBounds(currentX, currentY, currentWidth, currentHeight);
                    }

                    @Override
                    protected void end () {
                        EventQueue.invokeLater(new Runnable() {
                            @Override
                            public void run () {
                                if (placeholder != null) {
                                    placeholder.setDisplayEnabled(true);
                                    placeholder.setImage(animationPanel);
                                }
                                animationPanel.setVisible(false);
                                animationPanel.repaint();
                                layeredPane.remove(animationPanel);
                            }
                        });
                    }
                };
            }
        });
    }

    public static void moveCard (final int startX, final int startY, final int startWidth, final int endX, final int endY,
        final int endWidth, final CardPanel animationPanel, final CardPanel placeholder, final JLayeredPane layeredPane,
        final int speed) {
        UI.invokeLater(new Runnable() {
            @Override
            public void run () {
                final int startHeight = Math.round(startWidth * CardPanel.ASPECT_RATIO);
                final int endHeight = Math.round(endWidth * CardPanel.ASPECT_RATIO);

                animationPanel.setCardBounds(startX, startY, startWidth, startHeight);
                animationPanel.setAnimationPanel(true);
                Container parent = animationPanel.getParent();
                if (parent != null && !parent.equals(layeredPane)) {
                    layeredPane.add(animationPanel);
                    layeredPane.setLayer(animationPanel, JLayeredPane.MODAL_LAYER);
                }

                new Animation(speed) {
                    @Override
                    protected void update (float percentage) {
                        int currentX = startX + Math.round((endX - startX) * percentage);
                        int currentY = startY + Math.round((endY - startY) * percentage);
                        int currentWidth = startWidth + Math.round((endWidth - startWidth) * percentage);
                        int currentHeight = startHeight + Math.round((endHeight - startHeight) * percentage);
                        animationPanel.setCardBounds(currentX, currentY, currentWidth, currentHeight);
                    }

                    @Override
                    protected void end () {
                        EventQueue.invokeLater(new Runnable() {
                            @Override
                            public void run () {
                                if (placeholder != null) {
                                    placeholder.setDisplayEnabled(true);
                                    placeholder.setImage(animationPanel);
                                }
                                animationPanel.setVisible(false);
                                animationPanel.repaint();
                                layeredPane.remove(animationPanel);
                            }
                        });
                    }
                };
            }
        });
    }

    public static void shrinkCard () {
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
            protected void update (float percentage) {
                int currentWidth = startWidth + Math.round((endWidth - startWidth) * percentage);
                int currentHeight = startHeight + Math.round((endHeight - startHeight) * percentage);
                Point startPos = SwingUtilities.convertPoint(overPanel.getParent(), overPanel.getCardLocation(), layeredPane);
                int centerX = startPos.x + Math.round(endWidth / 2f);
                int centerY = startPos.y + Math.round(endHeight / 2f);
                int currentX = Math.max(0, centerX - Math.round(currentWidth / 2f));
                currentX = Math.min(currentX, layeredPane.getWidth() - currentWidth);
                int currentY = Math.max(0, centerY - Math.round(currentHeight / 2f));
                currentY = Math.min(currentY, layeredPane.getHeight() - currentHeight);
                animationPanel.tappedAngle = overPanel.tappedAngle * percentage;
                animationPanel.setCardBounds(currentX, currentY, currentWidth, currentHeight);
            }

            @Override
            protected void end () {
                animationPanel.setVisible(false);
                animationPanel.repaint();
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run () {
                        layeredPane.remove(animationPanel);
                    }
                });
            }
        };
    }

    public static boolean isShowingEnlargedCard () {
        synchronized (enlargeLock) {
            return enlargedAnimationPanel != null;
        }
    }

    public static void showCard(final MagePermanent card, int count) {
        if (count == 0) {
            return;
        }
        new Animation(600 / count) {
            @Override
            protected void start () {
            }

            @Override
            protected void update (float percentage) {
                float alpha = percentage;
                card.setAlpha(alpha);
                card.repaint();
            }

            @Override
            protected void end () {
                card.setAlpha(1.f);
            }
        };
    }

    public static void hideCard(final MagePermanent card, int count) {
        if (count == 0) {
            return;
        }
        new Animation(600 / count) {
            @Override
            protected void start () {
            }

            @Override
            protected void update (float percentage) {
                float alpha = 1 - percentage;
                card.setAlpha(alpha);
                card.repaint();
            }

            @Override
            protected void end () {
                card.setAlpha(0f);
            }
        };
    }
}
