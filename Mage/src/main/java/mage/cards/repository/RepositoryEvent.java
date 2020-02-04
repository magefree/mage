package mage.cards.repository;

import java.io.Serializable;
import java.util.EventObject;

import mage.game.events.ExternalEvent;

/**
 * @author JayDi85
 */
public class RepositoryEvent extends EventObject implements ExternalEvent, Serializable {

    public enum RepositoryEventType {
        DB_LOADED, DB_UPDATED
    }

    private RepositoryEventType eventType;

    public RepositoryEvent(RepositoryEventType eventType) {
        super(eventType);
        this.eventType = eventType;
    }

    public RepositoryEventType getEventType() {
        return eventType;
    }
}
