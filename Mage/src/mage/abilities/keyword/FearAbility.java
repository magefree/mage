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

import java.io.ObjectStreamException;
import mage.Constants.Duration;
import mage.abilities.Ability;
import mage.abilities.EvasionAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Loki
 */
public class FearAbility extends EvasionAbility<FearAbility> {

	private static final FearAbility fINSTANCE =  new FearAbility();

	private Object readResolve() throws ObjectStreamException {
		return fINSTANCE;
	}

	public static FearAbility getInstance() {
		return fINSTANCE;
	}

	private FearAbility() {
		this.addEffect(new FearEffect());
	}

	@Override
	public String getRule() {
		return "Fear";
	}

	@Override
	public FearAbility copy() {
		return fINSTANCE;
	}

}

class FearEffect extends RestrictionEffect<FearEffect> {

	public FearEffect() {
		super(Duration.WhileOnBattlefield);
	}

	public FearEffect(final FearEffect effect) {
		super(effect);
	}

	@Override
	public boolean applies(Permanent permanent, Ability source, Game game) {
		if (permanent.getAbilities().containsKey(FearAbility.getInstance().getId())) {
			return true;
		}
		return false;
	}

	@Override
	public boolean canBlock(Permanent attacker, Permanent blocker, Game game) {
		if (blocker.getAbilities().containsKey(FearAbility.getInstance().getId()) || blocker.getColor().isBlack()) {
			return true;
        }
		return false;
	}

	@Override
	public FearEffect copy() {
		return new FearEffect(this);
	}

}