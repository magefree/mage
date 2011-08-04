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

package mage.abilities.costs.mana;

import mage.Constants.ColoredManaSymbol;
import mage.Mana;
import mage.abilities.Ability;
import mage.game.Game;
import mage.players.ManaPool;

public class HybridManaCost extends ManaCostImpl<HybridManaCost> {
	private ColoredManaSymbol mana1;
	private ColoredManaSymbol mana2;

	public HybridManaCost(ColoredManaSymbol mana1, ColoredManaSymbol mana2) {
		this.mana1 = mana1;
		this.mana2 = mana2;
		this.cost = new Mana(mana1);
		this.cost.add(new Mana(mana2));
		addColoredOption(mana1);
		addColoredOption(mana2);
	}

	public HybridManaCost(HybridManaCost manaCost) {
		super(manaCost);
		this.mana1 = manaCost.mana1;
		this.mana2 = manaCost.mana2;
	}

	@Override
	public int convertedManaCost() {
		return 1;
	}

	@Override
	public boolean isPaid() {
		if (paid || isColoredPaid(this.mana1) || isColoredPaid(this.mana2))
			return true;
		return false;
	}

	@Override
	public void assignPayment(Game game, Ability ability, ManaPool pool) {
		if (assignColored(ability, game, pool, this.mana1))
			return;
		assignColored(ability, game, pool, this.mana2);
	}

	@Override
	public String getText() {
		return "{" + mana1.toString() + "/" + mana2.toString() + "}";
	}

	@Override
	public HybridManaCost getUnpaid() {
		return this;
	}

	@Override
	public boolean testPay(Mana testMana) {
		switch (mana1) {
			case B:
				if (testMana.getBlack() > 0)
					return true;
			case U:
				if (testMana.getBlue() >0)
					return true;
			case R:
				if (testMana.getRed() > 0)
					return true;
			case W:
				if (testMana.getWhite() > 0)
					return true;
			case G:
				if  (testMana.getGreen() > 0)
					return true;
		}
		switch (mana2) {
			case B:
				return testMana.getBlack() > 0;
			case U:
				return testMana.getBlue() > 0;
			case R:
				return testMana.getRed() > 0;
			case W:
				return testMana.getWhite() > 0;
			case G:
				return testMana.getGreen() > 0;
		}
		return false;
	}

	@Override
	public HybridManaCost copy() {
		return new HybridManaCost(this);
	}
}
