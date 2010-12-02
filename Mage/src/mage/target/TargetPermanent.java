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

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.Constants.Zone;
import mage.MageObject;
import mage.abilities.Ability;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TargetPermanent<T extends TargetPermanent<T>> extends TargetObject<TargetPermanent<T>> {

	protected FilterPermanent filter;

	public TargetPermanent() {
		this(1, 1, new FilterPermanent(), false);
	}

	public TargetPermanent(FilterPermanent filter) {
		this(1, 1, filter, false);
	}

	public TargetPermanent(int numTargets, FilterPermanent filter) {
		this(numTargets, numTargets, filter, false);
	}

	public TargetPermanent(int minNumTargets, int maxNumTargets, FilterPermanent filter, boolean notTarget) {
		this.minNumberOfTargets = minNumTargets;
		this.maxNumberOfTargets = maxNumTargets;
		this.zone = Zone.BATTLEFIELD;
		this.filter = filter;
		this.targetName = filter.getMessage();
		this.notTarget = notTarget;
	}

	public TargetPermanent(final TargetPermanent<T> target) {
		super(target);
		this.filter = target.filter.copy();
	}

	@Override
	public boolean canTarget(UUID id, Ability source, Game game) {
		return canTarget(null, id, source, game);
	}

	public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
		Permanent permanent = game.getPermanent(id);
		if (permanent != null) {
			if (source != null)
				//TODO: check for replacement effects
				return permanent.canBeTargetedBy(game.getObject(source.getSourceId())) && filter.match(permanent, controllerId, game);
			else
				return filter.match(permanent, controllerId, game);
		}
		return false;
	}

	@Override
	public FilterPermanent getFilter() {
		return this.filter;
	}

	@Override
	public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
		int count = 0;
		MageObject targetSource = game.getObject(sourceId);
		for (Permanent permanent: game.getBattlefield().getActivePermanents(filter, sourceControllerId, game)) {
			if (permanent.canBeTargetedBy(targetSource)) {
				count++;
				if (count >= this.minNumberOfTargets)
					return true;
			}
		}
		return false;
	}

	@Override
	public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
		Set<UUID> possibleTargets = new HashSet<UUID>();
		MageObject targetSource = game.getObject(sourceId);
		for (Permanent permanent: game.getBattlefield().getActivePermanents(filter, sourceControllerId, game)) {
			if (permanent.canBeTargetedBy(targetSource)) {
				possibleTargets.add(permanent.getId());
			}
		}
		return possibleTargets;
	}

	@Override
	public TargetPermanent copy() {
		return new TargetPermanent(this);
	}

}
