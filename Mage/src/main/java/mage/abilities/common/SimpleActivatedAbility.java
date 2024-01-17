package mage.abilities.common;

import mage.constants.Outcome;
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
        this(null, Zone.BATTLEFIELD, effect, cost);
    }

    public SimpleActivatedAbility(Zone zone, Effect effect, Cost cost) {
        this(null, zone, effect, cost);
    }

    public SimpleActivatedAbility(Outcome outcome, Zone zone, Effect effect, Cost cost) {
        super(outcome, zone, effect, cost);
    }

    protected SimpleActivatedAbility(final SimpleActivatedAbility ability) {
        super(ability);
    }

    @Override
    public SimpleActivatedAbility copy() {
        return new SimpleActivatedAbility(this);
    }

}
