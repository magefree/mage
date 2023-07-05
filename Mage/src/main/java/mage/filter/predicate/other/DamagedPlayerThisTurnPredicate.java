package mage.filter.predicate.other;

import mage.abilities.Ability;
import mage.constants.TargetController;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.PlayerDamagedBySourceWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class DamagedPlayerThisTurnPredicate implements ObjectSourcePlayerPredicate<Controllable> {

    private final TargetController controller;

    private final boolean combatDamageOnly;

    public DamagedPlayerThisTurnPredicate(TargetController controller) {
        this(controller, false);
    }

    public DamagedPlayerThisTurnPredicate(TargetController controller, boolean combatDamageOnly) {
        this.controller = controller;
        this.combatDamageOnly = combatDamageOnly;
    }

    @Override
    public boolean apply(ObjectSourcePlayer<Controllable> input, Game game) {
        UUID objectId = input.getObject().getId();
        UUID playerId = input.getPlayerId();

        switch (controller) {
            case YOU:
                return playerDealtDamageBy(playerId, objectId, game);
            case CONTROLLER:
                return game.getAbility(input.getSource().getId(), input.getSourceId()).map(
                        ability -> playerDealtDamageBy(ability.getControllerId(), objectId, game)
                ).orElse(false);
            case OPPONENT:
                for (UUID opponentId : game.getOpponents(playerId)) {
                    if (playerDealtDamageBy(opponentId, objectId, game)) {
                        return true;
                    }
                }
                return false;
            case NOT_YOU:
                for (UUID notYouId : game.getState().getPlayersInRange(playerId, game)) {
                    if (!notYouId.equals(playerId)) {
                        if (playerDealtDamageBy(notYouId, objectId, game)) {
                            return true;
                        }
                    }
                }
                return false;
            case ANY:
                for (UUID anyId : game.getState().getPlayersInRange(playerId, game)) {
                    if (playerDealtDamageBy(anyId, objectId, game)) {
                        return true;
                    }
                }
                return false;
            default:
                return false;
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
        return "Damaged player (" + controller.toString() + ')';
    }
}
