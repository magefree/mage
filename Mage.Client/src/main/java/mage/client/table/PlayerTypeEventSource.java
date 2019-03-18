

package mage.client.table;

import mage.client.util.*;

import java.io.Serializable;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PlayerTypeEventSource implements EventSource<Event>, Serializable {

    protected final EventDispatcher<Event> dispatcher = new EventDispatcher<Event>() {};

    @Override
    public void addListener(Listener<Event> listener) {
        dispatcher.addListener(listener);
    }

    public void playerTypeChanged() {
        dispatcher.fireEvent(new Event(null, ClientEventType.PLAYER_TYPE_CHANGED));
    }

    @Override
    public void clearListeners() {
        dispatcher.clearListeners();
    }

}

