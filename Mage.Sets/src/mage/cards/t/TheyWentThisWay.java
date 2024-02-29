package mage.cards.t;

import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheyWentThisWay extends CardImpl {

    public TheyWentThisWay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Search your library for a basic land card, put it onto the battlefield tapped, then shuffle. Investigate.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true
        ));
        this.getSpellAbility().addEffect(new InvestigateEffect());
    }

    private TheyWentThisWay(final TheyWentThisWay card) {
        super(card);
    }

    @Override
    public TheyWentThisWay copy() {
        return new TheyWentThisWay(this);
    }
}
