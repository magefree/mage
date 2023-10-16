package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author TheElk801
 */
public class ChaosEnsuesTriggeredAbility extends TriggeredAbilityImpl {

    public ChaosEnsuesTriggeredAbility(Effect effect) {
        super(Zone.COMMAND, effect);
        this.setTriggerPhrase("Whenever chaos ensues, ");
    }

    private ChaosEnsuesTriggeredAbility(final ChaosEnsuesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ChaosEnsuesTriggeredAbility copy() {
        return new ChaosEnsuesTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CHAOS_ENSUES;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }
}
