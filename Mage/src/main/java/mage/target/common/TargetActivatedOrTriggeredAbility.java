package mage.target.common;

import mage.filter.common.FilterActivatedOrTriggeredAbility;
import mage.target.TargetStackObject;

public class TargetActivatedOrTriggeredAbility extends TargetStackObject {

    private static final FilterActivatedOrTriggeredAbility defaultFilter = new FilterActivatedOrTriggeredAbility();

    public TargetActivatedOrTriggeredAbility() {
        this(1, 1);
    }

    public TargetActivatedOrTriggeredAbility(FilterActivatedOrTriggeredAbility filter) {
        this(1, 1, filter);
    }

    public TargetActivatedOrTriggeredAbility(int minNumTargets, int maxNumTargets) {
        this(minNumTargets, maxNumTargets, defaultFilter);
    }

    public TargetActivatedOrTriggeredAbility(int minNumTargets, int maxNumTargets, FilterActivatedOrTriggeredAbility filter) {
        super(minNumTargets, maxNumTargets, filter);
    }

    protected TargetActivatedOrTriggeredAbility(final TargetActivatedOrTriggeredAbility target) {
        super(target);
    }

    @Override
    public TargetActivatedOrTriggeredAbility copy() {
        return new TargetActivatedOrTriggeredAbility(this);
    }
}
