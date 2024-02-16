package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author Susucr
 */
public class SourcePhaseOutTriggeredAbility extends TriggeredAbilityImpl {

    public SourcePhaseOutTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("Whenever {this} phases out, ");
        setWorksPhasedOut(true);
    }

    protected SourcePhaseOutTriggeredAbility(final SourcePhaseOutTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SourcePhaseOutTriggeredAbility copy() {
        return new SourcePhaseOutTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PHASED_OUT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return getSourceId().equals(event.getTargetId());
    }
}
