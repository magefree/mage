package mage.filter.predicate.permanent;

import mage.constants.SubType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author KholdFuzion
 */

public enum ControllerControlsIslandPredicate implements Predicate<Permanent> {
    instance;

    private static final FilterLandPermanent filter = new FilterLandPermanent("Island");

    static {
        filter.add(SubType.ISLAND.getPredicate());
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        return game.getBattlefield().countAll(filter, input.getControllerId(), game) > 0;
    }

    @Override
    public String toString() {
        return "Controls an island";
    }
}
