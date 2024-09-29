package mage.game.events;

import java.io.Serializable;

/**
 * TODO: there are duplicated interfaces of EventSource, must combine in single
 * @author BetaSteward_at_googlemail.com
 * @param <E>
 */
public interface EventSource<E extends ExternalEvent> extends Serializable {

    void addListener(Listener<E> listener);

    void removeListener(Listener<E> listener);
    
    void removeAllListeners();
}
