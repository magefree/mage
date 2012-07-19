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
import mage.abilities.MageSingleton;
import mage.abilities.effects.RestrictionEffect;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.io.ObjectStreamException;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class UnblockableAbility extends EvasionAbility<UnblockableAbility> implements MageSingleton {

    private static final UnblockableAbility fINSTANCE = new UnblockableAbility();

    private Object readResolve() throws ObjectStreamException {
        return fINSTANCE;
    }

    public static UnblockableAbility getInstance() {
        return fINSTANCE;
    }

    private UnblockableAbility() {
        this.addEffect(new UnblockableEffect());
    }

    @Override
    public String getRule() {
        return "Unblockable";
    }

    @Override
    public UnblockableAbility copy() {
        return fINSTANCE;
    }

}

class UnblockableEffect extends RestrictionEffect<UnblockableEffect> implements MageSingleton {

    public UnblockableEffect() {
        super(Duration.EndOfGame);
    }

    public UnblockableEffect(final UnblockableEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getAbilities().containsKey(UnblockableAbility.getInstance().getId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return false;
    }

    @Override
    public UnblockableEffect copy() {
        return new UnblockableEffect(this);
    }

}