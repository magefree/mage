package mage.cards.h;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

public class HomicidalBruteWatcher extends Watcher {

    public HomicidalBruteWatcher() {
        super(WatcherScope.CARD);
    }

    public HomicidalBruteWatcher(final HomicidalBruteWatcher watcher) {
        super(watcher);
    }

    @Override
    public HomicidalBruteWatcher copy() {
        return new HomicidalBruteWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (condition) {
            return;
        }
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED && event.getSourceId().equals(sourceId)) {
            condition = true;
        }
    }
}