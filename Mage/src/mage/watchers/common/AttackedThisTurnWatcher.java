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
package mage.watchers.common;

import mage.Constants;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.WatcherImpl;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author magenoxx_at_gmail.com
 */
public class AttackedThisTurnWatcher extends WatcherImpl<AttackedThisTurnWatcher> {

    public Set<UUID> attackedThisTurnCreatures = new HashSet<UUID>();

    public AttackedThisTurnWatcher() {
        super("AttackedThisTurn", Constants.WatcherScope.GAME);
    }

    public AttackedThisTurnWatcher(final AttackedThisTurnWatcher watcher) {
        super(watcher);
        this.attackedThisTurnCreatures.addAll(watcher.attackedThisTurnCreatures);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED) {
            this.attackedThisTurnCreatures.add(event.getSourceId());
        }
    }
    
    public Set<UUID> getAttackedThisTurnCreatures() {
        return this.attackedThisTurnCreatures;
    }

    @Override
    public AttackedThisTurnWatcher copy() {
        return new AttackedThisTurnWatcher(this);
    }

    @Override
    public void reset() {
        super.reset();
        this.attackedThisTurnCreatures.clear();
    }

}