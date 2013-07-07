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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class DrasticRevelation extends CardImpl<DrasticRevelation> {

    public DrasticRevelation(UUID ownerId) {
        super(ownerId, 111, "Drastic Revelation", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{U}{B}{R}");
        this.expansionSetCode = "ARB";

        this.color.setRed(true);
        this.color.setBlue(true);
        this.color.setBlack(true);

        // Discard your hand. Draw seven cards, then discard three cards at random.
        this.getSpellAbility().addEffect(new DrasticRevelationEffect());
    }

    public DrasticRevelation(final DrasticRevelation card) {
        super(card);
    }

    @Override
    public DrasticRevelation copy() {
        return new DrasticRevelation(this);
    }
}

class DrasticRevelationEffect extends OneShotEffect<DrasticRevelationEffect> {

    DrasticRevelationEffect() {
        super(Outcome.DrawCard);
        staticText = "Discard your hand. Draw seven cards, then discard three cards at random";
    }

    DrasticRevelationEffect(final DrasticRevelationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        you.discardToMax(game);
        you.drawCards(7, game);
        Cards hand = you.getHand();
        for (int i = 0; i < 3; i++) {
            Card card = hand.getRandom(game);
            if (card != null) {
                you.discard(card, source, game);
            }
        }
        return false;
    }

    @Override
    public DrasticRevelationEffect copy() {
        return new DrasticRevelationEffect(this);
    }
}