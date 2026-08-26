
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.hint.Hint;
import mage.abilities.hint.common.EnduringStoryHint;
import mage.designations.DesignationType;
import mage.game.Game;

/**
 *
 * @author muz
 */
public enum EnduringStoryCondition implements Condition {

    instance;

    public static Hint getHint() {
        return EnduringStoryHint.instance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getPlayer(source.getControllerId()).hasDesignation(DesignationType.ENDURING_STORY);
    }

    @Override
    public String toString() {
        return "you have an enduring story";
    }
}
