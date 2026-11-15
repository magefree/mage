package mage.filter.predicate.other;

import mage.abilities.Ability;
import mage.filter.predicate.Predicate;
import mage.game.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Ability of one of the classes, e.g. equip abilities
 *
 * @author JayDi85
 */
public class AbilityClassPredicate implements Predicate<Ability> {

    private final List<Class<? extends Ability>> abilityClasses;

    @SafeVarargs
    public AbilityClassPredicate(Class<? extends Ability>... abilityClasses) {
        if (abilityClasses.length == 0) {
            throw new IllegalArgumentException("Wrong code usage: ability classes must be set");
        }
        this.abilityClasses = new ArrayList<>(Arrays.asList(abilityClasses));
    }

    @Override
    public boolean apply(Ability input, Game game) {
        return abilityClasses.stream().anyMatch(abilityClass -> abilityClass.isInstance(input));
    }

    @Override
    public String toString() {
        return "AbilityClass(" + abilityClasses.stream().map(Class::getSimpleName).collect(Collectors.joining(", ")) + ')';
    }
}
