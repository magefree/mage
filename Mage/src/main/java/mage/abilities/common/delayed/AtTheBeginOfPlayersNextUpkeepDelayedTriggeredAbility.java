package mage.abilities.common.delayed;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 *
 * @author xenohedron
 */
public class AtTheBeginOfPlayersNextUpkeepDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final UUID playerId;

    public AtTheBeginOfPlayersNextUpkeepDelayedTriggeredAbility(Effect effect, UUID playerId) {
        this(effect, playerId, Duration.Custom, true);
        this.setTriggerPhrase("At the beginning of their next upkeep, ");
    }

    public AtTheBeginOfPlayersNextUpkeepDelayedTriggeredAbility(Effect effect, UUID playerID, Duration duration, boolean triggerOnlyOnce) {
        super(effect, duration, triggerOnlyOnce);
        this.playerId = playerID;
    }

    public AtTheBeginOfPlayersNextUpkeepDelayedTriggeredAbility(AtTheBeginOfPlayersNextUpkeepDelayedTriggeredAbility ability) {
        super(ability);
        this.playerId = ability.playerId;
    }

    @Override
    public AtTheBeginOfPlayersNextUpkeepDelayedTriggeredAbility copy() {
        return new AtTheBeginOfPlayersNextUpkeepDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.playerId);
    }
}
