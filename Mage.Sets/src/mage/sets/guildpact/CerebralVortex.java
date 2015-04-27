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
package mage.sets.guildpact;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.watchers.Watcher;

/**
 *
 * @author emerald000
 */
public class CerebralVortex extends CardImpl {

    public CerebralVortex(UUID ownerId) {
        super(ownerId, 107, "Cerebral Vortex", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{1}{U}{R}");
        this.expansionSetCode = "GPT";

        // Target player draws two cards, then Cerebral Vortex deals damage to that player equal to the number of cards he or she has drawn this turn.
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(2));
        this.getSpellAbility().addEffect(new CerebralVortexEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addWatcher(new CerebralVortexWatcher());
    }

    public CerebralVortex(final CerebralVortex card) {
        super(card);
    }

    @Override
    public CerebralVortex copy() {
        return new CerebralVortex(this);
    }
}

class CerebralVortexEffect extends OneShotEffect {
    
    CerebralVortexEffect() {
        super(Outcome.Damage);
        this.staticText = ", then Cerebral Vortex deals damage to that player equal to the number of cards he or she has drawn this turn";
    }
    
    CerebralVortexEffect(final CerebralVortexEffect effect) {
        super(effect);
    }
    
    @Override
    public CerebralVortexEffect copy() {
        return new CerebralVortexEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (targetPlayer != null) {
            CerebralVortexWatcher watcher = (CerebralVortexWatcher) game.getState().getWatchers().get("CerebralVortexWatcher");
            if (watcher != null) {
                targetPlayer.damage(watcher.getDraws(targetPlayer.getId()), source.getSourceId(), game, false, true);
                return true;
            }
        }
        return false;
    }
}

class CerebralVortexWatcher extends Watcher {

    private final Map<UUID, Integer> draws = new HashMap<>();

    CerebralVortexWatcher() {
        super("CerebralVortexWatcher", WatcherScope.GAME);
    }

    CerebralVortexWatcher(final CerebralVortexWatcher watcher) {
        super(watcher);
        for (Entry<UUID, Integer> entry: watcher.draws.entrySet()) {
            draws.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.DREW_CARD) {
            int count = 1;
            if (draws.containsKey(event.getPlayerId())) {
                count += draws.get(event.getPlayerId());
            }
            draws.put(event.getPlayerId(), count);
        }
    }

    @Override
    public void reset() {
        super.reset();
        draws.clear();
    }

    public int getDraws(UUID playerId) {
        if (draws.containsKey(playerId)) {
            return draws.get(playerId);
        }
        return 0;
    }

    @Override
    public CerebralVortexWatcher copy() {
        return new CerebralVortexWatcher(this);
    }
}
