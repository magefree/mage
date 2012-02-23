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

import java.util.UUID;
import mage.Constants.Zone;
import mage.MageObject;
import mage.abilities.Ability;
import mage.game.Game;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class TargetObject<T extends TargetObject<T>> extends TargetImpl<T> {

	protected TargetObject() {}
	
	public TargetObject(Zone zone) {
		this(1, 1, zone, false);
	}

	public TargetObject(int numTargets, Zone zone) {
		this(numTargets, numTargets, zone, false);
	}

	public TargetObject(final TargetObject target) {
		super(target);
	}

	public TargetObject(int minNumTargets, int maxNumTargets, Zone zone, boolean notTarget) {
		this.minNumberOfTargets = minNumTargets;
		this.maxNumberOfTargets = maxNumTargets;
		this.zone = zone;
		this.targetName = "card";
		this.notTarget = notTarget;
	}
	
	@Override
	public String getTargetedName(Game game) {
		StringBuilder sb = new StringBuilder();
		for (UUID targetId: getTargets()) {
            MageObject object = game.getObject(targetId);
            if (object != null)
                sb.append(object.getName()).append(" ");
		}
		return sb.toString();
	}

	@Override
	public boolean canTarget(UUID id, Game game) {
		MageObject object = game.getObject(id);
		if (object != null && game.getState().getZone(id).match(zone))
			return getFilter().match(object);
		return false;
	}

	@Override
	public boolean canTarget(UUID id, Ability source, Game game) {
		return canTarget(id, game);
	}

}
