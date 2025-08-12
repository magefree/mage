package mage.abilities.keyword;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.util.CardUtil;

/**
 * TODO: Implement properly
 *
 * @author TheElk801
 */
public class WaterbendAbility extends SimpleActivatedAbility {

    public WaterbendAbility(Effect effect, Cost cost) {
        this(Zone.BATTLEFIELD, effect, cost);
    }

    public WaterbendAbility(Zone zone, Effect effect, Cost cost) {
        super(zone, effect, cost);
    }

    private WaterbendAbility(final WaterbendAbility ability) {
        super(ability);
    }

    @Override
    public WaterbendAbility copy() {
        return new WaterbendAbility(this);
    }

    @Override
    public String getRule() {
        return "Waterbend " + CardUtil.getTextWithFirstCharUpperCase(super.getRule());
    }
}
