package org.mage.test.rollback;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Max-mage
 */
public class AttachmentsTest extends CardTestPlayerBase {

    /**
     * Animate Dead must work after a rollback of the turn where it was already cast
     * (card in graveyard must not keep attachments from the rolled back game state)
     */
    @Test
    public void test_AnimateDeadAfterRollback() {
        // Enchant creature card in a graveyard
        // When Animate Dead enters, if it's on the battlefield, it loses "enchant creature card in a graveyard" and gains
        // "enchant creature put onto the battlefield with Animate Dead." Return enchanted creature card to the
        // battlefield under your control and attach Animate Dead to it. When Animate Dead leaves the battlefield, that
        // creature's controller sacrifices it.
        // Enchanted creature gets -1/-0.
        addCard(Zone.HAND, playerA, "Animate Dead"); // {1}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion"); // 2/2

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Animate Dead", "Silvercoat Lion");
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("first cast", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion", 1);

        // back to the start of turn 3 and cast it again
        rollbackTurns(3, PhaseStep.POSTCOMBAT_MAIN, playerA, 0);
        rollbackAfterActionsStart();
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Animate Dead", "Silvercoat Lion");
        rollbackAfterActionsEnd();

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Animate Dead", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 2 - 1, 2);
    }
}
