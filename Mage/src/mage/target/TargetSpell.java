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

import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TargetSpell extends TargetObject<TargetSpell> {

	protected FilterSpell filter;

	public TargetSpell() {
		this(1, 1, new FilterSpell());
	}

	public TargetSpell(FilterSpell filter) {
		this(1, 1, filter);
	}

	public TargetSpell(int numTargets, FilterSpell filter) {
		this(numTargets, numTargets, filter);
	}

	public TargetSpell(int minNumTargets, int maxNumTargets, FilterSpell filter) {
		this.minNumberOfTargets = minNumTargets;
		this.maxNumberOfTargets = maxNumTargets;
		this.zone = Zone.STACK;
		this.filter = filter;
		this.targetName = filter.getMessage();
	}

	public TargetSpell(final TargetSpell target) {
		super(target);
		this.filter = target.filter.copy();
	}

	@Override
	public FilterSpell getFilter() {
		return filter;
	}

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        // rule 114.4. A spell or ability on the stack is an illegal target for itself.
        if (source != null && source.getId().equals(id)) {
            return false;
        }
        Spell spell = game.getStack().getSpell(id);
        if (spell != null) {
            return filter.match(spell);
        }
        return false;
    }

	@Override
	public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
		return canChoose(sourceControllerId, game);
	}

	@Override
	public boolean canChoose(UUID sourceControllerId, Game game) {
		int count = 0;
		for (StackObject stackObject: game.getStack()) {
			if (stackObject instanceof Spell && game.getPlayer(sourceControllerId).getInRange().contains(stackObject.getControllerId()) && filter.match((Spell)stackObject)) {
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
			if (stackObject instanceof Spell && game.getPlayer(sourceControllerId).getInRange().contains(stackObject.getControllerId()) && filter.match((Spell)stackObject)) {
				possibleTargets.add(stackObject.getId());
			}
		}
		return possibleTargets;
	}

	@Override
	public TargetSpell copy() {
		return new TargetSpell(this);
	}

}
