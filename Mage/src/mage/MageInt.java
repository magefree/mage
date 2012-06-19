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

package mage;

import java.io.Serializable;
import mage.util.Copyable;

public class MageInt implements Serializable, Copyable<MageInt> {

    public static MageInt EmptyMageInt = new MageInt(Integer.MIN_VALUE, null) {

        private static final String exceptionMessage = "MageInt.EmptyMageInt can't be modified.";

        @Override
        public void boostValue(int amount) {
            throw new RuntimeException(exceptionMessage);
        }

        @Override
        public void setValue(int value) {
            throw new RuntimeException(exceptionMessage);
        }
    };

    protected int baseValue;
    protected String cardValue = "";

    public MageInt(int value) {
        this.baseValue = value;
        this.cardValue = Integer.toString(value);
    }

    public MageInt(int baseValue, String cardValue) {
        this.baseValue = baseValue;
        this.cardValue = cardValue;
    }

    @Override
    public MageInt copy() {
        if (this == EmptyMageInt)
            return this;
        return new MageInt(baseValue, cardValue);
    }

    public int getValue() {
        return baseValue;
    }

    public void setValue(int value) {
        this.baseValue = value;
    }

    public void boostValue(int amount) {
        this.baseValue += amount;
    }

    @Override
    public String toString() {
        return cardValue;
    }

}
