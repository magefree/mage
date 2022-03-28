package mage.abilities.common;

import mage.abilities.effects.Effect;
import mage.abilities.keyword.CyclingAbility;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.StackObject;

/**
 * @author TheElk801
 */
public class CycleOrDiesTriggeredAbility extends ZoneChangeTriggeredAbility {

    public CycleOrDiesTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.ALL, effect, "When you cycle {this} or it dies, ", optional);
    }

    public CycleOrDiesTriggeredAbility(CycleOrDiesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE
                || event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getSourceId().equals(this.getSourceId())) {
            return false;
        }
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            return ((ZoneChangeEvent) event).isDiesEvent();
        }
        StackObject object = game.getStack().getStackObject(event.getSourceId());
        return object != null && object.getStackAbility() instanceof CyclingAbility;
    }

    @Override
    public CycleOrDiesTriggeredAbility copy() {
        return new CycleOrDiesTriggeredAbility(this);
    }
}
