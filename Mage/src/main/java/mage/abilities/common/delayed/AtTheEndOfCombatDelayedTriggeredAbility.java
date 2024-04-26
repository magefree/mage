package mage.abilities.common.delayed;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

public class AtTheEndOfCombatDelayedTriggeredAbility extends DelayedTriggeredAbility {
    public AtTheEndOfCombatDelayedTriggeredAbility(Effect effect) {
        super(effect);
        setTriggerPhrase("At end of combat, ");
    }

    public AtTheEndOfCombatDelayedTriggeredAbility(AtTheEndOfCombatDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AtTheEndOfCombatDelayedTriggeredAbility copy() {
        return new AtTheEndOfCombatDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.END_COMBAT_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }

    @Override
    public void trigger(Game game, UUID controllerId, GameEvent triggeringEvent) {
        System.out.println("delayed trigger zcc: " + this.getSourceObjectZoneChangeCounter());
        System.out.println("delayed trigger id: " + this.getSourceId() + " " + this);
        super.trigger(game, controllerId, triggeringEvent);
    }
}
