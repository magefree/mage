/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility extends DelayedTriggeredAbility<AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility> {

    public AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility(Effect effect) {
        this(effect, Duration.Custom, true);
    }

    public AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility(Effect effect, Duration duration, Boolean triggerOnlyOnce) {
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
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE && event.getPlayerId().equals(this.controllerId);
    }
}

