package mage.abilities.common;

import mage.Constants;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;

public class AttacksOrBlocksTriggeredAbility extends TriggeredAbilityImpl<AttacksOrBlocksTriggeredAbility> {
    public AttacksOrBlocksTriggeredAbility(Effect effect, boolean optional) {
		super(Constants.Zone.BATTLEFIELD, effect, optional);
	}

    public AttacksOrBlocksTriggeredAbility(final AttacksOrBlocksTriggeredAbility ability) {
		super(ability);
	}

    @Override
    public AttacksOrBlocksTriggeredAbility copy() {
        return new AttacksOrBlocksTriggeredAbility(this);
    }

    @Override
	public String getRule() {
		return "When {this} attacks or blocks, " + super.getRule();
	}

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
         if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED || event.getType() == GameEvent.EventType.BLOCKER_DECLARED) {
            if (event.getSourceId().equals(this.getSourceId())) {
                return true;
            }
        }
        return false;
    }
}
