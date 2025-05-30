package mage.game.events;

import java.util.Collection;
import java.util.UUID;

/**
 * @author jimga150
 */
public class LifeLostBatchEvent extends BatchEvent<LifeLostEvent> {

    public LifeLostBatchEvent(LifeLostEvent firstEvent) {
        super(EventType.LOST_LIFE_BATCH, false, false, false, firstEvent);
    }

    public static int getLifeLostByPlayer(Collection<LifeLostEvent> events, UUID playerId) {
        return events.stream()
                .filter(ev -> ev.getTargetId().equals(playerId))
                .mapToInt(GameEvent::getAmount)
                .sum();
    }

}
