package org.mage.card.arcane;

import mage.cards.FrameStyle;
import mage.client.dialog.PreferencesDialog;
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
        } else if (card.getFrameStyle().equals(FrameStyle.RETRO)  || card.getFrameStyle().equals(FrameStyle.LEA_ORIGINAL_DUAL_LAND_ART_BASIC) || PreferencesDialog.getRenderRetroFrames()) {
            return new RetroCardRenderer(card);
        } else {
            return new ModernCardRenderer(card);
        }
    }
}
