package mage.filter.predicate.other;

import mage.abilities.Ability;
import mage.abilities.keyword.ExhaustAbility;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 * @author TheElk801
 */
public enum ExhaustAbilityPredicate implements Predicate<Ability> {
    instance;

    @Override
    public boolean apply(Ability input, Game game) {
        return ExhaustAbility.isExhaust(input);
    }
}
