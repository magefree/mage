package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;

import java.util.Objects;

/**
 * @author TheElk801
 */
public enum OneOpponentCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getOpponents(source.getControllerId(), true)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .count() <= 1;
    }

    @Override
    public String toString() {
        return "you have one opponent";
    }
}
