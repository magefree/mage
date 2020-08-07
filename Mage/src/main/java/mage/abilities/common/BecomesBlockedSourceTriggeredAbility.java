
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
public class BecomesBlockedSourceTriggeredAbility extends TriggeredAbilityImpl {

    public BecomesBlockedSourceTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public BecomesBlockedSourceTriggeredAbility(final BecomesBlockedSourceTriggeredAbility ability) {
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
    public BecomesBlockedSourceTriggeredAbility copy() {
        return new BecomesBlockedSourceTriggeredAbility(this);
    }
}
