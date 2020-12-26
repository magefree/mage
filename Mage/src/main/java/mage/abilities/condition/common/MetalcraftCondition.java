package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.game.Game;

/**
 * Describes condition when Metacraft mechanic is turned on.
 *
 * @author nantuko
 */
public enum MetalcraftCondition implements Condition {
    instance;
    private static final FilterPermanent filter = new FilterPermanent("artifact");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }


    @Override
    public boolean apply(Game game, Ability source) {
        return game.getBattlefield().containsControlled(filter, source, game, 3);
    }

    @Override
    public String toString() {
        return "you control three or more artifacts";
    }

}
