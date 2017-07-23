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
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public class GreenwardenOfMurasa extends CardImpl {

    public GreenwardenOfMurasa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add("Elemental");
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // When Greenwarden of Murasa enters the battlefield, you may return target card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect(), true);
        ability.addTarget(new TargetCardInYourGraveyard());
        this.addAbility(ability);

        // When Greenwarden of  Murasa dies, you may exile it. If you do, return target card from your graveyard to your hand.
        ability = new DiesTriggeredAbility(new GreenwardenOfMurasaEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard());
        this.addAbility(ability);
    }

    public GreenwardenOfMurasa(final GreenwardenOfMurasa card) {
        super(card);
    }

    @Override
    public GreenwardenOfMurasa copy() {
        return new GreenwardenOfMurasa(this);
    }
}

class GreenwardenOfMurasaEffect extends OneShotEffect {

    public GreenwardenOfMurasaEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may exile it. If you do, return target card from your graveyard to your hand";
    }

    public GreenwardenOfMurasaEffect(final GreenwardenOfMurasaEffect effect) {
        super(effect);
    }

    @Override
    public GreenwardenOfMurasaEffect copy() {
        return new GreenwardenOfMurasaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        Card targetCard = game.getCard(getTargetPointer().getFirst(game, source));
        if (controller != null && sourceObject != null && targetCard != null) {
            if (controller.chooseUse(outcome, "Exile " + sourceObject.getLogName() + " to return card from your graveyard to your hand?", source, game)) {
                // Setting the fixed target prevents to return Greenwarden of Murasa itself (becuase it's exiled meanwhile),
                // but of course you can target it as the ability triggers I guess
                Effect effect = new ReturnToHandTargetEffect();
                effect.setTargetPointer(new FixedTarget(targetCard.getId(), targetCard.getZoneChangeCounter(game)));
                new ExileSourceEffect().apply(game, source);
                return effect.apply(game, source);
            }
            return true;
        }
        return false;
    }
}
