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
package mage.cards.l;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public class Lobotomy extends CardImpl {

    public Lobotomy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}{B}");

        // Target player reveals his or her hand, then you choose a card other than a basic land card from it. Search that player's graveyard, hand, and library for all cards with the same name as the chosen card and exile them. Then that player shuffles his or her library.
        this.getSpellAbility().addEffect(new LobotomyEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public Lobotomy(final Lobotomy card) {
        super(card);
    }

    @Override
    public Lobotomy copy() {
        return new Lobotomy(this);
    }
}

class LobotomyEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("card other than a basic land card");

    static {
        filter.add(Predicates.not(Predicates.and(new CardTypePredicate(CardType.LAND), new SupertypePredicate(SuperType.BASIC))));
    }

    public LobotomyEffect() {
        super(Outcome.Benefit);
        staticText = "Target player reveals his or her hand, then you choose a card other than a basic land card from it. Search that player's graveyard, hand, and library for all cards with the same name as the chosen card and exile them. Then that player shuffles his or her library";
    }

    public LobotomyEffect(final LobotomyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (targetPlayer != null && sourceObject != null && controller != null) {

            // reveal hand of target player
            targetPlayer.revealCards(sourceObject.getIdName(), targetPlayer.getHand(), game);

            // You choose card other than a basic land card
            TargetCard target = new TargetCard(Zone.HAND, filter);
            target.setNotTarget(true);
            Card chosenCard = null;
            if (controller.chooseTarget(Outcome.Benefit, targetPlayer.getHand(), target, source, game)) {
                chosenCard = game.getCard(target.getFirstTarget());
            }

            // Exile all cards with the same name
            // Building a card filter with the name
            FilterCard filterNamedCards = new FilterCard();
            if (chosenCard != null) {
                filterNamedCards.add(new NamePredicate(chosenCard.getName()));
                filterNamedCards.setMessage("cards named " + chosenCard.getName());
            } else {
                filterNamedCards.add(new NamePredicate("----")); // so no card matches
            }
            Cards cardsToExile = new CardsImpl();
            // The cards you're searching for must be found and exiled if they're in the graveyard because it's a public zone.
            // Finding those cards in the hand and library is optional, because those zones are hidden (even if the hand is temporarily revealed).
            // search cards in graveyard
            if (chosenCard != null) {
                for (Card checkCard : targetPlayer.getGraveyard().getCards(game)) {
                    if (checkCard.getName().equals(chosenCard.getName())) {
                        cardsToExile.add(checkCard);
                    }
                }
                // search cards in hand
                TargetCard targetCardsHand = new TargetCard(0, Integer.MAX_VALUE, Zone.HAND, filterNamedCards);
                controller.chooseTarget(outcome, targetPlayer.getHand(), targetCardsHand, source, game);
                for (UUID cardId : targetCardsHand.getTargets()) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        cardsToExile.add(card);
                    }
                }
            }

            // search cards in Library
            // If the player has no nonland cards in his or her hand, you can still search that player's library and have him or her shuffle it.
            if (chosenCard != null || controller.chooseUse(outcome, "Search library anyway?", source, game)) {
                TargetCardInLibrary targetCardsLibrary = new TargetCardInLibrary(0, Integer.MAX_VALUE, filterNamedCards);
                controller.searchLibrary(targetCardsLibrary, game, targetPlayer.getId());
                for (UUID cardId : targetCardsLibrary.getTargets()) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        cardsToExile.add(card);
                    }
                }

            }
            if (!cardsToExile.isEmpty()) {
                controller.moveCards(cardsToExile, Zone.EXILED, source, game);
            }
            targetPlayer.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }

    @Override
    public LobotomyEffect copy() {
        return new LobotomyEffect(this);
    }
}
