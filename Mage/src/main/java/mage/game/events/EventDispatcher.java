package mage.game.events;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author BetaSteward_at_googlemail.com
 * @param <E>
 */
public abstract class EventDispatcher<E extends ExternalEvent> implements Serializable {

    private final List<Listener<E>> listeners = new CopyOnWriteArrayList<>();

    public void addListener(Listener<E> listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeListener(Listener<E> listener) {
        listeners.remove(listener);
    }

    public void fireEvent(E event) {
        for (Listener<E> listener : listeners) {
            listener.event(event);
        }
    }

    public void removeAllListener() {
        listeners.clear();
    }
}
