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
package mage.sets.seventhedition;

import java.util.List;
import java.util.UUID;
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
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author Quercitron
 */
public class AncestralMemories extends CardImpl<AncestralMemories> {

    public AncestralMemories(UUID ownerId) {
        super(ownerId, 59, "Ancestral Memories", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{U}{U}{U}");
        this.expansionSetCode = "7ED";

        this.color.setBlue(true);

        // Look at the top seven cards of your library. Put two of them into your hand and the rest into your graveyard.
        this.getSpellAbility().addEffect(new AncestralMemoriesEffect());
    }

    public AncestralMemories(final AncestralMemories card) {
        super(card);
    }

    @Override
    public AncestralMemories copy() {
        return new AncestralMemories(this);
    }
}

class AncestralMemoriesEffect extends OneShotEffect<AncestralMemoriesEffect> {

    public AncestralMemoriesEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Look at the top seven cards of your library. Put two of them into your hand and the rest into your graveyard";
    }

    public AncestralMemoriesEffect(final AncestralMemoriesEffect effect) {
        super(effect);
    }

    @Override
    public AncestralMemoriesEffect copy() {
        return new AncestralMemoriesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());

        if (player != null) {
            Cards cards = new CardsImpl(Zone.PICK);
            int cardsCount = Math.min(7, player.getLibrary().size());
            for (int i = 0; i < cardsCount; i++) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    cards.add(card);
                    game.setZone(card.getId(), Zone.PICK);
                }
            }

            if (cards.size() > 0) {
                player.lookAtCards("Ancestral Memories", cards, game);

                TargetCard target = new TargetCard(Math.min(2, cards.size()), Zone.PICK, new FilterCard("two cards to put in your hand"));
                target.setRequired(true);
                if (player.choose(Outcome.Benefit, cards, target, game)) {
                    for (UUID targetId : (List<UUID>)target.getTargets()) {
                        Card card = cards.get(targetId, game);
                        if (card != null) {
                            card.moveToZone(Zone.HAND, source.getId(), game, false);
                            cards.remove(card);
                        }   
                    }
                }

                for (Card card : cards.getCards(game)) {
                    card.moveToZone(Zone.GRAVEYARD, source.getId(), game, true);
                }
            }
            return true;
        }
        return false;
    }
}
