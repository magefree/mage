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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Constants.PhaseStep;
import mage.Constants.TurnPhase;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class Phase implements Serializable {

	protected TurnPhase type;
	protected List<Step> steps = new ArrayList<Step>();
	protected EventType event;
	protected EventType preEvent;
	protected EventType postEvent;

	protected UUID activePlayerId;
	protected Step currentStep;
	protected int count;

	public TurnPhase getType() {
		return type;
	}

	public Step getStep() {
		return currentStep;
	}

	public void setStep(Step step) {
		this.currentStep = step;
	}

	public void resetCount() {
		count = 0;
	}

	public int getCount() {
		return count;
	}

	public boolean play(Game game, UUID activePlayerId) {
		if (game.isGameOver())
			return false;

		this.activePlayerId = activePlayerId;

		if (!game.replaceEvent(new GameEvent(event, null, null, activePlayerId))) {
			game.fireEvent(new GameEvent(preEvent, null, null, activePlayerId));

			for (Step step: steps) {
				if (game.isGameOver())
					return false;
				currentStep = step;
				if (!game.getState().getTurnMods().skipStep(activePlayerId, currentStep.getType()))
					playStep(game, activePlayerId);
			}
			count++;
			game.fireEvent(new GameEvent(postEvent, null, null, activePlayerId));
			return true;
		}
		return false;
	}

	public void prePriority(Game game, UUID activePlayerId) {
		currentStep.beginStep(game, activePlayerId);
	}

	public void postPriority(Game game, UUID activePlayerId) {
		currentStep.endStep(game, activePlayerId);
		//20091005 - 500.4/703.4n
		game.emptyManaPools();
		game.saveState();
		//20091005 - 500.9
		playExtraSteps(game, currentStep.getType());
	}

	protected void playStep(Game game, UUID activePlayerId) {
		if (!currentStep.skipStep(game, activePlayerId)) {
			prePriority(game, activePlayerId);
			currentStep.priority(game, activePlayerId);
			postPriority(game, activePlayerId);
		}
	}

	private void playExtraSteps(Game game, PhaseStep afterStep) {
		while (true) {
			Step extraStep = game.getState().getTurnMods().extraStep(activePlayerId, afterStep);
			if (extraStep == null)
				return;
			currentStep = extraStep;
			playStep(game, activePlayerId);
		}
	}

}
