package org.mage.card.arcane;

import com.google.common.base.Function;
import com.google.common.collect.MapMaker;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.UUID;
import mage.cards.action.ActionCallback;
import mage.client.util.ImageCaches;
import mage.constants.CardType;
import mage.view.CardView;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.graphics.GraphicsUtilities;
import static org.mage.plugins.card.constants.Constants.THUMBNAIL_SIZE_FULL;
import org.mage.plugins.card.images.ImageCache;

public class CardPanelRenderImpl extends CardPanel {
    
    private static final Logger LOGGER = Logger.getLogger(CardPanelRenderImpl.class);
    
    private static boolean cardViewEquals(CardView a, CardView b) {
        if (a == b) {
            return true;
        }
        if (!a.getName().equals(b.getName())) {
            return false;
        }
        if (!a.getPower().equals(b.getPower())) {
            return false;
        }
        if (!a.getToughness().equals(b.getToughness())) {
            return false;
        }
        if (!a.getLoyalty().equals(b.getLoyalty())) {
            return false;
        }
        if (0 != a.getColor().compareTo(b.getColor())) {
            return false;
        }
        if (!a.getCardTypes().equals(b.getCardTypes())) {
            return false;
        }
        if (!a.getSubTypes().equals(b.getSubTypes())) {
            return false;
        }
        if (!a.getSuperTypes().equals(b.getSuperTypes())) {
            return false;
        }
        if (!a.getManaCost().equals(b.getManaCost())) {
            return false;
        }
        if (!a.getRules().equals(b.getRules())) {
            return false;
        }
        if (!a.getExpansionSetCode().equals(b.getExpansionSetCode())) {
            return false;
        }
        return true;       
    }
    
    class ImageKey {
        final BufferedImage artImage;
        final int width;
        final int height;
        final boolean isChoosable;
        final boolean isSelected;
        final CardView view;
        final int hashCode;
        
        public ImageKey(CardView view, BufferedImage artImage, int width, int height, boolean isChoosable, boolean isSelected) {
            this.view = view;
            this.artImage = artImage;
            this.width = width;
            this.height = height;
            this.isChoosable = isChoosable;
            this.isSelected = isSelected;
            this.hashCode = hashCodeImpl();
        }

        private int hashCodeImpl() {
            StringBuilder sb = new StringBuilder();
            sb.append((char)(artImage != null ? 1 : 0));
            sb.append((char)width);
            sb.append((char)height);
            sb.append((char)(isSelected ? 1 : 0));
            sb.append((char)(isChoosable ? 1 : 0));
            sb.append((char)(this.view.isPlayable() ? 1 : 0));
            sb.append((char)(this.view.isCanAttack() ? 1 : 0));
            sb.append(this.view.getName());
            sb.append(this.view.getPower());
            sb.append(this.view.getToughness());
            sb.append(this.view.getLoyalty());
            sb.append(this.view.getColor().toString());
            sb.append(this.view.getExpansionSetCode());
            for (CardType type: this.view.getCardTypes()) {
                sb.append((char)type.ordinal());
            }
            for (String s: this.view.getSuperTypes()) {
                sb.append(s);
            }
            for (String s: this.view.getSubTypes()) {
                sb.append(s);
            }
            for (String s: this.view.getManaCost()) {
                sb.append(s);
            }
            for (String s: this.view.getRules()) {
                sb.append(s);
            }
            return sb.toString().hashCode();    
        }
        
        @Override
        public int hashCode() {
            return hashCode;
        }
        
        @Override
        public boolean equals(Object object) {
            // Initial checks
            if (this == object) {
                return true;
            }
            if (object == null) {
                return false;
            }
            if (!(object instanceof ImageKey)) {
                return false;
            }
            final ImageKey other = (ImageKey)object;
            
            // Compare
            if ((artImage != null) != (other.artImage != null)) {
                return false;
            }
            if (width != other.width) {
                return false;
            }
            if (height != other.height) {
                return false;
            }
            if (isChoosable != other.isChoosable) {
                return false;
            }
            if (isSelected != other.isSelected) {
                return false;
            }
            return cardViewEquals(view, other.view);
        }
    }
    
    // Map of generated images
    private final static Map<ImageKey, BufferedImage> IMAGE_CACHE = new MapMaker().softValues().makeMap();
    
    // The art image for the card, loaded in from the disk
    private BufferedImage artImage;
    
    // The rendered card image, with or without the art image loaded yet
    // = null while invalid
    private BufferedImage cardImage;
    private CardRenderer cardRenderer;
    
    public CardPanelRenderImpl(CardView newGameCard, UUID gameId, final boolean loadImage, ActionCallback callback, final boolean foil, Dimension dimension) {
        // Call to super
        super(newGameCard, gameId, loadImage, callback, foil, dimension);
        
        // Renderer
        cardRenderer = new ModernCardRenderer(gameCard, isTransformed());
        
        // Draw the parts
        initialDraw();
    }

    @Override
    public void transferResources(CardPanel panel) {
        if (panel instanceof CardPanelRenderImpl) {
            CardPanelRenderImpl impl = (CardPanelRenderImpl)panel;
            
            // Use the art image and current rendered image from the card
            artImage = impl.artImage;
            cardRenderer.setArtImage(artImage);
            cardImage = impl.cardImage;
        }
    }

    @Override
    protected void paintCard(Graphics2D g) {  
        // Render the card if we don't have an image ready to use
        if (cardImage == null) {
            // Try to get card image from cache based on our card characteristics
            ImageKey key =
                    new ImageKey(gameCard, artImage, 
                            getCardWidth(), getCardHeight(), 
                            isChoosable(), isSelected());
            cardImage = IMAGE_CACHE.get(key);
            
            // No cached copy exists? Render one and cache it
            if (cardImage == null) {
                cardImage = renderCard();
                IMAGE_CACHE.put(key, cardImage);
            }
        }
        
        // And draw the image we now have
        g.drawImage(cardImage, 0, 0, null);
    }
    
    /**
     * Render the card to a new BufferedImage at it's current dimensions
     * @return 
     */
    private BufferedImage renderCard() {
        int cardWidth = getCardWidth();
        int cardHeight = getCardHeight();
        int cardXOffset = getCardXOffset();
        int cardYOffset = getCardYOffset();

        // Create image to render to
        BufferedImage image = 
                GraphicsUtilities.createCompatibleTranslucentImage(getWidth(), getHeight());
        Graphics2D g2d = image.createGraphics();
        
        // Render with Antialialsing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw card itself
        g2d.translate(cardXOffset, cardYOffset);
        cardRenderer.draw(g2d, cardWidth - cardXOffset, cardHeight - cardYOffset);
        g2d.translate(-cardXOffset, -cardYOffset);
        
        // Done
        g2d.dispose();
        return image;
    }

    private int updateImageStamp;
    @Override
    public void updateImage() {
        // Invalidate
        artImage = null;
        cardImage = null;
        
        // Stop animation
        tappedAngle = isTapped() ? CardPanel.TAPPED_ANGLE : 0;
        flippedAngle = isFlipped() ? CardPanel.FLIPPED_ANGLE : 0;
        
        // Schedule a repaint
        repaint();

        // Submit a task to draw with the card art when it arrives
        final int stamp = ++updateImageStamp;
        Util.threadPool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    final BufferedImage srcImage;
                    if (gameCard.isFaceDown()) {
                        // Nothing to do
                        srcImage = null;
                    } else if (getCardWidth() > THUMBNAIL_SIZE_FULL.width) {
                        srcImage = ImageCache.getImage(gameCard, getCardWidth(), getCardHeight());
                    } else {
                        srcImage = ImageCache.getThumbnail(gameCard);
                    }
                    UI.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            if (stamp == updateImageStamp) {
                                CardPanelRenderImpl.this.artImage = srcImage;
                                CardPanelRenderImpl.this.cardRenderer.setArtImage(srcImage);
                                if (srcImage != null) {
                                    // Invalidate and repaint
                                    CardPanelRenderImpl.this.cardImage = null;
                                    repaint();
                                }
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } catch (Error err) {
                    err.printStackTrace();
                }
            }
        });
    }
    
    @Override
    public void update(CardView card) {
        // Update super
        super.update(card);
        
        // Update renderer
        cardRenderer = new ModernCardRenderer(gameCard, isTransformed());
        
        // Repaint
        repaint();
    }
    
    @Override
    public void setCardBounds(int x, int y, int cardWidth, int cardHeight) {
        super.setCardBounds(x, y, cardWidth, cardHeight);
        
        // Rerender
        cardImage = null;
    }

    @Override
    public Image getImage() {
        // Render impl never returns a card image
        return artImage;
    }

    @Override
    public void showCardTitle() {
        // Nothing to do, rendered cards always have a title
    }
}