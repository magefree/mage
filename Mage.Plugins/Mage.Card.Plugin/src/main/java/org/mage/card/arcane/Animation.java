package org.mage.card.arcane;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

import mage.cards.MagePermanent;

abstract public class Animation {
	static private final long TARGET_MILLIS_PER_FRAME = 30;
	//static private final float HALF_PI = (float)(Math.PI / 2);

	static private Timer timer = new Timer("Animation", true);

	//static private CardPanel delayedCardPanel;
	//static private long delayedTime;
	static private CardPanel enlargedCardPanel;
	static private CardPanel enlargedAnimationPanel;
	static private Object enlargeLock = new Object();

	private TimerTask timerTask;
	private FrameTimer frameTimer;
	private long elapsed;

	public Animation (final long duration) {
		this(duration, 0);
	}

	public Animation (final long duration, long delay) {
		timerTask = new TimerTask() {
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
				if (elapsed == duration) end();
			}
		};
		timer.scheduleAtFixedRate(timerTask, delay, TARGET_MILLIS_PER_FRAME);
	}

	abstract protected void update (float percentage);

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
	private class FrameTimer {
		static private final int SAMPLES = 6;
		static private final long MAX_FRAME = 100; // Max time for one frame, to weed out spikes.

		private long samples[] = new long[SAMPLES];
		private int sampleIndex;

		public FrameTimer () {
			long currentTime = System.currentTimeMillis();
			for (int i = SAMPLES - 1; i >= 0; i--)
				samples[i] = currentTime - (SAMPLES - i) * TARGET_MILLIS_PER_FRAME;
		}

		public long getTimeSinceLastFrame () {
			long currentTime = System.currentTimeMillis();

			int id = sampleIndex - 1;
			if (id < 0) id += SAMPLES;

			long timeSinceLastSample = currentTime - samples[id];

			// If the slice was too big, advance all the previous times by the diff.
			if (timeSinceLastSample > MAX_FRAME) {
				long diff = timeSinceLastSample - MAX_FRAME;
				for (int i = 0; i < SAMPLES; i++)
					samples[i] += diff;
			}

			long timeSinceOldestSample = currentTime - samples[sampleIndex];
			samples[sampleIndex] = currentTime;
			sampleIndex = (sampleIndex + 1) % SAMPLES;

			return timeSinceOldestSample / (long)SAMPLES;
		}
	}

	static public void tapCardToggle (final CardPanel panel, final MagePermanent parent) {
		new Animation(300) {
			protected void start () {
				parent.onBeginAnimation();
			}

			protected void update (float percentage) {
				panel.tappedAngle = CardPanel.TAPPED_ANGLE * percentage;
				if (!panel.isTapped()) panel.tappedAngle = CardPanel.TAPPED_ANGLE - panel.tappedAngle;
				panel.repaint();
			}

			protected void end () {
				panel.tappedAngle = panel.isTapped() ? CardPanel.TAPPED_ANGLE : 0;
				parent.onEndAnimation();
				parent.repaint();
			}
		};
	}

	// static public void moveCardToPlay (Component source, final CardPanel dest, final CardPanel animationPanel) {
	static public void moveCardToPlay (final int startX, final int startY, final int startWidth, final int endX, final int endY,
		final int endWidth, final CardPanel animationPanel, final CardPanel placeholder, final JLayeredPane layeredPane,
		final int speed) {
		UI.invokeLater(new Runnable() {
			public void run () {
				final int startHeight = Math.round(startWidth * CardPanel.ASPECT_RATIO);
				final int endHeight = Math.round(endWidth * CardPanel.ASPECT_RATIO);
				final float a = 2f;
				final float sqrta = (float)Math.sqrt(1 / a);

				animationPanel.setCardBounds(startX, startY, startWidth, startHeight);
				animationPanel.setAnimationPanel(true);
				Container parent = animationPanel.getParent();
				if (parent != layeredPane) {
					layeredPane.add(animationPanel);
					layeredPane.setLayer(animationPanel, JLayeredPane.MODAL_LAYER);
				}

				new Animation(700) {
					protected void update (float percentage) {
						if (placeholder != null && !placeholder.isShowing()) {
							cancel();
							return;
						}
						int currentX = startX + Math.round((endX - startX + endWidth / 2f) * percentage);
						int currentY = startY + Math.round((endY - startY + endHeight / 2f) * percentage);
						int currentWidth, currentHeight;
						int midWidth = Math.max(200, endWidth * 2);
						int midHeight = Math.round(midWidth * CardPanel.ASPECT_RATIO);
						if (percentage <= 0.5f) {
							percentage = percentage * 2;
							float pp = sqrta * (1 - percentage);
							percentage = 1 - a * pp * pp;
							currentWidth = startWidth + Math.round((midWidth - startWidth) * percentage);
							currentHeight = startHeight + Math.round((midHeight - startHeight) * percentage);
						} else {
							percentage = (percentage - 0.5f) * 2;
							float pp = sqrta * percentage;
							percentage = a * pp * pp;
							currentWidth = midWidth + Math.round((endWidth - midWidth) * percentage);
							currentHeight = midHeight + Math.round((endHeight - midHeight) * percentage);
						}
						currentX -= Math.round(currentWidth / 2);
						currentY -= Math.round(currentHeight / 2);
						animationPanel.setCardBounds(currentX, currentY, currentWidth, currentHeight);
					}

					protected void end () {
						EventQueue.invokeLater(new Runnable() {
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

	static public void moveCard (final int startX, final int startY, final int startWidth, final int endX, final int endY,
		final int endWidth, final CardPanel animationPanel, final CardPanel placeholder, final JLayeredPane layeredPane,
		final int speed) {
		UI.invokeLater(new Runnable() {
			public void run () {
				final int startHeight = Math.round(startWidth * CardPanel.ASPECT_RATIO);
				final int endHeight = Math.round(endWidth * CardPanel.ASPECT_RATIO);

				animationPanel.setCardBounds(startX, startY, startWidth, startHeight);
				animationPanel.setAnimationPanel(true);
				Container parent = animationPanel.getParent();
				if (parent != layeredPane) {
					layeredPane.add(animationPanel);
					layeredPane.setLayer(animationPanel, JLayeredPane.MODAL_LAYER);
				}

				new Animation(speed) {
					protected void update (float percentage) {
						int currentX = startX + Math.round((endX - startX) * percentage);
						int currentY = startY + Math.round((endY - startY) * percentage);
						int currentWidth = startWidth + Math.round((endWidth - startWidth) * percentage);
						int currentHeight = startHeight + Math.round((endHeight - startHeight) * percentage);
						animationPanel.setCardBounds(currentX, currentY, currentWidth, currentHeight);
					}

					protected void end () {
						EventQueue.invokeLater(new Runnable() {
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

	static public void shrinkCard () {
		CardPanel enlargedCardPanel, enlargedAnimationPanel;
		synchronized (enlargeLock) {
			//delayedCardPanel = null;
			//delayedTime = 0;
			enlargedCardPanel = Animation.enlargedCardPanel;
			enlargedAnimationPanel = Animation.enlargedAnimationPanel;
			if (enlargedAnimationPanel == null) return;
			Animation.enlargedCardPanel = null;
			Animation.enlargedAnimationPanel = null;
		}

		final CardPanel overPanel = enlargedCardPanel, animationPanel = enlargedAnimationPanel;

		animationPanel.setAnimationPanel(true);
		final JLayeredPane layeredPane = SwingUtilities.getRootPane(overPanel).getLayeredPane();
		layeredPane.setLayer(animationPanel, JLayeredPane.MODAL_LAYER);

		final int startWidth = animationPanel.getCardWidth();
		final int startHeight = Math.round(startWidth * CardPanel.ASPECT_RATIO);
		final int endWidth = overPanel.getCardWidth();
		final int endHeight = Math.round(endWidth * CardPanel.ASPECT_RATIO);

		new Animation(200) {
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

			protected void end () {
				animationPanel.setVisible(false);
				animationPanel.repaint();
				EventQueue.invokeLater(new Runnable() {
					public void run () {
						layeredPane.remove(animationPanel);
					}
				});
			}
		};
	}

	static public boolean isShowingEnlargedCard () {
		synchronized (enlargeLock) {
			return enlargedAnimationPanel != null;
		}
	}
	
	static public void showCard(final CardPanel panel) {
		new Animation(600) {
			protected void start () {
			}

			protected void update (float percentage) {
				float alpha = percentage;
				panel.setAlpha(alpha);
				panel.repaint();
			}

			protected void end () {
			}
		};
	}
	
	static public void hideCard(final CardPanel panel, final MagePermanent card) {
		new Animation(600) {
			protected void start () {
			}

			protected void update (float percentage) {
				float alpha = 1 - percentage;
				panel.setAlpha(alpha);
				card.setAlpha(alpha);
				card.repaint();
			}

			protected void end () {
			}
		};
	}
}
