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

package mage.abilities.effects.common.continious;

import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.SubLayer;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class BoostEquippedEffect extends ContinuousEffectImpl<BoostEquippedEffect> {

	private DynamicValue power;
	private DynamicValue toughness;

	public BoostEquippedEffect(int power, int toughness) {
		this(power, toughness, Duration.WhileOnBattlefield);
	}

	public BoostEquippedEffect(int power, int toughness, Duration duration) {
        this(new StaticValue(power), new StaticValue(toughness), duration);
	}

    public BoostEquippedEffect(DynamicValue powerDynamicValue, DynamicValue toughnessDynamicValue) {
        this(powerDynamicValue, toughnessDynamicValue, Duration.WhileOnBattlefield);
    }

    public BoostEquippedEffect(DynamicValue powerDynamicValue, DynamicValue toughnessDynamicValue, Duration duration) {
        super(duration, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.power = powerDynamicValue;
        this.toughness = toughnessDynamicValue;
		setText();
    }

	public BoostEquippedEffect(final BoostEquippedEffect effect) {
		super(effect);
		this.power = effect.power.clone();
		this.toughness = effect.toughness.clone();
	}

	@Override
	public BoostEquippedEffect copy() {
		return new BoostEquippedEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Permanent equipment = game.getPermanent(source.getSourceId());
		if (equipment != null && equipment.getAttachedTo() != null) {
			Permanent creature = game.getPermanent(equipment.getAttachedTo());
			if (creature != null) {
				creature.addPower(power.calculate(game, source));
				creature.addToughness(toughness.calculate(game, source));
			}
		}
		return true;
	}

	private void setText() {
		StringBuilder sb = new StringBuilder();
		sb.append("Equipped creature gets ");
        String p = power.toString();
        if(!p.startsWith("-"))
            sb.append("+");
        sb.append(p).append("/");
        String t = toughness.toString();
        if(!t.startsWith("-")){
            if(p.startsWith("-"))
                sb.append("-");
            else
                sb.append("+");
        }
        sb.append(t);
		if (duration != Duration.WhileOnBattlefield)
			sb.append(" ").append(duration.toString());
        String message = power.getMessage();
        if (message.length() > 0) {
            sb.append(" for each ");
        }
        sb.append(message);
		staticText = sb.toString();
	}

}
