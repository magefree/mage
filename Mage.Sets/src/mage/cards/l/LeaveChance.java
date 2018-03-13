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
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.other.OwnerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public class LeaveChance extends SplitCard {

    public LeaveChance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY}, "{1}{W}", "{3}{R}", SpellAbilityType.SPLIT_AFTERMATH);

        // Return any number of target permanents you own to your hand.
        FilterPermanent filter = new FilterPermanent("permanents you own");
        filter.add(new OwnerPredicate(TargetController.YOU));
        getLeftHalfCard().getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        getLeftHalfCard().getSpellAbility().addTarget(new TargetPermanent(0, Integer.MAX_VALUE, filter, false));

        // Chance
        // Sorcery
        // Aftermath
        ((CardImpl) (getRightHalfCard())).addAbility(new AftermathAbility().setRuleAtTheTop(true));
        // Discard any number of cards, then draw that many cards.
        getRightHalfCard().getSpellAbility().addEffect(new ChanceEffect());

    }

    public LeaveChance(final LeaveChance card) {
        super(card);
    }

    @Override
    public LeaveChance copy() {
        return new LeaveChance(this);
    }
}

class ChanceEffect extends OneShotEffect {

    ChanceEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Discard any number of cards, then draw that many cards";
    }

    ChanceEffect(final ChanceEffect effect) {
        super(effect);
    }

    @Override
    public ChanceEffect copy() {
        return new ChanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cardsInHand = controller.getHand().copy();
            TargetCard target = new TargetCardInHand(0, cardsInHand.size(), new FilterCard());
            controller.chooseTarget(outcome, cardsInHand, target, source, game);
            if (!target.getTargets().isEmpty()) {
                for (UUID cardId : target.getTargets()) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        controller.discard(card, source, game);
                    }
                }
                game.applyEffects();
                controller.drawCards(target.getTargets().size(), game);
            }
            return true;
        }
        return false;
    }
}
