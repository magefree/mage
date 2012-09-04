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
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public class MakeAWish extends CardImpl<MakeAWish> {

    public MakeAWish(UUID ownerId) {
        super(ownerId, 192, "Make a Wish", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{3}{G}");
        this.expansionSetCode = "ISD";

        this.color.setGreen(true);

        // Return two cards at random from your graveyard to your hand.
        this.getSpellAbility().addEffect(new MakeAWishEffect());
    }

    public MakeAWish(final MakeAWish card) {
        super(card);
    }

    @Override
    public MakeAWish copy() {
        return new MakeAWish(this);
    }
}

class MakeAWishEffect extends OneShotEffect<MakeAWishEffect> {

    public MakeAWishEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return two cards at random from your graveyard to your hand";
    }

    public MakeAWishEffect(final MakeAWishEffect effect) {
        super(effect);
    }

    @Override
    public MakeAWishEffect copy() {
        return new MakeAWishEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Cards cards = player.getGraveyard();
            for (int i = 0; i < 2 && !cards.isEmpty(); i++) {
                Card card = cards.getRandom(game);
                if (card != null) {
                    card.moveToZone(Zone.HAND, source.getId(), game, true);
                    cards.remove(card);
                    game.informPlayers(card.getName() + " returned to the hand of " + player.getName());
                }
            }
            return true;
        }
        return false;
    }
}
