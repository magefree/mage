
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.filter.FilterPermanent;
import mage.game.Game;

/**
 *
 * @author North
 */
public class PermanentsOnBattlefieldCount implements DynamicValue {

    private FilterPermanent filter;
    private Integer multiplier;

    public PermanentsOnBattlefieldCount() {
        this(new FilterPermanent(), 1);
    }

    public PermanentsOnBattlefieldCount(FilterPermanent filter) {
        this(filter, 1);
    }

    /**
     * 
     * @param filter
     * @param multiplier 
     */
    public PermanentsOnBattlefieldCount(FilterPermanent filter, Integer multiplier) {
        this.filter = filter;
        this.multiplier = multiplier;
    }

    public PermanentsOnBattlefieldCount(final PermanentsOnBattlefieldCount dynamicValue) {
        this.filter = dynamicValue.filter;
        this.multiplier = dynamicValue.multiplier;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int value = game.getBattlefield().count(filter, sourceAbility.getSourceId(), sourceAbility.getControllerId(), game);
        if (multiplier != null) {
            value *= multiplier;
        }
        return value;
    }

    @Override
    public PermanentsOnBattlefieldCount copy() {
        return new PermanentsOnBattlefieldCount(this);
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
        return multiplier == null ? "the number of " + filter.getMessage() : filter.getMessage();
    }
}
