package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;

public enum NotMyTurnCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return !source.isControlledBy(game.getActivePlayerId());
    }

    @Override
    public String toString() {
        return "if it's not your turn";
    }
}
