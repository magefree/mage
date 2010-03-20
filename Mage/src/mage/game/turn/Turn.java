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

package mage.game.turn;

import java.io.Serializable;
import java.util.UUID;
import mage.Constants.PhaseStep;
import mage.Constants.TurnPhase;
import mage.game.Game;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Turn implements Serializable {

	private TurnPhase phase;
	private PhaseStep step;
	private UUID activePlayerId;
	private UUID priorityPlayerId;

	public TurnPhase getPhase() {
		return phase;
	}

	public PhaseStep getStep() {
		return step;
	}

	public UUID getActivePlayerId() {
		return activePlayerId;
	}

	public void setActivePlayerId(UUID activePlayerId) {
		this.activePlayerId = activePlayerId;
	}

	public UUID getPriorityPlayerId() {
		return priorityPlayerId;
	}

	public void setPriorityPlayerId(UUID priorityPlayerId) {
		this.priorityPlayerId = priorityPlayerId;
	}

	private boolean nextStep(Game game) {
		if (step == null) {
			phase = TurnPhase.BEGINNING;
			step = PhaseStep.UNTAP;
			return true;
		}
		switch(step) {
			case UNTAP:
				step = PhaseStep.UPKEEP;
				return true;
			case UPKEEP:
				step = PhaseStep.DRAW;
				return true;
			case DRAW:
				phase = TurnPhase.PRECOMBAT_MAIN;
				step = PhaseStep.PRECOMBAT_MAIN;
				return true;
			case PRECOMBAT_MAIN:
				phase = TurnPhase.COMBAT;
				step = PhaseStep.BEGIN_COMBAT;
				return true;
			case BEGIN_COMBAT:
				step = PhaseStep.DECLARE_ATTACKERS;
				return true;
			case DECLARE_ATTACKERS:
				//20091005 - 508.6
				if (game.getCombat().getGroups().size() > 0)
					step = PhaseStep.DECLARE_BLOCKERS;
				else
					step = PhaseStep.END_COMBAT;
				return true;
			case DECLARE_BLOCKERS:
				//20091005 - 510.5
				if (game.getCombat().hasFirstOrDoubleStrike(game))
					step = PhaseStep.FIRST_COMBAT_DAMAGE;
				else
					step = PhaseStep.COMBAT_DAMAGE;
				return true;
			case FIRST_COMBAT_DAMAGE:
				step = PhaseStep.COMBAT_DAMAGE;
				return true;
			case COMBAT_DAMAGE:
				step = PhaseStep.END_COMBAT;
				return true;
			case END_COMBAT:
				phase = TurnPhase.POSTCOMBAT_MAIN;
				step = PhaseStep.POSTCOMBAT_MAIN;
				return true;
			case POSTCOMBAT_MAIN:
				phase = TurnPhase.END;
				step = PhaseStep.END_TURN;
				return true;
			case END_TURN:
				step = PhaseStep.CLEANUP;
				return true;
			case CLEANUP:
				return false;
		}

		return false;
	}

	public void play(Game game, UUID activePlayerId) {
		if (game.isGameOver())
			return;

		phase = null;
		step = null;
		this.activePlayerId = activePlayerId;
		game.getPlayer(activePlayerId).beginTurn();
		while (nextStep(game)) {
			playStep(game);
			game.saveState();
		}
	}

	private void playStep(Game game) {
		if (game.isGameOver())
			return;

		switch(step) {
			case UNTAP:
				game.playUntapStep();
				break;
			case UPKEEP:
				game.playUpkeepStep();
				break;
			case DRAW:
				game.playDrawStep();
				break;
			case PRECOMBAT_MAIN:
				game.playPreCombatMainStep();
				break;
			case BEGIN_COMBAT:
				game.playBeginCombatStep();
				break;
			case DECLARE_ATTACKERS:
				game.playDeclareAttackersStep();
				break;
			case DECLARE_BLOCKERS:
				game.playDeclareBlockersStep();
				break;
			case FIRST_COMBAT_DAMAGE:
				game.playCombatDamageStep(true);
				break;
			case COMBAT_DAMAGE:
				game.playCombatDamageStep(false);
				break;
			case END_COMBAT:
				game.playEndCombatStep();
				break;
			case POSTCOMBAT_MAIN:
				game.playPostMainStep();
				break;
			case END_TURN:
				game.playEndStep();
				break;
			case CLEANUP:
				game.playCleanupStep();
				break;
		}
		//20091005 - 500.4/703.4n
		game.emptyManaPools();
	}


}
