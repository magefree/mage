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
package mage.abilities.dynamicvalue;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.game.Game;

/**
 * The first calculated value is used as long as the class instance is in use
 *
 * IMPORTANT: If used the ability / effect that uses a locked in dynamic value
 * has to really copy the dnamic value in its copy method (not reference)
 *
 * @author LevelX2
 */
public class LockedInDynamicValue implements DynamicValue {

    private boolean valueChecked = false;
    private int lockedInValue;
    private final DynamicValue basicDynamicValue;

    public LockedInDynamicValue(DynamicValue dynamicValue) {
        this.basicDynamicValue = dynamicValue;
    }

    public LockedInDynamicValue(LockedInDynamicValue dynamicValue, final boolean copy) {
        this.basicDynamicValue = dynamicValue.basicDynamicValue;
        this.lockedInValue = dynamicValue.lockedInValue;
        this.valueChecked = dynamicValue.valueChecked;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (!valueChecked) {
            lockedInValue = basicDynamicValue.calculate(game, sourceAbility, effect);
            valueChecked = true;
        }
        return lockedInValue;
    }

    @Override
    public LockedInDynamicValue copy() {
        return new LockedInDynamicValue(this, true);
    }

    @Override
    public String getMessage() {
        return basicDynamicValue.getMessage();
    }

}
