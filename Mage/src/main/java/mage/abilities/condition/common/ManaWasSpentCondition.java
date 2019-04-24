

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


public class ManaWasSpentCondition implements Condition {

    protected ColoredManaSymbol coloredManaSymbol;

    public ManaWasSpentCondition(ColoredManaSymbol coloredManaSymbol) {
        this.coloredManaSymbol = coloredManaSymbol;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getAbilityType() == AbilityType.SPELL) {
            return (source.getManaCostsToPay().getPayment().getColor(coloredManaSymbol) > 0);
        }
        ManaSpentToCastWatcher watcher = (ManaSpentToCastWatcher) game.getState().getWatchers().get(ManaSpentToCastWatcher.class.getSimpleName(), source.getSourceId());
        if (watcher != null) {
            Mana payment = watcher.getAndResetLastPayment();
            if (payment != null) {
                return payment.getColor(coloredManaSymbol) > 0;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return new StringBuilder("{").append(coloredManaSymbol.toString()).append("} was spent to cast it").toString();
    }

}
