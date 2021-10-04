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
        Permanent creatureWithGreatestPower = input.getObject();
        for (Permanent p : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_CONTROLLED_CREATURE, input.getObject().getControllerId(), game)) {
            if (p.getPower().getValue() >= creatureWithGreatestPower.getPower().getValue()) {
                creatureWithGreatestPower = p;
            }
        }
        return (creatureWithGreatestPower == input.getObject());
    }
}
