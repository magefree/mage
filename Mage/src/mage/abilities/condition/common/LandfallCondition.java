package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.LandfallWatcher;

/**
 * @author Loki
 */
public class LandfallCondition implements Condition {
    private final static LandfallCondition instance = new LandfallCondition();

    public static LandfallCondition getInstance() {
        return instance;
    }

    private LandfallCondition() {
    }

    @Override
    public boolean apply(Game game, Ability source) {
        LandfallWatcher watcher = (LandfallWatcher) game.getState().getWatchers().get("LandPlayed");
        return watcher != null && watcher.landPlayed(source.getControllerId());
    }
}
