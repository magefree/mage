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
package mage.cards.b;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 */
public class BrilliantUltimatum extends CardImpl {

    public BrilliantUltimatum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{W}{W}{U}{U}{U}{B}{B}");

        // Exile the top five cards of your library. An opponent separates those cards into two piles. You may play any number of cards from one of those piles without paying their mana costs.
        this.getSpellAbility().addEffect(new BrilliantUltimatumEffect());
    }

    public BrilliantUltimatum(final BrilliantUltimatum card) {
        super(card);
    }

    @Override
    public BrilliantUltimatum copy() {
        return new BrilliantUltimatum(this);
    }
}

class BrilliantUltimatumEffect extends OneShotEffect {

    public BrilliantUltimatumEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Exile the top five cards of your library. An opponent separates those cards into two piles. You may play any number of cards from one of those piles without paying their mana costs";
    }

    public BrilliantUltimatumEffect(final BrilliantUltimatumEffect effect) {
        super(effect);
    }

    @Override
    public BrilliantUltimatumEffect copy() {
        return new BrilliantUltimatumEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller == null || sourceObject == null) {
            return false;
        }

        Cards pile2 = new CardsImpl();
        pile2.addAll(controller.getLibrary().getTopCards(game, 5));
        controller.moveCardsToExile(pile2.getCards(game), source, game, true, source.getSourceId(), sourceObject.getIdName());

        TargetOpponent targetOpponent = new TargetOpponent(true);
        targetOpponent.choose(outcome, source.getControllerId(), source.getSourceId(), game);
        Player opponent = game.getPlayer(targetOpponent.getFirstTarget());
        if (opponent != null) {
            TargetCard target = new TargetCard(0, pile2.size(), Zone.EXILED, new FilterCard("cards to put in the first pile"));
            target.setRequired(false);
            Cards pile1 = new CardsImpl();
            List<Card> pileOne = new ArrayList<>();
            List<Card> pileTwo = new ArrayList<>();
            if (opponent.choose(Outcome.Neutral, pile2, target, game)) {
                List<UUID> targets = target.getTargets();
                for (UUID targetId : targets) {
                    Card card = pile2.get(targetId, game);
                    if (card != null) {
                        pile1.add(card);
                        pile2.remove(card);
                    }
                }
            }
            pileOne.addAll(pile1.getCards(game));
            pileTwo.addAll(pile2.getCards(game));
            controller.revealCards("Pile 1 - " + sourceObject.getIdName(), pile1, game);
            controller.revealCards("Pile 2 - " + sourceObject.getIdName(), pile2, game);

            boolean choice = controller.choosePile(Outcome.PlayForFree, "Which pile (play for free)?", pileOne, pileTwo, game);
            String selectedPileName;
            List<Card> selectedPileCards;
            Cards selectedPile;
            if (choice) {
                selectedPileName = "pile 1";
                selectedPileCards = pileOne;
                selectedPile = pile1;
            } else {
                selectedPileName = "pile 2";
                selectedPileCards = pileTwo;
                selectedPile = pile2;
            }
            game.informPlayers(controller.getLogName() + " chose " + selectedPileName + '.');
            while (!selectedPileCards.isEmpty() && controller.chooseUse(Outcome.PlayForFree, "Do you want to play a card for free from " + selectedPileName + '?', source, game)) {
                TargetCard targetExiledCard = new TargetCard(Zone.EXILED, new FilterCard());
                if (controller.chooseTarget(Outcome.PlayForFree, selectedPile, targetExiledCard, source, game)) {
                    Card card = selectedPile.get(targetExiledCard.getFirstTarget(), game);
                    if (controller.playCard(card, game, true, true)) {
                        selectedPileCards.remove(card);
                        selectedPile.remove(card);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
