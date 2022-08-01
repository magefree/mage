package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 * @author jeffwadsworth
 */
public class ControllerPlaysLandTriggeredAbility extends TriggeredAbilityImpl {

    public ControllerPlaysLandTriggeredAbility(Zone zone, Effect effect, Boolean optional) {
        super(zone, effect, optional);
        setTriggerPhrase("Whenever you play a land, ");
    }

    public ControllerPlaysLandTriggeredAbility(ControllerPlaysLandTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LAND_PLAYED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent land = game.getPermanent(event.getTargetId());
        return land != null && land.getControllerId().equals(controllerId);
    }

    @Override
    public ControllerPlaysLandTriggeredAbility copy() {
        return new ControllerPlaysLandTriggeredAbility(this);
    }
}
