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

package mage.view;

import java.io.Serializable;
import java.util.UUID;
import mage.game.GameState;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CombatGroupView implements Serializable {

	private CardsView attackers = new CardsView();
	private CardsView blockers = new CardsView();
	private String defenderName;

	public CombatGroupView(CombatGroup combatGroup, GameState game) {
		Player player = game.getPlayer(combatGroup.getDefenderId());
		if (player != null) {
			this.defenderName = player.getName();
		}
		else {
			Permanent perm = game.getPermanent(combatGroup.getDefenderId());
			this.defenderName = perm.getName();
		}
		for (UUID id: combatGroup.getAttackers()) {
			attackers.add(new PermanentView(game.getPermanent(id)));
		}
		for (UUID id: combatGroup.getBlockers()) {
			blockers.add(new PermanentView(game.getPermanent(id)));
		}
	}

	public String getDefenderName() {
		return defenderName;
	}

	public CardsView getAttackers() {
		return attackers;
	}

	public CardsView getBlockers() {
		return blockers;
	}
}
