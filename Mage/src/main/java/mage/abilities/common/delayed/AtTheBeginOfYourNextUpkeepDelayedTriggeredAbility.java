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
public class AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility(Effect effect) {
        this(effect, Duration.Custom, true);
    }

    public AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility(Effect effect, Duration duration, boolean triggerOnlyOnce) {
        super(effect, duration, triggerOnlyOnce);
    }

    public AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility(AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility copy() {
        return new AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.controllerId);
    }
}
