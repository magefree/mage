package mage.abilities.condition.common;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.AbilityType;
import mage.constants.ColoredManaSymbol;
import mage.constants.ManaType;
import mage.game.Game;
import mage.util.CardUtil;
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
    GREEN(ColoredManaSymbol.G),
    COLORLESS(ManaType.COLORLESS);

    protected final ColoredManaSymbol coloredManaSymbol;
    protected final ManaType manaType;

    ManaWasSpentCondition(ColoredManaSymbol coloredManaSymbol) {
        this(coloredManaSymbol, null);
    }

    ManaWasSpentCondition(ManaType manaType) {
        this(null, manaType);
    }

    ManaWasSpentCondition(ColoredManaSymbol coloredManaSymbol, ManaType manaType) {
        this.coloredManaSymbol = coloredManaSymbol;
        this.manaType = manaType;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getAbilityType() == AbilityType.SPELL) {
            return source.getManaCostsToPay().getUsedManaToPay().getColor(coloredManaSymbol) > 0;
        }
        ManaSpentToCastWatcher watcher = game.getState().getWatcher(ManaSpentToCastWatcher.class);
        if (watcher != null) {
            Mana payment = watcher.getManaPayment(CardUtil.getSourceStackMomentReference(game, source));
            if (payment != null) {
                if (coloredManaSymbol != null) {
                    return payment.getColor(coloredManaSymbol) > 0;
                } else if (manaType != null) {
                    return payment.get(manaType) > 0;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        if (coloredManaSymbol != null) {
            return "{" + coloredManaSymbol + "} was spent to cast it";
        } else if (manaType != null) {
            return "{" + manaType + "} was spent to cast it";
        }
        return "";
    }

    @Override
    public boolean caresAboutManaColor() {
        return true;
    }
}
