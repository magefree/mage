package mage.filter.predicate.other;

import mage.abilities.Ability;
import mage.abilities.keyword.PowerUpAbility;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 * @author muz
 */
public enum PowerUpAbilityPredicate implements Predicate<Ability> {
    instance;

    @Override
    public boolean apply(Ability input, Game game) {
        return input instanceof PowerUpAbility;
    }
}
