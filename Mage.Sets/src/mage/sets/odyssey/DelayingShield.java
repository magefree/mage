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
package mage.sets.odyssey;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class DelayingShield extends CardImpl {

    public DelayingShield(UUID ownerId) {
        super(ownerId, 17, "Delaying Shield", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");
        this.expansionSetCode = "ODY";


        // If damage would be dealt to you, put that many delay counters on Delaying Shield instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DelayingShieldReplacementEffect()));
        
        // At the beginning of your upkeep, remove all delay counters from Delaying Shield. For each delay counter removed this way, you lose 1 life unless you pay {1}{W}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DelayingShieldUpkeepEffect(), TargetController.YOU, false));
    }

    public DelayingShield(final DelayingShield card) {
        super(card);
    }

    @Override
    public DelayingShield copy() {
        return new DelayingShield(this);
    }
}

class DelayingShieldReplacementEffect extends ReplacementEffectImpl {
    
    DelayingShieldReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.PreventDamage);
        staticText = "If damage would be dealt to you, put that many delay counters on {this} instead";
    }

    DelayingShieldReplacementEffect(final DelayingShieldReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamageEvent damageEvent = (DamageEvent) event;
        new AddCountersSourceEffect(CounterType.DELAY.createInstance(damageEvent.getAmount())).apply(game, source);
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getType() == EventType.DAMAGE_PLAYER && event.getTargetId().equals(source.getControllerId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public DelayingShieldReplacementEffect copy() {
        return new DelayingShieldReplacementEffect(this);
    }
}

class DelayingShieldUpkeepEffect extends OneShotEffect {
    
    DelayingShieldUpkeepEffect() {
        super(Outcome.Benefit);
        this.staticText = "remove all delay counters from {this}. For each delay counter removed this way, you lose 1 life unless you pay {1}{W}";
    }
    
    DelayingShieldUpkeepEffect(final DelayingShieldUpkeepEffect effect) {
        super(effect);
    }
    
    @Override
    public DelayingShieldUpkeepEffect copy() {
        return new DelayingShieldUpkeepEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            int numCounters = permanent.getCounters().getCount(CounterType.DELAY);
            permanent.removeCounters(CounterType.DELAY.createInstance(), game);
            for (int i = numCounters; i > 0; i--) {
                if (player.chooseUse(Outcome.Benefit, "Pay {1}{W}? (" + i + " counters left to pay)", game)) {
                    Cost cost = new ManaCostsImpl<>("{1}{W}");
                    if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false)) {
                        continue;
                    }
                }
                new LoseLifeSourceControllerEffect(1).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
