package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.DiscardedCardWatcher;

/**
 *
 * @author weirddan455
 */
public enum PlayerDiscardedThisTurnCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return DiscardedCardWatcher.playerInRangeDiscarded(source.getControllerId(), game);
    }

    @Override
    public String toString() {
        return "a player discarded a card this turn";
    }
}
