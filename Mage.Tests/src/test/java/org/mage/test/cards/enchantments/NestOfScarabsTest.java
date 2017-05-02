package org.mage.test.cards.enchantments;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9
 */
public class NestOfScarabsTest extends CardTestPlayerBase {

    /*
    * Reported bug: Nest of Scarabs not triggering off -1/-1 counters placed on creatures.
    * NOTE: this test is failing due to bug in code. See issue #3174
    */
    @Test
    public void scarabsWithSoulStinger_TwoCountersTwoTokens() {
        
        /*
        Nest of Scarabs 2B
        Enchantment
        Whenever you put one or more -1/-1 counters on a creature, create that many 1/1 black Insect creature tokens. 
        */
        String nScarabs = "Nest of Scarabs";
        
        /*
        Soulstinger 3B
        Creature - Scorpion Demon 4/5
        When Soulstinger enters the battlefield, put two -1/-1 counter on target creature you control.
        When Soulstinger dies, you may put a -1/-1 counter on target creature for each -1/-1 counter on Soulstinger. 
        */
        String stinger = "Soulstinger";
        
        addCard(Zone.BATTLEFIELD, playerA, nScarabs);
        addCard(Zone.HAND, playerA, stinger);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, stinger);
        addTarget(playerA, stinger); // place two -1/-1 counters on himself
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertPermanentCount(playerA, nScarabs, 1);
        assertPermanentCount(playerA, stinger, 1);
        assertCounterCount(playerA, stinger, CounterType.M1M1, 2);
        assertPowerToughness(playerA, stinger, 2, 3); // 4/5 with two -1/-1 counters
        assertPermanentCount(playerA, "Insect", 2); // two counters = two insects
    }
}
