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
package mage.sets.shardsofalara;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class AgonyWarp extends CardImpl<AgonyWarp> {

    public AgonyWarp(UUID ownerId) {
        super(ownerId, 153, "Agony Warp", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{U}{B}");
        this.expansionSetCode = "ALA";

        this.color.setBlue(true);
        this.color.setBlack(true);

        // Target creature gets -3/-0 until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("creature to get -3/-0")));
        // Target creature gets -0/-3 until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("creature to get -0/-3")));
        this.getSpellAbility().addEffect(new AgonyWarpEffect());
    }

    public AgonyWarp(final AgonyWarp card) {
        super(card);
    }

    @Override
    public AgonyWarp copy() {
        return new AgonyWarp(this);
    }
}

class AgonyWarpEffect extends ContinuousEffectImpl<AgonyWarpEffect> {

    public AgonyWarpEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.UnboostCreature);
        this.staticText = "Target creature gets -0/-3 until end of turn";
    }

    public AgonyWarpEffect(final AgonyWarpEffect effect) {
        super(effect);
    }

    @Override
    public AgonyWarpEffect copy() {
        return new AgonyWarpEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int affectedTargets = 0;
        Permanent target1 = game.getPermanent(source.getFirstTarget());
        Permanent target2 = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (target1 != null) {
            target1.addPower(-3);
            affectedTargets++;
        }
        if (target2 != null) {
            target2.addToughness(-3);
            affectedTargets++;
        }
        return affectedTargets > 0;
    }
}
