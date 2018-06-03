
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

    private FilterPermanent filter;
    private Integer multiplier;

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
        if (targetOpponentId != null) {
            int value = game.getBattlefield().countAll(filter, targetOpponentId, game);
            return multiplier * value;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        if (multiplier != null) {
            return multiplier.toString();
        }
        return "X";
    }

    @Override
    public String getMessage() {
        return filter.getMessage() + " target opponent controls";
    }
}
