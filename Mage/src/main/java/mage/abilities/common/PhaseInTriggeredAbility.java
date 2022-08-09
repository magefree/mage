package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author TheElk801
 */
public class PhaseInTriggeredAbility extends TriggeredAbilityImpl {

    public PhaseInTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("When {this} phases in, ");
    }

    public PhaseInTriggeredAbility(final PhaseInTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PhaseInTriggeredAbility copy() {
        return new PhaseInTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PHASED_IN;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return getSourceId().equals(event.getTargetId());
    }
}
