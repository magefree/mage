package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

/**
 * @author nantuko, BetaSteward_at_googlemail.com (spjspj)
 */
public class CastSpellYourLastTurnWatcher extends Watcher {

    private final Map<UUID, Integer> amountOfSpellsCastOnPrevTurn = new HashMap<>();
    private final Map<UUID, Integer> amountOfSpellsCastOnCurrentTurn = new HashMap<>();
    private UUID lastActivePlayer = null;

    public CastSpellYourLastTurnWatcher() {
        super(WatcherScope.GAME);
    }

    public CastSpellYourLastTurnWatcher(final CastSpellYourLastTurnWatcher watcher) {
        super(watcher);
        for (Entry<UUID, Integer> entry : watcher.amountOfSpellsCastOnCurrentTurn.entrySet()) {
            amountOfSpellsCastOnCurrentTurn.put(entry.getKey(), entry.getValue());
        }
        for (Entry<UUID, Integer> entry : watcher.amountOfSpellsCastOnPrevTurn.entrySet()) {
            amountOfSpellsCastOnPrevTurn.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void watch(GameEvent event, Game game) {
        lastActivePlayer = game.getActivePlayerId();
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            UUID playerId = event.getPlayerId();
            if (playerId != null && playerId.equals(lastActivePlayer)) {
                amountOfSpellsCastOnCurrentTurn.putIfAbsent(playerId, 0);
                amountOfSpellsCastOnCurrentTurn.compute(playerId, (k, a) -> a + 1);
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        if (amountOfSpellsCastOnPrevTurn != null
                && lastActivePlayer != null
                && amountOfSpellsCastOnPrevTurn.get(lastActivePlayer) != null) {
            amountOfSpellsCastOnPrevTurn.remove(lastActivePlayer);
        }

        amountOfSpellsCastOnPrevTurn.putAll(amountOfSpellsCastOnCurrentTurn);
        amountOfSpellsCastOnCurrentTurn.clear();
        lastActivePlayer = null;
    }

    public Integer getAmountOfSpellsCastOnPlayersTurn(UUID playerId) {
        return amountOfSpellsCastOnPrevTurn.getOrDefault(playerId, 0);
    }
//
//    @Override
//    public CastSpellYourLastTurnWatcher copy() {
//        return new CastSpellYourLastTurnWatcher(this);
//    }
}
