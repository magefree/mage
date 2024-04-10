package mage.target.targetadjustment;

import mage.abilities.Ability;
import mage.game.Game;
import mage.target.Target;

/**
 *
 * @author TheElk801, notgreat
 */
public class XTargetsAdjuster implements TargetAdjuster {
    private Target blueprintTarget = null;

    public XTargetsAdjuster() {
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (blueprintTarget == null) {
            blueprintTarget = ability.getTargets().get(0).copy();
        }
        Target newTarget = blueprintTarget.copy();
        newTarget.setMaxNumberOfTargets(ability.getManaCostsToPay().getX());
        if (blueprintTarget.getMinNumberOfTargets() != 0) {
            newTarget.setMinNumberOfTargets(ability.getManaCostsToPay().getX());
        }
        ability.getTargets().clear();
        ability.addTarget(newTarget);
    }
}
