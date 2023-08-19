package mage.filter.common;

import mage.constants.SubType;
import mage.constants.TargetController;

/**
 * @author TheElk801
 */
public class FilterTeamCreaturePermanent extends FilterCreaturePermanent {

    public FilterTeamCreaturePermanent() {
        this("creature your team controls");
    }

    public FilterTeamCreaturePermanent(String name) {
        super(name);
        this.add(TargetController.TEAM.getControllerPredicate());

    }

    public FilterTeamCreaturePermanent(SubType subtype, String name) {
        super(subtype, name);
        this.add(TargetController.TEAM.getControllerPredicate());
    }

    protected FilterTeamCreaturePermanent(final FilterTeamCreaturePermanent filter) {
        super(filter);
    }

    @Override
    public FilterTeamCreaturePermanent copy() {
        return new FilterTeamCreaturePermanent(this);
    }
}
