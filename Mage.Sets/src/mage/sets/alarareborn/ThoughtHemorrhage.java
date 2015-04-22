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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.NameACardEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author jeffwadsworth
 */
public class ThoughtHemorrhage extends CardImpl {

    public ThoughtHemorrhage(UUID ownerId) {
        super(ownerId, 47, "Thought Hemorrhage", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{B}{R}");
        this.expansionSetCode = "ARB";


        

        // Name a nonland card. Target player reveals his or her hand. Thought Hemorrhage deals 3 damage to that player for each card with that name revealed this way. Search that player's graveyard, hand, and library for all cards with that name and exile them. Then that player shuffles his or her library.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new NameACardEffect(NameACardEffect.TypeOfName.NON_LAND_NAME));
        this.getSpellAbility().addEffect(new ThoughtHemorrhageEffect());
    }

    public ThoughtHemorrhage(final ThoughtHemorrhage card) {
        super(card);
    }

    @Override
    public ThoughtHemorrhage copy() {
        return new ThoughtHemorrhage(this);
    }
}

class ThoughtHemorrhageEffect extends OneShotEffect {

    final String rule = "Target player reveals his or her hand. {this} deals 3 damage to that player for each card with that name revealed this way. Search that player's graveyard, hand, and library for all cards with that name and exile them. Then that player shuffles his or her library";

    public ThoughtHemorrhageEffect() {
        super(Outcome.Detriment);
        staticText = rule;
    }

    public ThoughtHemorrhageEffect(final ThoughtHemorrhageEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + NameACardEffect.INFO_KEY);
        if (sourceObject != null && controller != null && cardName != null && !cardName.isEmpty()) {        
            Player targetPlayer = game.getPlayer(source.getFirstTarget());
            if (targetPlayer != null) {
                targetPlayer.revealCards("hand of " + targetPlayer.getName(), targetPlayer.getHand(), game);
                int cardsFound = 0;
                for (Card card : targetPlayer.getHand().getCards(game)) {
                    if (card.getName().equals(cardName)) {
                        cardsFound++;
                    }
                }
                if (cardsFound > 0) {
                    targetPlayer.damage(3 * cardsFound, source.getSourceId(), game, false, true);
                }
                // Exile all cards with the same name
                // Building a card filter with the name
                FilterCard filterNamedCards = new FilterCard();
                filterNamedCards.add(new NamePredicate(cardName));

                // The cards you're searching for must be found and exiled if they're in the graveyard because it's a public zone.
                // Finding those cards in the hand and library is optional, because those zones are hidden (even if the hand is temporarily revealed).
                // search cards in graveyard
                for (Card checkCard : targetPlayer.getGraveyard().getCards(game)) {
                    if (checkCard.getName().equals(cardName)) {
                        controller.moveCardToExileWithInfo(checkCard, null, "", source.getSourceId(), game, Zone.GRAVEYARD, true);
                    }
                }

                // search cards in hand
                TargetCardInHand targetCardsHand = new TargetCardInHand(0, Integer.MAX_VALUE, filterNamedCards);
                controller.chooseTarget(outcome, targetPlayer.getGraveyard(), targetCardsHand, source, game);
                for(UUID cardId:  targetCardsHand.getTargets()) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        controller.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.HAND, true);
                    }
                }

                // search cards in Library
                // If the player has no nonland cards in his or her hand, you can still search that player's library and have him or her shuffle it.
                TargetCardInLibrary targetCardsLibrary = new TargetCardInLibrary(0, Integer.MAX_VALUE, filterNamedCards);
                controller.searchLibrary(targetCardsLibrary, game, targetPlayer.getId());
                for(UUID cardId:  targetCardsLibrary.getTargets()) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        controller.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.LIBRARY, true);
                    }
                }
                targetPlayer.shuffleLibrary(game);
                return true;     
            }
        }
        return false;
    }

    @Override
    public ThoughtHemorrhageEffect copy() {
        return new ThoughtHemorrhageEffect(this);
    }
}
