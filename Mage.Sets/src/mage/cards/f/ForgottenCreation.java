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
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SkulkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class ForgottenCreation extends CardImpl {

    public ForgottenCreation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add("Zombie");
        this.subtype.add("Horror");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Skulk
        this.addAbility(new SkulkAbility());
        // At the beginning of your upkeep, you may discard all the cards in your hand. If you do, draw that many cards.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ForgottenCreationEffect(), TargetController.YOU, true));
    }

    public ForgottenCreation(final ForgottenCreation card) {
        super(card);
    }

    @Override
    public ForgottenCreation copy() {
        return new ForgottenCreation(this);
    }
}

class ForgottenCreationEffect extends OneShotEffect {

    public ForgottenCreationEffect() {
        super(Outcome.DrawCard);
        this.staticText = "you may discard all the cards in your hand. If you do, draw that many cards";
    }

    public ForgottenCreationEffect(final ForgottenCreationEffect effect) {
        super(effect);
    }

    @Override
    public ForgottenCreationEffect copy() {
        return new ForgottenCreationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int cardsInHand = controller.getHand().size();
            controller.discard(cardsInHand, false, source, game);
            controller.drawCards(cardsInHand, game);
            return true;
        }
        return false;
    }
}
