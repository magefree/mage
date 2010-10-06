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

package mage.game.permanent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.RangeOfInfluence;
import mage.abilities.keyword.PhasingAbility;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Battlefield implements Serializable {

	private Map<UUID, Permanent> field = new LinkedHashMap<UUID, Permanent>();

	public Battlefield () {}

	public Battlefield(final Battlefield battlefield) {
		for (UUID permId: battlefield.field.keySet()) {
			field.put(permId, battlefield.field.get(permId).copy());
		}
	}

	public Battlefield copy() {
		return new Battlefield(this);
	}

	public void reset(Game game) {
		for (Permanent perm: field.values()) {
			perm.reset(game);
		}
	}

	public void clear() {
		field.clear();
	}

	public int countAll(FilterPermanent filter) {
		int count = 0;
		for (Permanent permanent: field.values()) {
			if (filter.match(permanent)) {
				count++;
			}
		}
		return count;
	}

	public int countAll(FilterPermanent filter, UUID controllerId) {
		int count = 0;
		for (Permanent permanent: field.values()) {
			if (permanent.getControllerId().equals(controllerId) && filter.match(permanent)) {
				count++;
			}
		}
		return count;
	}


	public int count(FilterPermanent filter, UUID sourcePlayerId, Game game) {
		int count = 0;
		if (game.getRangeOfInfluence() == RangeOfInfluence.ALL) {
			for (Permanent permanent: field.values()) {
				if (filter.match(permanent, sourcePlayerId, game)) {
					count++;
				}
			}
		}
		else {
			Set<UUID> range = game.getPlayer(sourcePlayerId).getInRange();
			for (Permanent permanent: field.values()) {
				if (range.contains(permanent.getControllerId()) && filter.match(permanent, sourcePlayerId, game)) {
					count++;
				}
			}
		}
		return count;
	}

	public void addPermanent(Permanent permanent) {
		field.put(permanent.getId(), permanent);
	}

	public Permanent getPermanent(UUID key) {
		return field.get(key);
	}

	public void removePermanent(UUID key) {
		field.remove(key);
	}

	public boolean containsPermanent(UUID key) {
		return field.containsKey(key);
	}

	public void checkTriggers(GameEvent event, Game game) {
		if (game.getRangeOfInfluence() == RangeOfInfluence.ALL) {
			for (Permanent perm: field.values()) {
				if (perm.isPhasedIn())
					perm.checkTriggers(event, game);
			}
		}
		else {
			//20100423 - 801.7
			Set<UUID> range = game.getPlayer(event.getPlayerId()).getInRange();
			for (Permanent perm: field.values()) {
				if (range.contains(perm.getControllerId()) && perm.isPhasedIn())
					perm.checkTriggers(event, game);
			}
		}
	}

	public void endOfTurn(UUID controllerId, Game game) {
		for (Permanent perm: field.values()) {
			perm.endOfTurn(game);
		}
	}

	public Collection<Permanent> getAllPermanents() {
		return field.values();
	}

	public Set<UUID> getAllPermanentIds() {
		return field.keySet();
	}

	public List<Permanent> getAllActivePermanents() {
		List<Permanent> active = new ArrayList<Permanent>();
		for (Permanent perm: field.values()) {
			if (perm.isPhasedIn())
				active.add(perm);
		}
		return active;
	}

	/**
	 * Returns all Permanents on the battlefield that are controlled by the specified
	 * player id.  The method ignores the range of influence.
	 * 
	 * @param controllerId
	 * @return a list of Permanent
	 * @see Permanent
	 */
	public List<Permanent> getAllActivePermanents(UUID controllerId) {
		List<Permanent> active = new ArrayList<Permanent>();
		for (Permanent perm: field.values()) {
			if (perm.isPhasedIn() && perm.getControllerId().equals(controllerId))
				active.add(perm);
		}
		return active;
	}

	/**
	 * Returns all Permanents on the battlefield that match the specified CardType.
	 * This method ignores the range of influence.
	 * 
	 * @param type
	 * @return a list of Permanent
	 * @see Permanent
	 */
	public List<Permanent> getAllActivePermanents(CardType type) {
		List<Permanent> active = new ArrayList<Permanent>();
		for (Permanent perm: field.values()) {
			if (perm.isPhasedIn() && perm.getCardType().contains(type))
				active.add(perm);
		}
		return active;
	}

	/**
	 * Returns all Permanents on the battlefield that match the supplied filter.  
	 * This method ignores the range of influence.
	 * 
	 * @param filter
	 * @return a list of Permanent
	 * @see Permanent
	 */
	public List<Permanent> getAllActivePermanents(FilterPermanent filter) {
		List<Permanent> active = new ArrayList<Permanent>();
		for (Permanent perm: field.values()) {
			if (perm.isPhasedIn() && filter.match(perm))
				active.add(perm);
		}
		return active;
	}

	/**
	 * Returns all Permanents that match the filter and are controlled by controllerId.
	 * This method ignores the range of influence.
	 *
	 * @param filter
	 * @param controllerId
	 * @return a list of Permanent
	 * @see Permanent
	 */
	public List<Permanent> getAllActivePermanents(FilterPermanent filter, UUID controllerId) {
		List<Permanent> active = new ArrayList<Permanent>();
		for (Permanent perm: field.values()) {
			if (perm.isPhasedIn() && perm.getControllerId().equals(controllerId) && filter.match(perm))
				active.add(perm);
		}
		return active;
	}

	/**
	 * Returns all Permanents that are within the range of influence  of the specified player id
	 * and that match the supplied filter.
	 * 
	 * @param filter
	 * @param sourcePlayerId
	 * @param game
	 * @return a list of Permanent
	 * @see Permanent
	 */
	public List<Permanent> getActivePermanents(FilterPermanent filter, UUID sourcePlayerId, Game game) {
		if (game.getRangeOfInfluence() == RangeOfInfluence.ALL) {
			return getAllActivePermanents(filter);
		}
		else {
			List<Permanent> active = new ArrayList<Permanent>();
			Set<UUID> range = game.getPlayer(sourcePlayerId).getInRange();
			for (Permanent perm: field.values()) {
				if (perm.isPhasedIn() && range.contains(perm.getControllerId()) && filter.match(perm, sourcePlayerId, game))
					active.add(perm);
			}
			return active;
		}
	}

	/**
	 * Returns all Permanents that are within the range of influence  of the specified player id.
	 * 
	 * @param sourcePlayerId
	 * @param game
	 * @return a list of Permanent
	 * @see Permanent
	 */
	public List<Permanent> getActivePermanents(UUID sourcePlayerId, Game game) {
		if (game.getRangeOfInfluence() == RangeOfInfluence.ALL) {
			return getAllActivePermanents();
		}
		else {
			List<Permanent> active = new ArrayList<Permanent>();
			Set<UUID> range = game.getPlayer(sourcePlayerId).getInRange();
			for (Permanent perm: field.values()) {
				if (perm.isPhasedIn() && range.contains(perm.getControllerId()))
					active.add(perm);
			}
			return active;
		}
	}

	public List<Permanent> getPhasedIn(UUID controllerId) {
		List<Permanent> phasedIn = new ArrayList<Permanent>();
		for (Permanent perm: field.values()) {
			if (perm.getAbilities().containsKey(PhasingAbility.getInstance().getId()) && perm.isPhasedIn() && perm.getControllerId().equals(controllerId))
				phasedIn.add(perm);
		}
		return phasedIn;
	}

	public List<Permanent> getPhasedOut(UUID controllerId) {
		List<Permanent> phasedOut = new ArrayList<Permanent>();
		for (Permanent perm: field.values()) {
			if (!perm.isPhasedIn() && perm.getControllerId().equals(controllerId))
				phasedOut.add(perm);
		}
		return phasedOut;
	}

}
