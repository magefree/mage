
package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.watchers.Watcher;

/**
 * The watcher checks if a specific phase event has already happened during the
 * current turn. If not it returns false, otherwise true.
 *
 * @author LevelX2
 */
public class FirstTimeStepWatcher extends Watcher {

    private final EventType eventType;

    public FirstTimeStepWatcher(EventType eventType) {
        super(eventType.toString() + FirstTimeStepWatcher.class.getSimpleName(), WatcherScope.GAME);
        this.eventType = eventType;
    }

    public FirstTimeStepWatcher(final FirstTimeStepWatcher watcher) {
        super(watcher);
        this.eventType = watcher.eventType;
    }

    @Override
    public FirstTimeStepWatcher copy() {
        return new FirstTimeStepWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == eventType) {
            condition = true;
        }
    }
}
