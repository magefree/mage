

package mage.game.events;

import java.io.Serializable;

/**
 *
 * @author BetaSteward_at_googlemail.com
 * @param <E>
 */
public interface EventSource<E extends ExternalEvent> extends Serializable {

    void addListener(Listener<E> listener);
    
    void removeAllListener();
}
