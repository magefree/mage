
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public enum MonarchIsSourceControllerCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return source.isControlledBy(game.getMonarchId());
    }

    @Override
    public String toString() {
        return "if you're the monarch";
    }
}
