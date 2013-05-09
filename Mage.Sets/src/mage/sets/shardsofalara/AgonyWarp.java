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
import mage.Constants.Rarity;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

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
        Effect effect = new BoostTargetEffect(-3,0, Duration.EndOfTurn);
        effect.setText("Target creature gets -3/-0 until end of turn");
        this.getSpellAbility().addEffect(effect);
        Target target = new TargetCreaturePermanent(new FilterCreaturePermanent("first creature"));
        target.setRequired(true);
        this.getSpellAbility().addTarget(target);

        // Target creature gets -0/-3 until end of turn.
        Effect effect2 = new BoostTargetEffect(-0,-3, Duration.EndOfTurn);
        effect2.setText("<br></br>Target creature gets -0/-3 until end of turn");
        effect2.setTargetPointer(SecondTargetPointer.getInstance());
        this.getSpellAbility().addEffect(effect2);
        target = new TargetCreaturePermanent(new FilterCreaturePermanent("second creature (can be the same as the first)"));
        target.setRequired(true);
        this.getSpellAbility().addTarget(target);
    }

    public AgonyWarp(final AgonyWarp card) {
        super(card);
    }

    @Override
    public AgonyWarp copy() {
        return new AgonyWarp(this);
    }
}
