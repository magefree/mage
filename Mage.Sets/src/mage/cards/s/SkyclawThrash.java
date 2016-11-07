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
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class SkyclawThrash extends CardImpl {

    public SkyclawThrash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}{U}{R}");
        this.subtype.add("Viashino");
        this.subtype.add("Warrior");

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Skyclaw Thrash attacks, flip a coin. If you win the flip, Skyclaw Thrash gets +1/+1 and gains flying until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new SkyclawThrashEffect(), false));
    }

    public SkyclawThrash(final SkyclawThrash card) {
        super(card);
    }

    @Override
    public SkyclawThrash copy() {
        return new SkyclawThrash(this);
    }
}

class SkyclawThrashEffect extends OneShotEffect {

    public SkyclawThrashEffect() {
        super(Outcome.Benefit);
        this.staticText = "flip a coin. If you win the flip, {this} gets +1/+1 and gains flying until end of turn";
    }

    public SkyclawThrashEffect(final SkyclawThrashEffect effect) {
        super(effect);
    }

    @Override
    public SkyclawThrashEffect copy() {
        return new SkyclawThrashEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (controller.flipCoin(game) && sourcePermanent != null) {
                ContinuousEffect effect = new BoostTargetEffect(1, 1, Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(sourcePermanent, game));
                game.addEffect(effect, source);
                effect = new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(sourcePermanent, game));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}
