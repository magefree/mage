package mage.target.targetadjustment;

import mage.abilities.Ability;
import mage.game.Game;

/**
 * @author TheElk801
 */
public interface TargetAdjuster {

    void adjustTargets(Ability ability, Game game);

}
