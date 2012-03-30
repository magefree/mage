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

import java.util.Set;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public class TrapfindersTrick extends CardImpl<TrapfindersTrick> {

    public TrapfindersTrick(UUID ownerId) {
        super(ownerId, 73, "Trapfinder's Trick", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{1}{U}");
        this.expansionSetCode = "ZEN";

        this.color.setBlue(true);

        // Target player reveals his or her hand and discards all Trap cards.
        this.getSpellAbility().addEffect(new TrapfindersTrickEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public TrapfindersTrick(final TrapfindersTrick card) {
        super(card);
    }

    @Override
    public TrapfindersTrick copy() {
        return new TrapfindersTrick(this);
    }
}

class TrapfindersTrickEffect extends OneShotEffect<TrapfindersTrickEffect> {

    public TrapfindersTrickEffect() {
        super(Outcome.Discard);
        this.staticText = "Target player reveals his or her hand and discards all Trap cards";
    }

    public TrapfindersTrickEffect(final TrapfindersTrickEffect effect) {
        super(effect);
    }

    @Override
    public TrapfindersTrickEffect copy() {
        return new TrapfindersTrickEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            Cards hand = player.getHand();
            player.revealCards("Trapfinder's Trick", hand, game);
            Set<Card> cards = hand.getCards(game);
            for (Card card : cards) {
                if (card != null && card.hasSubtype("Trap")) {
                    player.discard(card, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
