package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author muz
 */
public class NoncombatDamageToPlayersWatcher extends Watcher {

    private final Set<UUID> damagedThisTurn = new HashSet<>();
    private final Set<UUID> damagedLastTurn = new HashSet<>();

    public NoncombatDamageToPlayersWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BEGINNING_PHASE_PRE && game.getTurnNum() == 1) {
            damagedThisTurn.clear();
            damagedLastTurn.clear();
            return;
        }
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                && !((DamagedPlayerEvent) event).isCombatDamage() && event.getAmount() > 0) {
            damagedThisTurn.add(event.getTargetId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        damagedLastTurn.clear();
        damagedLastTurn.addAll(damagedThisTurn);
        damagedThisTurn.clear();
    }

    public boolean opponentWasDamaged(UUID controllerId, boolean lastTurn, Game game) {
        Set<UUID> damaged = lastTurn ? damagedLastTurn : damagedThisTurn;
        return game.getOpponents(controllerId).stream().anyMatch(damaged::contains);
    }
}
