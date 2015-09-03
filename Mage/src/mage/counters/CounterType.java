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

    AGE("age"),
    AIM("aim"),
    ARROWHEAD("arrowhead"),
    AWAKENING("awakening"),
    BLAZE("blaze"),
    BRIBERY("bribery"),
    CHARGE("charge"),
    DELAY("delay"),
    DEPLETION("depletion"),
    DESPAIR("despair"),
    DEVOTION("devotion"),
    DIVINITY("divinity"),
    DOOM("doom"),
    ELIXIR("elixir"),
    EON("eon"),
    EYEBALL("eyeball"),
    FADE("fade"),
    FATE("fate"),
    FEATHER("feather"),
    FLOOD("flood"),
    FUSE("fuse"),
    GOLD("gold"),
    HATCHLING("hatchling"),
    HEALING("healing"),
    HOOFPRINT("hoofprint"),
    ICE("ice"),
    JAVELIN("javelin"),
    KI("ki"),
    LEVEL("level"),
    LORE("lore"),
    LUCK("luck"),
    LOYALTY("loyalty"),
    MANNEQUIN("mannequin"),
    M1M1(new BoostCounter(-1, -1).name),
    M2M2(new BoostCounter(-2, -2).name),
    MINING("mining"),
    P1P0(new BoostCounter(1, 0).name),
    P1P1(new BoostCounter(1, 1).name),
    P2P2(new BoostCounter(2, 2).name),
    PAGE("page"),
    PAIN("pain"),
    PETRIFICATION("petrification"),
    PLAGUE("plague"),
    POISON("poison"),
    PRESSURE("pressure"),
    QUEST("quest"),
    SHIELD("shield"),
    SHRED("shred"),
    SLIME("slime"),
    SPORE("spore"),
    STORAGE("storage"),
    STRIFE("strife"),
    STUDY("study"),
    THEFT("theft"),
    TIME("time"),
    TOWER("tower"),
    VELOCITY("velocity"),
    VERSE("verse"),
    VILE("vile"),
    VITALITY("vitality"),
    WISH("wish");

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
     * Create instance of counter type with defined amount of counters of the
     * given type.
     *
     * @param amount amount of counters of the given type.
     * @return
     */
    public Counter createInstance(int amount) {
        switch (this) {
            case P1P0:
                return new BoostCounter(1, 0, amount);
            case P1P1:
                return new BoostCounter(1, 1, amount);
            case P2P2:
                return new BoostCounter(2, 2, amount);
            case M1M1:
                return new BoostCounter(-1, -1, amount);
            case M2M2:
                return new BoostCounter(-2, -2, amount);
            default:
                return new Counter(name, amount);
        }
    }
}
