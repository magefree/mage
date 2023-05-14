
package mage.cards.d;

import mage.MageObject;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Styxo
 */
public final class DroidFactory extends CardImpl {

    public DroidFactory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {T}: Add {C} to your manapool.
        this.addAbility(new ColorlessManaAbility());

        // {T}, Sacrifice Droid Factor: Search your library for a basic Plains, Island or Swamp card and put it onto the battlefield tapped. Then shuffle your library.
        this.addAbility(new DroidFactoryAbility());

    }

    private DroidFactory(final DroidFactory card) {
        super(card);
    }

    @Override
    public DroidFactory copy() {
        return new DroidFactory(this);
    }

    public final class DroidFactoryAbility extends ActivatedAbilityImpl {

        public DroidFactoryAbility(DroidFactoryAbility ability) {
            super(ability);
        }

        public DroidFactoryAbility() {
            super(Zone.BATTLEFIELD, null);
            addCost(new TapSourceCost());
            addCost(new SacrificeSourceCost());
            FilterCard filter = new FilterCard("basic Plains, Island or Swamp");
            filter.add(CardType.LAND.getPredicate());
            List<Predicate<MageObject>> subtypePredicates = new ArrayList<>();
            subtypePredicates.add(SubType.SWAMP.getPredicate());
            subtypePredicates.add(SubType.PLAINS.getPredicate());
            subtypePredicates.add(SubType.ISLAND.getPredicate());
            filter.add(Predicates.or(subtypePredicates));
            filter.add(SuperType.BASIC.getPredicate());
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            addEffect(new SearchLibraryPutInPlayEffect(target, true));
        }

        @Override
        public DroidFactoryAbility copy() {
            return new DroidFactoryAbility(this);
        }
    }
}
