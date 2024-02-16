package mage.game.events;

import java.util.Set;
import java.util.UUID;

/**
 * Game event with batch support (batch is an event that can contain multiple events, example: DAMAGED_BATCH_FOR_PLAYERS)
 * <p>
 * Used by game engine to support event lifecycle for triggers
 *
 * @author JayDi85
 */
public interface BatchGameEvent<T extends GameEvent> {

    Set<T> getEvents();

    Set<UUID> getTargets();

}
