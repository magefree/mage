package mage.abilities.effects.common.continuous;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.targetpointer.FilterAllPermanentsTargetPointer;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class BoostControlledEffect extends BoostAllEffect {

    public BoostControlledEffect(int power, int toughness, Duration duration) {
        this(power, toughness, duration, StaticFilters.FILTER_PERMANENT_CREATURES, false);
    }

    public BoostControlledEffect(DynamicValue power, DynamicValue toughness, Duration duration) {
        this(power, toughness, duration, StaticFilters.FILTER_PERMANENT_CREATURES, false);
    }

    public BoostControlledEffect(int power, int toughness, Duration duration, boolean excludeSource) {
        this(power, toughness, duration, StaticFilters.FILTER_PERMANENT_CREATURES, excludeSource);
    }

    public BoostControlledEffect(int power, int toughness, Duration duration, FilterPermanent filter) {
        this(StaticValue.get(power), StaticValue.get(toughness), duration, filter, false);
    }

    public BoostControlledEffect(int power, int toughness, Duration duration, FilterPermanent filter, boolean excludeSource) {
        this(StaticValue.get(power), StaticValue.get(toughness), duration, filter, excludeSource);
    }

    public BoostControlledEffect(DynamicValue power, DynamicValue toughness, Duration duration, FilterPermanent filter, boolean excludeSource) {
        super(power, toughness, duration);
        FilterPermanent filterCopy = filter.copy();
        if (excludeSource) {
            filterCopy.add(AnotherPredicate.instance);
        }
        filterCopy.add(TargetController.YOU.getControllerPredicate());
        filterCopy.setMessage(withYouControl(withOtherPrefix(filter.getMessage(), excludeSource)));
        this.setTargetPointer(new FilterAllPermanentsTargetPointer(filterCopy));
    }

    protected BoostControlledEffect(final BoostControlledEffect effect) {
        super(effect);
    }

    @Override
    public BoostControlledEffect copy() {
        return new BoostControlledEffect(this);
    }
}
