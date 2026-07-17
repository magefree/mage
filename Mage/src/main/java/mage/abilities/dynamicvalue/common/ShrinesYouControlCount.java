package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;

/**
 * @author TheElk801
 */
public enum ShrinesYouControlCount implements DynamicValue {
    WHERE_X("X", "the number of Shrines you control"),
    FOR_EACH("1", "Shrine you control");

    private static final Hint hint = new ValueHint("Shrines you control", WHERE_X);

    public static Hint getHint() {
        return hint;
    }

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SHRINE);

    private final String number;
    private final String message;

    ShrinesYouControlCount(String number, String message) {
        this.number = number;
        this.message = message;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getBattlefield().count(filter, sourceAbility.getControllerId(), sourceAbility, game);
    }

    @Override
    public ShrinesYouControlCount copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return number;
    }
}
