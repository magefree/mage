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
import java.util.List;
import java.util.UUID;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CombatGroup implements Serializable {

	protected List<UUID> attackers = new ArrayList<UUID>();
	protected List<UUID> blockers = new ArrayList<UUID>();
	protected List<UUID> blockerOrder = new ArrayList<UUID>();
	protected boolean blocked;
	protected UUID defenderId;
	protected boolean defenderIsPlaneswalker;

	public CombatGroup(UUID defenderId, boolean defenderIsPlaneswalker) {
		this.defenderId = defenderId;
		this.defenderIsPlaneswalker = defenderIsPlaneswalker;
	}

	public boolean hasFirstOrDoubleStrike(Game game) {
		for (UUID permId: attackers) {
			if (hasFirstOrDoubleStrike(game.getPermanent(permId))) {
				return true;
			}
		}
		for (UUID permId: blockers) {
			if (hasFirstOrDoubleStrike(game.getPermanent(permId))) {
				return true;
			}
		}
		return false;
	}

	public UUID getDefenderId() {
		return defenderId;
	}

	public List<UUID> getAttackers() {
		return attackers;
	}

	public List<UUID> getBlockers() {
		return blockers;
	}

	public List<UUID> getBlockerOrder() {
		return blockerOrder;
	}

	private boolean hasFirstOrDoubleStrike(Permanent perm) {
		return perm.getAbilities().containsKey(FirstStrikeAbility.getInstance().getId()) || perm.getAbilities().containsKey(DoubleStrikeAbility.getInstance().getId());
	}

	private boolean hasFirstStrike(Permanent perm) {
		return perm.getAbilities().containsKey(FirstStrikeAbility.getInstance().getId());
	}

	private boolean hasTrample(Permanent perm) {
		return perm.getAbilities().containsKey(TrampleAbility.getInstance().getId());
	}

	public void assignDamage(boolean first, Game game) {
		if (attackers.size() > 0 && (!first || hasFirstOrDoubleStrike(game))) {
			if (blockers.size() == 0) {
				unblockedDamage(first, game);
			}
			else if (blockers.size() == 1) {
				singleBlockerDamage(first, game);
			}
			else {
				multiBlockerDamage(first, game);
			}
		}
	}

	private boolean canDamage(Permanent perm, boolean first) {
		return (first && hasFirstOrDoubleStrike(perm)) || (!first && !hasFirstStrike(perm));
	}

	private void unblockedDamage(boolean first, Game game) {
		for (UUID attackerId: attackers) {
			Permanent attacker = game.getPermanent(attackerId);
			if (canDamage(attacker, first)) {
				//20091005 - 510.1c, 702.17c
				if (!blocked || hasTrample(attacker))
					defenderDamage(attacker, attacker.getPower().getValue(), game);
			}
		}
	}

	private void singleBlockerDamage(boolean first, Game game) {
		//TODO:  handle banding
		Permanent blocker = game.getPermanent(blockers.get(0));
		Permanent attacker = game.getPermanent(attackers.get(0));
		if (blocker != null && attacker != null) {
			if (canDamage(attacker, first)) {
				int damage = attacker.getPower().getValue();
				if (hasTrample(attacker)) {
					int lethalDamage = blocker.getToughness().getValue() - blocker.getDamage();
					if (lethalDamage >= damage) {
						blocker.damage(damage, attacker.getId(), game);
					}
					else {
						Player player = game.getPlayer(attacker.getControllerId());
						int damageAssigned = player.getAmount(lethalDamage, damage, "Assign damage to " + blocker.getName(), game);
						blocker.damage(damageAssigned, attacker.getId(), game);
						damage -= damageAssigned;
						if (damage > 0)
							defenderDamage(attacker, damage, game);
					}
				}
				else {
					blocker.damage(damage, attacker.getId(), game);
				}
			}
			if (canDamage(blocker, first)) {
				attacker.damage(blocker.getPower().getValue(), blocker.getId(), game);
			}
		}
	}

	private void multiBlockerDamage(boolean first, Game game) {
		//TODO:  handle banding
		Permanent attacker = game.getPermanent(attackers.get(0));
		Player player = game.getPlayer(attacker.getControllerId());
		int damage = attacker.getPower().getValue();
		if (attacker != null && canDamage(attacker, first)) {
			for (UUID blockerId: blockerOrder) {
				Permanent blocker = game.getPermanent(blockerId);
				int lethalDamage = blocker.getToughness().getValue() - blocker.getDamage();
				if (lethalDamage >= damage) {
					blocker.damage(damage, attacker.getId(), game);
					damage = 0;
					break;
				}
				int damageAssigned = player.getAmount(lethalDamage, damage, "Assign damage to " + blocker.getName(), game);
				blocker.damage(damageAssigned, attacker.getId(), game);
				damage -= damageAssigned;
			}
			if (damage > 0 && hasTrample(attacker)) {
				defenderDamage(attacker, damage, game);
			}
			for (UUID blockerId: blockerOrder) {
				Permanent blocker = game.getPermanent(blockerId);
				if (canDamage(blocker, first)) {
					attacker.damage(blocker.getPower().getValue(), blocker.getId(), game);
				}
			}
		}
	}

	private void defenderDamage(Permanent attacker, int amount, Game game) {
		if (this.defenderIsPlaneswalker) {
			Permanent defender = game.getPermanent(defenderId);
			if (defender != null) {
				defender.damage(amount, attacker.getId(), game);
			}
		}
		else {
			Player defender = game.getPlayer(defenderId);
			defender.damage(amount, attacker.getId(), game);
		}
	}

	public boolean canBlock(Permanent blocker, Game game) {
		for (UUID attackerId: attackers) {
			if (!blocker.canBlock(attackerId, game))
				return false;
		}
		return true;
	}

	public void addBlocker(UUID blockerId, UUID playerId, Game game) {
		for (UUID attackerId: attackers) {
			if (game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.DECLARE_BLOCKER, blockerId, attackerId, playerId))) {
				return;
			}
		}
		game.getPermanent(blockerId).setBlocking(true);
		blockers.add(blockerId);
		this.blocked = true;
		for (UUID attackerId: attackers) {
			game.fireEvent(GameEvent.getEvent(GameEvent.EventType.BLOCKER_DECLARED, blockerId, attackerId, playerId));
		}
	}

	public int totalAttackerDamage(Game game) {
		int total = 0;
		for (UUID attackerId: attackers) {
			total += game.getPermanent(attackerId).getPower().getValue();
		}
		return total;
	}

	public boolean isDefenderIsPlaneswalker() {
		return defenderIsPlaneswalker;
	}

	void remove(UUID creatureId) {
		if (attackers.contains(creatureId)) {
			attackers.remove(creatureId);
		}
		if (blockers.contains(creatureId)) {
			blockers.remove(creatureId);
			if (blockerOrder.contains(creatureId))
				blockerOrder.remove(creatureId);
		}
	}

}
