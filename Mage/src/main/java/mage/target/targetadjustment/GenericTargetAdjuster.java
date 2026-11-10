package mage.target.targetadjustment;

import mage.target.Target;

public abstract class GenericTargetAdjuster implements TargetAdjuster {
    protected Target blueprintTarget = null;

    @Override
    public void addDefaultTargets(Target firstTarget) {
        if (blueprintTarget == null) {
            blueprintTarget = firstTarget.copy();
        } else {
            throw new IllegalStateException("Wrong code usage: target adjuster already has blueprint target - " + blueprintTarget);
        }
    }
}
