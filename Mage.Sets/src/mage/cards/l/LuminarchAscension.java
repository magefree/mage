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
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.token.AngelToken;
import mage.watchers.common.PlayerLostLifeWatcher;

/**
 *
 * @author jeffwadsworth
 */
public class LuminarchAscension extends CardImpl {

    private String rule = "At the beginning of each opponent's end step, if you didn't lose life this turn, you may put a quest counter on {this}. (Damage causes loss of life.)";

    public LuminarchAscension(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // At the beginning of each opponent's end step, if you didn't lose life this turn, you may put a quest counter on Luminarch Ascension.
        this.addAbility(new ConditionalTriggeredAbility(new LuminarchAscensionTriggeredAbility(), YouLostNoLifeThisTurnCondition.instance, rule));

        // {1}{W}: Create a 4/4 white Angel creature token with flying. Activate this ability only if Luminarch Ascension has four or more quest counters on it.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new AngelToken()), new ManaCostsImpl("{1}{W}"));
        ability.addCost(new SourceHasCountersCost(4, CounterType.QUEST));
        this.addAbility(ability);
    }

    public LuminarchAscension(final LuminarchAscension card) {
        super(card);
    }

    @Override
    public LuminarchAscension copy() {
        return new LuminarchAscension(this);
    }
}

class LuminarchAscensionTriggeredAbility extends TriggeredAbilityImpl {

    public LuminarchAscensionTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.QUEST.createInstance()), true);
    }

    public LuminarchAscensionTriggeredAbility(LuminarchAscensionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LuminarchAscensionTriggeredAbility copy() {
        return new LuminarchAscensionTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.END_TURN_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getOpponents(controllerId).contains(event.getPlayerId());
    }
}

class SourceHasCountersCost extends CostImpl {

    private final int counters;
    private final CounterType counterType;

    public SourceHasCountersCost(int counters, CounterType counterType) {
        this.counters = counters;
        this.counterType = counterType;
        this.text = "Activate this ability only if Luminarch Ascension has 4 or more quest counters on it";
    }

    public SourceHasCountersCost(final SourceHasCountersCost cost) {
        super(cost);
        this.counters = cost.counters;
        this.counterType = cost.counterType;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return (game.getPermanent(sourceId).getCounters(game).getCount(counterType) >= counters);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        this.paid = true;
        return paid;
    }

    @Override
    public SourceHasCountersCost copy() {
        return new SourceHasCountersCost(this);
    }
}

enum YouLostNoLifeThisTurnCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        PlayerLostLifeWatcher watcher = (PlayerLostLifeWatcher) game.getState().getWatchers().get(PlayerLostLifeWatcher.class.getSimpleName());
        if (watcher != null) {
            return (watcher.getLiveLost(source.getControllerId()) == 0);
        }
        return false;
    }
}
