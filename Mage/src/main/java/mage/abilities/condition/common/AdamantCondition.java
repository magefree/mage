package mage.abilities.condition.common;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.AbilityType;
import mage.constants.ColoredManaSymbol;
import mage.game.Game;
import mage.watchers.common.ManaSpentToCastWatcher;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public enum AdamantCondition implements Condition {
    WHITE(ColoredManaSymbol.W),
    BLUE(ColoredManaSymbol.U),
    BLACK(ColoredManaSymbol.B),
    RED(ColoredManaSymbol.R),
    GREEN(ColoredManaSymbol.G),
    ANY(null);

    private final ColoredManaSymbol coloredManaSymbol;

    private AdamantCondition(ColoredManaSymbol coloredManaSymbol) {
        this.coloredManaSymbol = coloredManaSymbol;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getAbilityType() == AbilityType.SPELL) {
            if (coloredManaSymbol == null) {
                return Arrays
                        .stream(ColoredManaSymbol.values())
                        .map(source.getManaCostsToPay().getUsedManaToPay()::getColor)
                        .anyMatch(i -> i > 2);
            }
            return source.getManaCostsToPay().getUsedManaToPay().getColor(coloredManaSymbol) > 2;
        }
        ManaSpentToCastWatcher watcher = game.getState().getWatcher(ManaSpentToCastWatcher.class);
        if (watcher == null) {
            return false;
        }
        Mana payment = watcher.getLastManaPayment(source.getSourceId());
        if (payment == null) {
            return false;
        }
        if (coloredManaSymbol == null) {
            return Arrays
                    .stream(ColoredManaSymbol.values())
                    .map(payment::getColor)
                    .anyMatch(i -> i > 2);
        }
        return payment.getColor(coloredManaSymbol) > 2;
    }
}
