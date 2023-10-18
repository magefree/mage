package mage.client.util;

import java.io.Serializable;

/**
 * TODO: there are duplicated interfaces of EventSource, must combine in single
 * @author BetaSteward_at_googlemail.com
 */
public interface EventSource<E extends Event> extends Serializable {
    void addListener(Listener<E> listener);

    void clearListeners();
}
