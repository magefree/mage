package org.mage.card.arcane;

import mage.cards.ArtRect;
import mage.cards.FrameStyle;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.CardRenderMode;
import mage.view.CardView;

/**
 * @author StravantUser, JayDi85
 */
public class CardRendererFactory {

    public CardRendererFactory() {
    }

    public CardRenderer create(CardView card) {
        return create(card, -1);
    }

    public CardRenderer create(CardView card, int renderModeOverride) {
        int renderMode = renderModeOverride == -1 ? PreferencesDialog.getRenderMode() : renderModeOverride;
        if (card.isSplitCard()) {
            return new ModernSplitCardRenderer(card);
        } else if (card.getArtRect() == ArtRect.FULL_LENGTH_LEFT || card.getArtRect() == ArtRect.FULL_LENGTH_RIGHT) {
            return new ModernCardRenderer(card);
        } else if (shouldRenderFuture(card, renderMode)) {
            return new FutureCardRenderer(card);
        } else if (shouldRenderRetro(card, renderMode)) {
            // TODO: implement split card renderer for retro cards
            return new RetroCardRenderer(card);
        } else {
            return new ModernCardRenderer(card);
        }
    }

    private static boolean shouldRenderFuture(CardView card, int renderMode) {
        boolean renderMTGO = card.getFrameStyle() == FrameStyle.FUTURE && renderMode == CardRenderMode.MTGO.getId();
        boolean forcedFuture = renderMode == CardRenderMode.FORCED_FUTURE.getId();
        return renderMTGO || forcedFuture;
    }

    private static boolean shouldRenderRetro(CardView card, int renderMode) {
        boolean renderMTGO = (card.getFrameStyle() == FrameStyle.RETRO || card.getFrameStyle() == FrameStyle.LEA_ORIGINAL_DUAL_LAND_ART_BASIC) && renderMode == CardRenderMode.MTGO.getId();
        boolean forcedRetro = renderMode == CardRenderMode.FORCED_RETRO.getId();
        return renderMTGO || forcedRetro;
    }
}
