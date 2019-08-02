/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author LevelX2 (spjspj)
 */
public class PermanentsEnteredBattlefieldYourLastTurnWatcher extends Watcher {

    private final Map<UUID, List<Permanent>> enteringBattlefield = new HashMap<>();
    private final Map<UUID, List<Permanent>> enteringBattlefieldLastTurn = new HashMap<>();
    private UUID lastActivePlayer = null;

    public PermanentsEnteredBattlefieldYourLastTurnWatcher() {
        super(WatcherScope.GAME);
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
        lastActivePlayer = game.getActivePlayerId();

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
        super.reset();
        if (enteringBattlefieldLastTurn != null
                && lastActivePlayer != null
                && enteringBattlefieldLastTurn.get(lastActivePlayer) != null) {
            enteringBattlefieldLastTurn.remove(lastActivePlayer);
        }
        enteringBattlefieldLastTurn.putAll(enteringBattlefield);
        enteringBattlefield.clear();
        lastActivePlayer = null;
    }

    public List<Permanent> getPermanentsEnteringOnPlayersLastTurn(Game game, UUID playerId) {
        if (game.isActivePlayer(playerId)) {
            return enteringBattlefield.get(playerId);
        }
        return enteringBattlefieldLastTurn.get(playerId);
    }
}
