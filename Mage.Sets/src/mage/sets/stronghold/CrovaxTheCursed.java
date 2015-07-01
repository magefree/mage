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
package mage.sets.stronghold;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class CrovaxTheCursed extends CardImpl {

    public CrovaxTheCursed(UUID ownerId) {
        super(ownerId, 5, "Crovax the Cursed", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.expansionSetCode = "STH";
        this.supertype.add("Legendary");
        this.subtype.add("Vampire");
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Crovax the Cursed enters the battlefield with four +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(4)), "with four +1/+1 counters on it"));

        // At the beginning of your upkeep, you may sacrifice a creature. If you do, put a +1/+1 counter on Crovax. If you don't, remove a +1/+1 counter from Crovax.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new CrovaxTheCursedEffect(), TargetController.YOU, false);
        this.addAbility(ability);


        // {B}: Crovax gains flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl("{B}")));
        
    }

    public CrovaxTheCursed(final CrovaxTheCursed card) {
        super(card);
    }

    @Override
    public CrovaxTheCursed copy() {
        return new CrovaxTheCursed(this);
    }
}

class CrovaxTheCursedEffect extends OneShotEffect {

    public CrovaxTheCursedEffect() {
        super(Outcome.Detriment);
        this.staticText = "you may sacrifice a creature. If you do, put a +1/+1 counter on {this}. If you don't, remove a +1/+1 counter from {this}";
    }

    public CrovaxTheCursedEffect(final CrovaxTheCursedEffect effect) {
        super(effect);
    }

    @Override
    public CrovaxTheCursedEffect copy() {
        return new CrovaxTheCursedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent sourceObject = (Permanent) source.getSourceObjectIfItStillExists(game);
            int creatures = game.getBattlefield().countAll(new FilterCreaturePermanent(), source.getControllerId(), game);
            if (creatures > 0 && controller.chooseUse(outcome, "Sacrifice a creature?", source, game)) {
                if (new SacrificeControllerEffect(new FilterCreaturePermanent(), 1, "").apply(game, source)) {
                    if (sourceObject != null) {
                        sourceObject.getCounters().addCounter(CounterType.P1P1.createInstance());
                        game.informPlayers(controller.getLogName() + " puts a +1/+1 counter on " + sourceObject.getName());
                    }
                }
            } else {
                if (sourceObject != null && sourceObject.getCounters().containsKey(CounterType.P1P1)) {
                    sourceObject.getCounters().removeCounter(CounterType.P1P1, 1);
                    game.informPlayers(controller.getLogName() + " removes a +1/+1 counter from " + sourceObject.getName());
                }
            }
            return true;
        }
        return false;
    }
}
