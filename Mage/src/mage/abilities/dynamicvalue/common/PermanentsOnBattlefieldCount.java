package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.filter.FilterPermanent;
import mage.game.Game;

/**
 *
 * @author North
 */
public class PermanentsOnBattlefieldCount implements DynamicValue {

    private FilterPermanent filter;
    private Integer amount;

    public PermanentsOnBattlefieldCount() {
        this(new FilterPermanent(), 1);
    }

    public PermanentsOnBattlefieldCount(FilterPermanent filter) {
        this(filter, 1);
    }

    public PermanentsOnBattlefieldCount(FilterPermanent filter, Integer amount) {
        this.filter = filter;
        this.amount = amount;
    }

    public PermanentsOnBattlefieldCount(final PermanentsOnBattlefieldCount dynamicValue) {
        this.filter = dynamicValue.filter;
        this.amount = dynamicValue.amount;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        int value = game.getBattlefield().count(filter, sourceAbility.getSourceId(), sourceAbility.getControllerId(), game);
        if (amount != null) {
            value *= amount;
        }
        return value;
    }

    @Override
    public DynamicValue copy() {
        return new PermanentsOnBattlefieldCount(this);
    }

    @Override
    public String toString() {
        if (amount != null) {
            return amount.toString();
        }
        return "X";
    }

    @Override
    public String getMessage() {
        return filter.getMessage();
    }
}
