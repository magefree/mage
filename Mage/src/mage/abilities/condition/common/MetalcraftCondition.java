package mage.abilities.condition.common;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;

/**
 * Describes condition when Metacraft mechanic is turned on.
 *
 * @author nantuko
 */
public class MetalcraftCondition implements Condition {

    private static final FilterPermanent filter = new FilterPermanent("artifact");

    static {
        filter.add(new CardTypePredicate(Constants.CardType.ARTIFACT));
    }

    private static MetalcraftCondition fInstance = new MetalcraftCondition();

    public static Condition getInstance() {
        return fInstance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getBattlefield().contains(filter, source.getControllerId(), 3, game);
    }
}
