
package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Thragtusk - Beast {4}{G} When Thragtusk enters the battlefield, you gain 5
 * life. When Thragtusk leaves the battlefield, put a 3/3 green Beast creature
 * token onto the battlefield.
 *
 * @author LevelX2
 */
public class ThragtuskTest extends CardTestPlayerBase {

    /**
     * Test if a Thragtusk is copied by a PhyrexianMetamorph that both triggers
     * correct work
     */
    @Test
    public void testPhyrexianMetamorph() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        // You may have Phyrexian Metamorph enter the battlefield as a copy of any artifact or creature on the battlefield, except it's an artifact in addition to its other types
        addCard(Zone.HAND, playerA, "Phyrexian Metamorph", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 6);
        addCard(Zone.HAND, playerB, "Public Execution", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Thragtusk", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phyrexian Metamorph");
        setChoice(playerA, true);
        setChoice(playerA, "Thragtusk");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Public Execution", "Thragtusk");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Thragtusk", 1);

        assertGraveyardCount(playerA, "Phyrexian Metamorph", 1);
        assertGraveyardCount(playerB, "Public Execution", 1);

        assertLife(playerA, 23);
        assertLife(playerB, 20); // Thragtusk ETB ability does not trigger if set to battlefield on test game start

        assertPermanentCount(playerA, "Beast Token", 1);

    }

    /**
     * Test if a Thragtusk is copied by a Phyrexian Metamorph that leave
     * battlefield ability does not work, if the copy left all abilities by Turn
     * to Frog
     */

    @Test
    public void testPhyrexianMetamorphTurnToFrog() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        // You may have Phyrexian Metamorph enter the battlefield as a copy of any artifact or creature on the battlefield, except it's an artifact in addition to its other types
        addCard(Zone.HAND, playerA, "Phyrexian Metamorph", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 6);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 6);
        addCard(Zone.HAND, playerB, "Tortoise Formation", 1);
        addCard(Zone.HAND, playerB, "Turn to Frog", 1);
        addCard(Zone.HAND, playerB, "Public Execution", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Thragtusk", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phyrexian Metamorph");
        setChoice(playerA, true);
        setChoice(playerA, "Thragtusk");

        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, "Tortoise Formation");

        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerB, "Turn to Frog", "Thragtusk");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Public Execution", "Thragtusk");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Tortoise Formation", 1);
        assertGraveyardCount(playerB, "Turn to Frog", 1);

        assertPermanentCount(playerB, "Thragtusk", 1);
        assertPermanentCount(playerA, "Thragtusk", 0);

        assertGraveyardCount(playerA, "Phyrexian Metamorph", 1);
        assertGraveyardCount(playerB, "Public Execution", 1);

        assertLife(playerA, 23);
        assertLife(playerB, 20); // Thragtusk ETB ability does not trigger if set to battlefield on test game start

        assertPermanentCount(playerA, "Beast Token", 0);

    }

}
