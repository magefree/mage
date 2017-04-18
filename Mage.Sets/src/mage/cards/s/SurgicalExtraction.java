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
package mage.cards.s;

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
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author North
 */
public class SurgicalExtraction extends CardImpl {

    private static final FilterCard filter = new FilterCard("card in a graveyard other than a basic land card");

    static {
        filter.add(Predicates.not(Predicates.and(new CardTypePredicate(CardType.LAND), new SupertypePredicate(SuperType.BASIC))));
    }

    public SurgicalExtraction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B/P}");

        // Choose target card in a graveyard other than a basic land card. Search its owner's graveyard,
        // hand, and library for any number of cards with the same name as that card and exile them.
        // Then that player shuffles his or her library.
        this.getSpellAbility().addEffect(new SurgicalExtractionEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(filter));
    }

    public SurgicalExtraction(final SurgicalExtraction card) {
        super(card);
    }

    @Override
    public SurgicalExtraction copy() {
        return new SurgicalExtraction(this);
    }
}

class SurgicalExtractionEffect extends OneShotEffect {

    public SurgicalExtractionEffect() {
        super(Outcome.Exile);
        this.staticText = "Choose target card in a graveyard other than a basic land card. Search its owner's graveyard, hand, and library for any number of cards with the same name as that card and exile them. Then that player shuffles his or her library";
    }

    public SurgicalExtractionEffect(final SurgicalExtractionEffect effect) {
        super(effect);
    }

    @Override
    public SurgicalExtractionEffect copy() {
        return new SurgicalExtractionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card chosenCard = game.getCard(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());

        // 6/1/2011 	"Any number of cards" means just that. If you wish, you can choose to
        //              leave some or all of the cards with the same name as the targeted card,
        //              including that card, in the zone they're in.
        if (chosenCard != null && controller != null) {
            Player owner = game.getPlayer(chosenCard.getOwnerId());
            if (owner != null) {
                FilterCard filterNamedCard = new FilterCard("card named " + chosenCard.getName());
                filterNamedCard.add(new NamePredicate(chosenCard.getName()));

                Cards cardsInLibrary = new CardsImpl();
                cardsInLibrary.addAll(owner.getLibrary().getCards(game));

                // cards in Graveyard
                int cardsCount = owner.getGraveyard().count(filterNamedCard, game);
                if (cardsCount > 0) {
                    filterNamedCard.setMessage("card named " + chosenCard.getName() + " in the graveyard of " + owner.getName());
                    TargetCardInGraveyard target = new TargetCardInGraveyard(0, cardsCount, filterNamedCard);
                    if (controller.chooseTarget(Outcome.Exile, owner.getGraveyard(), target, source, game)) {
                        List<UUID> targets = target.getTargets();
                        for (UUID targetId : targets) {
                            Card targetCard = owner.getGraveyard().get(targetId, game);
                            if (targetCard != null) {
                                controller.moveCardToExileWithInfo(targetCard, null, "", source.getSourceId(), game, Zone.GRAVEYARD, true);
                            }
                        }
                    }
                }

                // cards in Hand
                filterNamedCard.setMessage("card named " + chosenCard.getName() + " in the hand of " + owner.getName());
                TargetCardInHand targetCardInHand = new TargetCardInHand(0, Integer.MAX_VALUE, filterNamedCard);
                if (controller.chooseTarget(Outcome.Exile, owner.getHand(), targetCardInHand, source, game)) {
                    List<UUID> targets = targetCardInHand.getTargets();
                    for (UUID targetId : targets) {
                        Card targetCard = owner.getHand().get(targetId, game);
                        if (targetCard != null) {
                            controller.moveCardToExileWithInfo(targetCard, null, "", source.getSourceId(), game, Zone.HAND, true);                                
                        }
                    }
                }

                // cards in Library
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
                owner.shuffleLibrary(source, game);
                return true;
            }
        }

        return false;
    }
}
