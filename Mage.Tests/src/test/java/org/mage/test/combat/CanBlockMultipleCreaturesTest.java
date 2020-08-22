
package org.mage.test.combat;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 *
 * @author jeffwadsworth
 * @author Simown
 */
public class CanBlockMultipleCreaturesTest extends CardTestPlayerBase {

    @Test
    public void testMultipleBlockWithTrample() {

        addCard(Zone.BATTLEFIELD, playerA, "Watcher in the Web", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Ulrich, Uncontested Alpha", 1); // 6/6
        addCard(Zone.BATTLEFIELD, playerB, "Kessig Dire Swine", 1); // 6/6 (trample if delirium)
        addCard(Zone.BATTLEFIELD, playerB, "Howlpack Wolf", 1); // 3/3
        addCard(Zone.BATTLEFIELD, playerB, "Incorrigible Youths", 1); // 4/3

        // Trample requirement for Kessig Dire Swine
        addCard(Zone.GRAVEYARD, playerB, "Forest", 1);
        addCard(Zone.GRAVEYARD, playerB, "Memnite", 1);
        addCard(Zone.GRAVEYARD, playerB, "Flight", 1);
        addCard(Zone.GRAVEYARD, playerB, "Drain Life", 1);

        // Attack with all 4 creatures and block all with the Watcher in the Web
        attack(2, playerB, "Kessig Dire Swine");
        attack(2, playerB, "Ulrich, Uncontested Alpha");
        attack(2, playerB, "Howlpack Wolf");
        attack(2, playerB, "Incorrigible Youths");

        // BLOCKING ORDER MATTERS - the trampling creature must be selected to block first
        // You can manually change the blocking order but it's easier to assign them in order
        block(2, playerA, "Watcher in the Web", "Kessig Dire Swine");
        block(2, playerA, "Watcher in the Web", "Ulrich, Uncontested Alpha");
        block(2, playerA, "Watcher in the Web", "Howlpack Wolf");
        block(2, playerA, "Watcher in the Web", "Incorrigible Youths");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 19);

    }

    @Test
    public void testMultipleBlockWithTrample2() {

        addCard(Zone.BATTLEFIELD, playerA, "Watcher in the Web", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Ulrich, Uncontested Alpha", 1); // 6/6
        addCard(Zone.BATTLEFIELD, playerB, "Kessig Dire Swine", 1); // 6/6 (trample if delirium)
        addCard(Zone.BATTLEFIELD, playerB, "Howlpack Wolf", 1); // 3/3
        addCard(Zone.BATTLEFIELD, playerB, "Incorrigible Youths", 1); // 4/3

        // Trample requirement for Kessig Dire Swine
        addCard(Zone.GRAVEYARD, playerB, "Forest", 1);
        addCard(Zone.GRAVEYARD, playerB, "Memnite", 1);
        addCard(Zone.GRAVEYARD, playerB, "Flight", 1);
        addCard(Zone.GRAVEYARD, playerB, "Drain Life", 1);

        // Attack with all 4 creatures and block all with the Watcher in the Web
        attack(2, playerB, "Kessig Dire Swine");
        attack(2, playerB, "Ulrich, Uncontested Alpha");
        attack(2, playerB, "Howlpack Wolf");
        attack(2, playerB, "Incorrigible Youths");

        // BLOCKING ORDER MATTERS - the trampling creature must be selected to block first
        block(2, playerA, "Watcher in the Web", "Kessig Dire Swine");
        block(2, playerA, "Watcher in the Web", "Ulrich, Uncontested Alpha");
        block(2, playerA, "Watcher in the Web", "Howlpack Wolf");
        // Don't block Incorrigible Youths

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // Damage 1 from Kessig Dire Swine + 4 from Incorrigible Youths
        assertLife(playerA, 15);
    }

    @Test
    public void testCanOnlyBlockSingle() {

        // Hundred-Handed One {2}{W}{W}
        // Monstrosity 3. {3}{W}{W}{W} (If this creature isn't monstrous, put three +1/+1 counters on it and it becomes monstrous.)
        //As long as Hundred-Handed One is monstrous, it has reach and can block an additional ninety-nine creatures each combat.
        addCard(Zone.BATTLEFIELD, playerA, "Hundred-Handed One", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Bronze Sable", 1); // 2/1
        addCard(Zone.BATTLEFIELD, playerB, "Fabled Hero", 1);  // 2/2 double strike

        // Attack with all 4 creatures and try and block both with hundred-handed one
        attack(2, playerB, "Bronze Sable");
        attack(2, playerB, "Fabled Hero");

        block(2, playerA, "Hundred-Handed One", "Bronze Sable");
        block(2, playerA, "Hundred-Handed One", "Fabled Hero");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);

        // Will fail on purpose - we are trying to block too many creatures!
        try {
            execute();
            fail("Expected exception not thrown");
        } catch(UnsupportedOperationException e) {
            assertEquals("Hundred-Handed One cannot block Fabled Hero it is already blocking the maximum amount of creatures.", e.getMessage());
        }
    }

    @Test
    public void testCanBlockMultiple() {

        // Hundred-Handed One {2}{W}{W}
        // Monstrosity 3. {3}{W}{W}{W} (If this creature isn't monstrous, put three +1/+1 counters on it and it becomes monstrous.)
        // As long as Hundred-Handed One is monstrous, it has reach and can block an additional ninety-nine creatures each combat.
        addCard(Zone.BATTLEFIELD, playerA, "Hundred-Handed One", 1);
        // For monstrosity
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);

        addCard(Zone.BATTLEFIELD, playerB, "Bronze Sable", 1); // 2/1
        addCard(Zone.BATTLEFIELD, playerB, "Fabled Hero", 1);  // 2/2 double strike

        // Make hundred-handed one monstrous
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}{W}{W}{W}: Monstrosity 3.");

        // Attack with all 4 creatures and try and block both with hundred-handed one
        attack(2, playerB, "Bronze Sable");
        attack(2, playerB, "Fabled Hero");

        block(2, playerA, "Hundred-Handed One", "Bronze Sable");
        block(2, playerA, "Hundred-Handed One", "Fabled Hero");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);

        // Will not fail this time as hundred-handed one is monstrous and can block up to 100 creatures
        execute();

        // Was a 3/5 but monstrosity 3
        assertPowerToughness(playerA, "Hundred-Handed One", 6, 8);
        // No one has been hit
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }
    
    /*
     * Reported bug: Night Market Guard was able to block a creature with Menace
    */
    @Test
    public void testNightMarketGuardShouldNotBlockCreatureWithMenace()
    {
        /*
        Night Market Guard {3} 3/1
        Artifact Creature — Construct
        Night Market Guard can block an additional creature each combat.
        */
        String nMarketGuard = "Night Market Guard";
        
        /*
        Embraal Bruiser {1}{B}
        Creature - Human Warrior
        Embraal Bruiser enters the battlefield tapped.
        Embraal Bruiser has menace as long as you control an artifact.
        */
        String eBruiser = "Embraal Bruiser";
        
        /* 
        {0} 1/1
         * Artifact Creature — Construct
        */
        String memnite = "Memnite";
        
        addCard(Zone.BATTLEFIELD, playerA, nMarketGuard);
        addCard(Zone.BATTLEFIELD, playerB, eBruiser);
        addCard(Zone.BATTLEFIELD, playerB, memnite); // only here to grant Embraal Menace
        
        attack(4, playerB, eBruiser);
        block(4, playerA, nMarketGuard, eBruiser);
        
        setStopAt(4, PhaseStep.POSTCOMBAT_MAIN);

        // Catch the illegal block
        try {
            execute();
            fail("Expected exception not thrown");
        } catch(UnsupportedOperationException e) {
            assertEquals("Embraal Bruiser is blocked by 1 creature(s). It has to be blocked by 2 or more.", e.getMessage());
        }

    }
}