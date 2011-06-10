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

import mage.Constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DamageTargetEffect extends OneShotEffect<DamageTargetEffect> {

	protected DynamicValue amount;
	protected boolean preventable;

	public DamageTargetEffect(int amount) {
		 this(new StaticValue(amount), true);
	}

	public DamageTargetEffect(int amount, boolean preventable) {
		this(new StaticValue(amount), preventable);
	}

    public DamageTargetEffect(DynamicValue amount) {
        this(amount, true);
    }

    public DamageTargetEffect(DynamicValue amount, boolean preventable) {
        super(Outcome.Damage);
		this.amount = amount;
		this.preventable = preventable;
    }

	public int getAmount() {
        if (amount instanceof StaticValue) {
            return amount.calculate(null, null);
        } else {
            return 0;
        }
	}

	public DamageTargetEffect(final DamageTargetEffect effect) {
		super(effect);
		this.amount = effect.amount.clone();
		this.preventable = effect.preventable;
	}

	@Override
	public DamageTargetEffect copy() {
		return new DamageTargetEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Permanent permanent = game.getPermanent(source.getFirstTarget());
		if (permanent != null) {
			permanent.damage(amount.calculate(game, source), source.getSourceId(), game, preventable, false);
			return true;
		}
		Player player = game.getPlayer(source.getFirstTarget());
		if (player != null) {
			player.damage(amount.calculate(game, source), source.getSourceId(), game, false, preventable);
			return true;
		}
		return false;
	}

	@Override
	public String getText(Ability source) {
		StringBuilder sb = new StringBuilder();
		sb.append("{source} deals ").append(amount).append(" damage to target ");
		sb.append(source.getTargets().get(0).getTargetName());
        String message = amount.getMessage();
        if (message.length() > 0) {
            sb.append(" for each ");
        }
        sb.append(message);
		if (!preventable)
			sb.append(". The damage can't be prevented");
		return sb.toString();
	}

}
