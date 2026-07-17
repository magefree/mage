package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.CardsLeftGraveyardWatcher;

/**
 * @author muz
 */
public enum CardLeftYourGraveyardThisTurnCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return !game
                .getState()
                .getWatcher(CardsLeftGraveyardWatcher.class)
                .getCardsThatLeftGraveyard(source.getControllerId(), game)
                .isEmpty();
    }

    @Override
    public String toString() {
        return "if a card left your graveyard this turn";
    }
}
