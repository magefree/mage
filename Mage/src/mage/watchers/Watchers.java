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

package mage.watchers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Watchers extends HashMap<String, Watcher> {

    public Watchers() {}

    public Watchers(final Watchers watchers) {
        for (Map.Entry<String, Watcher> entry: watchers.entrySet()) {
            this.put(entry.getKey(), entry.getValue().copy());
        }
    }

    public Watchers copy() {
        return new Watchers(this);
    }

    public void add(Watcher watcher) {
        if (!this.containsKey(watcher.getKey()))
            this.put(watcher.getKey(), watcher);
    }

    public void watch(GameEvent event, Game game) {
        for (Watcher watcher: this.values()) {
            watcher.watch(event, game);
        }
    }

    public void reset() {
        for (Watcher watcher: this.values()) {
            watcher.reset();
        }
    }

//    public void setSourceId(UUID sourceId) {
//        for (Watcher watcher: this.values()) {
//            watcher.setSourceId(sourceId);
//        }
//    }
//    
//    public void setControllerId(UUID controllerId) {
//        for (Watcher watcher: this.values()) {
//            watcher.setControllerId(controllerId);
//        }
//    }

    public Watcher get(String key, UUID id) {
        return this.get(id + key);
    }

//    public Watcher get(UUID controllerId, UUID sourceId, String key) {
//        for (Watcher watcher: this) {
//            if ((watcher.getControllerId() == null || watcher.getControllerId().equals(controllerId)) && watcher.getKey().equals(key) && watcher.getSourceId().equals(sourceId))
//                return watcher;
//        }
//        return null;
//    }

}
