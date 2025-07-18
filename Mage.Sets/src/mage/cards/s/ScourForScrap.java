package mage.cards.s;

import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScourForScrap extends CardImpl {

    public ScourForScrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Choose one or both —
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // • Search your library for an artifact card, reveal it, put it into your hand, then shuffle.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_ARTIFACT_AN), true
        ));

        // • Return target artifact card from your graveyard to your hand.
        Mode mode = new Mode(new ReturnFromGraveyardToHandTargetEffect());
        mode.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_ARTIFACT_FROM_YOUR_GRAVEYARD));
        this.getSpellAbility().addMode(mode);
    }

    private ScourForScrap(final ScourForScrap card) {
        super(card);
    }

    @Override
    public ScourForScrap copy() {
        return new ScourForScrap(this);
    }
}
