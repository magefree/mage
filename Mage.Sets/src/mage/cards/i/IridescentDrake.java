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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.predicate.other.AuraCardCanAttachToPermanentId;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author TheElk801
 */
public class IridescentDrake extends CardImpl {

    public IridescentDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Iridescent Drake enters the battlefield, put target Aura card from a graveyard onto the battlefield under your control attached to Iridescent Drake.
        Ability ability = new EntersBattlefieldTriggeredAbility(new IridescentDrakeEffect());
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);
    }

    public IridescentDrake(final IridescentDrake card) {
        super(card);
    }

    @Override
    public IridescentDrake copy() {
        return new IridescentDrake(this);
    }
}

class IridescentDrakeEffect extends OneShotEffect {

    public IridescentDrakeEffect() {
        super(Outcome.Benefit);
        this.staticText = "put target Aura card from a graveyard onto the battlefield under your control attached to {this}";
    }

    public IridescentDrakeEffect(final IridescentDrakeEffect effect) {
        super(effect);
    }

    @Override
    public IridescentDrakeEffect copy() {
        return new IridescentDrakeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        Card targetAuraCard = game.getCard(source.getFirstTarget());
        if (controller != null
                && permanent != null
                && controller.canRespond()
                && targetAuraCard != null
                && new AuraCardCanAttachToPermanentId(permanent.getId()).apply(targetAuraCard, game)) {
            Target target = targetAuraCard.getSpellAbility().getTargets().get(0);
            if (target != null) {
                game.getState().setValue("attachTo:" + targetAuraCard.getId(), permanent);
                controller.moveCards(targetAuraCard, Zone.BATTLEFIELD, source, game);
                return permanent.addAttachment(targetAuraCard.getId(), game);
            }
        }
        return false;
    }
}
