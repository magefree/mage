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
	protected Mana cost;
	protected ManaOptions options;

	@Override
	public abstract T copy();

	public ManaCostImpl() {
		payment = new Mana();
		options = new ManaOptions();
	}

	public ManaCostImpl(final ManaCostImpl manaCost) {
		super(manaCost);
		this.payment = manaCost.payment.copy();
		this.cost = manaCost.cost.copy();
		this.options = manaCost.options.copy();
	}

	@Override
	public Mana getPayment() {
		return payment;
	}

	@Override
	public Mana getMana() {
		return cost;
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

	protected boolean assignColored(Ability ability, Game game, ManaPool pool, ColoredManaSymbol mana) {
		// first check special mana
		switch (mana) {
			case B:
				if (payConditionalBlack(ability, game, pool)) return true;
				if (pool.getBlack() > 0) {
					this.payment.addBlack();
					pool.removeBlack();
					return true;
				}
				break;
			case U:
				if (payConditionalBlue(ability, game, pool)) return true;
				if (pool.getBlue() > 0) {
					this.payment.addBlue();
					pool.removeBlue();
					return true;
				}
				break;
			case W:
				if (payConditionalWhite(ability, game, pool)) return true;
				if (pool.getWhite() > 0) {
					this.payment.addWhite();
					pool.removeWhite();
					return true;
				}
				break;
			case G:
				if (payConditionalGreen(ability, game, pool)) return true;
				if (pool.getGreen() > 0) {
					this.payment.addGreen();
					pool.removeGreen();
					return true;
				}
				break;
			case R:
				if (payConditionalRed(ability, game, pool)) return true;
				if (pool.getRed() > 0) {
					this.payment.addRed();
					pool.removeRed();
					return true;
				}
				break;
		}
		return false;
	}

	private boolean payConditionalRed(Ability ability, Game game, ManaPool pool) {
		if (pool.getConditionalRed(ability, game) > 0) {
			this.payment.addRed();
			pool.removeConditionalRed(ability, game);
			return true;
		}
		return false;
	}

	private boolean payConditionalGreen(Ability ability, Game game, ManaPool pool) {
		if (pool.getConditionalGreen(ability, game) > 0) {
			this.payment.addGreen();
			pool.removeConditionalGreen(ability, game);
			return true;
		}
		return false;
	}

	private boolean payConditionalWhite(Ability ability, Game game, ManaPool pool) {
		if (pool.getConditionalWhite(ability, game) > 0) {
			this.payment.addWhite();
			pool.removeConditionalWhite(ability, game);
			return true;
		}
		return false;
	}

	private boolean payConditionalBlue(Ability ability, Game game, ManaPool pool) {
		if (pool.getConditionalBlue(ability, game) > 0) {
			this.payment.addBlue();
			pool.removeConditionalBlue(ability, game);
			return true;
		}
		return false;
	}

	private boolean payConditionalBlack(Ability ability, Game game, ManaPool pool) {
		if (pool.getConditionalBlack(ability, game) > 0) {
			this.payment.addBlack();
			pool.removeConditionalBlack(ability, game);
			return true;
		}
		return false;
	}

	private boolean payConditionalColorless(Ability ability, Game game, ManaPool pool) {
		if (pool.getConditionalColorless(ability, game) > 0) {
			this.payment.addColorless();
			pool.removeConditionalColorless(ability, game);
			return true;
		}
		return false;
	}

	protected boolean assignColorless(Ability ability, Game game, ManaPool pool, int mana) {
		int conditionalCount = pool.getConditionalCount(ability, game);
		while (mana > payment.count() && (pool.count() > 0 || conditionalCount > 0)) {
			if (payConditionalColorless(ability, game, pool)) continue;
			if (payConditionalBlack(ability, game, pool)) continue;
			if (payConditionalBlue(ability, game, pool)) continue;
			if (payConditionalWhite(ability, game, pool)) continue;
			if (payConditionalGreen(ability, game, pool)) continue;
			if (payConditionalRed(ability, game, pool)) continue;

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
			break;
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
	public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
		if (noMana) {
			setPaid();
			return true;
		}
		Player player = game.getPlayer(controllerId);
		assignPayment(game, ability, player.getManaPool());
		while (!isPaid()) {
			if (player.playMana(this, game))
				assignPayment(game, ability, player.getManaPool());
			else
				return false;
		}
		return true;
	}

	@Override
	public void setPaid() {
		this.paid = true;
	}

	@Override
	public void setPayment(Mana mana) {
		this.payment.add(mana);
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
