/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.conditional;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author jeffwadsworth
 */
public class TheWretchedTest extends CardTestPlayerBase {

    @Test
    public void testGainControl_One_NoRegenThusNothingIsRemovedFromCombat() {

        // At end of combat, gain control of all creatures blocking The Wretched for as long as you control The Wretched.
        addCard(Zone.BATTLEFIELD, playerA, "The Wretched");
        addCard(Zone.BATTLEFIELD, playerB, "Wall of Pine Needles"); // a 3/3 with regeneration
        addCard(Zone.BATTLEFIELD, playerB, "Living Wall"); // 0/6 Wall with regeneration

        attack(3, playerA, "The Wretched");
        block(3, playerB, "Wall of Pine Needles", "The Wretched");
        block(3, playerB, "Living Wall", "The Wretched");
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "The Wretched", 1);
        assertPermanentCount(playerA, "Wall of Pine Needles", 1);
        assertPermanentCount(playerA, "Living Wall", 1);
    }

    @Test
    public void testGainControl_One_RegenWhichRemovesBlockerFromCombat() {

        addCard(Zone.BATTLEFIELD, playerA, "The Wretched");
        addCard(Zone.BATTLEFIELD, playerA, "Bad Moon"); // +1/+1 for black creatures

        addCard(Zone.BATTLEFIELD, playerB, "Forest"); // a 3/3 with regeneration
        addCard(Zone.BATTLEFIELD, playerB, "Wall of Pine Needles"); // a 3/3 with regeneration
        addCard(Zone.BATTLEFIELD, playerB, "Living Wall"); // 0/6 Wall with regeneration

        // The Wretched
        // Creature — Demon - Demon  2/5
        attack(3, playerA, "The Wretched");
        block(3, playerB, "Wall of Pine Needles", "The Wretched");
        block(3, playerB, "Living Wall", "The Wretched");

        activateAbility(3, PhaseStep.DECLARE_BLOCKERS, playerB, "{G}: Regenerate {this}."); // Wall of Pine Needles

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "The Wretched", 1);
        assertPermanentCount(playerA, "Living Wall", 1);

        assertPermanentCount(playerB, "Wall of Pine Needles", 1);

    }

    @Test
    public void testLoseControlOfTheWretched() {
        // At end of combat, gain control of all creatures blocking The Wretched for as long as you control The Wretched.
        addCard(Zone.BATTLEFIELD, playerA, "The Wretched");

        addCard(Zone.BATTLEFIELD, playerB, "Wall of Pine Needles"); // a 3/3 with regeneration
        addCard(Zone.BATTLEFIELD, playerB, "Living Wall"); // 0/6 Wall with regeneration
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.HAND, playerB, "Control Magic");

        attack(3, playerA, "The Wretched");
        block(3, playerB, "Wall of Pine Needles", "The Wretched");
        block(3, playerB, "Living Wall", "The Wretched");

        castSpell(4, PhaseStep.POSTCOMBAT_MAIN, playerB, "Control Magic", "The Wretched");

        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "The Wretched", 1);
        assertPermanentCount(playerA, "Wall of Pine Needles", 0);
        assertPermanentCount(playerB, "Wall of Pine Needles", 1);
        assertPermanentCount(playerB, "Living Wall", 1);
    }

    @Test
    public void testRegenTheWretchedThusRemovingFromCombat() {

        addCard(Zone.BATTLEFIELD, playerA, "The Wretched");
        addCard(Zone.HAND, playerA, "Regenerate");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Wall of Pine Needles"); // a 3/3 with regeneration
        addCard(Zone.BATTLEFIELD, playerB, "Wall of Spears"); // 3/2

        attack(3, playerA, "The Wretched");
        block(3, playerB, "Wall of Pine Needles", "The Wretched");
        block(3, playerB, "Wall of Spears", "The Wretched");

        castSpell(3, PhaseStep.DECLARE_BLOCKERS, playerA, "Regenerate", "The Wretched");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "The Wretched", 1);
        assertPermanentCount(playerB, "Wall of Pine Needles", 1);
        assertPermanentCount(playerB, "Wall of Spears", 1);
    }
}
