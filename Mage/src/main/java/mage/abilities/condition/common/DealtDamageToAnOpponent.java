package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.PlayerDamagedBySourceWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public enum DealtDamageToAnOpponent implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            PlayerDamagedBySourceWatcher watcher = game.getState().getWatcher(PlayerDamagedBySourceWatcher.class, opponentId);
            if (watcher != null && watcher.hasSourceDoneDamage(source.getSourceId(), game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "{this} dealt damage to an opponent this turn";
    }
}
