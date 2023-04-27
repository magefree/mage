package mage.abilities.dynamicvalue.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.filter.FilterPermanent;
import mage.game.Game;

/**
 *
 * @author LoneFox
 */
public class PermanentsTargetOpponentControlsCount implements DynamicValue {

    private final FilterPermanent filter;
    private final Integer multiplier;

    public PermanentsTargetOpponentControlsCount() {
        this(new FilterPermanent(), 1);
    }

    public PermanentsTargetOpponentControlsCount(FilterPermanent filter) {
        this(filter, 1);
    }

    public PermanentsTargetOpponentControlsCount(FilterPermanent filter, Integer multiplier) {
        this.filter = filter.copy();
        this.multiplier = multiplier;
    }

    public PermanentsTargetOpponentControlsCount(final PermanentsTargetOpponentControlsCount dynamicValue) {
        this.filter = dynamicValue.filter;
        this.multiplier = dynamicValue.multiplier;
    }

    @Override
    public PermanentsTargetOpponentControlsCount copy() {
        return new PermanentsTargetOpponentControlsCount(this);
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        UUID targetOpponentId = effect.getTargetPointer().getFirst(game, sourceAbility);
        if (targetOpponentId == null) {
            return 0;
        }
        int value = game.getBattlefield().countAll(filter, targetOpponentId, game);
        if (multiplier != null) {
            value *= multiplier;
        }
        return value;
    }

    @Override
    public String toString() {
        return multiplier == null ? "X" : multiplier.toString();
    }

    @Override
    public String getMessage() {
        return (multiplier == null ? "the number of " : "") + filter.getMessage() + " target opponent controls";
    }

    @Override
    public int getSign() {
        return multiplier == null ? 1 : multiplier;
    }
}
