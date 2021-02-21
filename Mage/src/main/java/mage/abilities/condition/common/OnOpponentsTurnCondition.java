package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public enum OnOpponentsTurnCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getOpponents(source.getControllerId()).contains(game.getActivePlayerId());
    }

    @Override
    public String toString() {
        return "on an opponent's turn";
    }
}
