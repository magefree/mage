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

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public class LastRites extends CardImpl {

    public LastRites(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");

        // Discard any number of cards. Target player reveals their hand, then you choose a nonland card from it for each card discarded this way. That player discards those cards.
        this.getSpellAbility().addEffect(new LastRitesEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

    }

    public LastRites(final LastRites card) {
        super(card);
    }

    @Override
    public LastRites copy() {
        return new LastRites(this);
    }
}

class LastRitesEffect extends OneShotEffect {

    LastRitesEffect() {
        super(Outcome.Discard);
        this.staticText = "Discard any number of cards. Target player reveals their hand, then you choose a nonland card from it for each card discarded this way. That player discards those cards";
    }

    LastRitesEffect(final LastRitesEffect effect) {
        super(effect);
    }

    @Override
    public LastRitesEffect copy() {
        return new LastRitesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (controller != null && targetPlayer != null) {
            Cards cardsInHand = controller.getHand().copy();
            TargetCard target = new TargetCardInHand(0, cardsInHand.size(), new FilterCard("cards to discard"));
            controller.chooseTarget(outcome, cardsInHand, target, source, game);
            int discardCount = target.getTargets().size();
            if (discardCount > 0) {
                for (UUID cardId : target.getTargets()) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        controller.discard(card, source, game);
                    }
                }
                if (targetPlayer != null) {
                    FilterCard filter = new FilterCard((discardCount > 1 ? "" : "a") + " nonland card" + (discardCount > 1 ? "s" : ""));
                    filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
                    StaticValue discardValue = new StaticValue(discardCount);
                    Effect effect = new DiscardCardYouChooseTargetEffect(discardValue, filter, TargetController.ANY);
                    effect.setTargetPointer(new FixedTarget(targetPlayer.getId()));
                    effect.apply(game, source);
                } else {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
