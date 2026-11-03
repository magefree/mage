package mage.target.targetadjustment;

import mage.abilities.Ability;
import mage.game.Game;
import mage.target.Target;

import java.io.Serializable;

/**
 * @author TheElk801
 */
@FunctionalInterface
public interface TargetAdjuster extends Serializable {

    // Warning: This is not Copyable, do not use changeable data inside (only use static objects like Filter)
    // Note: in playability check for cards, targets are not adjusted.
    void adjustTargets(Ability ability, Game game);

    /**
     * Set the blueprint target from the owner's first target (null if it has none).
     * Called once when the adjuster is set up; copies share the adjuster, so the blueprint must not change after.
     */
    default void addDefaultTargets(Target firstTarget) {
    }
}
