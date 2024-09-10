
package mage.cards.f;

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
public final class ForebodingLandscape extends CardImpl {

    private static final FilterCard filter = new FilterBasicLandCard("a basic Swamp, Forest, or Island card");

    static {
        filter.add(Predicates.or(
                SubType.SWAMP.getPredicate(),
                SubType.FOREST.getPredicate(),
                SubType.ISLAND.getPredicate()
        ));
    }

    public ForebodingLandscape(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}, Sacrifice Foreboding Landscape: Search your library for a basic Swamp, Forest, or Island card, put it onto the battlefield tapped, then shuffle.
        Ability ability = new SimpleActivatedAbility(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), true),
                new TapSourceCost()
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

        // Cycling {B}{G}{U}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{B}{G}{U}")));
    }

    private ForebodingLandscape(final ForebodingLandscape card) {
        super(card);
    }

    @Override
    public ForebodingLandscape copy() {
        return new ForebodingLandscape(this);
    }
}
