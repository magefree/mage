package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.cards.Card;
import mage.constants.SubType;
import mage.game.Game;
import mage.watchers.common.ProwlWatcher;

/**
 * Is it able to activate prowl cost (damage was made)
 *
 * @author JayDi85
 */
public enum ProwlCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        ProwlWatcher watcher = game.getState().getWatcher(ProwlWatcher.class);
        Card card = game.getCard(source.getSourceId());
        if (watcher != null && card != null) {
            for (SubType subtype : card.getSubtype(game)) {
                if (watcher.hasSubtypeMadeCombatDamage(source.getControllerId(), subtype)) {
                    return true;
                }
            }
        }
        return false;
    }
}
