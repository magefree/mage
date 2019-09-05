package mage.abilities.condition.common;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.AbilityType;
import mage.constants.ColoredManaSymbol;
import mage.game.Game;
import mage.watchers.common.ManaSpentToCastWatcher;

/**
 * @author TheElk801
 */


public enum AdamantCondition implements Condition {
    WHITE(ColoredManaSymbol.W),
    BLUE(ColoredManaSymbol.U),
    BLACK(ColoredManaSymbol.B),
    RED(ColoredManaSymbol.R),
    GREEN(ColoredManaSymbol.G);

    protected ColoredManaSymbol coloredManaSymbol;

    private AdamantCondition(ColoredManaSymbol coloredManaSymbol) {
        this.coloredManaSymbol = coloredManaSymbol;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getAbilityType() == AbilityType.SPELL) {
            return (source.getManaCostsToPay().getPayment().getColor(coloredManaSymbol) > 0);
        }
        ManaSpentToCastWatcher watcher = game.getState().getWatcher(ManaSpentToCastWatcher.class, source.getSourceId());
        if (watcher == null) {
            return false;
        }
        Mana payment = watcher.getAndResetLastPayment();
        if (payment == null) {
            return false;
        }
        return payment.getColor(coloredManaSymbol) > 2;
    }
}
