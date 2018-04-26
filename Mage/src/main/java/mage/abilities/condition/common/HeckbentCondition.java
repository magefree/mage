package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;

/**
 * @author stravant
 */
public enum HeckbentCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getPlayer(source.getControllerId()).getHand().size() <= 1;
    }

    @Override
    public String toString() {
        return "if you have one or fewer cards in hand";
    }
}
