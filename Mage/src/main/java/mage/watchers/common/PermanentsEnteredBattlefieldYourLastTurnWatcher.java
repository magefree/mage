/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.watchers.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2 (spjspj)
 */
public class PermanentsEnteredBattlefieldYourLastTurnWatcher extends Watcher {

    private final HashMap<UUID, List<Permanent>> enteringBattlefield = new HashMap<>();
    private final HashMap<UUID, List<Permanent>> enteringBattlefieldLastTurn = new HashMap<>();
    private final Map<UUID, Integer> activePlayer = new HashMap<>();

    public PermanentsEnteredBattlefieldYourLastTurnWatcher() {
        super(PermanentsEnteredBattlefieldYourLastTurnWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public PermanentsEnteredBattlefieldYourLastTurnWatcher(final PermanentsEnteredBattlefieldYourLastTurnWatcher watcher) {
        super(watcher);
        this.enteringBattlefield.putAll(watcher.enteringBattlefield);
        this.enteringBattlefieldLastTurn.putAll(watcher.enteringBattlefieldLastTurn);
    }

    @Override
    public PermanentsEnteredBattlefieldYourLastTurnWatcher copy() {
        return new PermanentsEnteredBattlefieldYourLastTurnWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        activePlayer.clear();
        activePlayer.putIfAbsent(game.getActivePlayerId(), 0);

        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            Permanent perm = game.getPermanentEntering(event.getTargetId());
            if (perm == null) {
                perm = game.getPermanent(event.getTargetId());
            }
            if (perm != null) {
                List<Permanent> permanents;
                if (!enteringBattlefield.containsKey(perm.getControllerId())) {
                    permanents = new ArrayList<>();
                    enteringBattlefield.put(perm.getControllerId(), permanents);
                } else {
                    permanents = enteringBattlefield.get(perm.getControllerId());
                }
                permanents.add(perm.copy()); // copy needed because attributes like color could be changed later
            }
        }
    }

    @Override
    public void reset() {
        for (Entry<UUID, List<Permanent>> entry : enteringBattlefieldLastTurn.entrySet()) {
            for (Entry<UUID, Integer> entry2 : activePlayer.entrySet()) {
                if (entry2.getKey() == entry.getKey()) {
                    enteringBattlefieldLastTurn.remove(entry.getKey());
                }
            }
        }
        enteringBattlefieldLastTurn.putAll(enteringBattlefield);
        enteringBattlefield.clear();
        activePlayer.clear();
    }

    public List<Permanent> getPermanentsEnteringOnPlayersLastTurn(Game game, UUID playerId) {
        if (game.getActivePlayerId() == playerId) {
            return enteringBattlefield.get(playerId);
        }
        return enteringBattlefieldLastTurn.get(playerId);
    }
}
