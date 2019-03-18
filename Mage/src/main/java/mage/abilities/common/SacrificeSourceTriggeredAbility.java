

package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */

public class SacrificeSourceTriggeredAbility extends TriggeredAbilityImpl {

    public SacrificeSourceTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.ALL, effect, optional);
    }

    public SacrificeSourceTriggeredAbility(final SacrificeSourceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SacrificeSourceTriggeredAbility copy() {
        return new SacrificeSourceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
         return event.getTargetId().equals(sourceId);
    }

    @Override
    public String getRule() {
        return "When you sacrifice {this}, " + super.getRule();
    }
}
