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
package mage.sets.commander2013;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.HasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public class SunDroplet extends CardImpl<SunDroplet> {

    public SunDroplet(UUID ownerId) {
        super(ownerId, 261, "Sun Droplet", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "C13";

        // Whenever you're dealt damage, put that many charge counters on Sun Droplet.
        this.addAbility(new SunDropletTriggeredAbility());
        // At the beginning of each upkeep, you may remove a charge counter from Sun Droplet. If you do, you gain 1 life.
        Effect effect = new DoIfCostPaid(new GainLifeEffect(1),new RemoveCountersSourceCost(CounterType.CHARGE.createInstance(1)));
        this.addAbility(new ConditionalTriggeredAbility(new BeginningOfUpkeepTriggeredAbility(effect, TargetController.ANY, false),
                new HasCounterCondition(CounterType.CHARGE, 1),
                "At the beginning of each upkeep, you may remove a charge counter from Sun Droplet. If you do, you gain 1 life", false));
    }

    public SunDroplet(final SunDroplet card) {
        super(card);
    }

    @Override
    public SunDroplet copy() {
        return new SunDroplet(this);
    }
}

class SunDropletTriggeredAbility extends TriggeredAbilityImpl<SunDropletTriggeredAbility> {

    public SunDropletTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SunDropletEffect(), false);
    }

    public SunDropletTriggeredAbility(final SunDropletTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SunDropletTriggeredAbility copy() {
        return new SunDropletTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if ((event.getType() == GameEvent.EventType.DAMAGED_PLAYER && event.getTargetId().equals(this.getControllerId()))) {
            this.getEffects().get(0).setValue("damageAmount", event.getAmount());
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you're dealt damage, put that many charge counters on {this}.";
    }
}

class SunDropletEffect extends OneShotEffect<SunDropletEffect> {

    public SunDropletEffect() {
        super(Outcome.Benefit);
    }

    public SunDropletEffect(final SunDropletEffect effect) {
        super(effect);
    }

    @Override
    public SunDropletEffect copy() {
        return new SunDropletEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return new AddCountersSourceEffect(CounterType.CHARGE.createInstance((Integer) this.getValue("damageAmount"))).apply(game, source);
    }
}
