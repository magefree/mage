
package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterBasicLandCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class PerilousLandscape extends CardImpl {

    private static final FilterCard filter = new FilterBasicLandCard("a basic Island, Mountain, or Plains card");

    static {
        filter.add(Predicates.or(
                SubType.ISLAND.getPredicate(),
                SubType.MOUNTAIN.getPredicate(),
                SubType.PLAINS.getPredicate()
        ));
    }

    public PerilousLandscape(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}, Sacrifice Perilous Landscape: Search your library for a basic Island, Mountain, or Plains card, put it onto the battlefield tapped, then shuffle.
        Ability ability = new SimpleActivatedAbility(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), true),
                new TapSourceCost()
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

        // Cycling {U}{R}{W}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{U}{R}{W}")));
    }

    private PerilousLandscape(final PerilousLandscape card) {
        super(card);
    }

    @Override
    public PerilousLandscape copy() {
        return new PerilousLandscape(this);
    }
}
