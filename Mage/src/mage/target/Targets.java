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

package mage.target;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Constants.Outcome;
import mage.abilities.Ability;
import mage.game.Game;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Targets extends ArrayList<Target> {

	public Targets() {}

	public Targets(final Targets targets) {
		for (Target target: targets) {
			this.add(target.copy());
		}
	}

	public List<Target> getUnchosen() {
		List<Target> unchosen = new ArrayList<Target>();
		for (Target target: this) {
			if (!target.isChosen())
				unchosen.add(target);
		}
		return unchosen;
	}

	public void clearChosen() {
		for (Target target: this) {
			target.clearChosen();
		}
	}

	public boolean isChosen() {
		for (Target target: this) {
			if (!target.isChosen())
				return false;
		}
		return true;
	}

	public boolean choose(Outcome outcome, UUID playerId, Ability source, Game game) {
		if (this.size() > 0) {
			while (!isChosen()) {
				Target target = this.getUnchosen().get(0);
				if (!target.choose(outcome, playerId, source, game))
					return false;
			}
		}
		return true;
	}

	public boolean stillLegal(Ability source, Game game) {
		for (Target target: this) {
			if (!target.isLegal(source, game)) {
				return false;
			}
		}
		return true;
	}

	public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
		for (Target target: this) {
			if (!target.canChoose(sourceId, sourceControllerId, game))
				return false;
		}
		return true;
	}

	public UUID getFirstTarget() {
		if (this.size() > 0)
			return this.get(0).getFirstTarget();
		return null;
	}

	public Targets copy() {
		return new Targets(this);
	}
}
