

package mage.abilities.keyword;

import mage.abilities.costs.mana.ManaCosts;
import mage.constants.SubType;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author Plopman
 */
public class IslandcyclingAbility extends CyclingAbility{
    private static final FilterLandCard filter = new FilterLandCard("Island card");
    private static final String text = "Islandcycling";
    static{
        filter.add(new SubtypePredicate(SubType.ISLAND));
    }
    
    public IslandcyclingAbility(ManaCosts costs) {
        super(costs, filter, text);
    }

    public IslandcyclingAbility(final IslandcyclingAbility ability) {
        super(ability);
    }

    @Override
    public IslandcyclingAbility copy() {
        return new IslandcyclingAbility(this);
    }
}
