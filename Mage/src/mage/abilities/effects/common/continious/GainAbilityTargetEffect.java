/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.abilities.effects.common.continious;

import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.SubLayer;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;
import mage.abilities.Mode;
import mage.target.Target;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GainAbilityTargetEffect extends ContinuousEffectImpl<GainAbilityTargetEffect> {

	protected Ability ability;

	public GainAbilityTargetEffect(Ability ability, Duration duration) {
		super(duration, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, 
                ability.getEffects().size() > 0 ? ability.getEffects().get(0).getOutcome() : Outcome.AddAbility);
		this.ability = ability;
	}

	public GainAbilityTargetEffect(final GainAbilityTargetEffect effect) {
		super(effect);
		this.ability = effect.ability.copy();
	}

	@Override
	public GainAbilityTargetEffect copy() {
		return new GainAbilityTargetEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		int affectedTargets = 0;
		for (UUID permanentId : targetPointer.getTargets(source)) {
			Permanent permanent = game.getPermanent(permanentId);
			if (permanent != null) {
				permanent.addAbility(ability);
				affectedTargets++;
			}
		}
		return affectedTargets > 0;
	}

	@Override
	public String getText(Mode mode) {
		StringBuilder sb = new StringBuilder();
        Target target = mode.getTargets().get(0);
        if(target.getNumberOfTargets() > 1){
            sb.append(target.getNumberOfTargets()).append(" target ").append(target.getTargetName()).append(" gain ");
        } else {
            sb.append("Target ").append(target.getTargetName()).append(" gains ");
        }
		sb.append(ability.getRule()).append(" ").append(duration.toString());
		return sb.toString();
	}

}