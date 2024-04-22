package mage.game.events;

import java.util.UUID;

/**
 * @author jimga150
 */
public class LifeLostBatchEvent extends BatchEvent<LifeLostEvent> {

    public LifeLostBatchEvent(LifeLostEvent firstEvent) {
        super(EventType.LOST_LIFE_BATCH, false, firstEvent);
    }

    public int getLifeLostByPlayer(UUID playerID) {
        return getEvents()
                .stream()
                .filter(ev -> ev.getTargetId().equals(playerID))
                .mapToInt(GameEvent::getAmount)
                .sum();
    }

}
