

package mage.game.events;

import java.io.Serializable;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
@FunctionalInterface
public interface Listener<E extends ExternalEvent> extends Serializable {
    void event(E event);
}
