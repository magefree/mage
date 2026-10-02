package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.NoncombatDamageToPlayersWatcher;

/**
 * @author muz
 */
public enum OpponentDealtNoncombatDamageCondition implements Condition {
    THIS_TURN(false),
    LAST_TURN(true);

    private final boolean lastTurn;

    OpponentDealtNoncombatDamageCondition(boolean lastTurn) {
        this.lastTurn = lastTurn;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        NoncombatDamageToPlayersWatcher watcher = game.getState().getWatcher(NoncombatDamageToPlayersWatcher.class);
        return watcher != null && watcher.opponentWasDamaged(source.getControllerId(), lastTurn, game);
    }

    @Override
    public String toString() {
        return lastTurn ? "an opponent was dealt noncombat damage last turn"
                : "an opponent has been dealt noncombat damage this turn";
    }
}
