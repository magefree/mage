package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.Watcher;

/**
 * @author Loki
 */
public class LandfallCondition implements Condition {
    private static LandfallCondition instance = new LandfallCondition();

    public static LandfallCondition getInstance() {
        return instance;
    }

    private LandfallCondition() {
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Watcher watcher = game.getState().getWatchers().get("LandPlayed", source.getControllerId());
        return watcher.conditionMet();
    }
}
