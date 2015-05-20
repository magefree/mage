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
package mage.sets.dragonsmaze;

import java.util.ArrayList;
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
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 */
public class ReapIntellect extends CardImpl {

    public ReapIntellect(UUID ownerId) {
        super(ownerId, 95, "Reap Intellect", Rarity.MYTHIC, new CardType[]{CardType.SORCERY}, "{X}{2}{U}{B}");
        this.expansionSetCode = "DGM";


        // Target opponent reveals his or her hand. You choose up to X nonland cards from it and exile them. For each card exiled this way, search that player's graveyard, hand, and library for any number of cards with the same name as that card and exile them. Then that player shuffles his or her library.
        this.getSpellAbility().addEffect(new ReapIntellectEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

    }

    public ReapIntellect(final ReapIntellect card) {
        super(card);
    }

    @Override
    public ReapIntellect copy() {
        return new ReapIntellect(this);
    }
}

class ReapIntellectEffect extends OneShotEffect {

    private static final FilterCard filterNonLands = new FilterCard("up to X nonland cards");

    static {
        filterNonLands.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
    }

    public ReapIntellectEffect() {
        super(Outcome.Benefit);
        staticText = "Target opponent reveals his or her hand. You choose up to X nonland cards from it and exile them. For each card exiled this way, search that player's graveyard, hand, and library for any number of cards with the same name as that card and exile them. Then that player shuffles his or her library";
    }

    public ReapIntellectEffect(final ReapIntellectEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (targetPlayer != null && sourceObject != null && controller != null) {
            
            // reveal hand of target player 
            targetPlayer.revealCards(sourceObject.getName(), targetPlayer.getHand(), game);
            
            // Chose cards to exile from hand
            Cards exiledCards = new CardsImpl();
            int xCost = Math.min(source.getManaCostsToPay().getX(), targetPlayer.getHand().size());
            TargetCardInHand target = new TargetCardInHand(0, xCost, filterNonLands);
            target.setNotTarget(true);
            controller.choose(Outcome.Benefit, targetPlayer.getHand(), target, game);
            for (UUID cardId : target.getTargets()) {
                Card chosenCard = game.getCard(cardId);
                if (chosenCard != null) {
                    controller.moveCardToExileWithInfo(chosenCard, null, "", source.getSourceId(), game, Zone.HAND, true);
                    exiledCards.add(chosenCard);
                }                        
            }
            // Exile other cards with the same name
            // 4/15/2013  If you don't exile any cards from the player's hand, you don't search that player's library
            if (!exiledCards.isEmpty()) {

                // Building a card filter with all names
                ArrayList<NamePredicate> names = new ArrayList<>();
                FilterCard filterNamedCards = new FilterCard();
                for (Card card: exiledCards.getCards(game)) {
                    if (exiledCards.size() == 1) {
                        filterNamedCards.add(new NamePredicate(card.getName()));
                    } else {
                        names.add(new NamePredicate(card.getName()));                            
                    }
                }
                if (exiledCards.size() > 1) {
                    filterNamedCards.add(Predicates.or(names));
                }
                
                // search cards in graveyard
                TargetCardInGraveyard targetCardsGraveyard = new TargetCardInGraveyard(0, Integer.MAX_VALUE, filterNamedCards);
                controller.chooseTarget(outcome, targetPlayer.getGraveyard(), targetCardsGraveyard, source, game);
                for(UUID cardId:  targetCardsGraveyard.getTargets()) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        controller.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.GRAVEYARD, true);
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
                TargetCardInLibrary targetCardsLibrary = new TargetCardInLibrary(0, Integer.MAX_VALUE, filterNamedCards);
                controller.searchLibrary(targetCardsLibrary, game, targetPlayer.getId());
                for(UUID cardId:  targetCardsLibrary.getTargets()) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        controller.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.LIBRARY, true);
                    }
                }

            }

            targetPlayer.shuffleLibrary(game);
            return true;
        }
        return false;
    }

    @Override
    public ReapIntellectEffect copy() {
        return new ReapIntellectEffect(this);
    }
}
