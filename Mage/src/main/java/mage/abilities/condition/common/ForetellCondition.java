package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;

/**
 *
 * @author weirddan455
 */
public enum ForetellCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getState().getValue(source.getSourceId().toString() + "Foretell Turn Number") != null;
    }

    @Override
    public String toString() {
        return "this spell was foretold";
    }
}
