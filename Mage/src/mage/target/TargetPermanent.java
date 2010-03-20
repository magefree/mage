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

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import mage.Constants.TargetController;
import mage.Constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TargetPermanent extends TargetObject {

	protected FilterPermanent filter;
	protected TargetController controller;

	public TargetPermanent() {
		this(1, 1, new FilterPermanent(), TargetController.ANY);
	}

	public TargetPermanent(FilterPermanent filter, TargetController controller) {
		this(1, 1, filter, controller);
	}

	public TargetPermanent(int numTargets, FilterPermanent filter, TargetController controller) {
		this(numTargets, numTargets, filter, controller);
	}

	public TargetPermanent(int minNumTargets, int maxNumTargets, FilterPermanent filter, TargetController controller) {
		this.minNumberOfTargets = minNumTargets;
		this.maxNumberOfTargets = maxNumTargets;
		this.zone = Zone.BATTLEFIELD;
		this.filter = filter;
		this.targetName = filter.getMessage();
		this.controller = controller;
	}

	@Override
	public boolean canTarget(UUID id, Game game) {
		return canTarget(null, id, game);
	}

	public boolean canTarget(UUID controllerId, UUID id, Game game) {
		Permanent permanent = game.getPermanent(id);
		if (controllerId != null)
			setController(controllerId, game);
		if (permanent != null) {
			if (this.source != null)
				return permanent.canTarget(game.getObject(this.source.getSourceId())) && filter.match(permanent);
			else
				return filter.match(permanent);
		}
		return false;
	}

	protected void setController(UUID controllerId, Game game) {
		filter.getControllerId().clear();
		switch (controller) {
			case ANY:
				break;
			case YOU:
				filter.getControllerId().add(controllerId);
				filter.setNotController(false);
				break;
			case NOT_YOU:
				filter.getControllerId().add(controllerId);
				filter.setNotController(true);
				break;
			case OPPONENT:
				filter.getControllerId().addAll(game.getOpponents(controllerId));
				filter.setNotController(false);
				break;
		}
	}

	@Override
	public FilterPermanent getFilter() {
		return this.filter;
	}

	@Override
	public boolean canChoose(UUID sourceId, Game game) {
		List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter);
		Iterator<Permanent> it = permanents.iterator();
		while(it.hasNext()) {
			Permanent permanent = it.next();
			if (!permanent.canTarget(game.getObject(sourceId))) {
				it.remove();
			}
		}
		if (permanents.size() >= this.minNumberOfTargets)
			return true;
		return false;
	}

}
