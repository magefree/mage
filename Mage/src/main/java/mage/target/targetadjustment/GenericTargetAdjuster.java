package mage.target.targetadjustment;

import mage.abilities.Ability;
import mage.target.Target;

public abstract class GenericTargetAdjuster implements TargetAdjuster {
    protected Target blueprintTarget = null;

    @Override
    public void addDefaultTargets(Ability ability) {
        if (blueprintTarget == null) {
            blueprintTarget = ability.getTargets().get(0).copy();
        } else {
            throw new IllegalStateException("Wrong code usage: target adjuster already has blueprint target - " + blueprintTarget);
        }
    }
}
