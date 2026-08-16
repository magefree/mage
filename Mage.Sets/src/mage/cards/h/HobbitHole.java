package mage.cards.h;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author muz
 */
public final class HobbitHole extends CardImpl {

    private static final FilterCard filter = new FilterCard("Halfling");

    static {
        filter.add(SubType.HALFLING.getPredicate());
    }

    public HobbitHole(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}, Sacrifice this land: Search your library for a basic land card, put it onto the battlefield tapped, then shuffle.
        Ability ability = new SimpleActivatedAbility(new SearchLibraryPutInPlayEffect(
            new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND_A), true
        ), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

        // Halflingcycling {4}
        this.addAbility(new CyclingAbility(new GenericManaCost(4), filter, "Halflingcycling"));
    }

    private HobbitHole(final HobbitHole card) {
        super(card);
    }

    @Override
    public HobbitHole copy() {
        return new HobbitHole(this);
    }
}
