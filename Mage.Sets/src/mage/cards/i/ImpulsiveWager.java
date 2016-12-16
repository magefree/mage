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

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author Styxo
 */
public class ImpulsiveWager extends CardImpl {

    public ImpulsiveWager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // As an additional cost to cast Irresponsible Gambling, discard a card at random.
        this.getSpellAbility().addCost(new DiscardCardCost(true));

        // If the discarded card was a nonland card, draw two cards. Otherwise, put a bounty counter on target creature.
        this.getSpellAbility().addEffect(new ImpulsiveWagerEffect());
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());
    }

    public ImpulsiveWager(final ImpulsiveWager card) {
        super(card);
    }

    @Override
    public ImpulsiveWager copy() {
        return new ImpulsiveWager(this);
    }
}

class ImpulsiveWagerEffect extends OneShotEffect {

    public ImpulsiveWagerEffect() {
        super(Outcome.Benefit);
        staticText = "If the discarded card was a nonland card, draw two cards. Otherwise, put a bounty counter on target creature";
    }

    public ImpulsiveWagerEffect(final ImpulsiveWagerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            DiscardCardCost cost = (DiscardCardCost) source.getCosts().get(0);
            if (cost != null) {
                List<Card> cards = cost.getCards();
                if (cards.size() == 1 && cards.get(0).getCardType().contains(CardType.LAND)) {
                    Effect effect = new AddCountersTargetEffect(CounterType.BOUNTY.createInstance());
                    effect.setTargetPointer(getTargetPointer());
                    effect.apply(game, source);
                } else {
                    player.drawCards(2, game);
                }

            }
            return true;
        }
        return false;
    }

    @Override
    public ImpulsiveWagerEffect copy() {
        return new ImpulsiveWagerEffect(this);
    }
}
