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
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author North
 */
public class FactOrFiction extends CardImpl<FactOrFiction> {

    public FactOrFiction(UUID ownerId) {
        super(ownerId, 57, "Fact or Fiction", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{3}{U}");
        this.expansionSetCode = "INV";

        this.color.setBlue(true);

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

class FactOrFictionEffect extends OneShotEffect<FactOrFictionEffect> {

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
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Cards cards = new CardsImpl(Zone.PICK);
		int count = Math.min(player.getLibrary().size(), 5);
        for (int i = 0; i < count; i++) {
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                cards.add(card);
                game.setZone(card.getId(), Zone.PICK);
            }
        }
        player.revealCards("Fact or Fiction", cards, game);

        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        if (!opponents.isEmpty()) {
            Player opponent = game.getPlayer(opponents.iterator().next());
            TargetCard target = new TargetCard(0, cards.size(), Zone.PICK, new FilterCard("cards to put in the first pile"));

            Cards pile1 = new CardsImpl();

			while (opponent.choose(Outcome.Neutral, cards, target, game));

			List<UUID> targets = target.getTargets();
			for (UUID targetId : targets) {
				Card card = cards.get(targetId, game);
				if (card != null) {
					pile1.add(card);
					cards.remove(card);
				}
			}

            player.revealCards("Pile 1 (Fact or Fiction)", pile1, game);
            player.revealCards("Pile 2 (Fact or Fiction)", cards, game);

            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Select a pile of cards to put into your hand:");

            StringBuilder sb = new StringBuilder("Pile 1: ");
            for (UUID cardId : pile1) {
                Card card = pile1.get(cardId, game);
                if (card != null) {
                    sb.append(card.getName()).append("; ");
                }
            }
            sb.delete(sb.length() - 2, sb.length());
            choice.getChoices().add(sb.toString());

            sb = new StringBuilder("Pile 2: ");
            for (UUID cardId : cards) {
                Card card = cards.get(cardId, game);
                if (card != null) {
                    sb.append(card.getName()).append("; ");
                }
            }
            sb.delete(sb.length() - 2, sb.length());
            choice.getChoices().add(sb.toString());

            Zone pile1Zone = Zone.GRAVEYARD;
            Zone pile2Zone = Zone.HAND;
            if (player.choose(Outcome.Neutral, choice, game)) {
                if (choice.getChoice().startsWith("Pile 1")) {
                    pile1Zone = Zone.HAND;
                    pile2Zone = Zone.GRAVEYARD;
                }
            }

            for (UUID cardUuid : pile1) {
                Card card = pile1.get(cardUuid, game);
                if (card != null) {
                    card.moveToZone(pile1Zone, source.getId(), game, false);
                }
            }
            for (UUID cardUuid : cards) {
                Card card = cards.get(cardUuid, game);
                if (card != null) {
                    card.moveToZone(pile2Zone, source.getId(), game, false);
                }
            }
        }

        return true;
    }
}
