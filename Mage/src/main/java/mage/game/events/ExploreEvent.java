package mage.game.events;

import mage.abilities.Ability;
import mage.game.permanent.Permanent;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author Grath
 */
public class ExploreEvent extends GameEvent {

    private Queue<EventType> eventQueue = new ArrayDeque<>();

    public ExploreEvent(Permanent permanent, Ability source, int amount) {
        super(EventType.EXPLORE, permanent.getId(), source, permanent.getControllerId(), amount, false);
        // Populate the queue with a number of EXPLOREs equal to the initial number of EXPLOREs.
        for (int i = 0; i < amount; ++i) {
            eventQueue.add(EventType.EXPLORE);
        }
    }

    public void doubleExplores() {
        // Process through the Queue, when we find an EXPLORE add another EXPLORE.
        Queue<EventType> newQueue = new ArrayDeque<>();
        for (EventType eventType : eventQueue) {
            if (eventType == EventType.EXPLORE) {
                newQueue.add(EventType.EXPLORE);
            }
            newQueue.add(eventType);
        }
        eventQueue = newQueue;
    }

    public void addScry() {
        // Process through the Queue, when we find an EXPLORE add a SCRY before it.
        Queue<EventType> newQueue = new ArrayDeque<>();
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
