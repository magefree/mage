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
package mage.sets.invasion;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author North
 */
public class FactOrFiction extends CardImpl {

    public FactOrFiction(UUID ownerId) {
        super(ownerId, 57, "Fact or Fiction", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{3}{U}");
        this.expansionSetCode = "INV";

        // Reveal the top five cards of your library. An opponent separates those cards into two piles. Put one pile into your hand and the other into your graveyard.
        this.getSpellAbility().addEffect(new FactOrFictionEffect());
    }

    public FactOrFiction(final FactOrFiction card) {
        super(card);
    }

    @Override
    public FactOrFiction copy() {
        return new FactOrFiction(this);
    }
}

class FactOrFictionEffect extends OneShotEffect {

    public FactOrFictionEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top five cards of your library. An opponent separates those cards into two piles. Put one pile into your hand and the other into your graveyard";
    }

    public FactOrFictionEffect(final FactOrFictionEffect effect) {
        super(effect);
    }

    @Override
    public FactOrFictionEffect copy() {
        return new FactOrFictionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }

        Cards cards = new CardsImpl();
        cards.addAll(controller.getLibrary().getTopCards(game, 5));
        controller.revealCards(sourceObject.getLogName(), cards, game);

        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        if (!opponents.isEmpty()) {
            Player opponent = game.getPlayer(opponents.iterator().next());
            TargetCard target = new TargetCard(0, cards.size(), Zone.LIBRARY, new FilterCard("cards to put in the first pile"));
            List<Card> pile1 = new ArrayList<>();
            if (opponent.choose(Outcome.Neutral, cards, target, game)) {
                List<UUID> targets = target.getTargets();
                for (UUID targetId : targets) {
                    Card card = cards.get(targetId, game);
                    if (card != null) {
                        pile1.add(card);
                        cards.remove(card);
                    }
                }
            }
            List<Card> pile2 = new ArrayList<>();
            pile2.addAll(cards.getCards(game));
            
            boolean choice = controller.choosePile(outcome, "Choose a pile to put into your hand.", pile1, pile2, game);            
            
            Zone pile1Zone = Zone.GRAVEYARD;
            Zone pile2Zone = Zone.HAND;
            if (choice) {
                pile1Zone = Zone.HAND;
                pile2Zone = Zone.GRAVEYARD;
            }

            StringBuilder sb = new StringBuilder("Pile 1, going to ").append(pile1Zone.equals(Zone.HAND)?"Hand":"Graveyard").append (": ");
            int i = 0;
            for (Card card : pile1) {
                i++;
                    sb.append(card.getName());
                if (i < pile1.size()) {
                        sb.append(", ");
                }
                card.moveToZone(pile1Zone, source.getSourceId(), game, false);
            }
            game.informPlayers(sb.toString());

            sb = new StringBuilder("Pile 2, going to ").append(pile2Zone.equals(Zone.HAND)?"Hand":"Graveyard").append (":");
            i = 0;
            for (Card card: pile2) {
                i++;
                sb.append(" ").append(card.getName());
                if (i < pile2.size()) {
                    sb.append(", ");
                }
                card.moveToZone(pile2Zone, source.getSourceId(), game, false);
            }
            game.informPlayers(sb.toString());
        }

        return true;
    }
}
