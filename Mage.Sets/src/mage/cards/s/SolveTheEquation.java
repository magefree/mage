package mage.cards.s;

import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SolveTheEquation extends CardImpl {

    public SolveTheEquation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Search your library for an instant or sorcery card, reveal it, put it into your hand, then shuffle.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY), true
        ));
    }

    private SolveTheEquation(final SolveTheEquation card) {
        super(card);
    }

    @Override
    public SolveTheEquation copy() {
        return new SolveTheEquation(this);
    }
}
