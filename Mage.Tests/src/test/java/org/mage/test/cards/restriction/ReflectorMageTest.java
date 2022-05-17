
package org.mage.test.cards.restriction;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */

public class ReflectorMageTest extends CardTestPlayerBase {


    /**
     * Reported bug: Reflector Mage returning a creature to its owners hand is additionally
     * incorrectly preventing the Reflector Mage's owner from casting that same creature.
     */
    @Test
    public void testReflectorMageAllowsOwnerToCastCreatureReturnedOnSameTurn() {

        // {1}{W}{U} When Reflector Mage enters the battlefield, return target creature an opponent controls to its owner's hand. 
        // That creature's owner can't cast spells with the same name as that creature until your next turn.
        addCard(Zone.HAND, playerA, "Reflector Mage"); // 2/3   
        addCard(Zone.HAND, playerA, "Bronze Sable", 1); // (2) 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Bronze Sable", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reflector Mage");
        addTarget(playerA, "Bronze Sable");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Bronze Sable");

        execute();

        assertPermanentCount(playerB, "Bronze Sable", 0);
        assertHandCount(playerB, "Bronze Sable", 1);
        assertPermanentCount(playerA, "Reflector Mage", 1);
        assertPermanentCount(playerA, "Bronze Sable", 1);
    }

    /**
     * Basic test to confirm the restriction effect still works on the opponent.
     */
    @Test
    public void testReflectorMageRestrictionEffect() {

        // {1}{W}{U} When Reflector Mage enters the battlefield, return target creature an opponent controls to its owner's hand. 
        // That creature's owner can't cast spells with the same name as that creature until your next turn.
        addCard(Zone.HAND, playerA, "Reflector Mage"); // 2/3   
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Bronze Sable", 1); // (2) 2/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reflector Mage");
        addTarget(playerA, "Bronze Sable");

        checkPlayableAbility("sable not available", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Bronze", false);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Bronze Sable", 0);
        assertHandCount(playerB, "Bronze Sable", 1);
        assertPermanentCount(playerA, "Reflector Mage", 1);
    }
}
