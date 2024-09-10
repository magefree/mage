package mage.abilities.common.delayed;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author jeffwadsworth
 */
public class AtTheBeginOfYourNextDrawStepDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public AtTheBeginOfYourNextDrawStepDelayedTriggeredAbility(Effect effect) {
        this(effect, Duration.Custom, true);
    }

    public AtTheBeginOfYourNextDrawStepDelayedTriggeredAbility(Effect effect, Duration duration, boolean triggerOnlyOnce) {
        super(effect, duration, triggerOnlyOnce);
    }

    public AtTheBeginOfYourNextDrawStepDelayedTriggeredAbility(AtTheBeginOfYourNextDrawStepDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AtTheBeginOfYourNextDrawStepDelayedTriggeredAbility copy() {
        return new AtTheBeginOfYourNextDrawStepDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.controllerId);
    }
}
