package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;

public class MyTurn implements Condition {
    private static MyTurn fInstance = new MyTurn();

    public static Condition getInstance() {
        return fInstance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getActivePlayerId().equals(source.getControllerId());
    }
}
