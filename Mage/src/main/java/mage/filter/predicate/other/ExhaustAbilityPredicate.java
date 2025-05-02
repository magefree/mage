package mage.filter.predicate.other;

import mage.abilities.keyword.ExhaustAbility;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.StackObject;

/**
 * @author TheElk801
 */
public enum ExhaustAbilityPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        return input.getStackAbility() instanceof ExhaustAbility;
    }
}
