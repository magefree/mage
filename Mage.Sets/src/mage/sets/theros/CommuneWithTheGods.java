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
package mage.sets.theros;

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
public class CommuneWithTheGods extends CardImpl {

    public CommuneWithTheGods(UUID ownerId) {
        super(ownerId, 155, "Commune with the Gods", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{1}{G}");
        this.expansionSetCode = "THS";


        // Reveal the top five cards of your library. You may put a creature or enchantment card from among them into your hand. Put the rest into your graveyard.
        this.getSpellAbility().addEffect(new CommuneWithTheGodsEffect());

    }

    public CommuneWithTheGods(final CommuneWithTheGods card) {
        super(card);
    }

    @Override
    public CommuneWithTheGods copy() {
        return new CommuneWithTheGods(this);
    }
}

class CommuneWithTheGodsEffect extends OneShotEffect {

    private static final FilterCard filterPutInHand = new FilterCard("creature or enchantment card to put in hand");
    static {
        filterPutInHand.add(Predicates.or(new CardTypePredicate(CardType.CREATURE), new CardTypePredicate(CardType.ENCHANTMENT)));
    }

    public CommuneWithTheGodsEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top five cards of your library. You may put a creature or enchantment card from among them into your hand. Put the rest into your graveyard";
    }

    public CommuneWithTheGodsEffect(final CommuneWithTheGodsEffect effect) {
        super(effect);
    }

    @Override
    public CommuneWithTheGodsEffect copy() {
        return new CommuneWithTheGodsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject != null && controller != null) {
            Cards cards = new CardsImpl();

            boolean properCardFound = false;
            int count = Math.min(controller.getLibrary().size(), 5);
            for (int i = 0; i < count; i++) {
                Card card = controller.getLibrary().removeFromTop(game);
                if (card != null) {
                    cards.add(card);
                    if (filterPutInHand.match(card, source.getSourceId(), source.getControllerId(), game)) {
                        properCardFound = true;
                    }
                }
            }

            if (!cards.isEmpty()) {
                controller.revealCards(sourceObject.getName(), cards, game);
                TargetCard target = new TargetCard(0, 1, Zone.LIBRARY, filterPutInHand);
                if (properCardFound && controller.choose(Outcome.DrawCard, cards, target, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        cards.remove(card);
                        controller.moveCardToHandWithInfo(card, source.getSourceId(), game, Zone.LIBRARY);
                    }

                }

                for (UUID cardId : cards) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        controller.moveCardToGraveyardWithInfo(card, source.getSourceId(), game, Zone.LIBRARY);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
