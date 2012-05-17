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
        return amount * game.getBattlefield().count(filter, sourceAbility.getSourceId(), sourceAbility.getControllerId(), game);
    }

    @Override
    public DynamicValue clone() {
        return new PermanentsOnBattlefieldCount(this);
    }

    @Override
    public String toString() {
		if (amount != null)
			return amount.toString();
		return "";
    }

    @Override
    public String getMessage() {
        return filter.getMessage();
    }
}
