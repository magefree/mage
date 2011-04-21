package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;

public class Hellbent implements Condition {

    private static Hellbent fInstance = new Hellbent();

    public static Condition getInstance() {
        return fInstance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getPlayer(source.getControllerId()).getHand().size() == 0;
    }
}
