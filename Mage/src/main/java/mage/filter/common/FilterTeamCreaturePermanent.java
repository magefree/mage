package mage.filter.common;

import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.predicate.permanent.ControllerPredicate;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author TheElk801
 */
public class FilterTeamCreaturePermanent extends FilterCreaturePermanent {

    public FilterTeamCreaturePermanent() {
        this("creature your team controls");
    }

    public FilterTeamCreaturePermanent(String name) {
        super(name);
        this.add(new ControllerPredicate(TargetController.TEAM));

    }

    public FilterTeamCreaturePermanent(SubType subtype, String name) {
        super(subtype, name);
        this.add(new ControllerPredicate(TargetController.TEAM));
    }

    public FilterTeamCreaturePermanent(final FilterTeamCreaturePermanent filter) {
        super(filter);
    }

    @Override
    public FilterTeamCreaturePermanent copy() {
        return new FilterTeamCreaturePermanent(this);
    }
}
