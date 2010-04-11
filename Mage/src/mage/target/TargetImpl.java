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
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class TargetImpl implements Target {

	protected List<UUID> targets = new ArrayList<UUID>();

	protected String targetName;
	protected Zone zone;
	protected int maxNumberOfTargets;
	protected int minNumberOfTargets;
	protected boolean required = false;
	protected Ability source;
	protected boolean chosen = false;

	@Override
	public int getNumberOfTargets() {
		return this.minNumberOfTargets;
	}

	@Override
	public int getMaxNumberOfTargets() {
		return this.maxNumberOfTargets;
	}

	@Override
	public String getMessage() {
		return "Select a " + targetName;
	}

	@Override
	public String getTargetName() {
		return targetName;
	}

	@Override
	public Zone getZone() {
		return zone;
	}

	@Override
	public boolean isRequired() {
		return required;
	}

	@Override
	public void setRequired(boolean required) {
		this.required = required;
	}

	@Override
	public boolean isChosen() {
		return chosen;
	}

	@Override
	public boolean doneChosing() {
		return targets.size() == maxNumberOfTargets;
	}

	@Override
	public void clearChosen() {
		targets.clear();
		chosen = false;
	}

	/**
	 * 
	 * @param id
	 * @param game
	 * @return true if able to add target
	 */
	@Override
	public void addTarget(UUID id, Game game) {
		if (source != null) {
			if (!game.replaceEvent(GameEvent.getEvent(EventType.TARGET, id, source.getSourceId(), source.getControllerId()))) {
				targets.add(id);
				game.fireEvent(GameEvent.getEvent(EventType.TARGETED, id, source.getSourceId(), source.getControllerId()));
			}
		}
		else {
			targets.add(id);
		}
	}

	@Override
	public boolean choose(Outcome outcome, Game game) {
		Player player = game.getPlayer(this.source.getControllerId());
		while (!isChosen()) {
			chosen = targets.size() >= minNumberOfTargets;
			if (!player.chooseTarget(outcome, this, game)) {
				return chosen;
			}
			chosen = targets.size() >= minNumberOfTargets;
		}
		while (!doneChosing()) {
			if (!player.chooseTarget(outcome, this, game)) {
				break;
			}
		}
		return true;
	}


	@Override
	public boolean isLegal(Game game) {
		for (UUID targetId: targets) {
			if (!canTarget(targetId, game))
				return false;
		}
		return true;
	}

	@Override
	public List<UUID> getTargets() {
		return targets;
	}

	@Override
	public UUID getLastTarget() {
		if (targets.size() > 0)
			return targets.get(targets.size() - 1);
		return null;
	}

	@Override
	public UUID getFirstTarget() {
		if (targets.size() > 0)
			return targets.get(0);
		return null;
	}

	@Override
	public Ability getAbility() {
		return source;
	}

	@Override
	public void setAbility(Ability ability) {
		this.source = ability;
	}

}
