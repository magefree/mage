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
package mage.cards.i;

import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author L_J
 */
public class InfinityElemental extends CardImpl {

    private class MaxMageInt extends MageInt {
        
        private boolean isInfinite = true;
        
        public MaxMageInt() {
            super(Integer.MAX_VALUE);
        }
    
        @Override
        public MaxMageInt copy() {
            if (Objects.equals(this, EmptyMageInt)) {
                return this;
            }
            return new MaxMageInt();
        }
    
        @Override
        public void setValue(int value) {
            this.boostedValue = value;
            this.isInfinite = false;
        }
    
        @Override
        public void boostValue(int amount) {
            if (!isInfinite) {
                this.boostedValue += amount;
            }
        }
    
        @Override
        public void resetToBaseValue() {
            this.boostedValue = this.baseValueModified;
            this.isInfinite = true;
        }
    };

    public InfinityElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MaxMageInt();
        this.toughness = new MageInt(5);
    }

    public InfinityElemental(final InfinityElemental card) {
        super(card);
    }

    @Override
    public InfinityElemental copy() {
        return new InfinityElemental(this);
    }
}
