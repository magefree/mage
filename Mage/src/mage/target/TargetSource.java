/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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

package mage.target;

import mage.Constants.Zone;
import mage.MageObject;
import mage.abilities.Ability;
import mage.filter.FilterObject;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TargetSource extends TargetObject<TargetSource> {

	protected FilterObject filter;

	public TargetSource() {
		this(1, 1, new FilterObject("source of your choice"));
	}

	public TargetSource(FilterObject filter) {
		this(1, 1, filter);
	}

	public TargetSource(int numTargets, FilterObject filter) {
		this(numTargets, numTargets, filter);
	}

	public TargetSource(int minNumTargets, int maxNumTargets, FilterObject filter) {
		this.minNumberOfTargets = minNumTargets;
		this.maxNumberOfTargets = maxNumTargets;
		this.zone = Zone.ALL;
		this.filter = filter;
		this.targetName = filter.getMessage();
	}

	public TargetSource(final TargetSource target) {
		super(target);
		this.filter = target.filter.copy();
	}

	@Override
	public FilterObject getFilter() {
		return filter;
	}

	@Override
	public void add(UUID id, Game game) {
		if (targets.size() < maxNumberOfTargets) {
			if (!targets.containsKey(id)) {
				MageObject object = game.getObject(id);
				if (object != null && object instanceof StackObject) {
					targets.put(((StackObject) object).getSourceId(), 0);
				}
				else {
					targets.put(id, 0);
				}
			}
		}
	}

	@Override
	public boolean canTarget(UUID id, Ability source, Game game) {
		return true;
	}

	@Override
	public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
		return canChoose(sourceControllerId, game);
	}

	@Override
	public boolean canChoose(UUID sourceControllerId, Game game) {
		int count = 0;
		for (StackObject stackObject: game.getStack()) {
			if (game.getPlayer(sourceControllerId).getInRange().contains(stackObject.getControllerId()) && filter.match(stackObject, game)) {
				count++;
				if (count >= this.minNumberOfTargets)
					return true;
			}
		}
		for (Permanent permanent: game.getBattlefield().getActivePermanents(sourceControllerId, game)) {
			if (filter.match(permanent, game)) {
				count++;
				if (count >= this.minNumberOfTargets)
					return true;
			}
		}
		return false;
	}

	@Override
	public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
		return possibleTargets(sourceControllerId, game);
	}

	@Override
	public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
		Set<UUID> possibleTargets = new HashSet<UUID>();
		for (StackObject stackObject: game.getStack()) {
			if (game.getPlayer(sourceControllerId).getInRange().contains(stackObject.getControllerId()) && filter.match(stackObject, game)) {
				possibleTargets.add(stackObject.getId());
			}
		}
		for (Permanent permanent: game.getBattlefield().getActivePermanents(sourceControllerId, game)) {
			if (filter.match(permanent, game)) {
				possibleTargets.add(permanent.getId());
			}
		}
		return possibleTargets;
	}

	@Override
	public TargetSource copy() {
		return new TargetSource(this);
	}

}
