/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.watchers.common;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageObject;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.watchers.WatcherImpl;

/* 20130711
 *903.14a A player that’s been dealt 21 or more combat damage by the same commander
 * over the course of the game loses the game. (This is a state-based action. See rule 704.) 
 *
 */

/**
 *
 * @author Plopman
 */
public class CommanderCombatDamageWatcher extends WatcherImpl<CommanderCombatDamageWatcher> {

    public Map<UUID, Integer> damageToPlayer = new HashMap<UUID, Integer>();

    public CommanderCombatDamageWatcher(UUID commander) {
        super("CommanderCombatDamageWatcher", WatcherScope.CARD);
        this.sourceId = commander;
    }
    

    public CommanderCombatDamageWatcher(final CommanderCombatDamageWatcher watcher) {
        super(watcher);
        this.damageToPlayer.putAll(watcher.damageToPlayer);
    }

    @Override
    public CommanderCombatDamageWatcher copy() {
        return new CommanderCombatDamageWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.DAMAGED_PLAYER && event instanceof DamagedPlayerEvent) {
            if (sourceId.equals(event.getSourceId())) {
                DamagedPlayerEvent damageEvent = (DamagedPlayerEvent)event;
                UUID playerUUID = event.getTargetId();
                Integer damage = damageToPlayer.get(playerUUID);
                if(damage == null){
                    damage = 0;
                }
                damage += damageEvent.getAmount();
                damageToPlayer.put(playerUUID, damage);
                Player player = game.getPlayer(playerUUID);
                MageObject commander = game.getObject(sourceId);
                if (player != null && commander != null){
                    game.informPlayers(commander.getName() + " did " + damage + " damages to " + player.getName() + " during the game.");
                }
            }
        }
    }

    public Map<UUID, Integer> getDamageToPlayer() {
        return damageToPlayer;
    }    

}
