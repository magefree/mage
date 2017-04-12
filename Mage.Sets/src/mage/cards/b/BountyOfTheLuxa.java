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
package mage.cards.b;

import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfPreCombatMainTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class BountyOfTheLuxa extends CardImpl {

    public BountyOfTheLuxa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{U}");

        //At the beginning of your precombat main phase, remove all flood counters from Bounty of the Luxa. If no flood counters were removed this way, put a flood counter on Bounty of the Luxa and draw a card. Otherwise, add {C}{G}{U} to your mana pool.
        this.addAbility(new BeginningOfPreCombatMainTriggeredAbility(new BountyOfTheLuxaEffect(), TargetController.YOU, false));

    }

    public BountyOfTheLuxa(final BountyOfTheLuxa card) {
        super(card);
    }

    @Override
    public BountyOfTheLuxa copy() {
        return new BountyOfTheLuxa(this);
    }

}

class BountyOfTheLuxaEffect extends OneShotEffect {

    public BountyOfTheLuxaEffect() {
        super(Outcome.Benefit);
        staticText = "remove all flood counters from {this}. If no flood counters were removed this way, put a flood counter on {this} and draw a card. Otherwise, add {C}{G}{U} to your mana pool";
    }

    public BountyOfTheLuxaEffect(final BountyOfTheLuxaEffect effect) {
        super(effect);
    }

    @Override
    public BountyOfTheLuxaEffect copy() {
        return new BountyOfTheLuxaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent bountyOfLuxa = game.getPermanent(source.getSourceId());
        if (controller != null
                && bountyOfLuxa != null) {
            if (bountyOfLuxa.getCounters(game).getCount(CounterType.FLOOD) > 0) {
                bountyOfLuxa.removeCounters(CounterType.FLOOD.createInstance(bountyOfLuxa.getCounters(game).getCount(CounterType.FLOOD)), game);
                if (bountyOfLuxa.getCounters(game).getCount(CounterType.FLOOD) == 0) {
                    Mana manaToAdd = new Mana();
                    manaToAdd.increaseColorless();
                    manaToAdd.increaseGreen();
                    manaToAdd.increaseBlue();
                    controller.getManaPool().addMana(manaToAdd, game, source);
                }
            } else {
                new AddCountersSourceEffect(CounterType.FLOOD.createInstance()).apply(game, source);
                new DrawCardSourceControllerEffect(1).apply(game, source);
            }
            return true;
        }
        return false;
    }

}
