
package mage.cards.s;

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
public final class SithRuins extends CardImpl {

    public SithRuins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {T}: Add {C} to your manapool.
        this.addAbility(new ColorlessManaAbility());

        // {T}, Sacrifice Sith Ruins: Search your library for a basic Island, Swamp or Mountain card and put it onto the battlefield tapped. Then shuffle your library.
        this.addAbility(new SithRuinsAbility());

    }

    private SithRuins(final SithRuins card) {
        super(card);
    }

    @Override
    public SithRuins copy() {
        return new SithRuins(this);
    }

    public static class SithRuinsAbility extends ActivatedAbilityImpl {

        public SithRuinsAbility(SithRuinsAbility ability) {
            super(ability);
        }

        public SithRuinsAbility() {
            super(Zone.BATTLEFIELD, null);
            addCost(new TapSourceCost());
            addCost(new SacrificeSourceCost());
            FilterCard filter = new FilterCard("basic Island, Swamp or Mountain");
            filter.add(CardType.LAND.getPredicate());
            List<Predicate<MageObject>> subtypePredicates = new ArrayList<>();
            subtypePredicates.add(SubType.SWAMP.getPredicate());
            subtypePredicates.add(SubType.MOUNTAIN.getPredicate());
            subtypePredicates.add(SubType.ISLAND.getPredicate());
            filter.add(Predicates.or(subtypePredicates));
            filter.add(SuperType.BASIC.getPredicate());
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            addEffect(new SearchLibraryPutInPlayEffect(target, true));
        }

        @Override
        public SithRuinsAbility copy() {
            return new SithRuinsAbility(this);
        }
    }
}
