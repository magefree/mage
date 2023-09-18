package mage.target.targetadjustment;

import mage.abilities.Ability;
import mage.game.Game;

import java.io.Serializable;

/**
 * @author TheElk801
 */
@FunctionalInterface
public interface TargetAdjuster extends Serializable {

    void adjustTargets(Ability ability, Game game);
}
