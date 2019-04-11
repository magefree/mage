package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author jmharmon
 */

public class PhaseOutTriggeredAbility extends TriggeredAbilityImpl {

    public PhaseOutTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public PhaseOutTriggeredAbility(final PhaseOutTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PhaseOutTriggeredAbility copy() {
        return new PhaseOutTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PHASED_OUT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return getSourceId().equals(event.getTargetId());
    }

    @Override
    public String getRule() {
        return "When {this} phases out, " + super.getRule();
    }
}
