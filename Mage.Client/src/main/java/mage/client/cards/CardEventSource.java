package mage.client.cards;

import mage.client.util.Event;
import mage.client.util.*;
import mage.view.SimpleCardView;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.Serializable;

/**
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class CardEventSource implements EventSource<Event>, Serializable {

    protected final EventDispatcher<Event> dispatcher = new EventDispatcher<Event>() {
    };

    @Override
    public void addListener(Listener<Event> listener) {
        dispatcher.addListener(listener);
    }

    @Override
    public void clearListeners() {
        dispatcher.clearListeners();
    }

    public void fireEvent(SimpleCardView card, ClientEventType eventType, int number) {
        fireEvent(new Event(card, eventType, number));
    }

    public void fireEvent(ClientEventType eventType) {
        fireEvent(new Event(null, eventType));
    }

    public void fireEvent(SimpleCardView card, ClientEventType eventType) {
        fireEvent(new Event(card, eventType));
    }

    public void fireEvent(SimpleCardView card, Component component, int x, int y, ClientEventType message) {
        fireEvent(new Event(card, message, 0, x, y, component));
    }

    public void fireEvent(SimpleCardView card, ClientEventType eventType, MouseEvent e, boolean forceFakeAltDown) {
        Event event;
        if (e != null) {
            // normal mouse event
            event = new Event(card, eventType, 0, e.getX(), e.getY(), e.getComponent(), e, forceFakeAltDown);
        } else {
            // fake mouse event
            event = new Event(card, eventType, 0, 0, 0, null, null, forceFakeAltDown);
        }
        fireEvent(event);
    }

    public void fireEvent(Event clientEvent) {
        dispatcher.fireEvent(clientEvent);
    }

}
