package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

/**
 * @author L_J
 */

public class ChooseBlockersRedundancyWatcher extends Watcher { // workaround for solving timestamp issues regarding "you choose which creatures block and how those creatures block" effects

    public int copyCount = 0;
    public int copyCountApply = 0;

    public ChooseBlockersRedundancyWatcher() {
        super(WatcherScope.GAME);
    }

    public ChooseBlockersRedundancyWatcher(final ChooseBlockersRedundancyWatcher watcher) {
        super(watcher);
        this.copyCount = watcher.copyCount;
        this.copyCountApply = watcher.copyCountApply;
    }

    @Override
    public void reset() {
        super.reset();
        copyCount = 0;
        copyCountApply = 0;
    }

    @Override
    public ChooseBlockersRedundancyWatcher copy() {
        return new ChooseBlockersRedundancyWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
    }

    public void increment() {
        copyCount++;
        copyCountApply = copyCount;
    }

    public void decrement() {
        if (copyCountApply > 0) {
            copyCountApply--;
        }
    }
}
