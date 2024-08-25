package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.IntCompareCondition;
import mage.constants.ComparisonType;
import mage.game.Game;
import mage.watchers.common.PlayerLostLifeWatcher;

/**
 * Needs PlayerLostLifeWatcher to work
 *
 * @author Susucr
 */
public class YouLostLifeCondition extends IntCompareCondition {

    /**
     * "if you lost life this turn"
     */
    public YouLostLifeCondition() {
        super(ComparisonType.MORE_THAN, 0);
    }

    public YouLostLifeCondition(ComparisonType type, int value) {
        super(type, value);
    }

    @Override
    protected int getInputValue(Game game, Ability source) {
        PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        return watcher == null ? 0 : watcher.getLifeLost(source.getControllerId());
    }

    @Override
    public String toString() {
        return "if you lost " + (value == 0 ? "" : (value + 1) + " or more ") + "life this turn";
    }
}
