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
package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;


/**
 *
 * @author LevelX2
 */
public class CommonBond extends CardImpl<CommonBond> {

    public CommonBond(UUID ownerId) {
        super(ownerId, 151, "Common Bond", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{G}{W}");
        this.expansionSetCode = "RTR";

        this.color.setGreen(true);
        this.color.setWhite(true);

        // Put a +1/+1 counter on target creature. Put a +1/+1 counter on target creature.
        this.getSpellAbility().addEffect(new CommonBondEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("first creature")));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("second creature (can be the same as the first)")));
    }

    public CommonBond(final CommonBond card) {
        super(card);
    }

    @Override
    public CommonBond copy() {
        return new CommonBond(this);
    }
}

class CommonBondEffect extends OneShotEffect<CommonBondEffect> {

    public CommonBondEffect() {
        super(Constants.Outcome.BoostCreature);
        staticText = "Put a +1/+1 counter on target creature. Put a +1/+1 counter on target creature.";
    }

    public CommonBondEffect(final CommonBondEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int affectedTargets = 0;
        Permanent permanent = game.getPermanent(source.getTargets().get(0).getFirstTarget());
        if (permanent != null) {
            permanent.addCounters(CounterType.P1P1.createInstance(1), game);
            affectedTargets ++;
        }
        permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (permanent != null) {
            permanent.addCounters(CounterType.P1P1.createInstance(1), game);
            affectedTargets ++;
        }
        return affectedTargets > 0;
    }

    @Override
    public CommonBondEffect copy() {
        return new CommonBondEffect(this);
    }
}
