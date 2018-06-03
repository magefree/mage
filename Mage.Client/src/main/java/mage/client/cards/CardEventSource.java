
package mage.client.cards;

import mage.client.util.*;
import mage.client.util.Event;
import mage.view.SimpleCardView;

import java.awt.*;
import java.io.Serializable;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CardEventSource implements EventSource<Event>, Serializable {

    protected final EventDispatcher<Event> dispatcher = new EventDispatcher<Event>() {
    };

    @Override
    public void addListener(Listener<Event> listener) {
        dispatcher.addListener(listener);
    }

    public void fireEvent(SimpleCardView card, ClientEventType eventType, int number){
        dispatcher.fireEvent(new Event(card, eventType, number));
    }

    public void fireEvent(ClientEventType eventType){
        dispatcher.fireEvent(new Event(null, eventType));
    }

    public void fireEvent(SimpleCardView card, ClientEventType eventType){
        dispatcher.fireEvent(new Event(card, eventType));
    }

    public void fireEvent(SimpleCardView card, Component component, int x, int y, ClientEventType message) {
        dispatcher.fireEvent(new Event(card, message, x, y, component));
    }

    @Override
    public void clearListeners() {
        dispatcher.clearListeners();
    }

}
