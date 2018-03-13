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
package mage.cards.i;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public class IgnorantBliss extends CardImpl {

    public IgnorantBliss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Exile all cards from your hand face down. At the beginning of the next end step, return those cards to your hand, then draw a card.
        this.getSpellAbility().addEffect(new IgnorantBlissExileEffect());
        this.getSpellAbility().addEffect(new IgnorantBlissReturnEffect());

    }

    public IgnorantBliss(final IgnorantBliss card) {
        super(card);
    }

    @Override
    public IgnorantBliss copy() {
        return new IgnorantBliss(this);
    }
}

class IgnorantBlissExileEffect extends OneShotEffect {

    IgnorantBlissExileEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile all cards from your hand face down";
    }

    IgnorantBlissExileEffect(final IgnorantBlissExileEffect effect) {
        super(effect);
    }

    @Override
    public IgnorantBlissExileEffect copy() {
        return new IgnorantBlissExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null
                && sourceObject != null) {
            Cards hand = controller.getHand();
            hand.getCards(game).stream().filter((card) -> (card != null)).map((card) -> {
                UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), 0);
                controller.moveCardsToExile(card, source, game, false, exileZoneId, sourceObject.getIdName());
                return card;
            }).forEachOrdered((card) -> {
                card.setFaceDown(true, game);
            });
            return true;
        }
        return false;
    }
}

class IgnorantBlissReturnEffect extends OneShotEffect {

    IgnorantBlissReturnEffect() {
        super(Outcome.DrawCard);
        this.staticText = "At the beginning of the next end step, return those cards to your hand, then draw a card";
    }

    IgnorantBlissReturnEffect(final IgnorantBlissReturnEffect effect) {
        super(effect);
    }

    @Override
    public IgnorantBlissReturnEffect copy() {
        return new IgnorantBlissReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source.getSourceId(), 0));
            if (exileZone != null) {
                Effect effect = new ReturnFromExileEffect(exileZone.getId(), Zone.HAND);
                AtTheBeginOfNextEndStepDelayedTriggeredAbility ability = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
                ability.addEffect(new DrawCardSourceControllerEffect(1));
                game.addDelayedTriggeredAbility(ability, source);
                return true;
            }
        }
        return false;
    }
}
