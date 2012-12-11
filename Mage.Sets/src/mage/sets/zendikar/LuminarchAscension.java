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
package mage.sets.zendikar;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.AngelToken;
import mage.watchers.common.PlayerLostLifeWatcher;

/**
 *
 * @author jeffwadsworth
 */
public class LuminarchAscension extends CardImpl<LuminarchAscension> {
    
    private String rule = "At the beginning of each opponent's end step, if you didn't lose life this turn, you may put a quest counter on Luminarch Ascension. (Damage causes loss of life.)";
    
    public LuminarchAscension(UUID ownerId) {
        super(ownerId, 25, "Luminarch Ascension", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        this.expansionSetCode = "ZEN";

        this.color.setWhite(true);

        // At the beginning of each opponent's end step, if you didn't lose life this turn, you may put a quest counter on Luminarch Ascension.
        this.addAbility(new ConditionalTriggeredAbility(new LuminarchAscensionTriggeredAbility(), YouLostNoLifeThisTurnCondition.getInstance(), rule, true));
        
        // {1}{W}: Put a 4/4 white Angel creature token with flying onto the battlefield. Activate this ability only if Luminarch Ascension has four or more quest counters on it.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new CreateTokenEffect(new AngelToken()), new ManaCostsImpl("{1}{W}"));
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

class LuminarchAscensionTriggeredAbility extends TriggeredAbilityImpl<LuminarchAscensionTriggeredAbility> {

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
        public boolean checkTrigger(GameEvent event, Game game) {
            if (event.getType() == GameEvent.EventType.END_PHASE_PRE
                    && game.getOpponents(controllerId).contains(event.getPlayerId())) {
                return true;
            }
            return false;
        }
    }

class SourceHasCountersCost extends CostImpl<SourceHasCountersCost> {

    private int counters;
    private CounterType counterType;

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
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        return (game.getPermanent(sourceId).getCounters().getCount(counterType) >= counters);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        this.paid = true;
        return paid;
    }

    @Override
    public SourceHasCountersCost copy() {
        return new SourceHasCountersCost(this);
    }
}

class YouLostNoLifeThisTurnCondition implements Condition {

    private static YouLostNoLifeThisTurnCondition fInstance = new YouLostNoLifeThisTurnCondition();

    public static Condition getInstance() {
        return fInstance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        PlayerLostLifeWatcher watcher = (PlayerLostLifeWatcher) game.getState().getWatchers().get("PlayerLostLifeWatcher");
        if (watcher != null) {
            return (watcher.getLiveLost(source.getControllerId()) == 0);
        }
        return false;
    }
}

