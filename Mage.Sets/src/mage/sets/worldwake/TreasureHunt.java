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
package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.players.Library;
import mage.players.Player;

/**
 *
 * @author North
 */
public class TreasureHunt extends CardImpl<TreasureHunt> {

    public TreasureHunt(UUID ownerId) {
        super(ownerId, 42, "Treasure Hunt", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{1}{U}");
        this.expansionSetCode = "WWK";

        this.color.setBlue(true);

        // Reveal cards from the top of your library until you reveal a nonland card, then put all cards revealed this way into your hand.
        this.getSpellAbility().addEffect(new TreasureHuntEffect());
    }

    public TreasureHunt(final TreasureHunt card) {
        super(card);
    }

    @Override
    public TreasureHunt copy() {
        return new TreasureHunt(this);
    }
}

class TreasureHuntEffect extends OneShotEffect<TreasureHuntEffect> {

    public TreasureHuntEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal cards from the top of your library until you reveal a nonland card, then put all cards revealed this way into your hand";
    }

    public TreasureHuntEffect(final TreasureHuntEffect effect) {
        super(effect);
    }

    @Override
    public TreasureHuntEffect copy() {
        return new TreasureHuntEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && player.getLibrary().size() > 0) {
            CardsImpl cards = new CardsImpl();
            Library library = player.getLibrary();
            Card card = null;
            do {
                card = library.removeFromTop(game);
                if (card != null) {
                    cards.add(card);
                    card.moveToZone(Zone.HAND, source.getId(), game, false);
                }
            } while (library.size() > 0 && card != null && card.getCardType().contains(CardType.LAND));

            if (!cards.isEmpty()) {
                player.revealCards("Treasure Hunt", cards, game);
            }
            return true;
        }
        return false;
    }
}
