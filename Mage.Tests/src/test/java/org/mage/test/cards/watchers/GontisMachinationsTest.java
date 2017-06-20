package org.mage.test.cards.watchers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9
 */
public class GontisMachinationsTest extends CardTestPlayerBase {

    /*   
Gonti's Machinations {B}
Enchantment
Whenever you lose life for the first time each turn, you get {E}. (You get an energy counter. Damage causes loss of life.)
Pay {E}{E}, Sacrifice Gonti's Machinations: Each opponent loses 3 life. You gain life equal to the life lost this way.
    */
    private final String gMachinations = "Gonti's Machinations";
    
    /*
     * Reported bug: [[Gonti's Machinations]] currently triggers and gain 1 energy whenever you lose life instead of only the first life loss of each turn.
     * See issue #3499 (test is currently failing due to bug in code)
    */
    @Test
    public void machinations_ThreeCreaturesCombatDamage_OneTrigger() {
        
        String memnite = "Memnite"; // {0} 1/1
        String gBears = "Grizzly Bears"; // {1}{G} 2/2
        String hGiant = "Hill Giant"; // {2}{R} 3/3
        
        addCard(Zone.BATTLEFIELD, playerB, gMachinations);
        addCard(Zone.BATTLEFIELD, playerA, memnite);
        addCard(Zone.BATTLEFIELD, playerA, gBears);
        addCard(Zone.BATTLEFIELD, playerA, hGiant);
        
        attack(3, playerA, memnite);
        attack(3, playerA, gBears);
        attack(3, playerA, hGiant);
        
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        
        assertTapped(memnite, true);
        assertTapped(gBears, true);
        assertTapped(hGiant, true);
        assertLife(playerB, 14); // 1 + 2 + 3 damage
        assertCounterCount(playerB, CounterType.ENERGY, 1);
    }
    
    /*
     * Reported bug: [[Gonti's Machinations]] currently triggers and gain 1 energy whenever you lose life instead of only the first life loss of each turn.
     * See issue #3499 (test is currently failing due to bug in code)
    */
    @Test
    public void machinations_NonCombatDamageThreeTimes_OneTrigger() {
        
        String bolt = "Lightning Bolt"; // {R} deal 3
        
        addCard(Zone.BATTLEFIELD, playerB, gMachinations);
        addCard(Zone.HAND, playerA, bolt, 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, playerB);
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertTappedCount("Mountain", true, 3);
        assertGraveyardCount(playerA, bolt, 3);
        assertLife(playerB, 11); // 3 x 3 damage
        assertCounterCount(playerB, CounterType.ENERGY, 1);
    }
}
