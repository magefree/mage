package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.watchers.Watcher;

/**
 * @author Susucr
 */
public class CardsExiledThisTurnWatcher extends Watcher {

    private int countExiled = 0;

    public CardsExiledThisTurnWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE
                && ((ZoneChangeEvent) event).getToZone() == Zone.EXILED) {
            countExiled++;
        }
    }

    public int getCountCardsExiledThisTurn() {
        return countExiled;
    }

    @Override
    public void reset() {
        super.reset();
        countExiled = 0;
    }
}
