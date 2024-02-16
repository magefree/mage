package mage.target.common;

import mage.filter.FilterStackObject;
import mage.filter.common.FilterActivatedOrTriggeredAbility;
import mage.target.TargetStackObject;

public class TargetActivatedOrTriggeredAbility extends TargetStackObject {

    private static final FilterStackObject defaultFilter = new FilterActivatedOrTriggeredAbility();

    public TargetActivatedOrTriggeredAbility() {
        this(1, 1);
    }

    public TargetActivatedOrTriggeredAbility(FilterStackObject filter) {
        this(1, 1, filter);
    }

    public TargetActivatedOrTriggeredAbility(int minNumTargets, int maxNumTargets) {
        this(minNumTargets, maxNumTargets, defaultFilter);
    }

    public TargetActivatedOrTriggeredAbility(int minNumTargets, int maxNumTargets, FilterStackObject filter) {
        super(minNumTargets, maxNumTargets, filter);
    }

    protected TargetActivatedOrTriggeredAbility(final TargetActivatedOrTriggeredAbility target) {
        super(target);
    }
}
