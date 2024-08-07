package mage.filter.predicate.other;

import mage.abilities.Ability;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.StackObject;

/**
 * @author notgreat
 */
public enum NotManaAbilityPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        if (!(input instanceof Ability)) {
            return false;
        }
        return !((Ability) input).isManaAbility();
    }

    @Override
    public String toString() {
        return "isn't a mana ability";
    }
}
