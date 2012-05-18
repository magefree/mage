package mage.abilities.common;

import mage.Constants;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * Beginning of controlled end step triggered ability
 * @author Loki
 */
public class BeginningOfYourEndStepTriggeredAbility extends TriggeredAbilityImpl<BeginningOfYourEndStepTriggeredAbility> {
    public BeginningOfYourEndStepTriggeredAbility(Effect effect, boolean optional) {
        super(Constants.Zone.BATTLEFIELD, effect, optional);
    }

    public BeginningOfYourEndStepTriggeredAbility(final BeginningOfYourEndStepTriggeredAbility ability) {
        super(ability);
    }


    @Override
    public BeginningOfYourEndStepTriggeredAbility copy() {
        return new BeginningOfYourEndStepTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.END_PHASE_PRE && event.getPlayerId().equals(this.controllerId)) {
			return true;
		}
		return false;
    }

    @Override
    public String getRule() {
        if (optional) {
            return "At the beginning of your end step, you may " + modes.getText();
        }
        return "At the beginning of your end step, " + modes.getText();
    }
}
