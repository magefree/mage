package mage.target.targetadjustment;

import mage.abilities.Ability;
import mage.game.Game;

import java.io.Serializable;

/**
 * @author TheElk801
 */
@FunctionalInterface
public interface TargetAdjuster extends Serializable {

    // Warning: This is not Copyable, do not use changeable data inside (only use static objects like Filter)
    void adjustTargets(Ability ability, Game game);

    /**
     * Add default blueprint target to the ability
     */
    default void addDefaultTargets(Ability ability) {
    }

    /**
     * Adjust the targets for checking inside of Ability.canChooseTargetAbility
     * By default do nothing, but some adjusters will want to modify the target first
     */
    default void adjustTargetsCheck(Ability ability, Game game) {
    }
}
