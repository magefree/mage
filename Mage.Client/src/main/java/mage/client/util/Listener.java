
package mage.client.util;

import java.io.Serializable;

/**
 *
 * @author BetaSteward_at_googlemail.com
 * @param <E>
 */
@FunctionalInterface
public interface Listener<E extends Event> extends Serializable {

    void event(E event);
}
