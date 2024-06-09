

package mage.cards.r;

import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author LokiX
 */
public final class RampantGrowth extends CardImpl {

    public RampantGrowth(UUID ownerId, CardSetInfo setInfo){
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}");

        // Search your library for a basic land card, put it onto the battlefield tapped, then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true, true));
    }

    private RampantGrowth(final RampantGrowth card) {
        super(card);
    }

    @Override
    public RampantGrowth copy() {
        return new RampantGrowth(this);
    }
}
