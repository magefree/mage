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

package mage.abilities.effects.common.counter;

import java.util.UUID;

import mage.Constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author nantuko
 */
public class AddCountersControllerEffect extends OneShotEffect<AddCountersControllerEffect> {

    private Counter counter;
    private boolean enchantedEquipped;

    /**
     * 
     * @param counter Counter to add. Includes type and amount.
     * @param enchantedEquipped If true, not source controller will get counter, 
     * 	but permanent's controller that source enchants or equippes.
     */
	public AddCountersControllerEffect(Counter counter, boolean enchantedEquipped) {
		super(Outcome.Benefit);
		this.counter = counter.copy();
		this.enchantedEquipped = enchantedEquipped;
		setText();
	}

	public AddCountersControllerEffect(final AddCountersControllerEffect effect) {
		super(effect);
		if (effect.counter != null)
			this.counter = effect.counter.copy();
		this.enchantedEquipped = effect.enchantedEquipped;
	}

	@Override
	public boolean apply(Game game, Ability source) {
		UUID uuid = source.getControllerId();
		if (this.enchantedEquipped) {
			Permanent enchantment = game.getPermanent(source.getSourceId());
			if (enchantment != null && enchantment.getAttachedTo() != null) {
				UUID eUuid = enchantment.getAttachedTo();
				Permanent permanent = game.getPermanent(eUuid);
				if (permanent != null) {
					uuid = permanent.getControllerId();
				} else return false;
			} else return false;
		}
		Player player = game.getPlayer(uuid);
		if (player != null) {
			player.addCounters(counter, game);
			return true;
		}
		return false;
	}

    private void setText() {
		if (counter.getCount() > 1) {
			StringBuilder sb = new StringBuilder();
			sb.append("its controller gets ").append(Integer.toString(counter.getCount())).append(" ").append(counter.getName()).append(" counters");
			staticText = sb.toString();
		} else
			staticText = "its controller gets a " + counter.getName() + " counter";
	}

	@Override
	public AddCountersControllerEffect copy() {
		return new AddCountersControllerEffect(this);
	}
}
