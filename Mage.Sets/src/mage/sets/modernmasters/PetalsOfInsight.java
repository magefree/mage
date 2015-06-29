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
package mage.sets.modernmasters;

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
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class PetalsOfInsight extends CardImpl {

    public PetalsOfInsight(UUID ownerId) {
        super(ownerId, 60, "Petals of Insight", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{4}{U}");
        this.expansionSetCode = "MMA";
        this.subtype.add("Arcane");


        // Look at the top three cards of your library. You may put those cards on the bottom of your library in any order. If you do, return Petals of Insight to its owner's hand. Otherwise, draw three cards.
        this.getSpellAbility().addEffect(new PetalsOfInsightEffect());
    }

    public PetalsOfInsight(final PetalsOfInsight card) {
        super(card);
    }

    @Override
    public PetalsOfInsight copy() {
        return new PetalsOfInsight(this);
    }
}

class PetalsOfInsightEffect extends OneShotEffect {

    PetalsOfInsightEffect() {
        super(Outcome.Benefit);
        this.staticText = "Look at the top three cards of your library. You may put those cards on the bottom of your library in any order. If you do, return {this} to its owner's hand. Otherwise, draw three cards";
    }

    PetalsOfInsightEffect(final PetalsOfInsightEffect effect) {
        super(effect);
    }

    @Override
    public PetalsOfInsightEffect copy() {
        return new PetalsOfInsightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        int count = Math.min(player.getLibrary().size(), 3);
        for (int i = 0; i < count; i++) {
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                cards.add(card);
            }
        }
        player.lookAtCards("Petals of Insight", cards, game);
        if (player.chooseUse(outcome, "Put the cards on the bottom of your library in any order?", source, game)) {
            player.putCardsOnBottomOfLibrary(cards, game, source, true);
            Card spellCard = game.getStack().getSpell(source.getSourceId()).getCard();
            if (spellCard != null) {
                player.moveCardToHandWithInfo(spellCard, source.getSourceId(), game, Zone.STACK);
            }
        } else {
            for (UUID cardId: cards) {
                Card card = game.getCard(cardId);
                if (card != null) {
                    card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                }
            }
            player.drawCards(3, game);
        }
        return true;
    }
}
