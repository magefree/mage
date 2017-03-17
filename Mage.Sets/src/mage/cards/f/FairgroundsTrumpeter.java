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
package mage.cards.f;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

/**
 *
 * @author spjspj
 */
public class FairgroundsTrumpeter extends CardImpl {

    public FairgroundsTrumpeter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add("Elephant");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of each end step, if a +1/+1 counter was placed on a permanent under your control this turn, put a +1/+1 counter on Fairgrounds Trumpeter.
        this.addAbility(new ConditionalTriggeredAbility(new BeginningOfEndStepTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                TargetController.ANY, false), FairgroundsTrumpeterCondition.getInstance(),
                "At the beginning of each end step, if a +1/+1 counter was placed on a permanent under your control this turn, put a +1/+1 counter on {this}."),
                new FairgroundsTrumpeterWatcher());
    }

    public FairgroundsTrumpeter(final FairgroundsTrumpeter card) {
        super(card);
    }

    @Override
    public FairgroundsTrumpeter copy() {
        return new FairgroundsTrumpeter(this);
    }
}

class FairgroundsTrumpeterCondition implements Condition {

    private static final FairgroundsTrumpeterCondition instance = new FairgroundsTrumpeterCondition();

    public static Condition getInstance() {
        return instance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FairgroundsTrumpeterWatcher watcher = (FairgroundsTrumpeterWatcher) game.getState().getWatchers().get(FairgroundsTrumpeterWatcher.class.getName());
        return watcher != null && watcher.p1p1AddedToPermanent(source.getControllerId());
    }

    @Override
    public String toString() {
        return "if a +1/+1 counter was placed on a permanent under your control this turn";
    }

}

class FairgroundsTrumpeterWatcher extends Watcher {

    private final Set<UUID> players = new HashSet<>();

    public FairgroundsTrumpeterWatcher() {
        super(FairgroundsTrumpeterWatcher.class.getName(), WatcherScope.GAME);
    }

    public FairgroundsTrumpeterWatcher(final FairgroundsTrumpeterWatcher watcher) {
        super(watcher);
        this.players.addAll(watcher.players);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.COUNTER_ADDED && event.getData().equals(CounterType.P1P1.getName())) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent == null) {
                permanent = game.getPermanentEntering(event.getTargetId());
            }
            if (permanent != null) {
                players.add(permanent.getControllerId());
            }
        }
    }

    @Override
    public void reset() {
        players.clear();
    }

    public boolean p1p1AddedToPermanent(UUID playerId) {
        return players.contains(playerId);
    }

    @Override
    public FairgroundsTrumpeterWatcher copy() {
        return new FairgroundsTrumpeterWatcher(this);
    }
}
