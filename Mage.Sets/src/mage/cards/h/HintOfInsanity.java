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
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author jeffwadsworth
 */
public class HintOfInsanity extends CardImpl {

    public HintOfInsanity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target player reveals his or her hand. That player discards all nonland cards with the same name as another card in his or her hand.
        this.getSpellAbility().addEffect(new HintOfInsanityEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

    }

    public HintOfInsanity(final HintOfInsanity card) {
        super(card);
    }

    @Override
    public HintOfInsanity copy() {
        return new HintOfInsanity(this);
    }
}

class HintOfInsanityEffect extends OneShotEffect {

    public HintOfInsanityEffect() {
        super(Outcome.Discard);
        this.staticText = "Target player reveals his or her hand. That player discards all nonland cards with the same name as another card in his or her hand";
    }

    public HintOfInsanityEffect(final HintOfInsanityEffect effect) {
        super(effect);
    }

    @Override
    public HintOfInsanityEffect copy() {
        return new HintOfInsanityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterCard filter = new FilterCard("card from your hand");
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        String nameOfChosenCard;
        Card chosenCard;
        if (targetPlayer != null) {
            TargetCardInHand targetCard = new TargetCardInHand(filter);
            targetCard.setNotTarget(true);
            Cards cardsInHand = new CardsImpl();
            cardsInHand.addAll(targetPlayer.getHand());
            targetPlayer.revealCards("Hint of Insanity Reveal", cardsInHand, game);
            if (!cardsInHand.isEmpty()
                    && targetPlayer.choose(Outcome.Discard, targetCard, source.getSourceId(), game)) {
                chosenCard = game.getCard(targetCard.getFirstTarget());
                nameOfChosenCard = chosenCard.getName();
                for (Card card : cardsInHand.getCards(game)) {
                    if (card.getName().equals(nameOfChosenCard)
                            && !card.isLand()) {
                        targetPlayer.discard(card, source, game);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
