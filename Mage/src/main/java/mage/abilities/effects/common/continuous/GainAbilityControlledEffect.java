package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.targetpointer.FilterAllPermanentsTargetPointer;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class GainAbilityControlledEffect extends GainAbilityTargetEffect {

    public GainAbilityControlledEffect(Ability ability, Duration duration, FilterPermanent filter) {
        this(duration, filter, false, ability);
    }

    public GainAbilityControlledEffect(Ability ability, Duration duration, FilterPermanent filter, boolean excludeSource) {
        this(duration, filter, excludeSource, ability);
    }

    public GainAbilityControlledEffect(Duration duration, FilterPermanent filter, Ability... abilities) {
        this(duration, filter, false, abilities);
    }

    public GainAbilityControlledEffect(Duration duration, FilterPermanent filter, boolean excludeSource, Ability... abilities) {
        super(duration, abilities);
        FilterPermanent filterCopy = filter.copy();
        if (excludeSource) {
            filterCopy.add(AnotherPredicate.instance);
        }
        filterCopy.add(TargetController.YOU.getControllerPredicate());
        filterCopy.setMessage(withYouControl(withOtherPrefix(filter.getMessage(), excludeSource)));
        this.setTargetPointer(new FilterAllPermanentsTargetPointer(filterCopy));

        this.generateGainAbilityDependencies(getGrantedAbilities(), filter);
    }

    protected GainAbilityControlledEffect(final GainAbilityControlledEffect effect) {
        super(effect);
    }

    @Override
    public GainAbilityControlledEffect copy() {
        return new GainAbilityControlledEffect(this);
    }
}
