package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.targetpointer.FilterAllPermanentsTargetPointer;

/**
 * Grants abilities to every permanent matching a filter. All the work is in the superclass;
 * this only installs the target pointer.
 *
 * @author Loki
 */
public class GainAbilityAllEffect extends GainAbilityTargetEffect {

    public GainAbilityAllEffect(Ability ability, Duration duration, FilterPermanent filter) {
        this(ability, duration, filter, false);
    }

    public GainAbilityAllEffect(Ability ability, Duration duration, FilterPermanent filter, String text) {
        this(ability, duration, filter, false);
        this.staticText = text;
    }

    public GainAbilityAllEffect(Ability ability, Duration duration, FilterPermanent filter, boolean excludeSource) {
        super(ability, duration);
        FilterPermanent filterCopy = filter.copy();
        if (excludeSource) {
            filterCopy.add(AnotherPredicate.instance);
        }
        filterCopy.setMessage(withOtherPrefix(filter.getMessage(), excludeSource));
        this.setTargetPointer(new FilterAllPermanentsTargetPointer(filterCopy));

        this.generateGainAbilityDependencies(ability, filter);
    }

    protected GainAbilityAllEffect(final GainAbilityAllEffect effect) {
        super(effect);
    }

    @Override
    public GainAbilityAllEffect copy() {
        return new GainAbilityAllEffect(this);
    }
}
