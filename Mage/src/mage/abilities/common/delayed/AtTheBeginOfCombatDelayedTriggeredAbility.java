package mage.abilities.common.delayed;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;

public class AtTheBeginOfCombatDelayedTriggeredAbility extends DelayedTriggeredAbility<AtTheBeginOfCombatDelayedTriggeredAbility> {
    public AtTheBeginOfCombatDelayedTriggeredAbility(Effect effect) {
        super(effect);
    }

    public AtTheBeginOfCombatDelayedTriggeredAbility(AtTheBeginOfCombatDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AtTheBeginOfCombatDelayedTriggeredAbility copy() {
        return new AtTheBeginOfCombatDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.COMBAT_PHASE_PRE) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return " At the beginning of the next combat, " + modes.getText();
    }
}
