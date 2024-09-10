

package mage.abilities.keyword;

import mage.abilities.costs.mana.ManaCosts;
import mage.filter.StaticFilters;

/**
 * @author Loki
 */
public class BasicLandcyclingAbility extends CyclingAbility {
    private static final String text = "Basic landcycling";

    public BasicLandcyclingAbility(ManaCosts costs) {
        super(costs, StaticFilters.FILTER_CARD_BASIC_LAND, text);
    }

    protected BasicLandcyclingAbility(final BasicLandcyclingAbility ability) {
        super(ability);
    }

    @Override
    public BasicLandcyclingAbility copy() {
        return new BasicLandcyclingAbility(this);
    }
}
