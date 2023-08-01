
package mage.abilities.common;

import mage.constants.Zone;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.Effect;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SimpleActivatedAbility extends ActivatedAbilityImpl {

    public SimpleActivatedAbility(Effect effect, ManaCosts cost) {
        super(Zone.BATTLEFIELD, effect, cost);
    }

    public SimpleActivatedAbility(Effect effect, Cost cost) {
        super(Zone.BATTLEFIELD, effect, cost);
    }

    public SimpleActivatedAbility(Zone zone, Effect effect, ManaCosts cost) {
        super(zone, effect, cost);
    }

    public SimpleActivatedAbility(Zone zone, Effect effect, Costs<Cost> costs) {
        super(zone, effect, costs);
    }

    public SimpleActivatedAbility(Zone zone, Effect effect, Cost cost) {
        super(zone, effect, cost);
    }

    protected SimpleActivatedAbility(SimpleActivatedAbility ability) {
        super(ability);
    }

    @Override
    public SimpleActivatedAbility copy() {
        return new SimpleActivatedAbility(this);
    }

}
