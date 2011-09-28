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
package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.TimingRule;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public class DesperateRavings extends CardImpl<DesperateRavings> {

    public DesperateRavings(UUID ownerId) {
        super(ownerId, 139, "Desperate Ravings", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{1}{R}");
        this.expansionSetCode = "ISD";

        this.color.setRed(true);

        // Draw two cards, then discard a card at random.
        this.getSpellAbility().addEffect(new DesperateRavingsEffect());
        // Flashback {2}{U}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{2}{U}"), TimingRule.INSTANT));
    }

    public DesperateRavings(final DesperateRavings card) {
        super(card);
    }

    @Override
    public DesperateRavings copy() {
        return new DesperateRavings(this);
    }
}

class DesperateRavingsEffect extends OneShotEffect<DesperateRavingsEffect> {

    public DesperateRavingsEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Draw two cards, then discard a card at random";
    }

    public DesperateRavingsEffect(final DesperateRavingsEffect effect) {
        super(effect);
    }

    @Override
    public DesperateRavingsEffect copy() {
        return new DesperateRavingsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.drawCards(2, game);
            Cards hand = player.getHand();
            Card card = hand.getRandom(game);
            if (card != null) {
                player.discard(card, source, game);
            }
            return true;
        }
        return false;
    }
}
