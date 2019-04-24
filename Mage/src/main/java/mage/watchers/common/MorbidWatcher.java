
package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.watchers.Watcher;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class MorbidWatcher extends Watcher {

    public MorbidWatcher() {
        super(MorbidWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public MorbidWatcher(final MorbidWatcher watcher) {
        super(watcher);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (condition) {
            return;
        }
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE
                && ((ZoneChangeEvent) event).isDiesEvent()
                && ((ZoneChangeEvent) event).getTarget().isCreature()) {
            condition = true;
        }
    }

    @Override
    public MorbidWatcher copy() {
        return new MorbidWatcher(this);
    }

}
