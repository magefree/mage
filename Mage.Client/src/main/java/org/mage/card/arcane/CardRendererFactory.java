package org.mage.card.arcane;

import mage.cards.FrameStyle;
import mage.view.CardView;

/**
 * @author StravantUser
 */
public class CardRendererFactory {

    public CardRendererFactory() {
    }

    public CardRenderer create(CardView card) {
        if (card.isSplitCard()) {
            return new ModernSplitCardRenderer(card);
        } else if (card.getFrameStyle().equals(FrameStyle.RETRO)) {
            return new RetroCardRenderer(card);
        } else {
            return new ModernCardRenderer(card);
        }
    }
}
