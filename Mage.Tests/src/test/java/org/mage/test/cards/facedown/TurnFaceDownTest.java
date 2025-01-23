package org.mage.test.cards.facedown;

import mage.constants.EmptyNames;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author PurpleCrowbar
 */
public class TurnFaceDownTest extends CardTestPlayerBase {

    // MDFCs cannot be turned face down when on the battlefield
    @Test
    public void testTurnMDFCFaceDown() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.HAND, playerA, "Ixidron", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Tergrid, God of Fright", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ixidron");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, EmptyNames.FACE_DOWN_CREATURE.toString(), 0);
    }

    // TDFCs cannot be turned face down when on the battlefield
    @Test
    public void testTurnTDFCFaceDown() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.HAND, playerA, "Ixidron", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Delver of Secrets", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ixidron");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, EmptyNames.FACE_DOWN_CREATURE.toString(), 0);
    }

    // TODO: Currently no implemented cards can turn token creatures face down. These tests are to be uncommented after the implementation of [WHO] Cyber Conversion
    // Tokens can be turned face down
    /*
    @Test
    public void testTurnTokenFaceDown() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Cyber Conversion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 1);
        addCard(Zone.HAND, playerB, "Sprout", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Sprout", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cyber Conversion", "Saproling Token");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, EmptyNames.FACE_DOWN_CREATURE.toString(), 1);
    }

    // Incubator tokens can't be turned face down
    @Test
    public void testTurnIncubatorTokenFaceDown() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, "Searing Barb", 1); // Incubate 1
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Cyber Conversion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Searing Barb", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cyber Conversion", "Incubator Token");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 0);
    }
     */

    // Nontoken, non-DFC creatures can be turned face down
    @Test
    public void testTurnNonTokenFaceDown() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.HAND, playerA, "Ixidron", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Runeclaw Bear", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ixidron");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, EmptyNames.FACE_DOWN_CREATURE.toString(), 1);
    }
}
