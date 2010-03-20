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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Zone;
import mage.abilities.keyword.PhasingAbility;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Battlefield implements Serializable {

	private Map<UUID, Permanent> field = new HashMap<UUID, Permanent>();

	public void reset() {
		for (Permanent perm: field.values()) {
			perm.reset();
		}
	}

	public void clear() {
		field.clear();
	}

	public int count(FilterPermanent filter) {
		int count = 0;
		for (Permanent permanent: field.values()) {
			if (filter.match(permanent)) {
				count++;
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

	public void handleEvent(GameEvent event, Game game) {
		for (Permanent perm: field.values()) {
			if (perm.isPhasedIn())
				perm.handleEvent(event, game);
		}
	}

	public void endOfTurn(UUID controllerId, Game game) {
		for (Permanent perm: field.values()) {
			if (perm.getControllerId().equals(controllerId))
				perm.endOfTurn(game);
		}
	}

	public Collection<Permanent> getAllPermanents() {
		return field.values();
	}

	public Collection<Permanent> getAllPermanents(UUID controllerId) {
		List<Permanent> perms = new ArrayList<Permanent>();
		for (Permanent perm: field.values()) {
			if (perm.getControllerId().equals(controllerId))
				perms.add(perm);
		}
		return perms;
	}

	public List<Permanent> getActivePermanents() {
		List<Permanent> active = new ArrayList<Permanent>();
		for (Permanent perm: field.values()) {
			if (perm.isPhasedIn())
				active.add(perm);
		}
		return active;
	}

	public List<Permanent> getActivePermanents(UUID controllerId) {
		List<Permanent> active = new ArrayList<Permanent>();
		for (Permanent perm: field.values()) {
			if (perm.isPhasedIn() && perm.getControllerId().equals(controllerId))
				active.add(perm);
		}
		return active;
	}

	public List<Permanent> getActivePermanents(CardType type) {
		List<Permanent> active = new ArrayList<Permanent>();
		for (Permanent perm: field.values()) {
			if (perm.isPhasedIn() && perm.getCardType().contains(type))
				active.add(perm);
		}
		return active;
	}

	public List<Permanent> getActivePermanents(FilterPermanent filter) {
		List<Permanent> active = new ArrayList<Permanent>();
		for (Permanent perm: field.values()) {
			if (perm.isPhasedIn() && filter.match(perm))
				active.add(perm);
		}
		return active;
	}

	public List<Permanent> getActivePermanents(UUID controllerId, CardType type) {
		List<Permanent> active = new ArrayList<Permanent>();
		for (Permanent perm: field.values()) {
			if (perm.isPhasedIn() && perm.getCardType().contains(type) && perm.getControllerId().equals(controllerId))
				active.add(perm);
		}
		return active;
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

//	public List<Permanent> getMatches(FilterPermanent filter) {
//		List<Permanent> matches = new ArrayList<Permanent>();
//		for (Permanent perm: field.values()) {
//			if (filter.match(perm))
//				matches.add(perm);
//		}
//		return matches;
//	}

}
