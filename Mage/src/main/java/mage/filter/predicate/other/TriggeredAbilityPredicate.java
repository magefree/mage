package mage.filter.predicate.other;

import mage.abilities.Ability;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.StackObject;

/**
 * @author TheElk801
 */
public enum TriggeredAbilityPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        return input instanceof Ability && ((Ability) input).isTriggeredAbility();
    }
}
