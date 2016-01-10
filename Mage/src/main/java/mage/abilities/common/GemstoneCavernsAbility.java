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
package mage.abilities.common;

/**
 *
 * @author emerald000
 */
import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

public class GemstoneCavernsAbility extends StaticAbility {
    public GemstoneCavernsAbility() {
        super(Zone.HAND, new GemstoneCavernsEffect());
    }

    public GemstoneCavernsAbility(final GemstoneCavernsAbility ability) {
        super(ability);
    }

    @Override
    public GemstoneCavernsAbility copy() {
        return new GemstoneCavernsAbility(this);
    }

       @Override
    public String getRule() {
        return "If {this} is in your opening hand and you're not playing first, you may begin the game with {this} on the battlefield with a luck counter on it. If you do, exile a card from your hand.";
    }
}

class GemstoneCavernsEffect extends OneShotEffect {
    
    GemstoneCavernsEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "you may begin the game with {this} on the battlefield with a luck counter on it. If you do, exile a card from your hand.";
    }
    
    GemstoneCavernsEffect(final GemstoneCavernsEffect effect) {
        super(effect);
    }
    
    @Override
    public GemstoneCavernsEffect copy() {
        return new GemstoneCavernsEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Card card = game.getCard(source.getSourceId());
            if (card != null) {
                if (card.putOntoBattlefield(game, Zone.HAND, source.getSourceId(), source.getControllerId())) {
                    Permanent permanent = game.getPermanent(card.getId());
                    if (permanent != null) {
                        permanent.addCounters(CounterType.LUCK.createInstance(), game);
                        Cost cost = new ExileFromHandCost(new TargetCardInHand());
                        if (cost.canPay(source, source.getSourceId(), source.getControllerId(), game)) {
                            cost.pay(source, game, source.getSourceId(), source.getControllerId(), true, null);
                        }
                    }
                }
            }
        }
        return false;
    }
}