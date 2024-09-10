

package mage.abilities.keyword;

import mage.abilities.costs.mana.ManaCosts;
import mage.constants.SubType;
import mage.filter.common.FilterLandCard;

/**
 * @author Plopman
 */
public class MountaincyclingAbility extends CyclingAbility {
    private static final FilterLandCard filter = new FilterLandCard("Mountain card");
    private static final String text = "Mountaincycling";

    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
    }

    public MountaincyclingAbility(ManaCosts costs) {
        super(costs, filter, text);
    }

    protected MountaincyclingAbility(final MountaincyclingAbility ability) {
        super(ability);
    }

    @Override
    public MountaincyclingAbility copy() {
        return new MountaincyclingAbility(this);
    }
}
