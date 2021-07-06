package mage.abilities.condition.common;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.AbilityType;
import mage.game.Game;
import mage.watchers.common.ManaSpentToCastWatcher;

/**
 * @author TheElk801
 */
public enum TreasureSpentToCastCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getAbilityType() == AbilityType.SPELL) {
            return source.getManaCostsToPay().getUsedManaToPay().getTreasure() > 0;
        }
        ManaSpentToCastWatcher watcher = game.getState().getWatcher(ManaSpentToCastWatcher.class);
        if (watcher != null) {
            Mana payment = watcher.getAndResetLastPayment(source.getSourceId());
            if (payment != null) {
                return payment.getTreasure() > 0;
            }
        }
        return false;
    }
}
