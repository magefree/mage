package org.mage.test.cards.single.vis;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class DesertionTest extends CardTestPlayerBase {

    @Test
    public void test_MultipleCounter() {
        // possible bug: error NPE if target spell already countered before resolve

        // Counter target spell.
        // If an artifact or creature spell is countered this way, put that card onto the battlefield under your
        // control instead of into its owner's graveyard.
        addCard(Zone.HAND, playerA, "Desertion", 2); // {3}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2 * 5);
        //
        addCard(Zone.HAND, playerA, "Grizzly Bears");  // {1}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        // counter same spell 2x times
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Desertion", "Grizzly Bears", "Cast Grizzly Bears");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Desertion", "Grizzly Bears", "Cast Grizzly Bears");
        checkStackObject("before resolve", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grizzly Bears", 1);
        checkStackObject("before resolve", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Desertion", 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerA, "Desertion", 2);
    }
}
