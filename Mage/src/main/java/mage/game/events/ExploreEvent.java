package mage.game.events;

import mage.abilities.Ability;
import mage.game.permanent.Permanent;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author Grath
 */
public class ExploreEvent extends GameEvent {

    private Queue<EventType> eventQueue = new ArrayDeque<EventType>();

    public ExploreEvent(Permanent permanent, Ability source, int amount) {
        super(EventType.EXPLORE, permanent.getId(), source, permanent.getControllerId(), amount, false);
        for (int i = 0; i < amount; ++i) {
            eventQueue.add(EventType.EXPLORE);
        }
    }

    public void DoubleExplores() {
        Queue<EventType> newQueue = new ArrayDeque<EventType>();
        for (EventType eventType : eventQueue) {
            if (eventType == EventType.EXPLORE) {
                newQueue.add(EventType.EXPLORE);
            }
            newQueue.add(eventType);
        }
        eventQueue = newQueue;
    }

    public void AddScry() {
        Queue<EventType> newQueue = new ArrayDeque<EventType>();
        for (EventType eventType : eventQueue) {
            if (eventType == EventType.EXPLORE) {
                newQueue.add(EventType.SCRY);
            }
            newQueue.add(eventType);
        }
        eventQueue = newQueue;
    }

    public Queue<EventType> getEventQueue() {
        return eventQueue;
    }
}
