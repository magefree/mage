
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author North
 */
public class BecomesBlockedTriggeredAbility extends TriggeredAbilityImpl {

    public BecomesBlockedTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public BecomesBlockedTriggeredAbility(final BecomesBlockedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATURE_BLOCKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever {this} becomes blocked, " + super.getRule();
    }

    @Override
    public BecomesBlockedTriggeredAbility copy() {
        return new BecomesBlockedTriggeredAbility(this);
    }
}
