package org.mage.card.arcane;

/**
 * @author stravant@gmail.com
 * Attributes of a card panel outside of the CardView itself that the renderer
 * needs to know in order to render a card.
 * <p>
 * Any attribute added here must also be added to CardPanelRenderModeMTGO.imageKey (rendered images are cached by it).
 */
public class CardPanelAttributes {
    public final int cardWidth;
    public final int cardHeight;
    public final boolean isSelected;
    public final boolean isChoosable;
    public final boolean isTransformed;

    public CardPanelAttributes(int cardWidth, int cardHeight, boolean isChoosable, boolean isSelected, boolean isTransformed) {
        this.cardWidth = cardWidth;
        this.cardHeight = cardHeight;
        this.isChoosable = isChoosable;
        this.isSelected = isSelected;
        this.isTransformed = isTransformed;
    }
}
