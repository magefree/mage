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

/**
 * Enum for counters, names and instances.
 *
 * @author nantuko
 */
public enum CounterType {
    AGE("Age"),
    AIM("Aim"),
    ARROWHEAD("Arrowhead"),
    AWAKENING("Awakening"),
    BLAZE("Blaze"),
    BRIBERY("Bribery"),
    CHARGE("Charge"),
    DELAY("Delay"),
    DEPLETION("Depletion"),
    DESPAIR("Despair"),
    DEVOTION("Devotion"),
    DIVINITY("Divinity"),
    DOOM("Doom"),
    ELIXIR("Elixir"),
    EON("Eon"),
    EYEBALL("Eyeball"),
    FADE("Fade"),
    FATE("Fate"),
    FEATHER("Feather"),
    FUSE("Fuse"),
    HATCHLING("Hatchling"),
    HOOFPRINT("Hoofprint"),
    ICE("Ice"),
    KI("Ki"),
    LEVEL("Level"),
    LORE("Lore"),
    LUCK("Luck"),
    LOYALTY("Loyalty"),
    M1M1(new BoostCounter(-1, -1).name),
    MINING("Mining"),
    P1P1(new BoostCounter(1, 1).name),
    PAGE("Page"),
    PAIN("Pain"),
    PETRIFICATION("Petrification"),
    PLAGUE("Plague"),
    POISON("Poison"),
    PRESSURE("Pressure"),
    QUEST("Quest"),
    SLIME("Slime"),
    SPORE("Spore"),
    STORAGE("Storage"),
    STUDY("Study"),
    THEFT("Theft"),
    TIME("Time"),
    TOWER("Tower"),
    VELOCITY("Velocity"),
    VILE("Vile"),
    WISH("Wish");

    private final String name;

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
        switch (this) {
            case P1P1:
                return new BoostCounter(1, 1, amount);
            case M1M1:
                return new BoostCounter(-1, -1, amount);
            default:
                return new Counter(name, amount);
        }
    }
}
