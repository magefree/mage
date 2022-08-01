
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;

/**
 * @author LevelX2
 */
public class EntersBattlefieldOrLeavesSourceTriggeredAbility extends TriggeredAbilityImpl {

    public EntersBattlefieldOrLeavesSourceTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setLeavesTheBattlefieldTrigger(true);
        setTriggerPhrase("When {this} enters or leaves the battlefield, ");
    }

    public EntersBattlefieldOrLeavesSourceTriggeredAbility(final EntersBattlefieldOrLeavesSourceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EntersBattlefieldOrLeavesSourceTriggeredAbility copy() {
        return new EntersBattlefieldOrLeavesSourceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD || event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                && event.getTargetId().equals(getSourceId())) {
            return true;
        }
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && event.getTargetId().equals(this.getSourceId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
                return true;
            }
        }
        return false;
    }
}
