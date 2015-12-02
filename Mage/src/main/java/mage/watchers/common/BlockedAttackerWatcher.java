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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import mage.MageObjectReference;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public class BlockedAttackerWatcher extends Watcher {

    public HashMap<MageObjectReference, Set<MageObjectReference>> blockData = new HashMap<>();

    public BlockedAttackerWatcher() {
        super("BlockedAttackerWatcher", WatcherScope.GAME);
    }

    public BlockedAttackerWatcher(final BlockedAttackerWatcher watcher) {
        super(watcher);
        for (MageObjectReference mageObjectReference : watcher.blockData.keySet()) {
            Set<MageObjectReference> blockedAttackers = new HashSet<>();
            blockedAttackers.addAll(watcher.blockData.get(mageObjectReference));
            blockData.put(mageObjectReference, blockedAttackers);
        }
    }

    @Override
    public BlockedAttackerWatcher copy() {
        return new BlockedAttackerWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.BLOCKER_DECLARED) {
            MageObjectReference blocker = new MageObjectReference(event.getSourceId(), game);
            Set<MageObjectReference> blockedAttackers = blockData.get(blocker);
            if (blockedAttackers != null) {
                blockedAttackers.add(new MageObjectReference(event.getTargetId(), game));
            } else {
                blockedAttackers = new HashSet<>();
                blockedAttackers.add(new MageObjectReference(event.getTargetId(), game));
                blockData.put(blocker, blockedAttackers);
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        blockData.clear();
    }

    public boolean creatureHasBlockedAttacker(Permanent attacker, Permanent blocker, Game game) {
        Set<MageObjectReference> blockedAttackers = blockData.get(new MageObjectReference(blocker, game));
        return blockedAttackers != null && blockedAttackers.contains(new MageObjectReference(attacker, game));
    }
}
