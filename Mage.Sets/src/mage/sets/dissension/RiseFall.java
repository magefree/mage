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
package mage.sets.dissension;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class RiseFall extends SplitCard {

    public RiseFall(UUID ownerId) {
        super(ownerId, 156, "Rise", "Fall", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{U}{B}", "{B}{R}", false);
        this.expansionSetCode = "DIS";

        // Rise
        // Return target creature card from a graveyard and target creature on the battlefield to their owners' hands.
        getLeftHalfCard().getSpellAbility().addEffect(new RiseEffect());
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card from a graveyard")));
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Fall
        // Target player reveals two cards at random from his or her hand, then discards each nonland card revealed this way.
        getRightHalfCard().getSpellAbility().addEffect(new FallEffect());
        getRightHalfCard().getSpellAbility().addTarget(new TargetPlayer());
    }

    public RiseFall(final RiseFall card) {
        super(card);
    }

    @Override
    public RiseFall copy() {
        return new RiseFall(this);
    }
}

class RiseEffect extends OneShotEffect {

    public RiseEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return target creature card from a graveyard and target creature on the battlefield to their owners' hands";
    }

    public RiseEffect(final RiseEffect effect) {
        super(effect);
    }

    @Override
    public RiseEffect copy() {
        return new RiseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cardsToHand = new CardsImpl();
            Card cardInGraveyard = game.getCard(getTargetPointer().getFirst(game, source));
            if (cardInGraveyard != null) {
                cardsToHand.add(cardInGraveyard);
            }
            Permanent permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
            if (permanent != null) {
                cardsToHand.add(permanent);
            }
            controller.moveCards(cardsToHand, null, Zone.HAND, source, game);
            return true;
        }
        return false;
    }
}

class FallEffect extends OneShotEffect {

    public FallEffect() {
        super(Outcome.Discard);
        this.staticText = "Target player reveals two cards at random from his or her hand, then discards each nonland card revealed this way";
    }

    public FallEffect(final FallEffect effect) {
        super(effect);
    }

    @Override
    public FallEffect copy() {
        return new FallEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null) {
            Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (targetPlayer != null) {
                if (targetPlayer.getHand().size() > 0) {
                    Cards cards = new CardsImpl();
                    Card card = targetPlayer.getHand().getRandom(game);
                    cards.add(card);
                    if (targetPlayer.getHand().size() > 1) {
                        do {
                            card = targetPlayer.getHand().getRandom(game);
                        } while (cards.contains(card.getId()));
                        cards.add(card);
                    }
                    targetPlayer.revealCards(sourceObject.getName(), cards, game);
                    for (Card cardToDiscard : cards.getCards(game)) {
                        if (!cardToDiscard.getCardType().contains(CardType.LAND)) {
                            targetPlayer.discard(cardToDiscard, source, game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
