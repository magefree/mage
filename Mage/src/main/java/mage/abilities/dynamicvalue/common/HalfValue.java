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
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;

/**
 *
 * @author Quercitron
 */
public class HalfValue implements DynamicValue {

    private final DynamicValue value;
    private final boolean roundedUp;

    public HalfValue(final DynamicValue value, final boolean roundedUp) {
        this.value = value.copy();
        this.roundedUp = roundedUp;
    }

    public HalfValue(final HalfValue halfValue) {
        this.value = halfValue.value.copy();
        this.roundedUp = halfValue.roundedUp;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return (value.calculate(game, sourceAbility, effect) + (roundedUp ? 1 : 0)) / 2;
    }

    @Override
    public HalfValue copy() {
        return new HalfValue(this);
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("half ").append(value.getMessage());
        if (roundedUp) {
            sb.append(", rounded up");
        } else {
            sb.append(", rounded down");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "half ";
    }
}
