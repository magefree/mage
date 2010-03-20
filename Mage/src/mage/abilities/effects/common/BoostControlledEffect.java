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

package mage.abilities.effects.common;

import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.SubLayer;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class BoostControlledEffect extends ContinuousEffectImpl {

	private int power;
	private int toughness;
	protected FilterCreaturePermanent filter;

	public BoostControlledEffect(int power, int toughness, Duration duration) {
		this(power, toughness, duration, null);
	}

	public BoostControlledEffect(int power, int toughness, Duration duration, FilterCreaturePermanent filter) {
		super(duration, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
		this.power = power;
		this.toughness = toughness;
		this.filter = filter;
	}

	@Override
	public boolean apply(Game game) {
		if (filter != null) {
			filter.getControllerId().clear();
			filter.getControllerId().add(this.source.getControllerId());
			for (Permanent perm: game.getBattlefield().getActivePermanents(filter)) {
				perm.addPower(power);
				perm.addToughness(toughness);
			}
		}
		else {
			for (Permanent perm: game.getBattlefield().getActivePermanents(this.source.getControllerId(), CardType.CREATURE)) {
				perm.addPower(power);
				perm.addToughness(toughness);
			}
		}
		return true;
	}

	@Override
	public String getText() {
		StringBuilder sb = new StringBuilder();
		if (filter == null)
			sb.append("Creatures");
		else
			sb.append(filter.getMessage());
		sb.append(" you control get ");
		sb.append(String.format("%1$+d/%2$+d", power, toughness));
		sb.append((duration==Duration.EndOfTurn?" until end of turn":""));
		return sb.toString();
	}

}
