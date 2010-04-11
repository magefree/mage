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

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.UUID;
import mage.Constants.PhaseStep;
import mage.Constants.TurnPhase;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TurnMods extends ArrayList<TurnMod> {

	public boolean extraTurn(UUID playerId) {
		ListIterator<TurnMod> it = this.listIterator(this.size());
		while (it.hasPrevious()) {
			TurnMod turnMod = it.previous();
			if (turnMod.isExtraTurn() == true && turnMod.getPlayerId().equals(playerId)) {
				it.remove();
				return true;
			}
		}
		return false;
	}

	public PhaseStep extraStep(UUID playerId, PhaseStep afterStep) {
		ListIterator<TurnMod> it = this.listIterator(this.size());
		while (it.hasPrevious()) {
			TurnMod turnMod = it.previous();
			if (turnMod.getExtraStep() != null && turnMod.getPlayerId().equals(playerId) && (turnMod.getAfterStep() == null || turnMod.getAfterStep() == afterStep)) {
				it.remove();
				return turnMod.getExtraStep();
			}
		}
		return null;
	}

	public TurnPhase extraPhase(UUID playerId, TurnPhase afterPhase) {
		ListIterator<TurnMod> it = this.listIterator(this.size());
		while (it.hasPrevious()) {
			TurnMod turnMod = it.previous();
			if (turnMod.getExtraPhase() != null && turnMod.getPlayerId().equals(playerId) && turnMod.getExtraPhase() != null && (turnMod.getAfterPhase() == null || turnMod.getAfterPhase() == afterPhase)) {
				it.remove();
				return turnMod.getExtraPhase();
			}
		}
		return null;
	}

}
