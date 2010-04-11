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

package mage.abilities.costs;

import java.util.ArrayList;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.VariableManaCost;
import mage.game.Game;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CostsImpl<T extends Cost> extends ArrayList<T> implements Costs<T> {

	protected Ability ability;

	public CostsImpl() {}

	public CostsImpl(Ability ability) {
		this.ability = ability;
	}

	@Override
	public String getText() {
		if (this.size() == 0)
			return "";

		StringBuilder sbText = new StringBuilder();
		for (T cost: this) {
			sbText.append(cost.getText()).append(", ");
		}
		sbText.setLength(sbText.length() - 2);
		return sbText.toString();
	}

	@Override
	public boolean canPay(UUID playerId, Game game) {
		for (Cost cost: this) {
			if (!cost.canPay(playerId, game))
				return false;
		}
		return true;
	}

	@Override
	public boolean pay(Game game, boolean noMana) {
		if (this.size() > 0) {
			while (!isPaid()) {
				Cost cost = getFirstUnpaid();
				if (!cost.pay(game, noMana))
					return false;
			}
		}
		return true;
	}

	@Override
	public boolean isPaid() {
		for (T cost: this) {
			if (!(cost instanceof VariableManaCost) && !cost.isPaid())
				return false;
		}
		return true;
	}

	@Override
	public void clearPaid() {
		for (T cost: this) {
			cost.clearPaid();
		}
	}

	@Override
	public void setPaid() {
		for (T cost: this) {
			cost.setPaid();
		}
	}

	@Override
	public Ability getAbility() {
		return ability;
	}

	@Override
	public void setAbility(Ability ability) {
		this.ability = ability;
		for (T cost: this) {
			cost.setAbility(ability);
		}
	}

	@Override
	public boolean add(T cost) {
		cost.setAbility(ability);
		return super.add(cost);
	}

	@Override
	public Costs<T> getUnpaid() {
		Costs<T> unpaid = new CostsImpl<T>(ability);
		for (T cost: this) {
			if (!cost.isPaid())
				unpaid.add(cost);
		}
		return unpaid;
	}

	protected T getFirstUnpaid() {
		Costs<T> unpaid = getUnpaid();
		if (unpaid.size() > 0) {
			return unpaid.get(0);
		}
		return null;
 	}

//	@Override
//	public ManaCosts getManaCosts() {
//		ManaCosts manaCosts = new ManaCosts(ability);
//		for (T cost: this) {
//			if (cost instanceof ManaCost)
//				manaCosts.add((ManaCost)cost);
//		}
//		return manaCosts;
//	}
//
//	@Override
//	public List<VariableManaCost> getVariableCosts() {
//		List<VariableManaCost> variableCosts = new ArrayList<VariableManaCost>();
//		for (T cost: this) {
//			if (cost instanceof ManaCosts)
//				variableCosts.addAll(((ManaCosts)cost).getVariableCosts());
//		}
//		return variableCosts;
//	}
}
