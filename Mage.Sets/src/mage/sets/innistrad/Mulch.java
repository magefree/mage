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
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public class Mulch extends CardImpl<Mulch> {

    public Mulch(UUID ownerId) {
        super(ownerId, 196, "Mulch", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{1}{G}");
        this.expansionSetCode = "ISD";

        this.color.setGreen(true);

        // Reveal the top four cards of your library. Put all land cards revealed this way into your hand and the rest into your graveyard.
        this.getSpellAbility().addEffect(new MulchEffect());
    }

    public Mulch(final Mulch card) {
        super(card);
    }

    @Override
    public Mulch copy() {
        return new Mulch(this);
    }
}

class MulchEffect extends OneShotEffect<MulchEffect> {

    public MulchEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top four cards of your library. Put all land cards revealed this way into your hand and the rest into your graveyard";
    }

    public MulchEffect(final MulchEffect effect) {
        super(effect);
    }

    @Override
    public MulchEffect copy() {
        return new MulchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Cards cards = new CardsImpl(Zone.PICK);
            int count = Math.min(player.getLibrary().size(), 4);
            for (int i = 0; i < count; i++) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    cards.add(card);
                    game.setZone(card.getId(), Zone.PICK);
                    if (card.getCardType().contains(CardType.LAND)) {
                        card.moveToZone(Zone.HAND, source.getId(), game, true);
                    } else {
                        card.moveToZone(Zone.GRAVEYARD, source.getId(), game, false);
                    }
                }
            }
            Card sourceCard = game.getCard(source.getSourceId());
            if (!cards.isEmpty() && sourceCard != null) {
                player.revealCards(sourceCard.getName(), cards, game);
                return true;
            }
        }
        return false;
    }
}
