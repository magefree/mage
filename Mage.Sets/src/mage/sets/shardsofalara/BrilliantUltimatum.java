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
package mage.sets.shardsofalara;

import java.util.ArrayList;
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
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 */
public class BrilliantUltimatum extends CardImpl<BrilliantUltimatum> {

    public BrilliantUltimatum(UUID ownerId) {
        super(ownerId, 159, "Brilliant Ultimatum", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{B}{B}");
        this.expansionSetCode = "ALA";

        this.color.setBlue(true);
        this.color.setBlack(true);
        this.color.setWhite(true);

        // Exile the top five cards of your library. An opponent separates those cards into two piles. You may play any number of cards from one of those piles without paying their mana costs.
        this.getSpellAbility().addEffect(new BrilliantUltimatumEffect());
        this.getSpellAbility().addTarget(new TargetOpponent(true, true));

    }

    public BrilliantUltimatum(final BrilliantUltimatum card) {
        super(card);
    }

    @Override
    public BrilliantUltimatum copy() {
        return new BrilliantUltimatum(this);
    }
}

class BrilliantUltimatumEffect extends OneShotEffect<BrilliantUltimatumEffect> {

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
        Player you = game.getPlayer(source.getControllerId());
        if (you == null) {
            return false;
        }

        Cards pile2 = new CardsImpl();
        int max = Math.min(you.getLibrary().size(), 5);
        for (int i = 0; i < max; i++) {
            Card card = you.getLibrary().removeFromTop(game);
            if (card != null) {
                card.moveToExile(source.getSourceId(), "Brilliant Ultimatum", source.getId(), game);
                pile2.add(card);
            }
        }

        Player opponent = game.getPlayer(source.getFirstTarget());
        if (opponent != null) {
            TargetCard target = new TargetCard(0, pile2.size(), Zone.EXILED, new FilterCard("cards to put in the first pile"));
            Cards pile1 = new CardsImpl();
            List<Card> pileOne = new ArrayList<Card>();
            List<Card> pileTwo = new ArrayList<Card>();
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
            for (UUID cardID : pile1) {
                Card card = pile1.get(cardID, game);
                pileOne.add(card);
            }
            for (UUID cardId : pile2) {
                Card card = pile2.get(cardId, game);
                pileTwo.add(card);
            }
            
            you.revealCards("Pile 1 (Brilliant Ultimatum)", pile1, game);
            you.revealCards("Pile 2 (Brilliant Ultimatum)", pile2, game);
            
            boolean choice = you.choosePile(Outcome.PlayForFree, "Which pile (play for free)?", pileOne, pileTwo, game);
            if (choice) {
                game.informPlayer(you, you.getName() + " chose Pile 1.");
                while (!pileOne.isEmpty() && you.chooseUse(Outcome.PlayForFree, "Do you want to play a card from Pile 1?", game)) {
                    TargetCard targetExiledCard = new TargetCard(Zone.EXILED, new FilterCard());
                    targetExiledCard.setRequired(true);
                    if (you.chooseTarget(Outcome.PlayForFree, pile1, targetExiledCard, source, game)) {
                        Card card = pile1.get(targetExiledCard.getFirstTarget(), game);
                        if (card != null) {
                            if (card.getCardType().contains(CardType.LAND)) {
                                you.playLand(card, game);
                                pileOne.remove(card);
                                pile1.remove(card);
                            } else {
                                you.cast(card.getSpellAbility(), game, true);
                                pileOne.remove(card);
                                pile1.remove(card);
                            }
                        }
                    }
                }
            } else {
                game.informPlayer(you, you.getName() + " chose Pile 2.");
                while (!pileTwo.isEmpty() && you.chooseUse(Outcome.PlayForFree, "Do you want to play a card from Pile 2?", game)) {
                    TargetCard targetExiledCard = new TargetCard(Zone.EXILED, new FilterCard());
                    targetExiledCard.setRequired(true);
                    if (you.chooseTarget(Outcome.PlayForFree, pile2, targetExiledCard, source, game)) {
                        Card card = pile2.get(targetExiledCard.getFirstTarget(), game);
                        if (card != null) {
                            if (card.getCardType().contains(CardType.LAND)) {
                                you.playLand(card, game);
                                pileTwo.remove(card);
                                pile2.remove(card);
                            } else {
                                you.cast(card.getSpellAbility(), game, true);
                                pileTwo.remove(card);
                                pile2.remove(card);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
