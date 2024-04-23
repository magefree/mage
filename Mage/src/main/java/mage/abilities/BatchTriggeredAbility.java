
package mage.abilities;

import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.stream.Stream;

/**
 * Batch triggers (e.g. 'When... one or more ..., ')
 * are triggers that require a little more details.
 *
 * @author Susucr
 */
public interface BatchTriggeredAbility<T extends GameEvent> extends TriggeredAbility {

    /**
     * filter a batch event into all it's sub events that are relevant.
     * <p>
     * Properly filtering is required for further analysis of trigger + event,
     * for instance for complex NUMBER_OF_TRIGGERS triggers.
     * e.g. Umezawa's Jitte + Felix Five-Boots.
     */
    public Stream<T> filterBatchEvent(GameEvent event, Game game);
}
