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

package mage.abilities.keyword;

import mage.Constants.Duration;
import mage.abilities.Ability;
import mage.abilities.EvasionAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.io.ObjectStreamException;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FlyingAbility extends EvasionAbility<FlyingAbility> {

	private static final FlyingAbility fINSTANCE =  new FlyingAbility();

	private Object readResolve() throws ObjectStreamException {
		return fINSTANCE;
	}

	public static FlyingAbility getInstance() {
		return fINSTANCE;
	}

	private FlyingAbility() {
		this.addEffect(new FlyingEffect());
	}

	@Override
	public String getRule() {
		return "Flying";
	}

	@Override
	public FlyingAbility copy() {
		return fINSTANCE;
	}

}

class FlyingEffect extends RestrictionEffect<FlyingEffect> {

	public FlyingEffect() {
		super(Duration.WhileOnBattlefield);
	}

	public FlyingEffect(final FlyingEffect effect) {
		super(effect);
	}

    @Override
    public void newId() {
        // do nothing
    }

	@Override
	public boolean applies(Permanent permanent, Ability source, Game game) {
		if (permanent.getAbilities().containsKey(FlyingAbility.getInstance().getId())) {
			return true;
		}
		return false;
	}

	@Override
	public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
		if (blocker.getAbilities().containsKey(FlyingAbility.getInstance().getId()) || blocker.getAbilities().containsKey(ReachAbility.getInstance().getId()))
			return true;
		return false;
	}

	@Override
	public FlyingEffect copy() {
		return new FlyingEffect(this);
	}

}