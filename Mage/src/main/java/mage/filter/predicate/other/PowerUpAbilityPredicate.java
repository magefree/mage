package mage.filter.predicate.other;

import mage.abilities.keyword.PowerUpAbility;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.StackObject;

/**
 * @author muz
 */
public enum PowerUpAbilityPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        return input.getStackAbility() instanceof PowerUpAbility;
    }
}
