package org.mage.card.arcane;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import mage.cards.action.ActionCallback;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.view.CardView;
import mage.view.CounterView;
import mage.view.PermanentView;
import mage.view.StackAbilityView;
import org.jdesktop.swingx.graphics.GraphicsUtilities;
import org.mage.plugins.card.images.ImageCache;
import org.mage.plugins.card.images.ImageCacheData;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Render mode: MTGO
 */
public class CardPanelRenderModeMTGO extends CardPanel {

    // Map of generated images
    private final static Cache<ImageKey, BufferedImage> IMAGE_CACHE = CacheBuilder
            .newBuilder()
            .maximumSize(3000)
            .expireAfterAccess(60, TimeUnit.MINUTES)
            .softValues()
            .build();

    // The art image for the card, loaded in from the disk
    private BufferedImage artImage;

    // The faceart image for the card, loaded in from the disk (based on artid from mtgo)
    private BufferedImage faceArtImage;

    // Factory to generate card appropriate views
    private final CardRendererFactory cardRendererFactory = new CardRendererFactory();

    // The rendered card image, with or without the art image loaded yet
    // = null while invalid
    private BufferedImage cardImage;
    private CardRenderer cardRenderer;

    private int updateArtImageStamp;

    private static boolean cardViewEquals(CardView a, CardView b) { // TODO: This belongs in CardView
        if (a == b) {
            return true;
        }
        if (a == null || b == null || a.getClass() != b.getClass()) {
            return false;
        }

        if (!(a.getDisplayName().equals(b.getDisplayName()) // TODO: Original code not checking everything. Why is it only checking these values?
                && a.getPower().equals(b.getPower())
                && a.getToughness().equals(b.getToughness())
                && a.getLoyalty().equals(b.getLoyalty())
                && 0 == a.getColor().compareTo(b.getColor())
                && a.getCardTypes().equals(b.getCardTypes())
                && a.getSubTypes().equals(b.getSubTypes())
                && a.getSuperTypes().equals(b.getSuperTypes())
                && a.getManaCostStr().equals(b.getManaCostStr())
                && a.getRules().equals(b.getRules())
                && Objects.equals(a.getRarity(), b.getRarity())
                && Objects.equals(a.getCardNumber(), b.getCardNumber())
                && Objects.equals(a.getExpansionSetCode(), b.getExpansionSetCode())
                && a.getFrameStyle() == b.getFrameStyle()
                && Objects.equals(a.getCounters(), b.getCounters())
                && a.isFaceDown() == b.isFaceDown())) {
            return false;
        }

        if (!(a instanceof PermanentView)) {
            return true;
        }
        PermanentView aa = (PermanentView) a;
        PermanentView bb = (PermanentView) b;
        return aa.hasSummoningSickness() == bb.hasSummoningSickness()
                && aa.getDamage() == bb.getDamage();
    }

    private static class ImageKey {
        final BufferedImage artImage;
        final int width;
        final int height;
        final boolean isChoosable;
        final boolean isSelected;
        final boolean isTransformed;
        final CardView view;
        final int hashCode;

        public ImageKey(CardView view, BufferedImage artImage, int width, int height, boolean isChoosable, boolean isSelected, boolean isTransformed) {
            this.view = view;
            this.artImage = artImage;
            this.width = width;
            this.height = height;
            this.isChoosable = isChoosable;
            this.isSelected = isSelected;
            this.isTransformed = isTransformed;
            this.hashCode = hashCodeImpl();
        }

        private int hashCodeImpl() { // TODO: Why is this using a string builder???
            StringBuilder sb = new StringBuilder();
            sb.append((char) (artImage != null ? 1 : 0));
            sb.append((char) width);
            sb.append((char) height);
            sb.append((char) (isSelected ? 1 : 0));
            sb.append((char) (isChoosable ? 1 : 0));
            sb.append((char) (isTransformed ? 1 : 0));
            sb.append((char) (this.view.isPlayable() ? 1 : 0));
            sb.append((char) (this.view.isCanAttack() ? 1 : 0));
            sb.append((char) (this.view.isCanBlock() ? 1 : 0));
            sb.append((char) (this.view.isFaceDown() ? 1 : 0));
            sb.append((char) this.view.getFrameStyle().ordinal());
            if (this.view instanceof PermanentView) {
                sb.append((char) (((PermanentView) this.view).hasSummoningSickness() ? 1 : 0));
                sb.append((char) (((PermanentView) this.view).getDamage()));
            }
            sb.append(this.view.getDisplayName());
            sb.append(this.view.getPower());
            sb.append(this.view.getToughness());
            sb.append(this.view.getLoyalty());
            sb.append(this.view.getColor().toString());
            sb.append(this.view.getType());
            sb.append(this.view.getExpansionSetCode());
            for (CardType type : this.view.getCardTypes()) {
                sb.append((char) type.ordinal());
            }
            for (SuperType s : this.view.getSuperTypes()) {
                sb.append(s);
            }
            for (SubType s : this.view.getSubTypes()) {
                sb.append(s);
            }
            sb.append(this.view.getManaCostStr());
            for (String s : this.view.getRules()) {
                sb.append(s);
            }
            if (this.view.getCounters() != null) {
                for (CounterView v : this.view.getCounters()) {
                    sb.append(v.getName()).append(v.getCount());
                }
            }
            return sb.toString().hashCode();
        }

        @Override
        public int hashCode() {
            return hashCode;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || this.getClass() != object.getClass()) {
                return false;
            }
            final ImageKey that = (ImageKey) object;

            return (artImage == null) == (that.artImage == null)
                    && this.width == that.width
                    && this.height == that.height
                    && this.isChoosable == that.isChoosable
                    && this.isSelected == that.isSelected
                    && cardViewEquals(this.view, that.view);
        }
    }

    public CardPanelRenderModeMTGO(CardView newGameCard, UUID gameId, final boolean loadImage, ActionCallback callback,
                                   final boolean foil, Dimension dimension, boolean needFullPermanentRender) {
        // Call to super
        super(newGameCard, gameId, loadImage, callback, foil, dimension, needFullPermanentRender);

        // Renderer
        cardRenderer = cardRendererFactory.create(getGameCard());

        // Draw the parts
        initialDraw();
    }

    @Override
    public Image getImage() {
        if (artImage == null) {
            return null;
        }
        if (getGameCard().isFaceDown()) {
            return getFaceDownImage().getImage();
        } else {
            return ImageCache.getImageOriginal(getGameCard()).getImage();
        }
    }

    @Override
    protected void paintCard(Graphics2D g) {
        // Render the card if we don't have an image ready to use
        if (cardImage == null) {
            // Try to get card image from cache based on our card characteristics
            ImageKey key = new ImageKey(getGameCard(), artImage,
                    getCardWidth(), getCardHeight(),
                    isChoosable(), isSelected(), isTransformed());
            try {
                cardImage = IMAGE_CACHE.get(key, this::renderCard);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }

            // No cached copy exists? Render one and cache it
        }

        // And draw the image we now have
        int cardOffsetX = 0;
        int cardOffsetY = 0;
        g.drawImage(cardImage, cardOffsetX, cardOffsetY, null);
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
            cardRenderer.setArtImage(artImage);
            faceArtImage = impl.faceArtImage;
            cardRenderer.setFaceArtImage(faceArtImage);
            cardImage = impl.cardImage;
        }
    }

    @Override
    public void update(CardView card) {
        // Update super
        super.update(card);

        // Update renderer
        cardImage = null;
        cardRenderer = cardRendererFactory.create(getGameCard());
        cardRenderer.setArtImage(artImage);
        cardRenderer.setFaceArtImage(faceArtImage);

        // Repaint
        repaint();
    }

    @Override
    public void updateArtImage() {
        // Invalidate
        artImage = null;
        cardImage = null;
        cardRenderer.setArtImage(null);
        cardRenderer.setFaceArtImage(null);

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
                    final BufferedImage srcImage;
                    final BufferedImage faceArtSrcImage;
                    if (getGameCard().isFaceDown()) {
                        // Nothing to do
                        srcImage = null;
                        faceArtSrcImage = null;
                    } else {
                        srcImage = ImageCache.getImage(getGameCard(), getCardWidth(), getCardHeight()).getImage();
                        faceArtSrcImage = ImageCache.getFaceImage(getGameCard(), getCardWidth(), getCardHeight()).getImage();
                    }

                    UI.invokeLater(() -> {
                        if (stamp == updateArtImageStamp) {
                            artImage = srcImage;
                            cardRenderer.setArtImage(srcImage);
                            faceArtImage = faceArtSrcImage;
                            cardRenderer.setFaceArtImage(faceArtSrcImage);

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

    private ImageCacheData getFaceDownImage() {
        // TODO: add download default images
        if (isPermanent() && getGameCard() instanceof PermanentView) {
            if (((PermanentView) getGameCard()).isMorphed()) {
                return ImageCache.getMorphImage();
            } else {
                return ImageCache.getManifestImage();
            }
        } else if (this.getGameCard() instanceof StackAbilityView) {
            return ImageCache.getMorphImage();
        } else {
            return ImageCache.getCardbackImage();
        }
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
        Graphics2D g2d = image.createGraphics();

        // Render with Antialialsing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Draw card itself
        cardRenderer.draw(g2d, getAttributes(), image);

        // Done
        g2d.dispose();
        return image;
    }
}
