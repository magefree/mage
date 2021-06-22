package mage.abilities.dynamicvalue.common;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.AbilityType;
import mage.game.Game;
import mage.watchers.common.ManaSpentToCastWatcher;

/**
 * @author TheElk801
 */
public enum SnowManaSpentValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (sourceAbility.getAbilityType() == AbilityType.SPELL) {
            return sourceAbility.getManaCostsToPay().getUsedManaToPay().getSnow();
        }
        ManaSpentToCastWatcher watcher = game.getState().getWatcher(ManaSpentToCastWatcher.class);
        if (watcher == null) {
            return 0;
        }
        Mana payment = watcher.getAndResetLastPayment(sourceAbility.getSourceId());
        if (payment == null) {
            return 0;
        }
        return payment.getSnow();
    }

    @Override
    public SnowManaSpentValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "{S} spent to cast this spell";
    }
}
