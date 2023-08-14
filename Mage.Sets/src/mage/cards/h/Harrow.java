package mage.cards.h;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Viserion
 */
public final class Harrow extends CardImpl {

    public Harrow(UUID ownerId, CardSetInfo setInfo){
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}");
        this.color.setGreen(true);        

        // As an additional cost to cast Harrow, sacrifice a land.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_LAND_SHORT_TEXT)));

        // Search your library for up to two basic land cards and put them onto the battlefield. Then shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(0, 2, StaticFilters.FILTER_CARD_BASIC_LANDS);
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(target, false));
    }

    private Harrow(final Harrow card) {
        super(card);
    }

    @Override
    public Harrow copy() {
        return new Harrow(this);
    }
}
