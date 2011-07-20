package mage.abilities.common.delayed;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;

public class AtTheEndOfCombatDelayedTriggeredAbility extends DelayedTriggeredAbility<AtTheEndOfCombatDelayedTriggeredAbility> {
    public AtTheEndOfCombatDelayedTriggeredAbility(Effect effect) {
        super(effect);
    }

    public AtTheEndOfCombatDelayedTriggeredAbility(AtTheEndOfCombatDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AtTheEndOfCombatDelayedTriggeredAbility copy() {
        return new AtTheEndOfCombatDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.COMBAT_PHASE_POST) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At end of combat, " + modes.getText(this);
    }
}
