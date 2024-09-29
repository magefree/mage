package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author LevelX2
 */
public class BecomesMonarchSourceControllerTriggeredAbility extends TriggeredAbilityImpl {

    public BecomesMonarchSourceControllerTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        setTriggerPhrase("Whenever you become the monarch, ");
    }

    private BecomesMonarchSourceControllerTriggeredAbility(final BecomesMonarchSourceControllerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BECOMES_MONARCH;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getPlayerId());
    }

    @Override
    public BecomesMonarchSourceControllerTriggeredAbility copy() {
        return new BecomesMonarchSourceControllerTriggeredAbility(this);
    }
}
