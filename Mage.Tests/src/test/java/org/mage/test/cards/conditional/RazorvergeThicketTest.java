package org.mage.test.cards.conditional;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author tobz
 */
public class RazorvergeThicketTest extends CardTestPlayerBase {

    @Test
    public void testEntersTappedForThreeLands() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, "Razorverge Thicket");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Razorverge Thicket");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped("Razorverge Thicket", true);
    }

    @Test
    public void testEntersUntappedForTwoLands() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Razorverge Thicket");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Razorverge Thicket");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped("Razorverge Thicket", false);
    }

}
