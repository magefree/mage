package mage.abilities.common;

import mage.constants.Zone;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SimpleActivatedAbility extends ActivatedAbilityImpl {

    public SimpleActivatedAbility(Effect effect, Cost cost) {
        this(Zone.BATTLEFIELD, effect, cost);
    }

    public SimpleActivatedAbility(Zone zone, Effect effect, Cost cost) {
        super(zone, effect, cost);
    }

    protected SimpleActivatedAbility(final SimpleActivatedAbility ability) {
        super(ability);
    }

    @Override
    public SimpleActivatedAbility copy() {
        return new SimpleActivatedAbility(this);
    }

}
