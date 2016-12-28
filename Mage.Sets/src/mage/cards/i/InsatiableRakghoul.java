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
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author Styxo
 */
public class InsatiableRakghoul extends CardImpl {

    public InsatiableRakghoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}");
        this.subtype.add("Zombie");
        this.subtype.add("Mutant");
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Insatiable Rakghoul enters the battlefield with a +1/+1 counter on it, if a non-artifact creature died this turn.
        this.addAbility(new EntersBattlefieldAbility(new InsatiableRakghoulEffect(), false), new NonArtifactCreaturesDiedWatcher());
    }

    public InsatiableRakghoul(final InsatiableRakghoul card) {
        super(card);
    }

    @Override
    public InsatiableRakghoul copy() {
        return new InsatiableRakghoul(this);
    }
}

class InsatiableRakghoulEffect extends OneShotEffect {

    InsatiableRakghoulEffect() {
        super(Outcome.BoostCreature);
        staticText = "with a +1/+1 counter on it if a non-artifact creature died this turn";
    }

    InsatiableRakghoulEffect(final InsatiableRakghoulEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            NonArtifactCreaturesDiedWatcher watcher = (NonArtifactCreaturesDiedWatcher) game.getState().getWatchers().get("NonArtifactCreaturesDiedWatcher");
            if (watcher != null && watcher.conditionMet()) {
                Permanent permanent = game.getPermanentEntering(source.getSourceId());
                if (permanent != null) {
                    permanent.addCounters(CounterType.P1P1.createInstance(1), source, game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public InsatiableRakghoulEffect copy() {
        return new InsatiableRakghoulEffect(this);
    }
}

class NonArtifactCreaturesDiedWatcher extends Watcher {

    public NonArtifactCreaturesDiedWatcher() {
        super("NonArtifactCreaturesDiedWatcher", WatcherScope.GAME);
    }

    public NonArtifactCreaturesDiedWatcher(final NonArtifactCreaturesDiedWatcher watcher) {
        super(watcher);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.isDiesEvent() && zEvent.getTarget() != null
                    && zEvent.getTarget().getCardType().contains(CardType.CREATURE)
                    && !zEvent.getTarget().getCardType().contains(CardType.ARTIFACT)) {
                condition = true;
            }
        }
    }

    @Override
    public NonArtifactCreaturesDiedWatcher copy() {
        return new NonArtifactCreaturesDiedWatcher(this);
    }

}
