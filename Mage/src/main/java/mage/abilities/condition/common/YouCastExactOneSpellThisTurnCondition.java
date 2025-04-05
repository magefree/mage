package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.SpellsCastWatcher;

/**
 * @author androosss
 */
public enum YouCastExactOneSpellThisTurnCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        return watcher != null && watcher.getSpellsCastThisTurn(source.getControllerId()).size() == 1;
    }
}
