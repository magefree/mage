

package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;

/**
 *
 * @author LevelX2
 */

public class EntersBattlefieldOrDiesSourceTriggeredAbility extends TriggeredAbilityImpl {

    public EntersBattlefieldOrDiesSourceTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public EntersBattlefieldOrDiesSourceTriggeredAbility(final EntersBattlefieldOrDiesSourceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EntersBattlefieldOrDiesSourceTriggeredAbility copy() {
        return new EntersBattlefieldOrDiesSourceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD || event.getType() == GameEvent.EventType.ZONE_CHANGE ;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                && event.getTargetId().equals(getSourceId())) {
            return true;
        }
        if (event.getType() == EventType.ZONE_CHANGE && event.getTargetId().equals(this.getSourceId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD && zEvent.getToZone() == Zone.GRAVEYARD) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When {this} enters the battlefield or dies, " + super.getRule();
    }
}
