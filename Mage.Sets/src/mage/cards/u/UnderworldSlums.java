package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author Styxo
 */
public final class UnderworldSlums extends CardImpl {

    private static final FilterCard filter = new FilterCard("basic Swamp, Mountain, or Forest card");
    static {
        filter.add(SuperType.BASIC.getPredicate());
        filter.add(Predicates.or(
                SubType.SWAMP.getPredicate(),
                SubType.MOUNTAIN.getPredicate(),
                SubType.FOREST.getPredicate()
        ));
    }

    public UnderworldSlums(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {T}: Add {C} to your manapool.
        this.addAbility(new ColorlessManaAbility());

        // {T}, Sacrifice Underworld Slums: Search your library for a basic Swamp, Mountain or Forest card and put it onto the battlefield tapped. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), true), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

    }

    private UnderworldSlums(final UnderworldSlums card) {
        super(card);
    }

    @Override
    public UnderworldSlums copy() {
        return new UnderworldSlums(this);
    }

}
