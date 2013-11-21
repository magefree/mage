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

import mage.counters.common.AgeCounter;
import mage.counters.common.AimCounter;
import mage.counters.common.ArrowheadCounter;
import mage.counters.common.AwakeningCounter;
import mage.counters.common.BlazeCounter;
import mage.counters.common.BriberyCounter;
import mage.counters.common.ChargeCounter;
import mage.counters.common.DespairCounter;
import mage.counters.common.DevotionCounter;
import mage.counters.common.DivinityCounter;
import mage.counters.common.ElixirCounter;
import mage.counters.common.EonCounter;
import mage.counters.common.EyeballCounter;
import mage.counters.common.FadeCounter;
import mage.counters.common.FateCounter;
import mage.counters.common.FeatherCounter;
import mage.counters.common.FuseCounter;
import mage.counters.common.HatchlingCounter;
import mage.counters.common.HoofprintCounter;
import mage.counters.common.IceCounter;
import mage.counters.common.KiCounter;
import mage.counters.common.LevelCounter;
import mage.counters.common.LoreCounter;
import mage.counters.common.LoyaltyCounter;
import mage.counters.common.MiningCounter;
import mage.counters.common.MinusOneCounter;
import mage.counters.common.PageCounter;
import mage.counters.common.PainCounter;
import mage.counters.common.PetrificationCounter;
import mage.counters.common.PlagueCounter;
import mage.counters.common.PlusOneCounter;
import mage.counters.common.PoisonCounter;
import mage.counters.common.PressureCounter;
import mage.counters.common.QuestCounter;
import mage.counters.common.SlimeCounter;
import mage.counters.common.SporeCounter;
import mage.counters.common.StorageCounter;
import mage.counters.common.StudyCounter;
import mage.counters.common.TheftCounter;
import mage.counters.common.TimeCounter;
import mage.counters.common.TowerCounter;
import mage.counters.common.VileCounter;
import mage.counters.common.WishCounter;

/**
 * Enum for counters, names and instances.
 *
 * @author nantuko
 */
public enum CounterType {
    AGE(new AgeCounter().name),
    AIM(new AimCounter().name),
    ARROWHEAD(new ArrowheadCounter().name),
    AWAKENING(new AwakeningCounter().name),
    BLAZE(new BlazeCounter().name),
    BRIBERY(new BriberyCounter().name),
    CHARGE(new ChargeCounter().name),
    DESPAIR(new DespairCounter().name),
    DEVOTION(new DevotionCounter().name),
    DIVINITY(new DivinityCounter().name),
    ELIXIR(new ElixirCounter().name),
    EON(new EonCounter().name),
    EYEBALL(new EyeballCounter().name),
    FADE(new FadeCounter().name),
    FATE(new FateCounter().name),
    FEATHER(new FeatherCounter().name),
    FUSE(new FuseCounter().name),
    HATCHLING(new HatchlingCounter().name),
    HOOFPRINT(new HoofprintCounter().name),
    ICE(new IceCounter().name),
    KI(new KiCounter().name),
    LEVEL(new LevelCounter().name),
    LORE(new LoreCounter().name),
    LOYALTY(new LoyaltyCounter().name),
    M1M1(new MinusOneCounter().name),
    MINING(new MiningCounter().name),
    P1P1(new PlusOneCounter().name),
    PAGE(new PageCounter().name),
    PAIN(new PainCounter().name),
    PETRIFICATION(new PetrificationCounter().name),
    PLAGUE(new PlagueCounter().name),
    POISON(new PoisonCounter().name),
    PRESSURE(new PressureCounter().name),
    QUEST(new QuestCounter().name),
    SLIME(new SlimeCounter().name),
    SPORE(new SporeCounter().name),
    STORAGE(new StorageCounter().name),
    STUDY(new StudyCounter().name),
    THEFT(new TheftCounter().name),
    TIME(new TimeCounter().name),
    TOWER(new TowerCounter().name),
    VILE(new VileCounter().name),
    WISH(new WishCounter().name);

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
        switch(this) {
            case TOWER:
                return new TowerCounter(amount);
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
            case FATE:
                return new FateCounter(amount);
            case FEATHER:
                return new FeatherCounter(amount);
            case FUSE:
                return new FuseCounter(amount);
            case QUEST:
                return new QuestCounter(amount);
            case ARROWHEAD:
                return new ArrowheadCounter(amount);
            case AIM:
                return new AimCounter(amount);
            case EON:
                return new EonCounter(amount);
            case AWAKENING:
                return new AwakeningCounter(amount);
            case DEVOTION:
                return new DevotionCounter(amount);
            case DIVINITY:
                return new DivinityCounter(amount);
            case WISH:
                return new WishCounter(amount);
            case HOOFPRINT:
                return new HoofprintCounter(amount);
            case HATCHLING:
                return new HatchlingCounter(amount);
            case KI:
                return new KiCounter(amount);
            case SLIME:
                return new SlimeCounter(amount);
            case SPORE:
                return new SporeCounter(amount);
            case STORAGE:
                return new StorageCounter(amount);
            case STUDY:
                return new StudyCounter(amount);
            case EYEBALL:
                return new EyeballCounter(amount);
            case ELIXIR:
                return new ElixirCounter(amount);
            case PAIN:
                return new PainCounter(amount);
            case DESPAIR:
                return new DespairCounter(amount);
            case PAGE:
                return new PageCounter(amount);
            case PLAGUE:
                return new PlagueCounter(amount);
            case PRESSURE:
                return new PressureCounter(amount);
            case PETRIFICATION:
                return new PetrificationCounter(amount);
            case MINING:
                return new MiningCounter(amount);
            case THEFT:
                return new TheftCounter(amount);
            case AGE:
                return new AgeCounter(amount);
            case BLAZE:
                return new BlazeCounter(amount);
            case ICE:
                return new IceCounter(amount);
            case VILE:
                return new VileCounter(amount);
                                                                                    
        }
        return null;
    }
}
