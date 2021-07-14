package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.DiscardedCardWatcher;

/**
 * @author TheElk801
 */
public enum ControllerDiscardedThisTurnCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return DiscardedCardWatcher.checkPlayerDiscarded(source.getControllerId(), game);
    }

    @Override
    public String toString() {
        return "you've discarded a card this turn";
    }
}
