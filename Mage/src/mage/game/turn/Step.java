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
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class Step<T extends Step<T>> implements Serializable {

	private PhaseStep type;
	private boolean hasPriority;
	protected EventType stepEvent;
	protected EventType preStepEvent;
	protected EventType postStepEvent;

	public abstract T copy();

	public Step(PhaseStep type, boolean hasPriority) {
		this.type = type;
		this.hasPriority = hasPriority;
	}

	public Step(final Step step) {
		this.type = step.type;
		this.hasPriority = step.hasPriority;
		this.stepEvent = step.stepEvent;
		this.preStepEvent = step.preStepEvent;
		this.postStepEvent = step.postStepEvent;
	}

	public PhaseStep getType() {
		return type;
	}

	public void beginStep(Game game, UUID activePlayerId) {
		game.fireEvent(new GameEvent(preStepEvent, null, null, activePlayerId));
	}

	public void priority(Game game, UUID activePlayerId) {
		if (hasPriority)
			game.playPriority(activePlayerId);
	}

	public void endStep(Game game, UUID activePlayerId) {
		game.fireEvent(new GameEvent(postStepEvent, null, null, activePlayerId));
	}

	public boolean skipStep(Game game, UUID activePlayerId) {
		return game.replaceEvent(new GameEvent(stepEvent, null, null, activePlayerId));
	}

	public boolean getHasPriority() {
		return this.hasPriority;
	}

}
