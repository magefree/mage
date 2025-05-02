package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.CardsExiledThisTurnWatcher;

/**
 * Checks if at least one card was put into exile this turn.
 * <p>
 * /!\ Need the CardsExiledThisTurnWatcher to be set up.
 *
 * @author Susucr
 */
public enum WasCardExiledThisTurnCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        CardsExiledThisTurnWatcher watcher = game.getState().getWatcher(CardsExiledThisTurnWatcher.class);
        return watcher != null && watcher.getCountCardsExiledThisTurn() > 0;
    }

    @Override
    public String toString() {
        return "a card was put into exile this turn";
    }

}
