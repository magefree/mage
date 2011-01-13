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

package mage.abilities.keyword;

import mage.Constants.Duration;
import mage.abilities.Ability;
import mage.abilities.EvasionAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class LandwalkAbility extends EvasionAbility<LandwalkAbility> {

	public LandwalkAbility(FilterLandPermanent filter) {
		this.addEffect(new LandwalkEffect(filter));
	}

	public LandwalkAbility(final LandwalkAbility ability) {
		super(ability);
	}

	@Override
	public LandwalkAbility copy() {
		return new LandwalkAbility(this);
	}

}

class LandwalkEffect extends RestrictionEffect<LandwalkEffect> {

	protected FilterLandPermanent filter;

	public LandwalkEffect(FilterLandPermanent filter) {
		super(Duration.WhileOnBattlefield);
		this.filter = filter;
	}

	public LandwalkEffect(final LandwalkEffect effect) {
		super(effect);
		this.filter = effect.filter.copy();
	}

	@Override
	public boolean canBlock(Permanent attacker, Permanent blocker, Game game) {
		return game.getBattlefield().countAll(filter, blocker.getControllerId()) == 0;
	}

	@Override
	public boolean applies(Permanent permanent, Ability source, Game game) {
		if (permanent.getId().equals(source.getSourceId())) {
			return true;
		}
		return false;
	}

	@Override
	public LandwalkEffect copy() {
		return new LandwalkEffect(this);
	}

	@Override
	public String getText(Ability source) {
		return filter.getMessage() + "walk";
	}

}