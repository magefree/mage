package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;

/**
 *
 * @author LoneFox
 */

public class PermanentsTargetOpponentControlsCount implements DynamicValue {

    private FilterPermanent filter;
    private Integer multiplier;

    public PermanentsTargetOpponentControlsCount() {
        this(new FilterPermanent(), 1);
    }

    public PermanentsTargetOpponentControlsCount(FilterPermanent filter) {
        this(filter, 1);
    }

    public PermanentsTargetOpponentControlsCount(FilterPermanent filter, Integer multiplier) {
        this.filter = filter;
        this.multiplier = multiplier;
    }

    public PermanentsTargetOpponentControlsCount(final PermanentsTargetOpponentControlsCount dynamicValue) {
        this.filter = dynamicValue.filter;
        this.multiplier = dynamicValue.multiplier;
    }

    @Override
    public DynamicValue copy() {
        return new PermanentsTargetOpponentControlsCount(this);
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if(sourceAbility.getFirstTarget() != null) {
            filter.add(new ControllerIdPredicate(sourceAbility.getFirstTarget()));
            int value = game.getBattlefield().count(filter, sourceAbility.getSourceId(), sourceAbility.getControllerId(), game);
            return multiplier * value;
        }
        else {
            return 0;
        }
    }

    @Override
    public String toString() {
        if(multiplier != null) {
            return multiplier.toString();
        }
        return "X";
    }

    @Override
    public String getMessage() {
        return filter.getMessage() + " target opponent controls";
    }
}
