package org.mage.card.arcane;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import mage.cards.action.ActionCallback;
import mage.client.util.ImageCaches;
import mage.view.CardView;
import org.jdesktop.swingx.graphics.GraphicsUtilities;
import org.mage.plugins.card.images.ImageCache;
import org.mage.plugins.card.images.ImageCacheData;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Render mode: MTGO
 */
public class CardPanelRenderModeMTGO extends CardPanel {

    // TODO: share code and use for all images/rendering (potential injection point - images cache), see #969
    private static final boolean MTGO_MODE_RENDER_SMOOTH_IMAGES_ENABLED = false;
    private static final int MTGO_MODE_RENDER_SCALED_IMAGES_COEF = 1; // TODO: experiment with scale settings, is it useful to render in x2-x4 sizes?

    // https://www.mtg.onl/evolution-of-magic-token-card-frame-design/

    private static final Cache<String, BufferedImage> MTGO_MODE_RENDERED_CACHE = ImageCaches.register(
            CacheBuilder
                    .newBuilder()
                    .maximumSize(3000)
                    .expireAfterAccess(60, TimeUnit.MINUTES)
                    .softValues()
                    .build()
    );

    // The art image for the card, loaded in from the disk
    private BufferedImage artImage;
    // The file it was loaded from and the size it was scaled to on load (for caching purposes)
    private String artKey;

    // Factory to generate card appropriate views
    private final CardRendererFactory cardRendererFactory = new CardRendererFactory();

    // The rendered card image, with or without the art image loaded yet
    // = null while invalid
    private BufferedImage cardImage;
    private CardRenderer cardRenderer;

    private int updateArtImageStamp;
    private final int cardRenderMode;

    /**
     * Cache key for a rendered card image: the view's render signature, then the art it was drawn
     * with, then the panel state the signature does not cover.
     */
    private String imageKey() {
        CardPanelAttributes attribs = getAttributes();
        return getGameCard().getRenderSignature()
                + artKey + '|'
                + attribs.cardWidth + '|'
                + attribs.cardHeight + '|'
                + attribs.isChoosable + '|'
                + attribs.isSelected + '|'
                + attribs.isTransformed + '|'
                + cardRenderMode;
    }

    public CardPanelRenderModeMTGO(CardView newGameCard, UUID gameId, final boolean loadImage, ActionCallback callback,
                                   final boolean foil, Dimension dimension, boolean needFullPermanentRender, int renderMode) {
        // Call to super
        super(newGameCard, gameId, loadImage, callback, foil, dimension, needFullPermanentRender);

        // Renderer
        cardRenderMode = renderMode;
        cardRenderer = cardRendererFactory.create(getGameCard(), cardRenderMode);

        // Draw the parts
        initialDraw();
    }

    @Override
    public Image getImage() {
        if (artImage == null) {
            return null;
        }
        return ImageCache.getCardImageOriginal(getGameCard()).getImage();
    }

    @Override
    protected void paintCard(Graphics2D g) {
        // Render the card if we don't have an image ready to use
        if (cardImage == null) {
            // Try to get card image from cache based on our card characteristics
            try {
                cardImage = MTGO_MODE_RENDERED_CACHE.get(imageKey(), this::renderCard);
            } catch (Exception e) {
                // TODO: research and replace with logs, message and backface image
                throw new RuntimeException(e);
            }
        }

        // And draw the image we now have
        int cardOffsetX = 0;
        int cardOffsetY = 0;

        Graphics2D g2 = (Graphics2D) g.create();
        try {
            // render with antialiasing
            if (MTGO_MODE_RENDER_SMOOTH_IMAGES_ENABLED) {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            }

            // render scaled
            if (MTGO_MODE_RENDER_SCALED_IMAGES_COEF > 1) {
                g2.drawImage(cardImage, cardOffsetX, cardOffsetY, getCardWidth(), getCardHeight(), null);
            } else {
                g2.drawImage(cardImage, cardOffsetX, cardOffsetY, null);
            }
        } finally {
            g2.dispose();
        }
    }

    @Override
    public void setCardBounds(int x, int y, int cardWidth, int cardHeight) {
        int oldCardWidth = getCardWidth();
        int oldCardHeight = getCardHeight();

        super.setCardBounds(x, y, cardWidth, cardHeight);

        // Rerender if card size changed
        if (getCardWidth() != oldCardWidth || getCardHeight() != oldCardHeight) {
            cardImage = null;
        }
    }

    @Override
    public void setChoosable(boolean choosable) {
        if (choosable != isChoosable()) {
            super.setChoosable(choosable);
            // Invalidate our render and trigger a repaint
            cardImage = null;
            repaint();
        }
    }

    @Override
    public void setSelected(boolean selected) {
        if (selected != isSelected()) {
            super.setSelected(selected);
            // Invalidate our render and trigger a repaint
            cardImage = null;
            repaint();
        }
    }

    @Override
    public void showCardTitle() {
        // Nothing to do, rendered cards always have a title
    }

    @Override
    public void transferResources(CardPanel panel) {
        if (panel instanceof CardPanelRenderModeMTGO) {
            CardPanelRenderModeMTGO impl = (CardPanelRenderModeMTGO) panel;

            // Use the art image and current rendered image from the card
            artImage = impl.artImage;
            artKey = impl.artKey;
            cardRenderer.setArtImage(artImage);
            cardImage = impl.cardImage;
        }
    }

    @Override
    public void update(CardView card) {
        // Update super
        super.update(card);

        // Update renderer
        cardImage = null;
        cardRenderer = cardRendererFactory.create(getGameCard(), cardRenderMode);
        cardRenderer.setArtImage(artImage);

        // Repaint
        repaint();
    }

    @Override
    public void updateArtImage() {
        // Invalidate
        artImage = null;
        artKey = null;
        cardImage = null;
        cardRenderer.setArtImage(null);

        // Stop animation
        setTappedAngle(isTapped() ? CardPanel.TAPPED_ANGLE : 0);
        setFlippedAngle(isFlipped() ? CardPanel.FLIPPED_ANGLE : 0);

        // Schedule a repaint
        repaint();

        // See if the image is already loaded
        //artImage = ImageCache.tryGetImage(gameCard, getCardWidth(), getCardHeight());
        //this.cardRenderer.setArtImage(artImage);

        // Submit a task to draw with the card art when it arrives
        if (artImage == null) {
            final int stamp = ++updateArtImageStamp;
            Util.threadPool.submit(() -> {
                try {
                    final ImageCacheData srcData = ImageCache.getCardImage(getGameCard(), getCardWidth(), getCardHeight());
                    final BufferedImage srcImage = srcData.getImage();
                    final String srcKey = srcImage == null ? null
                            : srcData.getPath() + '@' + srcImage.getWidth() + 'x' + srcImage.getHeight();
                    UI.invokeLater(() -> {
                        if (stamp == updateArtImageStamp) {
                            artImage = srcImage;
                            artKey = srcKey;
                            cardRenderer.setArtImage(srcImage);
                            if (srcImage != null) {
                                // Invalidate and repaint
                                cardImage = null;
                                repaint();
                            }
                        }
                    });
                } catch (Exception | Error e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private CardPanelAttributes getAttributes() {
        return new CardPanelAttributes(getCardWidth(), getCardHeight(), isChoosable(), isSelected(), isTransformed());
    }

    /**
     * Render the card to a new BufferedImage at it's current dimensions
     *
     * @return image
     */
    private BufferedImage renderCard() {
        int cardWidth = getCardWidth();
        int cardHeight = getCardHeight();

        // Create image to render to
        BufferedImage image
                = GraphicsUtilities.createCompatibleTranslucentImage(cardWidth, cardHeight);
        Graphics2D g2 = image.createGraphics();
        try {
            // Render with Antialialsing
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            // Draw card itself
            cardRenderer.draw(g2, getAttributes(), image);
        } finally {
            g2.dispose();
        }

        return image;
    }
}
