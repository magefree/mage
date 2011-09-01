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
import java.util.*;
import mage.abilities.keyword.DeathtouchAbility;

import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.Copyable;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CombatGroup implements Serializable, Copyable<CombatGroup> {

	protected List<UUID> attackers = new ArrayList<UUID>();
	protected List<UUID> blockers = new ArrayList<UUID>();
	protected List<UUID> blockerOrder = new ArrayList<UUID>();
	protected List<UUID> attackerOrder = new ArrayList<UUID>();
	protected Map<UUID, UUID> players = new HashMap<UUID, UUID>();
	protected boolean blocked;
	protected UUID defenderId;
	protected boolean defenderIsPlaneswalker;

	public CombatGroup(UUID defenderId, boolean defenderIsPlaneswalker) {
		this.defenderId = defenderId;
		this.defenderIsPlaneswalker = defenderIsPlaneswalker;
	}

	public CombatGroup(final CombatGroup group) {
		this.blocked = group.blocked;
		this.defenderId = group.defenderId;
		this.defenderIsPlaneswalker = group.defenderIsPlaneswalker;
		for (UUID attackerId: group.attackers) {
			this.attackers.add(attackerId);
		}
		for (UUID blockerId: group.blockers) {
			this.blockers.add(blockerId);
		}
		for (UUID orderId: group.blockerOrder) {
			this.blockerOrder.add(orderId);
		}
		for (UUID orderId: group.attackerOrder) {
			this.attackerOrder.add(orderId);
		}
		for (Map.Entry<UUID, UUID> entry : group.players.entrySet()) {
			players.put(entry.getKey(), entry.getValue());
		}
	}

	protected String getValue(Game game) {
		StringBuilder sb = new StringBuilder(1024);
		for (UUID attackerId: attackers) {
			getPermanentValue(attackerId, sb, game);
		}
		for (UUID blockerId: blockers) {
			getPermanentValue(blockerId, sb, game);
		}
		return sb.toString();
	}

	private void getPermanentValue(UUID permId, StringBuilder sb, Game game) {
		Permanent perm = game.getPermanent(permId);
		sb.append(perm.getValue());
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

	private boolean hasDoubleStrike(Permanent perm) {
		return perm.getAbilities().containsKey(DoubleStrikeAbility.getInstance().getId());
	}

	private boolean hasTrample(Permanent perm) {
		return perm.getAbilities().containsKey(TrampleAbility.getInstance().getId());
	}

	public void assignDamageToBlockers(boolean first, Game game) {
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

	public void assignDamageToAttackers(boolean first, Game game) {
		if (blockers.size() > 0 && (!first || hasFirstOrDoubleStrike(game))) {
			if (attackers.size() == 1) {
				singleAttackerDamage(first, game);
			}
			else {
				multiAttackerDamage(first, game);
			}
		}
	}

	public void applyDamage(Game game) {
		for (UUID uuid : attackers) {
			Permanent permanent = game.getPermanent(uuid);
			if (permanent != null) {
				permanent.applyDamage(game);
			}
		}
		for (UUID uuid : blockers) {
			Permanent permanent = game.getPermanent(uuid);
			if (permanent != null) {
				permanent.applyDamage(game);
			}
		}
	}

	/**
	 * Determines if permanent can damage in current (First Strike or not) combat damage step
	 *
	 * @param perm Permanent to check
	 * @param first First strike or common combat damage step
	 * @return
	 */
	private boolean canDamage(Permanent perm, boolean first) {
		// if now first strike combat damage step
		if (first) {
			// should have first strike or double strike
			return hasFirstOrDoubleStrike(perm);
		}
		// if now not first strike combat
		else {
			if (hasFirstStrike(perm)) {
				// if it has first strike in non FS combat damage step
				// then it can damage only if it has ALSO double strike
				// Fixes Issue 200
				return hasDoubleStrike(perm);
			}
			// can damage otherwise
			return true;
		}
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
						blocker.markDamage(damage, attacker.getId(), game, true, true);
					}
					else {
						Player player = game.getPlayer(attacker.getControllerId());
						int damageAssigned = player.getAmount(lethalDamage, damage, "Assign damage to " + blocker.getName(), game);
						blocker.markDamage(damageAssigned, attacker.getId(), game, true, true);
						damage -= damageAssigned;
						if (damage > 0)
							defenderDamage(attacker, damage, game);
					}
				}
				else {
					blocker.markDamage(damage, attacker.getId(), game, true, true);
				}
			}
			if (canDamage(blocker, first)) {
				if (blocker.getBlocking() == 1) { // blocking several creatures handled separately
					int blockerDamage = blocker.getPower().getValue();
					attacker.markDamage(blockerDamage, blocker.getId(), game, true, true);
				}
			}
		}
	}

	private void multiBlockerDamage(boolean first, Game game) {
		//TODO:  handle banding
		Permanent attacker = game.getPermanent(attackers.get(0));
		if (attacker == null) {
			return;
		}
		Player player = game.getPlayer(attacker.getControllerId());
		int damage = attacker.getPower().getValue();
		if (canDamage(attacker, first)) {
			Map<UUID, Integer> assigned = new HashMap<UUID, Integer>();
			for (UUID blockerId: blockerOrder) {
				Permanent blocker = game.getPermanent(blockerId);
				int lethalDamage;
				if (attacker.getAbilities().containsKey(DeathtouchAbility.getInstance().getId()))
					lethalDamage = 1;
				else
					lethalDamage = blocker.getToughness().getValue() - blocker.getDamage();
				if (lethalDamage >= damage) {
					// Issue#73
					//blocker.damage(damage, attacker.getId(), game, true, true);
					assigned.put(blockerId, damage);
					damage = 0;
					break;
				}
				int damageAssigned = player.getAmount(lethalDamage, damage, "Assign damage to " + blocker.getName(), game);
				// Issue#73
				//blocker.damage(damageAssigned, attacker.getId(), game, true, true);
				assigned.put(blockerId, damageAssigned);
				damage -= damageAssigned;
			}
			if (damage > 0 && hasTrample(attacker)) {
				defenderDamage(attacker, damage, game);
			}
			for (UUID blockerId: blockerOrder) {
				Permanent blocker = game.getPermanent(blockerId);
				if (canDamage(blocker, first)) {
					if (blocker.getBlocking() == 1) { // blocking several creatures handled separately
						attacker.markDamage(blocker.getPower().getValue(), blocker.getId(), game, true, true);
					}
				}
			}
			// Issue#73
			for (Map.Entry<UUID, Integer> entry : assigned.entrySet()) {
				Permanent blocker = game.getPermanent(entry.getKey());
				blocker.markDamage(entry.getValue(), attacker.getId(), game, true, true);
			}
		}
		else {
			for (UUID blockerId: blockerOrder) {
				Permanent blocker = game.getPermanent(blockerId);
				if (canDamage(blocker, first)) {
					attacker.markDamage(blocker.getPower().getValue(), blocker.getId(), game, true, true);
				}
			}
		}
	}

	/**
	 * Damages attacking creatures by a creature that blocked several ones
	 * Damages only attackers as blocker was damage in {@link #singleBlockerDamage}.
	 *
	 * Handles abilities like "{this} an block any number of creatures.".
	 *
	 * @param  first
	 * @param game
	 */
	private void singleAttackerDamage(boolean first, Game game) {
		Permanent blocker = game.getPermanent(blockers.get(0));
		Permanent attacker = game.getPermanent(attackers.get(0));
		if (blocker != null && attacker != null) {
			if (canDamage(blocker, first)) {
				int damage = blocker.getPower().getValue();
				attacker.markDamage(damage, blocker.getId(), game, true, true);
			}
		}
	}

	/**
	 * Damages attacking creatures by a creature that blocked several ones
	 * Damages only attackers as blocker was damage in either {@link #singleBlockerDamage} or {@link #multiBlockerDamage}.
	 *
	 * Handles abilities like "{this} an block any number of creatures.".
	 *
	 * @param first
	 * @param game
	 */
	private void multiAttackerDamage(boolean first, Game game) {
		Permanent blocker = game.getPermanent(blockers.get(0));
		Player player = game.getPlayer(blocker.getControllerId());
		if (blocker == null) {
			return;
		}
		int damage = blocker.getPower().getValue();

		if (canDamage(blocker, first)) {
			Map<UUID, Integer> assigned = new HashMap<UUID, Integer>();
			for (UUID attackerId: attackerOrder) {
				Permanent attacker = game.getPermanent(attackerId);
				int lethalDamage;
				if (blocker.getAbilities().containsKey(DeathtouchAbility.getInstance().getId()))
					lethalDamage = 1;
				else
					lethalDamage = attacker.getToughness().getValue() - attacker.getDamage();
				if (lethalDamage >= damage) {
					assigned.put(attackerId, damage);
					damage = 0;
					break;
				}
				int damageAssigned = player.getAmount(lethalDamage, damage, "Assign damage to " + attacker.getName(), game);
				assigned.put(attackerId, damageAssigned);
				damage -= damageAssigned;
			}

			for (Map.Entry<UUID, Integer> entry : assigned.entrySet()) {
				Permanent attacker = game.getPermanent(entry.getKey());
				attacker.markDamage(entry.getValue(), blocker.getId(), game, true, true);
			}
		}
	}

	private void defenderDamage(Permanent attacker, int amount, Game game) {
		if (this.defenderIsPlaneswalker) {
			Permanent defender = game.getPermanent(defenderId);
			if (defender != null) {
				defender.markDamage(amount, attacker.getId(), game, true, true);
			}
		}
		else {
			Player defender = game.getPlayer(defenderId);
			defender.damage(amount, attacker.getId(), game, true, true);
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
			if (game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.DECLARE_BLOCKER, attackerId, blockerId, playerId))) {
				return;
			}
		}
		Permanent blocker = game.getPermanent(blockerId);
		if (blockerId != null && blocker != null) {
			blocker.setBlocking(blocker.getBlocking() + 1);
			blockers.add(blockerId);
			blockerOrder.add(blockerId);
			this.blocked = true;
			this.players.put(blockerId, playerId);
		}
	}

	public void pickBlockerOrder(UUID playerId, Game game) {
		if (blockers.isEmpty())
			return;
		Player player = game.getPlayer(playerId);
		List<UUID> blockerList = new ArrayList<UUID>(blockers);
		blockerOrder.clear();
		while (true) {
			if (blockerList.size() == 1) {
				blockerOrder.add(blockerList.get(0));
				break;
			}
			else {
				List<Permanent> blockerPerms = new ArrayList<Permanent>();
				for (UUID blockerId: blockerList) {
					blockerPerms.add(game.getPermanent(blockerId));
				}
				UUID blockerId = player.chooseBlockerOrder(blockerPerms, game);
				blockerOrder.add(blockerId);
				blockerList.remove(blockerId);
			}
		}
	}

	public void pickAttackerOrder(UUID playerId, Game game) {
		if (attackers.isEmpty())
			return;
		Player player = game.getPlayer(playerId);
		List<UUID> attackerList = new ArrayList<UUID>(attackers);
		attackerOrder.clear();
		while (true) {
			if (attackerList.size() == 1) {
				attackerOrder.add(attackerList.get(0));
				break;
			}
			else {
				List<Permanent> attackerPerms = new ArrayList<Permanent>();
				for (UUID attackerId: attackerList) {
					attackerPerms.add(game.getPermanent(attackerId));
				}
				UUID attackerId = player.chooseAttackerOrder(attackerPerms, game);
				attackerOrder.add(attackerId);
				attackerList.remove(attackerId);
			}
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
			//20100423 - 509.2a
			if (blockerOrder.contains(creatureId))
				blockerOrder.remove(creatureId);
		}
	}

	public void checkBlockRestrictions(Game game) {
		if (attackers.isEmpty()) {
			return;
		}
		for (UUID uuid : attackers) {
			Permanent attacker = game.getPermanent(uuid);
			if (attacker != null && this.blocked && attacker.getMinBlockedBy() > 1 && blockers.size() > 0 && blockers.size() < attacker.getMinBlockedBy()) {
				for (UUID blockerId : blockers) {
					Permanent blocker = game.getPermanent(blockerId);
					if (blocker != null) {
						blocker.setBlocking(blocker.getBlocking() - 1);
					}
				}
				blockers.clear();
				blockerOrder.clear();
				this.blocked = false;
				game.informPlayers(attacker.getName() + " can't be blocked except by " + attacker.getMinBlockedBy() + " or more creatures. Blockers discarded.");
				return;
			}
		}
		for (UUID blockerId : blockers) {
			for (UUID attackerId: attackers) {
				game.fireEvent(GameEvent.getEvent(GameEvent.EventType.BLOCKER_DECLARED, attackerId, blockerId, players.get(blockerId)));
			}
		}
	}

	@Override
	public CombatGroup copy() {
		return new CombatGroup(this);
	}

}
