/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.watchers.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public class PermanentsEnteredBattlefieldWatcher extends Watcher {

    private final HashMap<UUID, List<Permanent>> enteringBattlefield = new HashMap<>();
    private final HashMap<UUID, List<Permanent>> enteringBattlefieldLastTurn = new HashMap<>();

    public PermanentsEnteredBattlefieldWatcher() {
        super(PermanentsEnteredBattlefieldWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public PermanentsEnteredBattlefieldWatcher(final PermanentsEnteredBattlefieldWatcher watcher) {
        super(watcher);
        this.enteringBattlefield.putAll(watcher.enteringBattlefield);
        this.enteringBattlefieldLastTurn.putAll(watcher.enteringBattlefieldLastTurn);
    }

    @Override
    public PermanentsEnteredBattlefieldWatcher copy() {
        return new PermanentsEnteredBattlefieldWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
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
        enteringBattlefieldLastTurn.clear();
        enteringBattlefieldLastTurn.putAll(enteringBattlefield);
        enteringBattlefield.clear();
    }

    public List<Permanent> getThisTurnEnteringPermanents(UUID playerId) {
        return enteringBattlefield.get(playerId);
    }

    public boolean anotherCreatureEnteredBattlefieldUnderPlayersControlLastTurn(Permanent sourcePermanent, Game game) {
        if (enteringBattlefieldLastTurn.containsKey(sourcePermanent.getControllerId())) {
            for (Permanent permanent : enteringBattlefieldLastTurn.get(sourcePermanent.getControllerId())) {
                if (!permanent.getId().equals(sourcePermanent.getId())
                        //|| permanent.getZoneChangeCounter(game) == sourcePermanent.getZoneChangeCounter(game) why is this needed?
                        && permanent.isCreature()) {
                    return true;
                }
            }
        }
        return false;
    }
}
