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
package mage.sets.ninthedition;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.UUID;

/**
 *
 * @author noxx

 */
public class TeferisPuzzleBox extends CardImpl<TeferisPuzzleBox> {

    public TeferisPuzzleBox(UUID ownerId) {
        super(ownerId, 312, "Teferi's Puzzle Box", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "9ED";
    }

    @Override
    public void build() {
        // At the beginning of each player's draw step, that player puts the cards in his or her hand on the bottom of his or her library in any order, then draws that many cards.
        Ability ability = new BeginningOfDrawTriggeredAbility(new TeferisPuzzleBoxEffect(), Constants.TargetController.ANY, false);
        this.addAbility(ability);
    }

    public TeferisPuzzleBox(final TeferisPuzzleBox card) {
        super(card);
    }

    @Override
    public TeferisPuzzleBox copy() {
        return new TeferisPuzzleBox(this);
    }
}

class TeferisPuzzleBoxEffect extends OneShotEffect<TeferisPuzzleBoxEffect> {

    public TeferisPuzzleBoxEffect() {
        super(Constants.Outcome.Neutral);
        staticText = "At the beginning of each player's draw step, that player puts the cards in his or her hand on the bottom of his or her library in any order, then draws that many cards";
    }

    public TeferisPuzzleBoxEffect(final TeferisPuzzleBoxEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            int count = player.getHand().size();

            // puts the cards in his or her hand on the bottom of his or her library in any order
            Cards cards = new CardsImpl();
            for (Card card : player.getHand().getCards(game)) {
                cards.add(card.getId());
            }
            
            TargetCard target = new TargetCard(Constants.Zone.PICK, new FilterCard("card to put on the bottom of your library"));
            target.setRequired(true);
            while (cards.size() > 1) {
                player.choose(Constants.Outcome.Neutral, cards, target, game);
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    card.moveToZone(Constants.Zone.LIBRARY, source.getId(), game, false);
                }
                target.clearChosen();
            }
            if (cards.size() == 1) {
                Card card = cards.get(cards.iterator().next(), game);
                card.moveToZone(Constants.Zone.LIBRARY, source.getId(), game, false);
            }
            player.getHand().clear();

            // draws that many cards
            player.drawCards(count, game);
        }
        return true;
    }

    @Override
    public TeferisPuzzleBoxEffect copy() {
        return new TeferisPuzzleBoxEffect(this);
    }

}
