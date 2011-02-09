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

import mage.abilities.condition.common.ControlsPermanent;
import mage.abilities.effects.WhileConditionContiniousEffect;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.SubLayer;
import mage.abilities.Ability;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class BoostSourceWhileControlsEffect extends WhileConditionContiniousEffect<BoostSourceWhileControlsEffect> {

	private int power;
	private int toughness;
    private List<String> filterDescription;

	public BoostSourceWhileControlsEffect(FilterPermanent filter, int power, int toughness) {
		super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, new ControlsPermanent(filter), Outcome.BoostCreature);
		this.power = power;
		this.toughness = toughness;
        this.filterDescription = filter.getName();
	}

	public BoostSourceWhileControlsEffect(final BoostSourceWhileControlsEffect effect) {
		super(effect);
		this.power = effect.power;
		this.toughness = effect.toughness;
        this.filterDescription = new ArrayList<String>();
        this.filterDescription.addAll(effect.filterDescription);
	}

	@Override
	public BoostSourceWhileControlsEffect copy() {
		return new BoostSourceWhileControlsEffect(this);
	}

	@Override
	public boolean applyEffect(Game game, Ability source) {
		Permanent permanent = game.getPermanent(source.getSourceId());
		if (permanent != null) {
			permanent.addPower(power);
			permanent.addToughness(toughness);
		}
		return true;
	}

	@Override
	public String getText(Ability source) {
		return "{this} gets " + String.format("%1$+d/%2$+d", power, toughness) + " as long as you control a " + filterDescription;
	}
}
