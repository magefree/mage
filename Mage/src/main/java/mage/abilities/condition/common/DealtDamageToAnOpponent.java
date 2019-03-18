
package mage.abilities.condition.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.PlayerDamagedBySourceWatcher;

/**
 * @author LevelX2
 */
public class DealtDamageToAnOpponent implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            PlayerDamagedBySourceWatcher watcher = game.getState().getWatcher(PlayerDamagedBySourceWatcher.class, opponentId);
            if (watcher != null) {
                if (watcher.hasSourceDoneDamage(source.getSourceId(), game)) {
                    return true;
                }
            }
        }
        return false;
    }
}
