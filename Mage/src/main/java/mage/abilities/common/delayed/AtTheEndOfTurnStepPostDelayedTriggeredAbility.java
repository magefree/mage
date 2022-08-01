
package mage.abilities.common.delayed;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author nantuko
 */
public class AtTheEndOfTurnStepPostDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public AtTheEndOfTurnStepPostDelayedTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public AtTheEndOfTurnStepPostDelayedTriggeredAbility(Effect effect, boolean usesStack) {
        super(effect);
        this.usesStack = usesStack;
        setTriggerPhrase("At end of turn ");
    }

    public AtTheEndOfTurnStepPostDelayedTriggeredAbility(AtTheEndOfTurnStepPostDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AtTheEndOfTurnStepPostDelayedTriggeredAbility copy() {
        return new AtTheEndOfTurnStepPostDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.END_TURN_STEP_POST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }
}
