package mage.filter.predicate.other;

import mage.constants.TargetController;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.watchers.common.PlayerDamagedBySourceWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class DamagedPlayerThisTurnPredicate implements ObjectSourcePlayerPredicate<ObjectSourcePlayer<Controllable>> {

    private final TargetController controller;

    public DamagedPlayerThisTurnPredicate(TargetController controller) {
        this.controller = controller;
    }

    @Override
    public boolean apply(ObjectSourcePlayer<Controllable> input, Game game) {
        Controllable object = input.getObject();
        UUID playerId = input.getPlayerId();

        switch (controller) {
            case YOU:
                PlayerDamagedBySourceWatcher watcher = game.getState().getWatcher(PlayerDamagedBySourceWatcher.class, playerId);
                if (watcher != null) {
                    return watcher.hasSourceDoneDamage(object.getId(), game);
                }
                break;
            case OPPONENT:
                for (UUID opponentId : game.getOpponents(playerId)) {
                    watcher = game.getState().getWatcher(PlayerDamagedBySourceWatcher.class, opponentId);
                    if (watcher != null) {
                        return watcher.hasSourceDoneDamage(object.getId(), game);
                    }
                }
                break;
            case NOT_YOU:
                for (UUID notYouId : game.getState().getPlayersInRange(playerId, game)) {
                    if (!notYouId.equals(playerId)) {
                        watcher = game.getState().getWatcher(PlayerDamagedBySourceWatcher.class, notYouId);
                        if (watcher != null) {
                            return watcher.hasSourceDoneDamage(object.getId(), game);
                        }
                    }
                }
                break;
            case ANY:
                for (UUID anyId : game.getState().getPlayersInRange(playerId, game)) {
                    watcher = game.getState().getWatcher(PlayerDamagedBySourceWatcher.class, anyId);
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
