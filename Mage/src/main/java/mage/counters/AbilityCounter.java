package mage.counters;

import mage.abilities.Ability;

/**
 * @author TheElk801
 */
public class AbilityCounter extends Counter {

    private final Ability ability;

    AbilityCounter(Ability ability, int count) {
        this(ability, count, makeName(ability.getRule()));
    }

    AbilityCounter(Ability ability, int count, String name) {
        super(name, count);
        this.ability = ability;
    }

    private AbilityCounter(final AbilityCounter counter) {
        super(counter);
        this.ability = counter.ability;
    }

    public Ability getAbility() {
        return ability;
    }

    @Override
    public AbilityCounter copy() {
        return new AbilityCounter(this);
    }

    private static String makeName(String name) {
        return name.replaceAll(" <i>.*<\\/i>", "");
    }
}
