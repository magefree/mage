
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;

/**
 * @author Susucr
 */
public enum MonarchIsNotSetCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getMonarchId() == null;
    }

    @Override
    public String toString() {
        return "if there is no monarch";
    }
}
