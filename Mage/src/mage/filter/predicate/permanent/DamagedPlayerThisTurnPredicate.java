/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.filter.predicate.permanent;

import java.util.UUID;
import mage.constants.TargetController;
import mage.filter.predicate.ObjectPlayer;
import mage.filter.predicate.ObjectPlayerPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.PlayerDamagedBySourceWatcher;

/**
 *
 * @author LevelX2
 */

public class DamagedPlayerThisTurnPredicate implements ObjectPlayerPredicate<ObjectPlayer<Controllable>> {

    private TargetController controller;

    public DamagedPlayerThisTurnPredicate(TargetController controller) {
        this.controller = controller;
    }

    @Override
    public boolean apply(ObjectPlayer<Controllable> input, Game game) {
        Controllable object = input.getObject();
        UUID playerId = input.getPlayerId();

        switch (controller) {
            case YOU:
                PlayerDamagedBySourceWatcher watcher = (PlayerDamagedBySourceWatcher) game.getState().getWatchers().get("PlayerDamagedBySource",playerId);
                if (watcher != null ) {
                    return watcher.hasSourceDoneDamage(object.getId(), game);
                }
                break;
            case OPPONENT:
                for (UUID opponentId : game.getOpponents(playerId)) {
                    watcher = (PlayerDamagedBySourceWatcher) game.getState().getWatchers().get("PlayerDamagedBySource",opponentId);
                    if (watcher != null ) {
                        return watcher.hasSourceDoneDamage(object.getId(), game);
                    }
                }
                break;
            case NOT_YOU:
                Player you = game.getPlayer(playerId);
                if (you != null) {
                    for (UUID notYouId : you.getInRange()) {
                        if (!notYouId.equals(playerId)) {
                            watcher = (PlayerDamagedBySourceWatcher) game.getState().getWatchers().get("PlayerDamagedBySource",notYouId);
                            if (watcher != null ) {
                                return watcher.hasSourceDoneDamage(object.getId(), game);
                            }
                        }
                    }
                }
                break;
            case ANY:
                you = game.getPlayer(playerId);
                if (you != null) {
                    for (UUID anyId : you.getInRange()) {
                        watcher = (PlayerDamagedBySourceWatcher) game.getState().getWatchers().get("PlayerDamagedBySource",anyId);
                        if (watcher != null ) {
                            return watcher.hasSourceDoneDamage(object.getId(), game);
                        }
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
