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
package mage.cards.r;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

/**
 *
 * @author jeffwadsworth, MTGFan & L_J
 */
public class ReversePolarity extends CardImpl {

    public ReversePolarity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}{W}");

        // You gain X life, where X is twice the damage dealt to you so far this turn by artifacts.
        this.getSpellAbility().addEffect(new GainLifeEffect(new ReversePolarityAmount(), "You gain X life, where X is twice the damage dealt to you so far this turn by artifacts"));
        this.getSpellAbility().addWatcher(new ReversePolarityWatcher());
    }

    public ReversePolarity(final ReversePolarity card) {
        super(card);
    }

    @Override
    public ReversePolarity copy() {
        return new ReversePolarity(this);
    }
}

class ReversePolarityAmount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        ReversePolarityWatcher watcher = (ReversePolarityWatcher) game.getState().getWatchers().get(ReversePolarityWatcher.class.getSimpleName());
        if(watcher != null) {
            return watcher.getArtifactDamageReceivedThisTurn(source.getControllerId()) * 2;
        }
        return 0;
    }

    @Override
    public ReversePolarityAmount copy() {
        return new ReversePolarityAmount();
    }

    @Override
    public String getMessage() {
        return "";
    }
}

class ReversePolarityWatcher extends Watcher {

    private final Map<UUID, Integer> artifactDamageReceivedThisTurn = new HashMap<>();

    public ReversePolarityWatcher() {
        super(ReversePolarityWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public ReversePolarityWatcher(final ReversePolarityWatcher watcher) {
        super(watcher);
        for (Entry<UUID, Integer> entry : watcher.artifactDamageReceivedThisTurn.entrySet()) {
            artifactDamageReceivedThisTurn.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
            UUID playerId = event.getTargetId();
            if (playerId != null) {
                Permanent permanent = game.getPermanent(event.getSourceId());
                if (permanent != null && permanent.isArtifact()) {
                    artifactDamageReceivedThisTurn.putIfAbsent(playerId, 0);
                    artifactDamageReceivedThisTurn.compute(playerId, (k, v) -> v + event.getAmount());
                }
            }
        }
    }

    public int getArtifactDamageReceivedThisTurn(UUID playerId) {
        return artifactDamageReceivedThisTurn.getOrDefault(playerId, 0);
    }

    @Override
    public void reset() {
        artifactDamageReceivedThisTurn.clear();
    }

    @Override
    public ReversePolarityWatcher copy() {
        return new ReversePolarityWatcher(this);
    }
}
