package org.mage.test.cards.abilities.enters;

import mage.constants.PhaseStep;
import mage.constants.Zone;
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
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Master Biomancer", 1);
        addCard(Zone.HAND, playerA, "Mindeye Drake");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mindeye Drake");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
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
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Master Biomancer", 1);

        // If one or more +1/+1 counters would be placed on a creature you control, twice that many +1/+1 counters are placed on it instead.
        addCard(Zone.HAND, playerA, "Corpsejack Menace");
        addCard(Zone.HAND, playerA, "Mindeye Drake");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Corpsejack Menace");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Mindeye Drake");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
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

    /**
     * Progenitor Mimic
     * Creature - Shapeshifter 0/0
     * You may have Progenitor Mimic enter the battlefield as a copy of any creature on the battlefield
     * except it gains "At the beginning of your upkeep, if this creature isn't a token,
     * put a token onto the battlefield that's a copy of this creature."
     *
     * If Progenitor Mimic comes into play, it gets two +1/+1 counters from the Master Biomancer already in play.
     * It copies the Master Biomancer and is therfore a 4/6 creature.
     * The Token generated next round from Progenitor Mimic has to get 2 + 4 counters and is therefore a 8/10 creature.
     */
    @Test
    public void testWithProgenitorMimic() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        // A creature enters the battlefield and gets a counter for each point of power of Master Biomancer
        addCard(Zone.BATTLEFIELD, playerA, "Master Biomancer", 1);
        addCard(Zone.HAND, playerA, "Progenitor Mimic");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Progenitor Mimic");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Master Biomancer", 3);

        // the original Master Biomancer
        assertPowerToughness(playerA, "Master Biomancer", 2, 4);
        // the Progenitor Mimic copying the Master Biomancer
        assertPowerToughness(playerA, "Master Biomancer", 4, 6);
        // the first token created by the Progenitor Mimic copying the Master Biomancer
        assertPowerToughness(playerA, "Master Biomancer", 8, 10);
    }
}
