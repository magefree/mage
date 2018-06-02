
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;

public enum HellbentCondition implements Condition {

   instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getPlayer(source.getControllerId()).getHand().isEmpty();
    }

    @Override
    public String toString() {
        return "if you have no cards in hand";
    }
}
