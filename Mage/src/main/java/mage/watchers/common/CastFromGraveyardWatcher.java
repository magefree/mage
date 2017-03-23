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
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public class CastFromGraveyardWatcher extends Watcher {

    // holds which spell with witch zone change counter was cast from graveyard
    private final Map<UUID, HashSet<Integer>> spellsCastFromGraveyard = new HashMap<>();

    public CastFromGraveyardWatcher() {
        super(CastFromGraveyardWatcher.class.getName(), WatcherScope.GAME);
    }

    public CastFromGraveyardWatcher(final CastFromGraveyardWatcher watcher) {
        super(watcher);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        /**
         * This does still not handle if a spell is cast from hand and comes to
         * play from other zones during the same step. But at least the state is
         * reset if the game comes to a new step
         */
        if (event.getType() == GameEvent.EventType.SPELL_CAST && event.getZone() == Zone.GRAVEYARD) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (spell != null) {
                HashSet<Integer> zcc = spellsCastFromGraveyard.computeIfAbsent(spell.getSourceId(), k -> new HashSet<>());
                zcc.add(spell.getZoneChangeCounter(game));
            }

        }
    }

    public boolean spellWasCastFromGraveyard(UUID sourceId, int zcc) {
        Set zccSet = spellsCastFromGraveyard.get(sourceId);
        return zccSet != null && zccSet.contains(zcc);

    }

    @Override
    public void reset() {
        super.reset();
        spellsCastFromGraveyard.clear();
    }

    @Override
    public CastFromGraveyardWatcher copy() {
        return new CastFromGraveyardWatcher(this);
    }
}
