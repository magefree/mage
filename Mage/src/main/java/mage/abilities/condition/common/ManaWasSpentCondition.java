package mage.abilities.condition.common;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.AbilityType;
import mage.constants.ColoredManaSymbol;
import mage.game.Game;
import mage.watchers.common.ManaSpentToCastWatcher;

/**
 * Checks if the specific mana was spent to cast the spell
 *
 * @author LevelX2
 */
public enum ManaWasSpentCondition implements Condition {
    WHITE(ColoredManaSymbol.W),
    BLUE(ColoredManaSymbol.U),
    BLACK(ColoredManaSymbol.B),
    RED(ColoredManaSymbol.R),
    GREEN(ColoredManaSymbol.G);

    protected ColoredManaSymbol coloredManaSymbol;

    ManaWasSpentCondition(ColoredManaSymbol coloredManaSymbol) {
        this.coloredManaSymbol = coloredManaSymbol;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getAbilityType() == AbilityType.SPELL) {
            return source.getManaCostsToPay().getUsedManaToPay().getColor(coloredManaSymbol) > 0;
        }
        ManaSpentToCastWatcher watcher = game.getState().getWatcher(ManaSpentToCastWatcher.class);
        if (watcher != null) {
            Mana payment = watcher.getLastManaPayment(source.getSourceId());
            if (payment != null) {
                return payment.getColor(coloredManaSymbol) > 0;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "{" + coloredManaSymbol.toString() + "} was spent to cast it";
    }

    @Override
    public boolean caresAboutManaColor() {
        return true;
    }
}
