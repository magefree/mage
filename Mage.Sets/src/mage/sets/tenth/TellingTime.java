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
package mage.sets.tenth;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Library;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author North
 */
public class TellingTime extends CardImpl<TellingTime> {

    public TellingTime(UUID ownerId) {
        super(ownerId, 114, "Telling Time", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{1}{U}");
        this.expansionSetCode = "10E";

        this.color.setBlue(true);

        // Look at the top three cards of your library.
        // Put one of those cards into your hand, one on top of your library, and one on the bottom of your library.
        this.getSpellAbility().addEffect(new TellingTimeEffect());
    }

    public TellingTime(final TellingTime card) {
        super(card);
    }

    @Override
    public TellingTime copy() {
        return new TellingTime(this);
    }
}

class TellingTimeEffect extends OneShotEffect<TellingTimeEffect> {

    public TellingTimeEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Look at the top three cards of your library. Put one of those cards into your hand, one on top of your library, and one on the bottom of your library.";
    }

    public TellingTimeEffect(final TellingTimeEffect effect) {
        super(effect);
    }

    @Override
    public TellingTimeEffect copy() {
        return new TellingTimeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Cards cards = new CardsImpl();

        Library library = player.getLibrary();
        int n = Math.min(3, library.size());
        for (int i = 0; i < n; i++) {
            cards.add(library.removeFromTop(game));
        }

        player.lookAtCards("Telling Time", cards, game);

        Card card = pickCard(game, player, cards, "card to put in your hand");
        if (card != null) {
            card.moveToZone(Zone.HAND, source.getId(), game, false);
        }

        card = pickCard(game, player, cards, "card to put on top of your library");
        if (card != null) {
            card.moveToZone(Zone.LIBRARY, source.getId(), game, true);
        }

        if (!cards.isEmpty()) {
            card = cards.getRandom(game);
            card.moveToZone(Zone.LIBRARY, source.getId(), game, false);
        }

        return true;
    }

    private Card pickCard(Game game, Player player, Cards cards, String message) {
        if (cards.isEmpty()) {
            return null;
        }
        if (cards.size() == 1) {
            return cards.getRandom(null);
        }

        TargetCard target = new TargetCard(Constants.Zone.PICK, new FilterCard(message));
        target.setRequired(true);
        if (player.choose(Outcome.Benefit, cards, target, game)) {
            Card card = cards.get(target.getFirstTarget(), game);
            if (card != null) {
                cards.remove(card);
                return card;
            }
        }

        return null;
    }
}
