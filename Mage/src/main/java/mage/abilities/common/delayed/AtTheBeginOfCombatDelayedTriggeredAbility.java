package mage.abilities.common.delayed;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;

public class AtTheBeginOfCombatDelayedTriggeredAbility extends DelayedTriggeredAbility {
    public AtTheBeginOfCombatDelayedTriggeredAbility(Effect effect) {
        super(effect);
        setTriggerPhrase(" At the beginning of the next combat, ");
    }

    public AtTheBeginOfCombatDelayedTriggeredAbility(final AtTheBeginOfCombatDelayedTriggeredAbility ability) {
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
}
