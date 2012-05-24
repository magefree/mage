/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
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

package mage.filter;

import mage.Constants.AbilityType;
import mage.Constants.Outcome;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.game.Game;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FilterAbility<T extends Ability> extends FilterImpl<T, FilterAbility<T>> implements Filter<T> {

	protected static ListComparer<Outcome> compOutcome = new ListComparer<Outcome>();

	protected List<Outcome> outcomes = new ArrayList<Outcome>();
	protected ComparisonScope scopeOutcome = ComparisonScope.All;
	protected boolean notOutcome;
	protected List<AbilityType> types = new ArrayList<AbilityType>();
	protected boolean notType;
	protected Zone zone;
	protected boolean notZone;

	public FilterAbility() {
		super("");
	}

	public FilterAbility(FilterAbility<T> filter) {
		super(filter);
		for (Outcome outcome: filter.outcomes) {
			this.outcomes.add(outcome);
		}
		this.scopeOutcome = filter.scopeOutcome;
		this.notOutcome = filter.notOutcome;
		for (AbilityType aType: filter.types) {
			this.types.add(aType);
		}
		this.notType = filter.notType;
		this.zone = filter.zone;
		this.notZone = filter.notZone;
	}

	@Override
	public boolean match(T object, Game game) {

		if (zone != null) {
			if (object.getZone().match(zone) == notZone)
				return notFilter;
		}

		if (outcomes.size() > 0) {
			if (!compOutcome.compare(outcomes, object.getEffects().getOutcomes(), scopeOutcome, notOutcome))
				return notFilter;
		}
		
		if (types.size() > 0) {
			if (types.contains(object.getAbilityType()) == notType)
				return notFilter;
		}

		return !notFilter;
	}

	public List<Outcome> getOutcomes() {
		return this.outcomes;
	}

	public void setScopeOutcome(ComparisonScope scopeOutcome) {
		this.scopeOutcome = scopeOutcome;
	}

	public void setNotOutcome(boolean notOutcome) {
		this.notOutcome = notOutcome;
	}

	public List<AbilityType> getTypes() {
		return types;
	}

	public void setNotType(boolean notType) {
		this.notType = notType;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	public void setNotZone(boolean notZone) {
		this.notZone = notZone;
	}

	@Override
	public FilterAbility<T> copy() {
		return new FilterAbility<T>(this);
	}

}
