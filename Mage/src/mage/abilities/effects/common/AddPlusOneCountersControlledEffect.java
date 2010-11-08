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
import mage.abilities.effects.OneShotEffect;
import mage.counters.PlusOneCounter;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class AddPlusOneCountersControlledEffect extends OneShotEffect<AddPlusOneCountersControlledEffect> {

	private int amount;
	private FilterPermanent filter;

	public AddPlusOneCountersControlledEffect(int amount) {
		this(amount, FilterCreaturePermanent.getDefault());
	}

	public AddPlusOneCountersControlledEffect(int amount, FilterPermanent filter) {
		super(Outcome.Benefit);
		this.amount = amount;
		this.filter = filter;
	}

	public AddPlusOneCountersControlledEffect(final AddPlusOneCountersControlledEffect effect) {
		super(effect);
		this.amount = effect.amount;
		this.filter = effect.filter.copy();
	}

	@Override
	public AddPlusOneCountersControlledEffect copy() {
		return new AddPlusOneCountersControlledEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		for (Permanent perm: game.getBattlefield().getAllActivePermanents(filter, source.getControllerId())) {
			perm.getCounters().addCounter(new PlusOneCounter(amount));		}
		return true;
	}

	@Override
	public String getText(Ability source) {
		StringBuilder sb = new StringBuilder();
		sb.append("Put ").append(amount).append(" +1/+1 counter");
		if (amount > 1)
			sb.append("s");
		sb.append(" on each ").append(filter.getName()).append(" you control");
		return sb.toString();
	}

}
