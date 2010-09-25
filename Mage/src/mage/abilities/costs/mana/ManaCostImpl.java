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

import java.util.UUID;
import mage.Constants.ColoredManaSymbol;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.CostImpl;
import mage.abilities.mana.ManaOptions;
import mage.game.Game;
import mage.players.ManaPool;
import mage.players.Player;

public abstract class ManaCostImpl<T extends ManaCostImpl<T>> extends CostImpl<T> implements ManaCost {

	protected Mana payment;
	protected ManaOptions options;

	@Override
	public abstract T copy();

	public ManaCostImpl() {
		payment = new Mana();
		options = new ManaOptions();
	}

	public ManaCostImpl(final ManaCostImpl cost) {
		super(cost);
		this.payment = cost.payment.copy();
		this.options = cost.options.copy();
	}

	@Override
	public Mana getPayment() {
		return payment;
	}

	@Override
	public ManaOptions getOptions() {
		return options;
	}

	@Override
	public void clearPaid() {
		payment.clear();
		super.clearPaid();
	}

	protected boolean assignColored(ManaPool pool, ColoredManaSymbol mana) {
		switch (mana) {
			case B:
				if (pool.getBlack() > 0) {
					this.payment.addBlack();
					pool.removeBlack();
					return true;
				}
				break;
			case U:
				if (pool.getBlue() > 0) {
					this.payment.addBlue();
					pool.removeBlue();
					return true;
				}
				break;
			case W:
				if (pool.getWhite() > 0) {
					this.payment.addWhite();
					pool.removeWhite();
					return true;
				}
				break;
			case G:
				if (pool.getGreen() > 0) {
					this.payment.addGreen();
					pool.removeGreen();
					return true;
				}
				break;
			case R:
				if (pool.getRed() > 0) {
					this.payment.addRed();
					pool.removeRed();
					return true;
				}
				break;
		}
		return false;
	}

	protected boolean assignColorless(ManaPool pool, int mana) {
		while (mana > payment.count() && pool.count() > 0) {
			if (pool.getColorless() > 0) {
				this.payment.addColorless();
				pool.removeColorless();
				continue;
			}
			if (pool.getBlack() > 0) {
				this.payment.addBlack();
				pool.removeBlack();
				continue;
			}
			if (pool.getBlue() > 0) {
				this.payment.addBlue();
				pool.removeBlue();
				continue;
			}
			if (pool.getWhite() > 0) {
				this.payment.addWhite();
				pool.removeWhite();
				continue;
			}
			if (pool.getGreen() > 0) {
				this.payment.addGreen();
				pool.removeGreen();
				continue;
			}
			if (pool.getRed() > 0) {
				this.payment.addRed();
				pool.removeRed();
				continue;
			}
		}
		return mana > payment.count();
	}

	protected boolean isColoredPaid(ColoredManaSymbol mana) {
		switch (mana) {
			case B:
				if (this.payment.getBlack() > 0)
					return true;
			case U:
				if (this.payment.getBlue() > 0)
					return true;
			case W:
				if (this.payment.getWhite() > 0)
					return true;
			case G:
				if (this.payment.getGreen() > 0)
					return true;
			case R:
				if (this.payment.getRed() > 0)
					return true;
		}
		return false;
	}

	protected boolean isColorlessPaid(int mana) {
		if (this.payment.count() >= mana)
			return true;
		return false;
	}

	@Override
	public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
		return true;
	}

	@Override
	public boolean pay(Game game, UUID sourceId, UUID controllerId, boolean noMana) {
		if (noMana) {
			setPaid();
			return true;
		}
		Player player = game.getPlayer(controllerId);
		assignPayment(player.getManaPool());
		while (!isPaid()) {
			if (player.playMana(this, game))
				assignPayment(player.getManaPool());
			else
				return false;
		}
		return true;
	}

	@Override
	public void setPaid() {
		this.paid = true;
	}

	protected void addColoredOption(ColoredManaSymbol symbol) {
		switch (symbol) {
			case B:
				this.options.add(Mana.BlackMana);
				break;
			case U:
				this.options.add(Mana.BlueMana);
				break;
			case W:
				this.options.add(Mana.WhiteMana);
				break;
			case R:
				this.options.add(Mana.RedMana);
				break;
			case G:
				this.options.add(Mana.GreenMana);
				break;
		}
	}

}
