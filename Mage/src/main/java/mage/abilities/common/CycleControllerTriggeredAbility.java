package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.keyword.CyclingAbility;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;

/**
 * @author TheElk801
 */
public class CycleControllerTriggeredAbility extends TriggeredAbilityImpl {

    public CycleControllerTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public CycleControllerTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    private CycleControllerTriggeredAbility(final CycleControllerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getState().getStack().isEmpty()
                || !event.getPlayerId().equals(this.getControllerId())
                || event.getSourceId().equals(this.getSourceId())) {
            return false;
        }
        StackObject item = game.getState().getStack().getFirst();
        return item instanceof StackAbility
                && item.getStackAbility() instanceof CyclingAbility;
    }

    @Override
    public String getRule() {
        return "Whenever you cycle another card, " + super.getRule();
    }

    @Override
    public CycleControllerTriggeredAbility copy() {
        return new CycleControllerTriggeredAbility(this);
    }
}
