package mage.filter.common;

import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author TheElk801
 */
public class FilterTeamPermanent extends FilterPermanent {

    public FilterTeamPermanent() {
        this("permanent your team controls");
    }

    public FilterTeamPermanent(String name) {
        super(name);
        this.add(TargetController.TEAM.getControllerPredicate());

    }

    public FilterTeamPermanent(SubType subtype, String name) {
        super(subtype, name);
        this.add(TargetController.TEAM.getControllerPredicate());
    }

    public FilterTeamPermanent(final FilterTeamPermanent filter) {
        super(filter);
    }

    @Override
    public FilterTeamPermanent copy() {
        return new FilterTeamPermanent(this);
    }
}
