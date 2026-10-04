package mage.abilities.effects.common.continuous;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.targetpointer.FilterAllPermanentsTargetPointer;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class BoostAllEffect extends BoostGainAbilityGenericEffect {

    public BoostAllEffect(int power, int toughness, Duration duration) {
        this(power, toughness, duration, false);
    }

    public BoostAllEffect(DynamicValue power, DynamicValue toughness, Duration duration) {
        this(power, toughness, duration, StaticFilters.FILTER_PERMANENT_ALL_CREATURES, false);
    }

    public BoostAllEffect(int power, int toughness, Duration duration, boolean excludeSource) {
        this(power, toughness, duration, StaticFilters.FILTER_PERMANENT_ALL_CREATURES, excludeSource);
    }

    public BoostAllEffect(int power, int toughness, Duration duration, FilterPermanent filter, boolean excludeSource) {
        this(StaticValue.get(power), StaticValue.get(toughness), duration, filter, excludeSource);
    }

    public BoostAllEffect(int power, int toughness, Duration duration, FilterPermanent filter) {
        this(StaticValue.get(power), StaticValue.get(toughness), duration, filter, false);
    }

    public BoostAllEffect(DynamicValue power, DynamicValue toughness, Duration duration, FilterPermanent filter, boolean excludeSource) {
        this(power, toughness, duration, filter, excludeSource, null);
    }

    public BoostAllEffect(DynamicValue power, DynamicValue toughness, Duration duration, FilterPermanent filter, boolean excludeSource, String rule) {
        super(power, toughness, duration);
        filter = filter.copy();
        if (excludeSource) {
            filter.add(AnotherPredicate.instance);
        }
        filter.setMessage(withOtherPrefix(filter.getMessage(), excludeSource));
        this.staticText = rule;
        this.setTargetPointer(new FilterAllPermanentsTargetPointer(filter));
    }


    protected BoostAllEffect(final BoostAllEffect effect) {
        super(effect);
    }

    @Override
    public BoostAllEffect copy() {
        return new BoostAllEffect(this);
    }
}
