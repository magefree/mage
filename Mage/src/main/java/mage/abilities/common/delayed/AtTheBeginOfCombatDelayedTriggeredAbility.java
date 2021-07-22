package mage.abilities.common.delayed;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;

public class AtTheBeginOfCombatDelayedTriggeredAbility extends DelayedTriggeredAbility {
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
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COMBAT_PHASE_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }

    @Override
    public String getTriggerPhrase() {
        return " At the beginning of the next combat, ";
    }
}
