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
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class PlagueBoiler extends CardImpl<PlagueBoiler> {

    public PlagueBoiler(UUID ownerId) {
        super(ownerId, 254, "Plague Boiler", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "C13";

        // At the beginning of your upkeep, put a plague counter on Plague Boiler.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.PLAGUE.createInstance()), TargetController.YOU, false));
        // {1}{B}{G}: Put a plague counter on Plague Boiler or remove a plague counter from it.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new PlagueBoilerEffect(), new ManaCostsImpl("{1}{B}{G}")));
        // When Plague Boiler has three or more plague counters on it, sacrifice it. If you do, destroy all nonland permanents.
        this.addAbility(new PlagueBoilerTriggeredAbility());

    }

    public PlagueBoiler(final PlagueBoiler card) {
        super(card);
    }

    @Override
    public PlagueBoiler copy() {
        return new PlagueBoiler(this);
    }
}

class PlagueBoilerEffect extends OneShotEffect<PlagueBoilerEffect> {

    public PlagueBoilerEffect() {
        super(Outcome.Detriment);
        this.staticText = "Put a plague counter on {this} or remove a plague counter from it";
    }

    public PlagueBoilerEffect(final PlagueBoilerEffect effect) {
        super(effect);
    }

    @Override
    public PlagueBoilerEffect copy() {
        return new PlagueBoilerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            if (!sourcePermanent.getCounters().containsKey(CounterType.PLAGUE) || controller.chooseUse(outcome, "Put a plague counter on? (No removes one)", game)) {
                return new AddCountersSourceEffect(CounterType.PLAGUE.createInstance(), true).apply(game, source);
            } else {
                return new RemoveCounterSourceEffect(CounterType.PLAGUE.createInstance()).apply(game, source);
            }
        }
        return false;
    }
}

class PlagueBoilerTriggeredAbility extends TriggeredAbilityImpl<PlagueBoilerTriggeredAbility> {

    public PlagueBoilerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new PlagueBoilerSacrificeDestroyEffect(), false);
    }

    public PlagueBoilerTriggeredAbility(final PlagueBoilerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PlagueBoilerTriggeredAbility copy() {
        return new PlagueBoilerTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType().equals(EventType.COUNTER_ADDED) && event.getTargetId().equals(this.getSourceId()) && event.getData().equals(CounterType.PLAGUE.getName())) {
            Permanent sourcePermanent = game.getPermanent(this.getSourceId());
            if (sourcePermanent != null && sourcePermanent.getCounters().getCount(CounterType.PLAGUE) >= 3) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When {this} has three or more plague counters on it, " + super.getRule();
    }
}

class PlagueBoilerSacrificeDestroyEffect extends OneShotEffect<PlagueBoilerSacrificeDestroyEffect> {

    public PlagueBoilerSacrificeDestroyEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "sacrifice it. If you do, destroy all nonland permanents";
    }

    public PlagueBoilerSacrificeDestroyEffect(final PlagueBoilerSacrificeDestroyEffect effect) {
        super(effect);
    }

    @Override
    public PlagueBoilerSacrificeDestroyEffect copy() {
        return new PlagueBoilerSacrificeDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            if (sourcePermanent.sacrifice(source.getSourceId(), game)) {
                return new DestroyAllEffect(new FilterNonlandPermanent()).apply(game, source);
            }
        }
        return false;
    }
}
