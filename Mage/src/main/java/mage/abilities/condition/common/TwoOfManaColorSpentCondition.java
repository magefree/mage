package mage.abilities.condition.common;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.AbilityType;
import mage.constants.ColoredManaSymbol;
import mage.game.Game;
import mage.util.CardUtil;
import mage.watchers.common.ManaSpentToCastWatcher;

/**
 * @author TheElk801
 */
public enum TwoOfManaColorSpentCondition implements Condition {
    WHITE(ColoredManaSymbol.W),
    BLUE(ColoredManaSymbol.U),
    BLACK(ColoredManaSymbol.B),
    RED(ColoredManaSymbol.R),
    GREEN(ColoredManaSymbol.G);

    private final ColoredManaSymbol coloredManaSymbol;

    TwoOfManaColorSpentCondition(ColoredManaSymbol coloredManaSymbol) {
        this.coloredManaSymbol = coloredManaSymbol;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getAbilityType() == AbilityType.SPELL) {
            return source.getManaCostsToPay().getUsedManaToPay().getColor(coloredManaSymbol) >= 2;
        }
        ManaSpentToCastWatcher watcher = game.getState().getWatcher(ManaSpentToCastWatcher.class);
        if (watcher == null) {
            return false;
        }
        Mana payment = watcher.getManaPayment(CardUtil.getSourceStackMomentReference(game, source));
        if (payment == null) {
            return false;
        }
        return payment.getColor(coloredManaSymbol) >= 2;
    }

    @Override
    public boolean caresAboutManaColor() {
        return true;
    }

    @Override
    public String toString() {
        return "{" + coloredManaSymbol + "}{" + coloredManaSymbol + "} was spent to cast it";
    }
}
