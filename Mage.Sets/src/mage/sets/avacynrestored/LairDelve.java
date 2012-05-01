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
package mage.sets.avacynrestored;

import java.util.Set;
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
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author North
 */
public class LairDelve extends CardImpl<LairDelve> {

    public LairDelve(UUID ownerId) {
        super(ownerId, 184, "Lair Delve", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{2}{G}");
        this.expansionSetCode = "AVR";

        this.color.setGreen(true);

        // Reveal the top two cards of your library. Put all creature and land cards revealed this way into your hand and the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new LairDelveEffect());
    }

    public LairDelve(final LairDelve card) {
        super(card);
    }

    @Override
    public LairDelve copy() {
        return new LairDelve(this);
    }
}

class LairDelveEffect extends OneShotEffect<LairDelveEffect> {

    public LairDelveEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top two cards of your library. Put all creature and land cards revealed this way into your hand and the rest on the bottom of your library in any order";
    }

    public LairDelveEffect(final LairDelveEffect effect) {
        super(effect);
    }

    @Override
    public LairDelveEffect copy() {
        return new LairDelveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        CardsImpl cards = new CardsImpl();
        int amount = Math.min(2, player.getLibrary().size());
        for (int i = 0; i < amount; i++) {
            cards.add(player.getLibrary().removeFromTop(game));
        }
        player.revealCards("Lair Delve", cards, game);

        Set<Card> cardsList = cards.getCards(game);
        for (Card card : cardsList) {
            if (card.getCardType().contains(CardType.CREATURE) || card.getCardType().contains(CardType.LAND)) {
                card.moveToZone(Zone.HAND, source.getId(), game, true);
                cards.remove(card);
            }
        }

        TargetCard target = new TargetCard(Zone.PICK, new FilterCard("card to put on the bottom of your library"));
        target.setRequired(true);
        while (cards.size() > 1) {
            player.choose(Outcome.Neutral, cards, target, game);
            Card card = cards.get(target.getFirstTarget(), game);
            if (card != null) {
                cards.remove(card);
                card.moveToZone(Zone.LIBRARY, source.getId(), game, false);
            }
            target.clearChosen();
        }
        if (cards.size() == 1) {
            Card card = cards.get(cards.iterator().next(), game);
            card.moveToZone(Zone.LIBRARY, source.getId(), game, false);
        }

        return true;
    }
}
