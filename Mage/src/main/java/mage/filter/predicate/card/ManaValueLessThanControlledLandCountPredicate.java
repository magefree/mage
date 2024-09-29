package mage.filter.predicate.card;

import mage.MageObject;
import mage.filter.StaticFilters;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;

/**
 * @author xenohedron
 */
public enum ManaValueLessThanControlledLandCountPredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        return input.getObject().getManaValue() <= game.getBattlefield().countAll(StaticFilters.FILTER_LAND, game.getControllerId(input.getSourceId()), game);
    }

    @Override
    public String toString() {
        return "mana value less than or equal to the number of lands you control";
    }
}
