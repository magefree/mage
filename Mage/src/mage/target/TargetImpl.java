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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public abstract class TargetImpl<T extends TargetImpl<T>> implements Target {

	protected Map<UUID, Integer> targets = new HashMap<UUID, Integer>();

	protected String targetName;
	protected Zone zone;
	protected int maxNumberOfTargets;
	protected int minNumberOfTargets;
	protected boolean required = false;
	protected boolean chosen = false;
	protected boolean notTarget = false;

	@Override
	public abstract T copy();

	public TargetImpl() {
		this(false);
	}

	public TargetImpl(boolean notTarget) {
		this.notTarget = notTarget;
	}

	public TargetImpl(final TargetImpl<T> target) {
		this.targetName = target.targetName;
		this.zone = target.zone;
		this.maxNumberOfTargets = target.maxNumberOfTargets;
		this.minNumberOfTargets = target.minNumberOfTargets;
		this.required = target.required;
		this.chosen = target.chosen;
		for (UUID id: target.targets.keySet()) {
			this.targets.put(id, target.targets.get(id));
		}
	}

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
	public boolean isNotTarget() {
		return notTarget;
	}

	@Override
	public String getTargetName() {
		return targetName;
	}

	@Override
	public void setTargetName(String name) {
		this.targetName = name;
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
		if (maxNumberOfTargets != 0 && targets.size() == maxNumberOfTargets)
			return true;
		return chosen;
	}

	@Override
	public boolean doneChosing() {
		if (maxNumberOfTargets == 0)
			return false;
		return targets.size() == maxNumberOfTargets;
	}

	@Override
	public void clearChosen() {
		targets.clear();
		chosen = false;
	}

	@Override
	public void add(UUID id, Game game) {
		if (targets.size() < maxNumberOfTargets) {
			if (!targets.containsKey(id)) {
				targets.put(id, 0);
			}
		}
	}

	@Override
	public void addTarget(UUID id, Ability source, Game game) {
		//20100423 - 113.3
		if (maxNumberOfTargets == 0 || targets.size() < maxNumberOfTargets) {
			if (!targets.containsKey(id)) {
				if (source != null) {
					if (!game.replaceEvent(GameEvent.getEvent(EventType.TARGET, id, source.getSourceId(), source.getControllerId()))) {
						targets.put(id, 0);
						chosen = targets.size() >= minNumberOfTargets;
						game.fireEvent(GameEvent.getEvent(EventType.TARGETED, id, source.getSourceId(), source.getControllerId()));
					}
				}
				else {
					targets.put(id, 0);
				}
			}
		}
	}

	@Override
	public void addTarget(UUID id, int amount, Ability source, Game game) {
		if (targets.containsKey(id)) {
			amount += targets.get(id);
		}
		if (source != null) {
			if (!game.replaceEvent(GameEvent.getEvent(EventType.TARGET, id, source.getSourceId(), source.getControllerId()))) {
				targets.put(id, amount);
				chosen = targets.size() >= minNumberOfTargets;
				game.fireEvent(GameEvent.getEvent(EventType.TARGETED, id, source.getSourceId(), source.getControllerId()));
			}
		}
		else {
			targets.put(id, amount);
		}
	}

	@Override
	public boolean choose(Outcome outcome, UUID playerId, Game game) {
		Player player = game.getPlayer(playerId);
		while (!isChosen() && !doneChosing()) {
			chosen = targets.size() >= minNumberOfTargets;
			if (!player.choose(outcome, this, game)) {
				return chosen;
			}
			chosen = targets.size() >= minNumberOfTargets;
		}
		while (!doneChosing()) {
			if (!player.choose(outcome, this, game)) {
				break;
			}
		}
		return chosen = true;
	}

	@Override
	public boolean chooseTarget(Outcome outcome, UUID playerId, Ability source, Game game) {
		Player player = game.getPlayer(playerId);
		while (!isChosen() && !doneChosing()) {
			chosen = targets.size() >= minNumberOfTargets;
			if (!player.chooseTarget(outcome, this, source, game)) {
				return chosen;
			}
			chosen = targets.size() >= minNumberOfTargets;
		}
		while (!doneChosing()) {
			if (!player.chooseTarget(outcome, this, source, game)) {
				break;
			}
		}
		return chosen = true;
	}

	@Override
	public boolean isLegal(Ability source, Game game) {
		for (UUID targetId: targets.keySet()) {
			if (game.replaceEvent(GameEvent.getEvent(EventType.TARGET, targetId, source.getSourceId(), source.getControllerId())))
				return false;
			if (!canTarget(targetId, source, game))
				return false;
		}
		return true;
	}

	@Override
	public List<UUID> getTargets() {
		return new ArrayList<UUID>(targets.keySet());
	}

	@Override
	public int getTargetAmount(UUID targetId) {
		if (targets.containsKey(targetId))
			return targets.get(targetId);
		return 0;
	}

//	@Override
//	public UUID getLastTarget() {
//		if (targets.size() > 0)
//			return targets.keySet().iterator().next();
//		return null;
//	}

	@Override
	public UUID getFirstTarget() {
		if (targets.size() > 0)
			return targets.keySet().iterator().next();
		return null;
	}

}
