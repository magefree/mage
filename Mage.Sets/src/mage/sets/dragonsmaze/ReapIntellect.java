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

import java.util.List;
import java.util.UUID;
import mage.Constants;
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
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 */
public class ReapIntellect extends CardImpl<ReapIntellect> {

    public ReapIntellect(UUID ownerId) {
        super(ownerId, 95, "Reap Intellect", Rarity.MYTHIC, new CardType[]{CardType.SORCERY}, "{X}{2}{U}{B}");
        this.expansionSetCode = "DGM";

        this.color.setBlue(true);
        this.color.setBlack(true);

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

class ReapIntellectEffect extends OneShotEffect<ReapIntellectEffect> {

    private static final FilterCard filter = new FilterCard("up to X nonland cards");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
    }

    public ReapIntellectEffect() {
        super(Constants.Outcome.Benefit);
        staticText = "Target opponent reveals his or her hand. You choose up to X nonland cards from it and exile them. For each card exiled this way, search that player's graveyard, hand, and library for any number of cards with the same name as that card and exile them. Then that player shuffles his or her library";
    }

    public ReapIntellectEffect(final ReapIntellectEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cards exiledCards = new CardsImpl();
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player you = game.getPlayer(source.getControllerId());
        if (targetPlayer != null) {
            targetPlayer.revealCards("Reap Intellect", targetPlayer.getHand(), game);
            if (you != null) {
                int xCost = Math.min(source.getManaCostsToPay().getX(), targetPlayer.getHand().size());
                TargetCard target = new TargetCard(0, xCost, Zone.PICK, filter);
                target.setNotTarget(true);
                if (you.choose(Outcome.Benefit, targetPlayer.getHand(), target, game)) {
                    for (UUID cardId : (List<UUID>) target.getTargets()) {
                        Card chosenCard = game.getCard(cardId);
                        if (chosenCard != null) {
                            if (chosenCard.moveToExile(source.getSourceId(), "Reap Intellect", source.getId(), game)) {
                                exiledCards.add(chosenCard);
                            }
                        }
                    }
                    for (UUID cardId : exiledCards) {
                        if (cardId != null) {
                            if (targetPlayer != null) {
                                Card card = game.getCard(cardId);

                                // cards in Graveyard
                                Cards cardsInGraveyard = new CardsImpl(Constants.Zone.GRAVEYARD);
                                cardsInGraveyard.addAll(targetPlayer.getGraveyard());
                                you.lookAtCards("Reap Intellect search of Graveyard", cardsInGraveyard, game);
                                
                                // cards in Hand
                                Cards cardsInHand = new CardsImpl(Constants.Zone.HAND);
                                cardsInHand.addAll(targetPlayer.getHand());
                                you.lookAtCards("Reap Intellect search of Hand", cardsInHand, game);

                                //cards in Library
                                Cards cardsInLibrary = new CardsImpl(Constants.Zone.LIBRARY);
                                cardsInLibrary.addAll(targetPlayer.getLibrary().getCards(game));
                                you.lookAtCards("Reap Intellect search of Library", cardsInLibrary, game);

                                // exile same named cards from zones

                                for (Card checkCard : cardsInGraveyard.getCards(game)) {
                                    if (checkCard.getName().equals(card.getName())) {
                                        checkCard.moveToExile(source.getSourceId(), "Graveyard", source.getId(), game);
                                    }
                                }
                                for (Card checkCard : cardsInHand.getCards(game)) {
                                    if (checkCard.getName().equals(card.getName())) {
                                        checkCard.moveToExile(source.getSourceId(), "Hand", source.getId(), game);
                                    }
                                }

                                for (Card checkCard : cardsInLibrary.getCards(game)) {
                                    if (checkCard.getName().equals(card.getName())) {
                                        checkCard.moveToExile(source.getSourceId(), "Library", source.getId(), game);
                                    }
                                }
                            }
                        }
                    }
                    targetPlayer.shuffleLibrary(game);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public ReapIntellectEffect copy() {
        return new ReapIntellectEffect(this);
    }
}
