package mage.abilities.condition.common;

import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.ControlledModifiedCreatureAsSpellCastWatcher;

/**
 *
 * @author weirddan455
 */
public enum ControlledModifiedCreatureAsSpellCastCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        ControlledModifiedCreatureAsSpellCastWatcher watcher = game.getState().getWatcher(ControlledModifiedCreatureAsSpellCastWatcher.class);
        if (sourceObject == null || watcher == null) {
            return false;
        }
        return watcher.getModifiedCreatureCount(new MageObjectReference(sourceObject, game)) > 0;
    }

    @Override
    public String toString() {
        return "you controlled a modified creature as you cast this spell";
    }
}
