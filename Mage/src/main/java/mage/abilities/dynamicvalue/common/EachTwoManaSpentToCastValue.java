package mage.abilities.dynamicvalue.common;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.AbilityType;
import mage.constants.ColoredManaSymbol;
import mage.game.Game;
import mage.watchers.common.ManaSpentToCastWatcher;

/**
 * @author TheElk801
 */
public enum EachTwoManaSpentToCastValue implements DynamicValue {
    WHITE(ColoredManaSymbol.W),
    BLUE(ColoredManaSymbol.U),
    BLACK(ColoredManaSymbol.B),
    RED(ColoredManaSymbol.R),
    GREEN(ColoredManaSymbol.G);
    private final ColoredManaSymbol coloredManaSymbol;

    EachTwoManaSpentToCastValue(ColoredManaSymbol manaSymbol) {
        this.coloredManaSymbol = manaSymbol;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (sourceAbility.getAbilityType() == AbilityType.SPELL) {
            return sourceAbility.getManaCostsToPay().getUsedManaToPay().getColor(coloredManaSymbol) / 2;
        }
        Mana payment = game
                .getState()
                .getWatcher(ManaSpentToCastWatcher.class)
                .getLastManaPayment(sourceAbility.getSourceId());
        if (payment == null) {
            return 0;
        }
        return payment.getColor(coloredManaSymbol) / 2;
    }

    @Override
    public EachTwoManaSpentToCastValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "for each {" + this.coloredManaSymbol + "}{" + this.coloredManaSymbol + "} spent to cast it";
    }
}
