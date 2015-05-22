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
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public class Mulch extends CardImpl {

    public Mulch(UUID ownerId) {
        super(ownerId, 196, "Mulch", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{1}{G}");
        this.expansionSetCode = "ISD";


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

class MulchEffect extends OneShotEffect {

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
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            Cards cards = new CardsImpl();
            cards.addAll(controller.getLibrary().getTopCards(game, 4));
            if (!cards.isEmpty()) {
                controller.revealCards(sourceObject.getName(), cards, game);
                Cards landCards = new CardsImpl();
                Cards otherCards = new CardsImpl();
                for (Card card: cards.getCards(game)) {
                    if (card.getCardType().contains(CardType.LAND)) {
                        landCards.add(card);
                    } else {
                        otherCards.add(card);
                    }
                }
                controller.moveCards(landCards, Zone.LIBRARY, Zone.HAND, source, game);                
                controller.moveCards(otherCards, Zone.LIBRARY, Zone.GRAVEYARD, source, game);                
                
            }
            return true;
        }
        return false;
    }
}
