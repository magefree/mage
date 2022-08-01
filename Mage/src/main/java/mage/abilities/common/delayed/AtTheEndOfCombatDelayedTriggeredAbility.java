package mage.abilities.common.delayed;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;

public class AtTheEndOfCombatDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private static final String staticTriggerPhrase = "At end of combat, ";

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
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.END_COMBAT_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }

    @Override
    public String getStaticTriggerPhrase() {
        return staticTriggerPhrase;
    }
}
