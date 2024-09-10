

package mage.abilities.keyword;

import mage.abilities.costs.mana.ManaCosts;
import mage.constants.SubType;
import mage.filter.common.FilterLandCard;

/**
 * @author Plopman
 */
public class SwampcyclingAbility extends CyclingAbility {

    private static final FilterLandCard filter = new FilterLandCard("Swamp card");
    private static final String text = "Swampcycling";

    static {
        filter.add(SubType.SWAMP.getPredicate());
    }

    public SwampcyclingAbility(ManaCosts costs) {
        super(costs, filter, text);
    }

    protected SwampcyclingAbility(final SwampcyclingAbility ability) {
        super(ability);
    }

    @Override
    public SwampcyclingAbility copy() {
        return new SwampcyclingAbility(this);
    }
}
