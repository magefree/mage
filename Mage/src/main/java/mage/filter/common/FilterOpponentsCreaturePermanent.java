package mage.filter.common;

import mage.constants.SubType;
import mage.constants.TargetController;

/**
 * @author Styxo
 */
public class FilterOpponentsCreaturePermanent extends FilterCreaturePermanent {

    public FilterOpponentsCreaturePermanent() {
        this("creature an opponent controls");
    }

    public FilterOpponentsCreaturePermanent(String name) {
        super(name);
        this.add(TargetController.OPPONENT.getControllerPredicate());

    }

    public FilterOpponentsCreaturePermanent(SubType subtype, String name) {
        super(subtype, name);
        this.add(TargetController.OPPONENT.getControllerPredicate());
    }

    protected FilterOpponentsCreaturePermanent(final FilterOpponentsCreaturePermanent filter) {
        super(filter);
    }

    @Override
    public FilterOpponentsCreaturePermanent copy() {
        return new FilterOpponentsCreaturePermanent(this);
    }
}
