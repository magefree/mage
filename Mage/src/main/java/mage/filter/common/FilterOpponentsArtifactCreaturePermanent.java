package mage.filter.common;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;


public class FilterOpponentsArtifactCreaturePermanent extends FilterArtifactCreaturePermanent {

    public FilterOpponentsArtifactCreaturePermanent() {
        this("creature or artifact an opponent controls");
    }

    public FilterOpponentsArtifactCreaturePermanent(String name) {
        super(name);
        this.add(TargetController.OPPONENT.getControllerPredicate());

    }
    public FilterOpponentsArtifactCreaturePermanent(final FilterOpponentsArtifactCreaturePermanent filter) {super(filter);}

    @Override
    public FilterOpponentsArtifactCreaturePermanent copy(){
        return new FilterOpponentsArtifactCreaturePermanent(this);
    }
}
