package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrismaticVista extends CardImpl {

    public PrismaticVista(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}, Pay 1 life, Sacrifice Prismatic Vista: Search your library for a basic land card, put it onto the battlefield, then shuffle your library.
        Ability ability = new SimpleActivatedAbility(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND)), new TapSourceCost()
        );
        ability.addCost(new PayLifeCost(1));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private PrismaticVista(final PrismaticVista card) {
        super(card);
    }

    @Override
    public PrismaticVista copy() {
        return new PrismaticVista(this);
    }
}
