package mage.filter.predicate.other;

import mage.abilities.Ability;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 * @author notgreat
 */
public enum NotManaAbilityPredicate implements Predicate<Ability> {
    instance;

    @Override
    public boolean apply(Ability input, Game game) {
        return !input.isManaAbility();
    }

    @Override
    public String toString() {
        return "isn't a mana ability";
    }
}
