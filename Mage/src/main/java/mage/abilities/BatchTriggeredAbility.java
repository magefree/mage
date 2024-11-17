package mage.abilities;

import mage.game.Game;
import mage.game.events.BatchEvent;
import mage.game.events.GameEvent;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Batch triggers (e.g. 'When... one or more ..., ')
 * require additional logic to check the events in the batch.
 * Parametrized on the individual game event type.
 *
 * @see mage.game.events.BatchEvent
 *
 * @author Susucr, xenohedron
 */
public interface BatchTriggeredAbility<T extends GameEvent> extends TriggeredAbility {

    /**
     * Some events in the batch may not be relevant to the trigger logic.
     * If so, use this method to exclude them.
     */
    default boolean checkEvent(T event, Game game) {
        return true;
    }

    /**
     * For use in checkTrigger - streams all events that pass the event check
     */
    default List<T> getFilteredEvents(BatchEvent<T> event, Game game) {
        return event.getEvents()
                .stream()
                .filter(e -> checkEvent(e, game))
                .collect(Collectors.toList());
    }
}
