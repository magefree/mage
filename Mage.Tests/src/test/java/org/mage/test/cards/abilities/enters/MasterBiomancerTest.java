package org.mage.test.cards.abilities.enters;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */


public class MasterBiomancerTest extends CardTestPlayerBase {

    /* Master Biomancer  {2}{G}{U}
     * Creature - Elf Wizard
     * 2/4
     * Each other creature you control enters the battlefield with a number of additional +1/+1 counters 
     * on it equal to Master Biomancer's power and as a Mutant in addition to its other types.
     *
     */
    
    @Test
    public void testCreatureGetsCounters() {

        // a creature enters the battlefield and gets a counter for each point of power of Master Biomancer
        
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Master Biomancer", 1);
        addCard(Constants.Zone.HAND, playerA, "Mindeye Drake");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Mindeye Drake");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Master Biomancer", 1);
        assertPermanentCount(playerA, "Mindeye Drake", 1);

        assertPowerToughness(playerA, "Master Biomancer", 2, 4);
        //   P/T = 2/5 + (2 * +1/+1) = 4 / 7
        assertPowerToughness(playerA, "Mindeye Drake", 4, 7);
    }

    @Test
    public void testCreatureGetsDoubleCountersFromCorpsejackMenace() {

        // a creature enters the battlefield and gets a counter for each point of power of Master Biomancer
        // doubled by Corpsejack Menace (when he ist cast, his own ability will not apply)
        // http://blogs.magicjudges.org/rulestips/2013/03/corpsejack-menace-and-master-biomancer/

        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Master Biomancer", 1);

        addCard(Constants.Zone.HAND, playerA, "Corpsejack Menace");
        addCard(Constants.Zone.HAND, playerA, "Mindeye Drake");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Corpsejack Menace");
        castSpell(3, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Mindeye Drake");

        setStopAt(3, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Master Biomancer", 1);
        assertPermanentCount(playerA, "Corpsejack Menace", 1);
        assertPermanentCount(playerA, "Mindeye Drake", 1);

        assertPowerToughness(playerA, "Master Biomancer", 2, 4);

        //   P/T = 4/4 + (2 * +1/+1) = 6 / 6 (own doubling not active yet)
        assertPowerToughness(playerA, "Corpsejack Menace", 6, 6);

        //   P/T = 2/5 + 2* (2 * +1/+1) = 6 / 9
        assertPowerToughness(playerA, "Mindeye Drake", 6, 9);
    }
}
