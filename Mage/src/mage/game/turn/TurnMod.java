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
import mage.Constants.TurnPhase;

/**
 * stores extra turns, phases or steps
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TurnMod implements Serializable {

	private UUID playerId;
	private boolean extraTurn;
	private boolean skipTurn;
	private TurnPhase extraPhase;
	private TurnPhase skipPhase;
	private Step extraStep;
	private PhaseStep skipStep;
	private TurnPhase afterPhase;
	private PhaseStep afterStep;

	public TurnMod(UUID playerId, boolean skip) {
		this.playerId = playerId;
		if (skip)
			this.skipTurn = true;
		else
			this.extraTurn = true;
	}

	/**
	 *
	 * @param playerId
	 * @param extraPhase
	 * @param afterPhase - set to null if extraPhase is after the next phase
	 */
	public TurnMod(UUID playerId, TurnPhase phase, TurnPhase afterPhase, boolean skip) {
		this.playerId = playerId;
		if (skip)
			this.skipPhase = phase;
		else
			this.extraPhase = phase;
		this.afterPhase = afterPhase;
	}

	/**
	 *
	 * @param playerId
	 * @param extraStep
	 * @param afterStep - set to null if extraStep is after the next step
	 */
	public TurnMod(UUID playerId, Step step, PhaseStep afterStep) {
		this.playerId = playerId;
		this.extraStep = step;
		this.afterStep = afterStep;
	}

	public TurnMod(UUID playerId, PhaseStep step) {
		this.playerId = playerId;
		this.skipStep = step;
	}	

	public UUID getPlayerId() {
		return playerId;
	}

	public boolean isExtraTurn() {
		return extraTurn;
	}

	public boolean isSkipTurn() {
		return skipTurn;
	}

	public TurnPhase getExtraPhase() {
		return extraPhase;
	}

	public Step getExtraStep() {
		return extraStep;
	}

	public TurnPhase getSkipPhase() {
		return skipPhase;
	}

	public PhaseStep getSkipStep() {
		return skipStep;
	}

	public TurnPhase getAfterPhase() {
		return afterPhase;
	}

	public PhaseStep getAfterStep() {
		return afterStep;
	}

}
