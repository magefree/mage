package org.mage.card.arcane;

import mage.view.CardView;

/**
 * Created by StravantUser on 2017-03-30.
 */
public class CardRendererFactory {
    public CardRendererFactory() {

    }

    public CardRenderer create(CardView card, boolean isTransformed) {
        if (card.isSplitCard()) {
            return new ModernSplitCardRenderer(card, isTransformed);
        } else {
            return new ModernCardRenderer(card, isTransformed);
        }
    }
}
