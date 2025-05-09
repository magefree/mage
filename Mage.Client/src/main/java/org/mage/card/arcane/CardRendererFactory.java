package org.mage.card.arcane;

import mage.cards.FrameStyle;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.CardRenderMode;
import mage.view.CardView;

/**
 * @author StravantUser
 */
public class CardRendererFactory {

    public CardRendererFactory() {
    }

    public CardRenderer create(CardView card) {
        return create(card, -1);
    }

    public CardRenderer create(CardView card, int renderModeOverride) {
        if (card.isSplitCard()) {
            return new ModernSplitCardRenderer(card);
        } else if (shouldRenderRetro(card, renderModeOverride)) {
            // TODO: implement split card renderer for retro cards
            return new RetroCardRenderer(card);
        } else {
            return new ModernCardRenderer(card);
        }
    }

    private static boolean shouldRenderRetro(CardView card, int renderModeOverride) {
        int renderMode = PreferencesDialog.getRenderMode();
        if (renderModeOverride != -1) {
            renderMode = renderModeOverride;
        }
        boolean renderMTGO = (card.getFrameStyle().equals(FrameStyle.RETRO) || card.getFrameStyle().equals(FrameStyle.LEA_ORIGINAL_DUAL_LAND_ART_BASIC)) && renderMode == CardRenderMode.MTGO.ordinal();
        boolean forcedRetro = renderMode == CardRenderMode.FORCED_RETRO.ordinal();
        return renderMTGO || forcedRetro;
    }
}
