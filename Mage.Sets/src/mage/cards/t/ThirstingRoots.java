package mage.cards.t;

import mage.abilities.Mode;
import mage.abilities.effects.common.counter.ProliferateEffect;
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
public final class ThirstingRoots extends CardImpl {

    public ThirstingRoots(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Choose one--
        // * Search your library for a basic land card, reveal it, put it into your hand, then shuffle.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true
        ));

        // * Proliferate.
        this.getSpellAbility().addMode(new Mode(new ProliferateEffect()));
    }

    private ThirstingRoots(final ThirstingRoots card) {
        super(card);
    }

    @Override
    public ThirstingRoots copy() {
        return new ThirstingRoots(this);
    }
}
