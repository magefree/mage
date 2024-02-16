package mage.filter.predicate.permanent;

import mage.filter.StaticFilters;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author jeffwadsworth
 */
public enum GreatestPowerControlledPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        int greatestPower = Integer.MIN_VALUE;
        for (Permanent p : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_CONTROLLED_CREATURE, input.getPlayerId(), input.getSource(), game)) {
            greatestPower = Math.max(greatestPower, p.getPower().getValue());
        }
        return input.getObject().getPower().getValue() == greatestPower;
    }
}
