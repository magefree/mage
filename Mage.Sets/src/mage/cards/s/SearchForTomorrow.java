
package mage.cards.s;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class SearchForTomorrow extends CardImpl {

    public SearchForTomorrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}");


        // Search your library for a basic land card and put it onto the battlefield. Then shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND);
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(target, false, Outcome.PutLandInPlay));
        
        // Suspend 2-{G}
        this.addAbility(new SuspendAbility(2, new ManaCostsImpl<>("{G}"), this));
    }

    private SearchForTomorrow(final SearchForTomorrow card) {
        super(card);
    }

    @Override
    public SearchForTomorrow copy() {
        return new SearchForTomorrow(this);
    }
}
