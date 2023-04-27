package org.mage.test.rollback;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Quercitron
 */
public class ExtraTurnTest extends CardTestPlayerBase {

    // Test that rollback works correctly when extra turn is taken during an opponent turn.
    @Test
    public void testThatRollbackWorksCorrectlyWithExtraTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        // The next sorcery card you cast this turn can be cast as though it had flash.
        addCard(Zone.HAND, playerA, "Quicken");
        // Take an extra turn after this one.
        addCard(Zone.HAND, playerA, "Time Walk");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Quicken", true);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Time Walk");

        rollbackTurns(3, PhaseStep.PRECOMBAT_MAIN, playerA, 0);

        setStopAt(4, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertActivePlayer(playerA);
    }
}
