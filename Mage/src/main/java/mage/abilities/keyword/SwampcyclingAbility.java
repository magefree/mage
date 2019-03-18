

package mage.abilities.keyword;

import mage.abilities.costs.mana.ManaCosts;
import mage.constants.SubType;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author Plopman
 */
public class SwampcyclingAbility extends CyclingAbility{
    
    private static final FilterLandCard filter = new FilterLandCard("Swamp card");
    private static final String text = "Swampcycling";
    static{
        filter.add(new SubtypePredicate(SubType.SWAMP));
    }
    public SwampcyclingAbility(ManaCosts costs) {
        super(costs, filter, text);
    }

    public SwampcyclingAbility(final SwampcyclingAbility ability) {
        super(ability);
    }

    @Override
    public SwampcyclingAbility copy() {
        return new SwampcyclingAbility(this);
    }
}
