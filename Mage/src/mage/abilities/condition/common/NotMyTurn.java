package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;

public class NotMyTurn implements Condition {
    private static NotMyTurn fInstance = new NotMyTurn();

    public static Condition getInstance() {
        return fInstance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return !game.getActivePlayerId().equals(source.getControllerId());
    }
}
