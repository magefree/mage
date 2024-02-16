

package mage.abilities.keyword;

import mage.abilities.costs.mana.ManaCosts;
import mage.constants.SubType;
import mage.filter.common.FilterLandCard;

/**
 * @author Plopman
 */
public class ForestcyclingAbility extends CyclingAbility {
    private static final FilterLandCard filter = new FilterLandCard("Forest card");
    private static final String text = "Forestcycling";

    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public ForestcyclingAbility(ManaCosts costs) {
        super(costs, filter, text);
    }

    protected ForestcyclingAbility(final ForestcyclingAbility ability) {
        super(ability);
    }

    @Override
    public ForestcyclingAbility copy() {
        return new ForestcyclingAbility(this);
    }
}
