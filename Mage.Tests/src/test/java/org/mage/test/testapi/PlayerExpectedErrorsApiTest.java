package org.mage.test.testapi;

import junit.framework.TestCase;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests the expected errors for illegal attacks/blocks or other moves when writing tests for players
 *
 * @author Simown
 */

public class PlayerExpectedErrorsApiTest extends CardTestPlayerBase {

    @Test
    public void blockerNotFoundTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Ember Swallower");

        attack(3, playerA, "Ember Swallower");
        // Blocking but playerB doesn't control a Sedge Scorpion
        block(3, playerB, "Sedge Scorpion", "Ember Swallower");

        setStopAt(3, PhaseStep.END_TURN);

        try {
            execute();
            fail("Expected exception not thrown");
        } catch (AssertionError e) {
            // Error - blocking with permanents you don't control
            TestCase.assertEquals("No permanents found called Sedge Scorpion that match the filter criteria \"permanent you control\"", e.getMessage());
        }
    }

    @Test
    public void blockerNotFoundIndexOutOfRangeTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Polis Crusher", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Nessian Asp");

        // Try and block with 3 Polis Crusher - player only has 2 under their control
        attack(2, playerB, "Nessian Asp");
        block(2, playerA, "Polis Crusher:0", "Nessian Asp");
        block(2, playerA, "Polis Crusher:1", "Nessian Asp");
        block(2, playerA, "Polis Crusher:2", "Nessian Asp");

        setStopAt(3, PhaseStep.END_TURN);

        try {
            execute();
            fail("Expected exception not thrown");
        } catch (AssertionError e) {
            // Error - attacking with a permanent you don't control
            TestCase.assertEquals("Cannot find Polis Crusher:2 that match the filter criteria \"permanent you control\".\n" +
                    "Only 2 called Polis Crusher found for this controller(zero indexed).", e.getMessage());
        }
    }

    @Test
    public void attackerNotFoundTest() {

        addCard(Zone.BATTLEFIELD, playerA, "Ember Swallower");

        // Attacking but playerA doesn't control a "Fleshmad Steed" creature
        attack(3, playerA, "Fleshmad Steed");

        setStopAt(3, PhaseStep.END_TURN);

        try {
            execute();
            fail("Expected exception not thrown");
        } catch (AssertionError e) {
            // Error - attacking with a permanent you don't control
            TestCase.assertEquals("No permanents found called Fleshmad Steed that match the filter criteria \"permanent you control\"", e.getMessage());
        }
    }

    @Test
    public void attackerNotFoundIndexOutOfRangeTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Yoked Ox", 3);

        // Try and attack with 4 Yoked Ox - will fail as the player only has 3
        attack(3, playerA, "Yoked Ox:0");
        attack(3, playerA, "Yoked Ox:1");
        attack(3, playerA, "Yoked Ox:2");
        attack(3, playerA, "Yoked Ox:3");

        setStopAt(3, PhaseStep.END_TURN);

        try {
            execute();
            fail("Expected exception not thrown");
        } catch (AssertionError e) {
            // Error - attacking with a permanent you don't control
            TestCase.assertEquals("Cannot find Yoked Ox:3 that match the filter criteria \"permanent you control\".\n" +
                    "Only 3 called Yoked Ox found for this controller(zero indexed).", e.getMessage());
        }
    }

    @Test
    public void blockOnYourTurnTestA() {
        addCard(Zone.BATTLEFIELD, playerA, "Leonin Snarecaster");
        addCard(Zone.BATTLEFIELD, playerA, "Bronze Sable");

        attack(1, playerA, "Bronze Satyr");
        // Illegal block on your own turn and blocking one of your own creatures
        block(1, playerA, "Leonin Snarecaster", "Bronze Sable");

        setStopAt(3, PhaseStep.END_TURN);

        try {
            execute();
            fail("Expected exception not thrown");
        } catch (UnsupportedOperationException ue) {
            TestCase.assertEquals("PlayerA can't block on turn 1 as it is their turn", ue.getMessage());
        }
    }


    @Test
    public void blockOnYourTurnTestB() {
        addCard(Zone.BATTLEFIELD, playerB, "Leonin Snarecaster");
        addCard(Zone.BATTLEFIELD, playerB, "Bronze Sable");

        attack(6, playerB, "Bronze Satyr");
        // Illegal block on your own turn and blocking one of your own creatures
        block(6, playerB, "Leonin Snarecaster", "Bronze Sable");

        setStopAt(7, PhaseStep.END_TURN);

        try {
            execute();
        } catch (UnsupportedOperationException ue) {
            TestCase.assertEquals("PlayerB can't block on turn 6 as it is their turn", ue.getMessage());
        }
    }

    @Test
    public void attackOnOthersTurnTestA() {
        addCard(Zone.BATTLEFIELD, playerA, "Nemesis of Mortals");
        // Invalid attack - turn 2 is playerB's turn so attacking is not allowed
        attack(2, playerA, "Nemesis of Mortals");

        setStopAt(3, PhaseStep.END_TURN);

        try {
            execute();
        } catch (UnsupportedOperationException ue) {
            TestCase.assertEquals("PlayerA can't attack on turn 2 as it is not their turn", ue.getMessage());
        }

    }

    @Test
    public void attackOnOthersTurnTestB() {
        addCard(Zone.BATTLEFIELD, playerB, "Nemesis of Mortals");
        // Invalid attack - turn 2 is playerB's turn so attacking is not allowed
        attack(1, playerB, "Nemesis of Mortals");

        setStopAt(3, PhaseStep.END_TURN);

        try {
            execute();
        } catch (UnsupportedOperationException ue) {
            TestCase.assertEquals("PlayerB can't attack on turn 1 as it is not their turn", ue.getMessage());
        }

    }


    @Test
    public void cantBlockAnotherTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Fabled Hero");
        addCard(Zone.BATTLEFIELD, playerA, "Leafcrown Dryad");

        addCard(Zone.BATTLEFIELD, playerB, "Nemesis of Mortals");

        attack(1, playerA, "Fabled Hero");
        attack(1, playerA, "Leafcrown Dryad");
        // Failure - Nemesis of mortals can't block both creatures at the same time
        block(1, playerB, "Nemesis of Mortals", "Fabled Hero");
        block(1, playerB, "Nemesis of Mortals", "Leafcrown Dryad");

        setStopAt(3, PhaseStep.END_TURN);

        try {
            execute();
            fail("Expected exception not thrown");
        } catch (UnsupportedOperationException ue) {
            TestCase.assertEquals("Nemesis of Mortals cannot block Leafcrown Dryad it is already blocking the maximum amount of creatures.", ue.getMessage());
        }
    }

    @Test
    public void maximumBlockNotReachedTest() {
        // 3/2 with Menace
        addCard(Zone.BATTLEFIELD, playerA, "Boggart Brute");
        addCard(Zone.BATTLEFIELD, playerB, "Akroan Skyguard");

        attack(3, playerA, "Boggart Brute");
        // Block has to fail, because Two-Headed Sliver can't be blocked except by two or more creatures
        block(3, playerB, "Akroan Skyguard", "Boggart Brute");

        setStopAt(3, PhaseStep.END_TURN);

        try {
            execute();
            fail("Expected exception not thrown");
        } catch (UnsupportedOperationException e) {
            TestCase.assertEquals("Boggart Brute is blocked by 1 creature(s). It has to be blocked by 2 or more.", e.getMessage());
        }
    }

    @Test
    public void minimumBlockNotReachedTest() {
        /* Underworld Cerberus {3}{B}{3} 6/6
         *  Underworld Cerberus can't be blocked except by three or more creatures.
         *  Cards in graveyards can't be the targets of spells or abilities.
         *  When Underworld Cerberus dies, exile it and each player returns all creature cards from their graveyard to their hand.
         */
        addCard(Zone.BATTLEFIELD, playerA, "Underworld Cerberus");
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 2); // 1/1

        attack(3, playerA, "Underworld Cerberus");
        block(3, playerB, "Memnite:0", "Underworld Cerberus");
        block(3, playerB, "Memnite:1", "Underworld Cerberus");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);

        try {
            execute();
            fail("Expected exception not thrown");
        } catch (UnsupportedOperationException e) {
            assertEquals("Underworld Cerberus is blocked by 2 creature(s). It has to be blocked by 3 or more.", e.getMessage());
        }
    }

}
