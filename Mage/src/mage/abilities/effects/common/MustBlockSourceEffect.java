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

package mage.abilities.effects.common;

import mage.Constants.Duration;
import mage.abilities.Ability;
import mage.abilities.effects.RequirementBlockEffect;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class MustBlockSourceEffect extends RequirementBlockEffect<MustBlockSourceEffect> {

	public MustBlockSourceEffect() {
		super(Duration.WhileOnBattlefield);
	}

	public MustBlockSourceEffect(final MustBlockSourceEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		CombatGroup group = game.getCombat().findGroup(source.getSourceId());
		if (group != null) {
			Player defender = null;
			if (group.isDefenderIsPlaneswalker()) {
				Permanent planeswalker = game.getPermanent(group.getDefenderId());
				if (planeswalker != null) {
					defender = game.getPlayer(planeswalker.getControllerId());
				}
			}
			else {
				defender = game.getPlayer(group.getDefenderId());
			}
			if (defender != null) {
				for (Permanent creature: game.getBattlefield().getAllActivePermanents(FilterCreaturePermanent.getDefault(), defender.getId())) {
					if (group.canBlock(creature, game)) {
						group.addBlocker(creature.getId(), creature.getControllerId(), game);
					}
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public MustBlockSourceEffect copy() {
		return new MustBlockSourceEffect(this);
	}

	@Override
	public String getText(Ability source) {
		return "All creatures able to block {this} do so";
	}

}
