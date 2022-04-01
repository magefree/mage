
package mage.abilities.common;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.constants.TargetController;

/**
 *
 * @author jeffwadsworth
 */

public class ActivateOnlyByOpponentActivatedAbility extends ActivatedAbilityImpl {
    public ActivateOnlyByOpponentActivatedAbility(Zone zone, Effect effect, Cost cost) {
        super(zone, effect, cost);
        mayActivate = TargetController.OPPONENT;
    }

    public ActivateOnlyByOpponentActivatedAbility(final ActivateOnlyByOpponentActivatedAbility ability) {
        super(ability);
    }

    @Override
    public ActivateOnlyByOpponentActivatedAbility copy() {
        return new ActivateOnlyByOpponentActivatedAbility(this);
    }

    @Override
    public String getRule() {
        return super.getRule() + " Only your opponents may activate this ability.";
    }
}
