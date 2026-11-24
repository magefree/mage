package mage.abilities.effects.common.continuous;

import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.targetpointer.FilterAllPermanentsTargetPointer;

public class BoostOpponentsEffect extends BoostGainAbilityGenericEffect {

    public BoostOpponentsEffect(int power, int toughness, Duration duration) {
        this(power, toughness, duration, StaticFilters.FILTER_PERMANENT_CREATURES);
    }

    public BoostOpponentsEffect(int power, int toughness, Duration duration, FilterCreaturePermanent filter) {
        super(power, toughness, duration);
        filter = filter.copy();
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.setMessage(filter.getMessage() + " your opponents control");
        this.setTargetPointer(new FilterAllPermanentsTargetPointer(filter));

    }

    protected BoostOpponentsEffect(final BoostOpponentsEffect effect) {
        super(effect);
    }

    @Override
    public BoostOpponentsEffect copy() {
        return new BoostOpponentsEffect(this);
    }

}
