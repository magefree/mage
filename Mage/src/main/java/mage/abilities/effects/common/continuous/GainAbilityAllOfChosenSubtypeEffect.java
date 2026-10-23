package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.constants.Duration;
import mage.filter.FilterPermanent;

/**
 * @author LevelX2
 */
public class GainAbilityAllOfChosenSubtypeEffect extends GainAbilityAllEffect {

    public GainAbilityAllOfChosenSubtypeEffect(Ability ability, Duration duration, FilterPermanent filter) {
        super(ability, duration, modify(filter));
    }

    protected GainAbilityAllOfChosenSubtypeEffect(final GainAbilityAllOfChosenSubtypeEffect effect) {
        super(effect);
    }

    @Override
    public GainAbilityAllOfChosenSubtypeEffect copy() {
        return new GainAbilityAllOfChosenSubtypeEffect(this);
    }

    private static FilterPermanent modify(FilterPermanent filter) {
        FilterPermanent copy = filter.copy();
        copy.add(isChosenTypePredicate.instance);
        return copy;
    }
}
