package mage.cards.repository;

import mage.game.events.EventDispatcher;
import mage.game.events.EventSource;
import mage.game.events.Listener;

import java.io.Serializable;

/**
 * @author JayDi85
 */
public class RepositoryEventSource implements EventSource<RepositoryEvent>, Serializable {

    protected final EventDispatcher<RepositoryEvent> dispatcher = new EventDispatcher<RepositoryEvent>() {
    };

    @Override
    public void addListener(Listener<RepositoryEvent> listener) {
        dispatcher.addListener(listener);
    }

    @Override
    public void removeListener(Listener<RepositoryEvent> listener) {
        dispatcher.removeListener(listener);
    }

    @Override
    public void removeAllListeners() {
        dispatcher.removeAllListener();
    }

    public void fireRepositoryDbLoaded() {
        dispatcher.fireEvent(new RepositoryEvent(RepositoryEvent.RepositoryEventType.DB_LOADED));
    }

    public void fireRepositoryDbUpdated() {
        dispatcher.fireEvent(new RepositoryEvent(RepositoryEvent.RepositoryEventType.DB_UPDATED));
    }
}
