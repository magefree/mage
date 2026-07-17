package mage.cards.t;

import java.util.UUID;

import mage.abilities.effects.common.search.SearchLibraryPutInPlayTargetPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author muz
 */
public final class TurtleTracks extends CardImpl {

    public TurtleTracks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Any number of target players may each search their library for a basic land card, put it onto the battlefield under their control, then shuffle.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayTargetPlayerEffect(
            new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), false, false, true
        ).setText("Any number of target players may each search their library for a basic land card, put it onto the battlefield under their control, then shuffle."));
        this.getSpellAbility().addTarget(new TargetPlayer(0, Integer.MAX_VALUE, false));
    }

    private TurtleTracks(final TurtleTracks card) {
        super(card);
    }

    @Override
    public TurtleTracks copy() {
        return new TurtleTracks(this);
    }
}
