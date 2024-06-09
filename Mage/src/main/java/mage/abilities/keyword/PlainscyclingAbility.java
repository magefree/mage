

package mage.abilities.keyword;

import mage.abilities.costs.mana.ManaCosts;
import mage.constants.SubType;
import mage.filter.common.FilterLandCard;

/**
 * @author Loki
 */
public class PlainscyclingAbility extends CyclingAbility {
    private static final FilterLandCard filter = new FilterLandCard("Plains card");
    private static final String text = "Plainscycling";

    static {
        filter.add(SubType.PLAINS.getPredicate());
    }

    public PlainscyclingAbility(ManaCosts costs) {
        super(costs, filter, text);
    }

    protected PlainscyclingAbility(final PlainscyclingAbility ability) {
        super(ability);
    }

    @Override
    public PlainscyclingAbility copy() {
        return new PlainscyclingAbility(this);
    }
}
