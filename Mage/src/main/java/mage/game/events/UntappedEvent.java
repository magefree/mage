package mage.game.events;

import java.util.UUID;

/**
 * @author Susucr
 */
public class UntappedEvent extends GameEvent {
    public UntappedEvent(UUID targetId, UUID playerId, boolean duringUntapPhase) {
        super(EventType.UNTAPPED, targetId, null, playerId, 0, duringUntapPhase);
    }

    public boolean isAnUntapStepEvent() {
        return this.flag;
    }
}
