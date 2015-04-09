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
package mage.sets.planarchaos;

import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SplitSecondAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author jonubuu
 */
public class Extirpate extends CardImpl {

    private static final FilterCard filter = new FilterCard("card in a graveyard other than a basic land card");

    static {
        filter.add(Predicates.not(Predicates.and(new CardTypePredicate(CardType.LAND), new SupertypePredicate("Basic"))));
    }

    public Extirpate(UUID ownerId) {
        super(ownerId, 71, "Extirpate", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{B}");
        this.expansionSetCode = "PLC";

        // Split second
        this.addAbility(new SplitSecondAbility());
        // Choose target card in a graveyard other than a basic land card. Search its owner's graveyard, hand, and library for all cards with the same name as that card and exile them. Then that player shuffles his or her library.
        this.getSpellAbility().addEffect(new ExtirpateEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(filter));
    }

    public Extirpate(final Extirpate card) {
        super(card);
    }

    @Override
    public Extirpate copy() {
        return new Extirpate(this);
    }
}

class ExtirpateEffect extends OneShotEffect {

    public ExtirpateEffect() {
        super(Outcome.Exile);
        this.staticText = "Choose target card in a graveyard other than a basic land card. Search its owner's graveyard, hand, and library for any number of cards with the same name as that card and exile them. Then that player shuffles his or her library";
    }

    public ExtirpateEffect(final ExtirpateEffect effect) {
        super(effect);
    }

    @Override
    public ExtirpateEffect copy() {
        return new ExtirpateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        Card chosenCard = game.getCard(getTargetPointer().getFirst(game, source));
        if (chosenCard != null && sourceObject != null && controller != null) {                                   
            Player owner = game.getPlayer(chosenCard.getOwnerId());
            if (owner == null) {
                return false;
            }
            
            // Exile all cards with the same name
            // Building a card filter with the name
            FilterCard filterNamedCard = new FilterCard();
            filterNamedCard.add(new NamePredicate(chosenCard.getName()));                            

            // The cards you're searching for must be found and exiled if they're in the graveyard because it's a public zone.
            // Finding those cards in the hand and library is optional, because those zones are hidden (even if the hand is temporarily revealed).
            // search cards in graveyard
            for (Card checkCard : owner.getGraveyard().getCards(game)) {
                if (checkCard.getName().equals(chosenCard.getName())) {
                    controller.moveCardToExileWithInfo(checkCard, null, "", source.getSourceId(), game, Zone.GRAVEYARD, true);
                }
            }

            // search cards in hand
            filterNamedCard.setMessage("card named " + chosenCard.getName() + " in the hand of " + owner.getName());
            TargetCardInHand targetCardInHand = new TargetCardInHand(0, Integer.MAX_VALUE, filterNamedCard);
            if (controller.choose(Outcome.Exile, owner.getHand(), targetCardInHand, game)) {
                List<UUID> targets = targetCardInHand.getTargets();
                for (UUID targetId : targets) {
                    Card targetCard = owner.getHand().get(targetId, game);
                    if (targetCard != null) {
                        controller.moveCardToExileWithInfo(targetCard, null, "", source.getSourceId(), game, Zone.HAND, true);                                
                    }
                }
            }
            
            // search cards in Library
            filterNamedCard.setMessage("card named " + chosenCard.getName() + " in the library of " + owner.getName());
            TargetCardInLibrary targetCardInLibrary = new TargetCardInLibrary(0, Integer.MAX_VALUE, filterNamedCard);
            if (controller.searchLibrary(targetCardInLibrary, game, owner.getId())) {
                List<UUID> targets = targetCardInLibrary.getTargets();
                for (UUID targetId : targets) {
                    Card targetCard = owner.getLibrary().getCard(targetId, game);
                    if (targetCard != null) {
                        controller.moveCardToExileWithInfo(targetCard, null, "", source.getSourceId(), game, Zone.LIBRARY, true);                                
                    }
                }
            }
            owner.shuffleLibrary(game);
            return true;
        }
        return false;
    }
        
}
