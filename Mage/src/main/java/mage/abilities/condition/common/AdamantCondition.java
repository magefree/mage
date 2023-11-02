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
    COLORLESS(null, true),
    ANY(null);

    private final ColoredManaSymbol coloredManaSymbol;
    private final boolean colorless;


    private AdamantCondition(ColoredManaSymbol coloredManaSymbol) {
        this(coloredManaSymbol, false);
    }

    private AdamantCondition(ColoredManaSymbol coloredManaSymbol, boolean colorless) {
        this.coloredManaSymbol = coloredManaSymbol;
        this.colorless = colorless;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getAbilityType() == AbilityType.SPELL) {
            if (colorless) {
                return source.getManaCostsToPay().getUsedManaToPay().getColorless() > 2;
            }
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
        if (colorless) {
            return payment.getColorless() > 2;
        }
        if (coloredManaSymbol == null) {
            return Arrays
                .stream(ColoredManaSymbol.values())
                .map(payment::getColor)
                .anyMatch(i -> i > 2);
        }
        return payment.getColor(coloredManaSymbol) > 2;
    }

    @Override
    public boolean caresAboutManaColor() {
        return true;
    }
}
