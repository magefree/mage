
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.condition.IntCompareCondition;
import mage.game.Game;
import mage.watchers.common.PlayerLostLifeWatcher;

import java.util.UUID;

/**
 * Describes condition when an opponent has lost an amount of life
 *
 * @author LevelX2
 */
public class OpponentLostLifeCondition extends IntCompareCondition {

    public OpponentLostLifeCondition(ComparisonType type, int value) {
        super(type, value);
    }

    @Override
    protected int getInputValue(Game game, Ability source) {
        int maxLostLive = 0;
        PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        if (watcher != null) {
            for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                int lostLive = watcher.getLifeLost(opponentId);
                if (lostLive > maxLostLive) {
                    maxLostLive = lostLive;
                }
            }
        }
        return maxLostLive;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("if an opponent lost ");
        switch (type) {
            case MORE_THAN:
                sb.append(value + 1).append(" or more life this turn");
                break;
            case OR_GREATER:
                sb.append(value).append(" or more life this turn");
                break;
            case EQUAL_TO:
                sb.append(value).append(" life this turn");
                break;
            case FEWER_THAN:
                sb.append(" less than ").append(value).append(" life this turn");
                break;
            case OR_LESS:
                sb.append(value).append(" or less life this turn");
                break;
        }
        return sb.toString();
    }
}
