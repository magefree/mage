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

package mage.game.combat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.abilities.keyword.VigilanceAbility;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.players.PlayerList;
import mage.util.Copyable;


/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Combat implements Serializable, Copyable<Combat> {

	protected List<CombatGroup> groups = new ArrayList<CombatGroup>();
	protected Set<UUID> defenders = new HashSet<UUID>();
	protected UUID attackerId;

	public Combat() {}

	public Combat(final Combat combat) {
		this.attackerId = combat.attackerId;
		for (CombatGroup group: combat.groups) {
			groups.add(group.copy());
		}
		for (UUID defenderId: combat.defenders) {
			defenders.add(defenderId);
		}
	}

	public List<CombatGroup> getGroups() {
		return groups;
	}

	public Set<UUID> getDefenders() {
		return defenders;
	}

	public List<UUID> getAttackers() {
		List<UUID> attackers = new ArrayList<UUID>();
		for (CombatGroup group: groups) {
			attackers.addAll(group.attackers);
		}
		return attackers;
	}

	public List<UUID> getBlockers() {
		List<UUID> blockers = new ArrayList<UUID>();
		for (CombatGroup group: groups) {
			blockers.addAll(group.blockers);
		}
		return blockers;
	}

	public void clear() {
		groups.clear();
		defenders.clear();
		attackerId = null;
	}

	public int getValue(Game game) {
		StringBuilder sb = new StringBuilder();
		sb.append(attackerId).append(defenders);
		for (CombatGroup group: groups) {
			sb.append(group.getValue(game));
		}
		return sb.toString().hashCode();
	}

	public void setAttacker(UUID playerId) {
		this.attackerId = playerId;
	}

	public void selectAttackers(Game game) {
		if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.DECLARING_ATTACKERS, attackerId, attackerId))) {
			game.getPlayer(attackerId).selectAttackers(game);
			game.fireEvent(GameEvent.getEvent(GameEvent.EventType.DECLARED_ATTACKERS, attackerId, attackerId));
			game.fireInformEvent(game.getPlayer(attackerId).getName() + " attacks with " + groups.size() + " creatures");
		}
	}

	public void selectBlockers(Game game) {
		if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.DECLARING_BLOCKERS, attackerId, attackerId))) {
			for (UUID defenderId: defenders) {
				//check if defender is being attacked
				if (isAttacked(defenderId, game)) {
					game.getPlayer(defenderId).selectBlockers(game);
					game.fireEvent(GameEvent.getEvent(GameEvent.EventType.DECLARED_BLOCKERS, defenderId, defenderId));
				}
			}
		}
	}

	public void setDefenders(Game game) {
		Set<UUID> opponents = game.getOpponents(attackerId);
		PlayerList players;
		switch (game.getAttackOption()) {
			case LEFT:
				players = game.getState().getPlayerList(attackerId);
				while (true) {
					Player opponent = players.getNext(game);
					if (opponents.contains(opponent.getId())) {
						defenders.add(opponent.getId());
						break;
					}
				}
				break;
			case RIGHT:
				players = game.getState().getPlayerList(attackerId);
				while (true) {
					Player opponent = players.getPrevious(game);
					if (opponents.contains(opponent.getId())) {
						defenders.add(opponent.getId());
						break;
					}
				}
				break;
			case MULITPLE:
				defenders.addAll(game.getOpponents(attackerId));
				break;
		}
	}

	public void declareAttacker(UUID attackerId, UUID defenderId, Game game) {
		if (!defenders.contains(defenderId))
			return;
		Permanent defender = game.getPermanent(defenderId);
		CombatGroup newGroup = new CombatGroup(defenderId, defender != null);
		newGroup.attackers.add(attackerId);
		Permanent attacker = game.getPermanent(attackerId);
		if (!attacker.getAbilities().containsKey(VigilanceAbility.getInstance().getId()))
			attacker.setTapped(true);
		attacker.setAttacking(true);
		groups.add(newGroup);
	}

	public void removeFromCombat(UUID creatureId, Game game) {
		Permanent creature = game.getPermanent(creatureId);
		if (creature != null) {
			creature.setAttacking(false);
			creature.setBlocking(0);
			for (CombatGroup group: groups) {
				group.remove(creatureId);
			}
		}
	}

	public void endCombat(Game game) {
		Permanent creature;
		for (CombatGroup group: groups) {
			for (UUID attacker: group.attackers) {
				creature = game.getPermanent(attacker);
				if (creature != null) {
					creature.setAttacking(false);
					creature.setBlocking(0);
				}
			}
			for (UUID blocker: group.blockers) {
				creature = game.getPermanent(blocker);
				if (creature != null) {
					creature.setAttacking(false);
					creature.setBlocking(0);
				}
			}
		}
		clear();
	}

	public boolean hasFirstOrDoubleStrike(Game game) {
		for (CombatGroup group: groups) {
			if (group.hasFirstOrDoubleStrike(game))
				return true;
		}
		return false;
	}

	public CombatGroup findGroup(UUID attackerId) {
		for (CombatGroup group: groups) {
			if (group.getAttackers().contains(attackerId))
				return group;
		}
		return null;
	}

	public int totalUnblockedDamage(Game game) {
		int total = 0;
		for (CombatGroup group: groups) {
			if (group.getBlockers().isEmpty()) {
				total += group.totalAttackerDamage(game);
			}
		}
		return total;
	}

	public boolean attacksAlone() {
		return (groups.size() == 1 && groups.get(0).getAttackers().size() == 1);
	}

	public boolean noAttackers() {
		if (groups.size() == 0 || getAttackers().size() == 0)
			return true;
		return false;
	}

	public boolean isAttacked(UUID defenderId, Game game) {
		for (CombatGroup group: groups) {
			if (group.getDefenderId().equals(defenderId))
				return true;
			if (group.defenderIsPlaneswalker) {
				Permanent permanent = game.getPermanent(group.getDefenderId());
				if (permanent.getControllerId().equals(defenderId))
					return true;
			}
		}
		return false;
	}

	public void damageAssignmentOrder(Game game) {
		//TODO:  set damage assignment order
	}

	@Override
	public Combat copy() {
		return new Combat(this);
	}

}
