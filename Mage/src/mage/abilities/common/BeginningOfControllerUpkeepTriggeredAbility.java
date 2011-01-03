package mage.abilities.common;

import mage.Constants;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;

public class BeginningOfControllerUpkeepTriggeredAbility extends TriggeredAbilityImpl<BeginningOfControllerUpkeepTriggeredAbility> {

    public BeginningOfControllerUpkeepTriggeredAbility(Effect effect, boolean isOptional) {
        super(Constants.Zone.BATTLEFIELD, effect, isOptional);
    }

    public BeginningOfControllerUpkeepTriggeredAbility(final BeginningOfControllerUpkeepTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BeginningOfControllerUpkeepTriggeredAbility copy() {
        return new BeginningOfControllerUpkeepTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE && event.getPlayerId().equals(this.controllerId)) {
			return true;
		}
		return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of your upkeep, " + effects.getText(this);
    }
}
