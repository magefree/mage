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
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ControllerLifeCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EchoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class VolcanoHellion extends CardImpl {

    public VolcanoHellion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.HELLION);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Volcano Hellion has echo {X}, where X is your life total.
        this.addAbility(new EchoAbility(new ControllerLifeCount(), "{this} has echo {X}, where X is your life total."));

        // When Volcano Hellion enters the battlefield, it deals an amount of damage of your choice to you and target creature. The damage can't be prevented.
        Ability ability = new EntersBattlefieldTriggeredAbility(new VolcanoHellionEffect(), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    public VolcanoHellion(final VolcanoHellion card) {
        super(card);
    }

    @Override
    public VolcanoHellion copy() {
        return new VolcanoHellion(this);
    }
}

class VolcanoHellionEffect extends OneShotEffect {

    public VolcanoHellionEffect() {
        super(Outcome.AIDontUseIt);
        this.staticText = "it deals an amount of damage of your choice to you and target creature. The damage can't be prevented";
    }

    public VolcanoHellionEffect(final VolcanoHellionEffect effect) {
        super(effect);
    }

    @Override
    public VolcanoHellionEffect copy() {
        return new VolcanoHellionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (controller != null) {
            int amount = controller.getAmount(0, Integer.MAX_VALUE, "Choose the amount of damage to deliver to you and a target creature.  The damage can't be prevented.", game);
            if (amount > 0) {
                controller.damage(amount, source.getSourceId(), game, false, false);
                if (permanent != null) {
                    permanent.damage(amount, source.getSourceId(), game, false, false);
                }
                return true;
            }
        }
        return false;
    }
}
