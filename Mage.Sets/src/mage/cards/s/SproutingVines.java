
package mage.cards.s;

import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.StormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author Plopman
 */
public final class SproutingVines extends CardImpl {

    public SproutingVines(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}");


        // Search your library for a basic land card, reveal that card, and put it into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(1, 1, StaticFilters.FILTER_CARD_BASIC_LAND), true, true));
        // Storm
        this.addAbility(new StormAbility());
    }

    private SproutingVines(final SproutingVines card) {
        super(card);
    }

    @Override
    public SproutingVines copy() {
        return new SproutingVines(this);
    }
}
