package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.CardsDrawnThisTurnWatcher;

/**
 * @author TheElk801
 */
public enum DrewTwoOrMoreCardsCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        CardsDrawnThisTurnWatcher watcher = game.getState().getWatcher(CardsDrawnThisTurnWatcher.class);
        return watcher != null && watcher.getCardsDrawnThisTurn(source.getControllerId()) >= 2;
    }

    @Override
    public String toString() {
        return "you've drawn two or more cards this turn";
    }
}
