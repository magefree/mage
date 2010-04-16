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

package mage.game.turn;

import java.io.Serializable;
import java.util.UUID;
import mage.Constants.PhaseStep;
import mage.game.Game;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Step implements Serializable {

	private PhaseStep type;

	public Step(PhaseStep type) {
		this.type = type;
	}

	public PhaseStep getType() {
		return type;
	}

	public boolean play(Game game, UUID activePlayerId) {
		switch(type) {
			case UNTAP:
				return game.playUntapStep(activePlayerId);
			case UPKEEP:
				return game.playUpkeepStep(activePlayerId);
			case DRAW:
				return game.playDrawStep(activePlayerId);
			case PRECOMBAT_MAIN:
				return game.playPreCombatMainStep(activePlayerId);
			case BEGIN_COMBAT:
				return game.playBeginCombatStep(activePlayerId);
			case DECLARE_ATTACKERS:
				if (game.getPlayer(activePlayerId).hasAvailableAttackers(game))
					return game.playDeclareAttackersStep(activePlayerId);
				return false;
			case DECLARE_BLOCKERS:
				if (!game.getCombat().noAttackers())
					return game.playDeclareBlockersStep(activePlayerId);
				return false;
			case FIRST_COMBAT_DAMAGE:
				if (!game.getCombat().noAttackers() && game.getCombat().hasFirstOrDoubleStrike(game))
					return game.playCombatDamageStep(activePlayerId, true);
				return false;
			case COMBAT_DAMAGE:
				if (!game.getCombat().noAttackers())
					return game.playCombatDamageStep(activePlayerId, false);
				return false;
			case END_COMBAT:
				return game.playEndCombatStep(activePlayerId);
			case POSTCOMBAT_MAIN:
				return game.playPostMainStep(activePlayerId);
			case END_TURN:
				return game.playEndStep(activePlayerId);
			case CLEANUP:
				return game.playCleanupStep(activePlayerId);
		}
		return false;
	}

}
