package org.mage.card.arcane;

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
        } else {
            return new ModernCardRenderer(card);
        }
    }
}
