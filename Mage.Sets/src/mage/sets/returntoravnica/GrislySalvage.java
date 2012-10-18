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
package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public class GrislySalvage extends CardImpl<GrislySalvage> {

    public GrislySalvage(UUID ownerId) {
        super(ownerId, 165, "Grisly Salvage", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{B}{G}");
        this.expansionSetCode = "RTR";

        this.color.setBlack(true);
        this.color.setGreen(true);

        // Reveal the top five cards of your library. You may put a creature or land card from among them into your hand. Put the rest into your graveyard.
        this.getSpellAbility().addEffect(new GrislySalvageEffect());
    }

    public GrislySalvage(final GrislySalvage card) {
        super(card);
    }

    @Override
    public GrislySalvage copy() {
        return new GrislySalvage(this);
    }
}
class GrislySalvageEffect extends OneShotEffect<GrislySalvageEffect> {

    public GrislySalvageEffect() {
        super(Constants.Outcome.DrawCard);
        this.staticText = "Reveal the top five cards of your library. You may put a creature or land card from among them into your hand. Put the rest into your graveyard";
    }

    public GrislySalvageEffect(final GrislySalvageEffect effect) {
        super(effect);
    }

    @Override
    public GrislySalvageEffect copy() {
        return new GrislySalvageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Cards cards = new CardsImpl(Constants.Zone.PICK);

            boolean properCardFound = false;
            int count = Math.min(player.getLibrary().size(), 5);
            for (int i = 0; i < count; i++) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    cards.add(card);
                    if (card.getCardType().contains(CardType.CREATURE) || card.getCardType().contains(CardType.LAND)) {
                        properCardFound = true;
                    }
                }
            }

            if (!cards.isEmpty()) {
                player.revealCards("Grisly Salvage", cards, game);
                FilterCard filter = new FilterCard("creature or land card to put in hand");
                filter.add(Predicates.or(new CardTypePredicate(CardType.CREATURE), new CardTypePredicate(CardType.LAND)));
                TargetCard target = new TargetCard(Constants.Zone.PICK, filter);
                if (properCardFound && player.choose(Constants.Outcome.DrawCard, cards, target, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        cards.remove(card);
                        card.moveToZone(Constants.Zone.HAND, source.getId(), game, false);
                    }

                }

                for (UUID cardId : cards) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        card.moveToZone(Constants.Zone.GRAVEYARD, source.getId(), game, true);
                    }
                }
            }
            return true;
        }
        return false;
    }
}