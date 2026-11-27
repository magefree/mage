package org.mage.test.cards.single.tla;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Bumi, King of Three Trials
 * Legendary Creature - Human Noble Ally 4/4, {5}{G}
 * When Bumi enters, choose up to X, where X is the number of Lesson cards in
 * your graveyard --
 * * Put three +1/+1 counters on Bumi.
 * * Target player scries 3.
 * * Earthbend 3.
 */
public class BumiKingOfThreeTrialsTest extends CardTestPlayerBase {

    /**
     * CR 603.3c / 700.2b: the enters trigger is modal. With no Lesson card in
     * the graveyard X is 0, so no mode can be chosen and the ability is
     * removed from the stack; it must not stay there to resolve as a no-op.
     */
    @Test
    public void testNoLessonRemovesTheModalTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.HAND, playerA, "Bumi, King of Three Trials");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bumi, King of Three Trials");
        // Let the creature spell resolve. The enters trigger must then be off
        // the stack already: no mode can be chosen for a zero-mode charm.
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        checkStackSize("no mode chosen", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Bumi, King of Three Trials", 1);
    }
}
