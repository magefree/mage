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
package mage.sets.gatecrash;

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
import mage.players.Player;

/**
 * Gatecrash FAQ 01/2013
 *
 * If you reveal a nonland card, you may cast it during the resolution of Unexpected Results.
 * Ignore timing restrictions based on the card's type. Other timing restrictions, such as
 * "Cast [this card] only during combat," must be followed.
 *
 * If you can't cast the card (perhaps because there are no legal targets), or if you choose
 * not to, the card will remain on top of the library.
 *
 * If you cast a spell "without paying its mana cost," you can't pay alternative costs such
 * as overload costs. You can pay additional costs such as kicker costs. If the card has mandatory
 * additional costs, you must pay those.
 *
 * If the card has X Mana in its mana cost, you must choose 0 as its value.
 *
 * If you reveal a land card, Unexpected Results will be returned to your hand only if you put
 * that land card onto the battlefield. If you don't, Unexpected Results will be put into its
 * owner's graveyard.
 *
 * If you reveal a land card and put that card onto the battlefield, Unexpected Results will
 * be put into its owner's hand directly from the stack. It won't be put into any graveyard.
 *
 * @author LevelX2
 */
public class UnexpectedResults extends CardImpl<UnexpectedResults> {

    public UnexpectedResults(UUID ownerId) {
        super(ownerId, 203, "Unexpected Results", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{G}{U}");
        this.expansionSetCode = "GTC";

        this.color.setBlue(true);
        this.color.setGreen(true);

        // Shuffle your library, then reveal the top card. If it's a nonland card, you may cast it without paying its mana cost. If it's a land card, you may put it onto the battlefield and return Unexpected Results to its owner's hand.
        this.getSpellAbility().addEffect(new UnexpectedResultEffect());

    }

    public UnexpectedResults(final UnexpectedResults card) {
        super(card);
    }

    @Override
    public UnexpectedResults copy() {
        return new UnexpectedResults(this);
    }
}

class UnexpectedResultEffect extends OneShotEffect<UnexpectedResultEffect> {

    public UnexpectedResultEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Shuffle your library, then reveal the top card. If it's a nonland card, you may cast it without paying its mana cost. If it's a land card, you may put it onto the battlefield and return Unexpected Results to its owner's hand";
    }

    public UnexpectedResultEffect(final UnexpectedResultEffect effect) {
        super(effect);
    }

    @Override
    public UnexpectedResultEffect copy() {
        return new UnexpectedResultEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card sourceCard = game.getCard(source.getSourceId());
        if (controller == null || sourceCard == null) {
            return false;
        }
        if (controller.getLibrary().size() > 0) {
            controller.getLibrary().shuffle();
            Card card = controller.getLibrary().getFromTop(game);
            if (card == null) {
                return false;
            }
            controller.revealCards(sourceCard.getName(), new CardsImpl(card), game);
            if (card.getCardType().contains(CardType.LAND)) {
                String message = "Put " + card.getName() + " onto the battlefield?";
                if (controller.chooseUse(Outcome.PutLandInPlay, message, game)) {
                    card.putOntoBattlefield(game, Zone.LIBRARY, source.getId(), source.getControllerId());
                    return sourceCard.moveToZone(Zone.HAND, source.getId(), game, false);
                }
            } else {
                if (controller.chooseUse(outcome, new StringBuilder("Cast ").append(card.getName()).append(" without paying its mana cost?").toString(), game)) {
                    return controller.cast(card.getSpellAbility(), game, true);
                }
            }
            return true;
        }
        return false;
    }
}
