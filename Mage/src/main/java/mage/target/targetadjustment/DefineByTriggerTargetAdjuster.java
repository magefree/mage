package mage.target.targetadjustment;

import mage.abilities.Ability;
import mage.game.Game;

/**
 * Use this to indicate that the targets are dynamically added via the Ability directly
 * While still satisfying the Verify target check
 *
 * @author notgreat
 */
public enum DefineByTriggerTargetAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        //Do nothing
    }
}
