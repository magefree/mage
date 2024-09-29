package mage.filter.predicate.other;

import mage.constants.TargetController;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.watchers.common.PlayerDamagedBySourceWatcher;

import java.util.UUID;

/**
 * For use in abilities with this predicate:
 * "_ that dealt (combat) damage to _ this turn"
 *
 * @author LevelX2
 */
public class DamagedPlayerThisTurnPredicate implements ObjectSourcePlayerPredicate<Controllable> {

    private final TargetController playerDamaged;

    private final boolean combatDamageOnly;

    public DamagedPlayerThisTurnPredicate(TargetController playerDamaged) {
        this(playerDamaged, false);
    }

    public DamagedPlayerThisTurnPredicate(TargetController playerDamaged, boolean combatDamageOnly) {
        this.playerDamaged = playerDamaged;
        this.combatDamageOnly = combatDamageOnly;
    }

    @Override
    public boolean apply(ObjectSourcePlayer<Controllable> input, Game game) {
        UUID objectId = input.getObject().getId();
        UUID playerId = input.getPlayerId();

        switch (playerDamaged) {
            case YOU:
                // that dealt damage to you this turn
                return playerDealtDamageBy(playerId, objectId, game);
            case SOURCE_CONTROLLER:
                // that dealt damage to this spell/ability's controller this turn
                UUID controllerId = input.getSource().getControllerId();
                return playerDealtDamageBy(controllerId, objectId, game);
            case OPPONENT:
                // that dealt damage to an opponent this turn
                for (UUID opponentId : game.getOpponents(playerId)) {
                    if (playerDealtDamageBy(opponentId, objectId, game)) {
                        return true;
                    }
                }
                return false;
            case NOT_YOU:
                // that dealt damage to another player this turn
                for (UUID notYouId : game.getState().getPlayersInRange(playerId, game)) {
                    if (!notYouId.equals(playerId)) {
                        if (playerDealtDamageBy(notYouId, objectId, game)) {
                            return true;
                        }
                    }
                }
                return false;
            case ANY:
                // that dealt damage to a player this turn
                for (UUID anyId : game.getState().getPlayersInRange(playerId, game)) {
                    if (playerDealtDamageBy(anyId, objectId, game)) {
                        return true;
                    }
                }
                return false;
            default:
                throw new UnsupportedOperationException("TargetController not supported");
        }
    }

    private boolean playerDealtDamageBy(UUID playerId, UUID objectId, Game game) {
        PlayerDamagedBySourceWatcher watcher = game.getState().getWatcher(PlayerDamagedBySourceWatcher.class, playerId);
        if (watcher == null) {
            return false;
        }
        if (combatDamageOnly) {
            return watcher.hasSourceDoneCombatDamage(objectId, game);
        }
        return watcher.hasSourceDoneDamage(objectId, game);
    }

    @Override
    public String toString() {
        return "Damaged player (" + playerDamaged.toString() + ')';
    }
}
