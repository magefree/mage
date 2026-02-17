package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 * Counts artifact and/or creature cards in opponent's graveyards
 *
 * @author Vernon
 */
public class OpponentGraveyardCardCount implements DynamicValue {

    private static final FilterCard FILTER = new FilterCard("artifact and/or creature cards");

    static {
        FILTER.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE)
        ));
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (sourceAbility == null) {
            return 0;
        }

        int total = 0;
        Player controller = game.getPlayer(sourceAbility.getControllerId());

        if (controller == null) {
            return 0;
        }

        // Count in all opponent graveyards
        for (Player opponent : game.getOpponents(controller)) {
            total += opponent.getGraveyard().count(FILTER, game);
        }

        return total;
    }

    @Override
    public DynamicValue copy() {
        return new OpponentGraveyardCardCount();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the number of artifact and/or creature cards in opponents' graveyards";
    }
}

