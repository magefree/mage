
package mage.filter.predicate.permanent;

import java.util.UUID;
import mage.constants.TargetController;
import mage.filter.predicate.ObjectPlayer;
import mage.filter.predicate.ObjectPlayerPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.watchers.common.PlayerDamagedBySourceWatcher;

/**
 *
 * @author LevelX2
 */
public class DamagedPlayerThisTurnPredicate implements ObjectPlayerPredicate<ObjectPlayer<Controllable>> {

    private final TargetController controller;

    public DamagedPlayerThisTurnPredicate(TargetController controller) {
        this.controller = controller;
    }

    @Override
    public boolean apply(ObjectPlayer<Controllable> input, Game game) {
        Controllable object = input.getObject();
        UUID playerId = input.getPlayerId();

        switch (controller) {
            case YOU:
                PlayerDamagedBySourceWatcher watcher = (PlayerDamagedBySourceWatcher) game.getState().getWatchers().get(PlayerDamagedBySourceWatcher.class.getSimpleName(), playerId);
                if (watcher != null) {
                    return watcher.hasSourceDoneDamage(object.getId(), game);
                }
                break;
            case OPPONENT:
                for (UUID opponentId : game.getOpponents(playerId)) {
                    watcher = (PlayerDamagedBySourceWatcher) game.getState().getWatchers().get(PlayerDamagedBySourceWatcher.class.getSimpleName(), opponentId);
                    if (watcher != null) {
                        return watcher.hasSourceDoneDamage(object.getId(), game);
                    }
                }
                break;
            case NOT_YOU:
                for (UUID notYouId : game.getState().getPlayersInRange(playerId, game)) {
                    if (!notYouId.equals(playerId)) {
                        watcher = (PlayerDamagedBySourceWatcher) game.getState().getWatchers().get(PlayerDamagedBySourceWatcher.class.getSimpleName(), notYouId);
                        if (watcher != null) {
                            return watcher.hasSourceDoneDamage(object.getId(), game);
                        }
                    }
                }
                break;
            case ANY:
                for (UUID anyId : game.getState().getPlayersInRange(playerId, game)) {
                    watcher = (PlayerDamagedBySourceWatcher) game.getState().getWatchers().get(PlayerDamagedBySourceWatcher.class.getSimpleName(), anyId);
                    if (watcher != null) {
                        return watcher.hasSourceDoneDamage(object.getId(), game);
                    }
                }
                return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "Damaged player (" + controller.toString() + ')';
    }
}
