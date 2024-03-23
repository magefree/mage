package mage.target.targetadjustment;

import mage.abilities.Ability;
import mage.game.Game;

import java.io.Serializable;

/**
 * @author TheElk801
 */
@FunctionalInterface
public interface TargetAdjuster extends Serializable {

    // Please note that if refactoring and a copy() method is to be added, you must also
    // refactor subclasses which are not enums. Currently only EachOpponentPermanentTargetsAdjuster.

    void adjustTargets(Ability ability, Game game);
}
