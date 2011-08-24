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

package mage.counters;

import mage.counters.common.*;

/**
 * Enum for counters, names and instances.
 *
 * @author nantuko
 */
public enum CounterType {
    P1P1(new PlusOneCounter().name),
    M1M1(new MinusOneCounter().name),
    POISON(new PoisonCounter().name),
    CHARGE(new ChargeCounter().name),
    LORE(new LoreCounter().name),
    LOYALTY(new LoyaltyCounter().name),
    LEVEL(new LevelCounter().name),
    TIME(new TimeCounter().name),
    FADE(new FadeCounter().name),
    FEATHER(new FeatherCounter().name),
    QUEST(new QuestCounter().name),
    ARROWHEAD(new ArrowheadCounter().name),
    EON(new EonCounter().name),
	AWAKENING(new AwakeningCounter().name),
    DEVOTION(new DevotionCounter().name),
    DIVINE(new DivineCounter().name),
	WISH(new WishCounter().name),
    HOOFPRINT(new HoofprintCounter().name);

    private String name;

    private CounterType(String name) {
        this.name = name;
    }

    /**
     * Get counter string name.
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * Create instance of counter type with amount equal to 1.
     *
     * @return
     */
    public Counter createInstance() {
        return createInstance(1);
    }

    /**
     * Create instance of counter type with defined amount of counters of the given type.
     *
     * @param amount amount of counters of the given type.
     * @return
     */
    public Counter createInstance(int amount) {
        switch(this) {
            case P1P1:
                return new PlusOneCounter(amount);
            case M1M1:
                return new MinusOneCounter(amount);
            case POISON:
                return new PoisonCounter(amount);
            case CHARGE:
                return new ChargeCounter(amount);
            case LORE:
                return new LoreCounter(amount);
            case LOYALTY:
                return new LoyaltyCounter(amount);
            case LEVEL:
                return new LevelCounter(amount);
            case TIME:
                return new TimeCounter(amount);
            case FADE:
                return new FadeCounter(amount);
            case FEATHER:
                return new FeatherCounter(amount);
            case QUEST:
                return new QuestCounter(amount);
            case ARROWHEAD:
                return new ArrowheadCounter(amount);
            case EON:
                return new EonCounter(amount);
			case AWAKENING:
				return new AwakeningCounter(amount);
            case DEVOTION:
				return new DevotionCounter(amount);
            case DIVINE:
                return new DivineCounter(amount);
			case WISH:
				return new WishCounter(amount);
            case HOOFPRINT:
                return new HoofprintCounter(amount);
        }
        return null;
    }
}
