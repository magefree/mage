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
public class SourcePhaseInTriggeredAbility extends TriggeredAbilityImpl {

    public SourcePhaseInTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("When {this} phases in, ");
    }

    public SourcePhaseInTriggeredAbility(final SourcePhaseInTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SourcePhaseInTriggeredAbility copy() {
        return new SourcePhaseInTriggeredAbility(this);
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
