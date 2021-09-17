package mage.watchers.common;

import mage.MageObjectReference;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author nantuko, BetaSteward_at_googlemail.com
 */
public class CastSpellLastTurnWatcher extends Watcher {

    private final Map<UUID, Integer> amountOfSpellsCastOnPrevTurn = new HashMap<>();
    private final Map<UUID, Integer> amountOfSpellsCastOnCurrentTurn = new HashMap<>();
    private final List<MageObjectReference> spellsCastThisTurnInOrder = new ArrayList<>();
    private int activePlayerPrevTurnCount = 0;
    private int activePlayerThisTurnCount = 0;

    public CastSpellLastTurnWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            spellsCastThisTurnInOrder.add(new MageObjectReference(event.getTargetId(), game));
            UUID playerId = event.getPlayerId();
            if (playerId != null) {
                amountOfSpellsCastOnCurrentTurn.putIfAbsent(playerId, 0);
                amountOfSpellsCastOnCurrentTurn.compute(playerId, (k, a) -> a + 1);
            }
            if (game.isActivePlayer(playerId)) {
                activePlayerThisTurnCount++;
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        amountOfSpellsCastOnPrevTurn.clear();
        amountOfSpellsCastOnPrevTurn.putAll(amountOfSpellsCastOnCurrentTurn);
        amountOfSpellsCastOnCurrentTurn.clear();
        spellsCastThisTurnInOrder.clear();
        activePlayerPrevTurnCount = activePlayerThisTurnCount;
        activePlayerThisTurnCount = 0;
    }

    public Map<UUID, Integer> getAmountOfSpellsCastOnPrevTurn() {
        return amountOfSpellsCastOnPrevTurn;
    }

    public Map<UUID, Integer> getAmountOfSpellsCastOnCurrentTurn() {
        return amountOfSpellsCastOnCurrentTurn;
    }

    public int getAmountOfSpellsAllPlayersCastOnCurrentTurn() {
        return amountOfSpellsCastOnCurrentTurn.values().stream().reduce(0, Integer::sum);
    }

    public int getAmountOfSpellsPlayerCastOnCurrentTurn(UUID playerId) {
        return amountOfSpellsCastOnCurrentTurn.getOrDefault(playerId, 0);
    }

    public int getSpellOrder(MageObjectReference spell, Game game) {
        int index = 0;
        for (MageObjectReference mor : spellsCastThisTurnInOrder) {
            index++;
            if (mor.equals(spell)) {
                return index;
            }
        }
        return 0;
    }

    public int getActivePlayerPrevTurnCount() {
        return activePlayerPrevTurnCount;
    }

    public int getActivePlayerThisTurnCount() {
        return activePlayerThisTurnCount;
    }
}
