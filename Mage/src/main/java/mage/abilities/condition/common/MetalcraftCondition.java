package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.CardType;
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
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    private static final MetalcraftCondition fInstance = new MetalcraftCondition();

    public static Condition getInstance() {
        return fInstance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getBattlefield().contains(filter, source.getControllerId(), 3, game);
    }

    @Override
    public String toString() {
        return "you control three or more artifacts";
    }
    
}
