package org.mage.test.cards.single.dft;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author jimga150
 */
public class ElvishRefuelerTest extends CardTestPlayerBase {

    @Test
    public void testExhaustPrevention() {
        // During your turn, as long as you haven’t activated an exhaust ability this turn,
        // you may activate exhaust abilities as though they haven’t been activated.
        String refueler = "Elvish Refueler";
        addCard(Zone.BATTLEFIELD, playerA, refueler);
        addCard(Zone.HAND, playerA, refueler);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7);

        // Should be able to activate refueler's exhaust ability
        checkPlayableAbility("Should be able to activate exhaust",
                1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exhaust", true);
        // Activate refueler's exhaust ability
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exhaust");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        // Should not be able to activate refueler's exhaust ability
        checkPlayableAbility("Should not be able to activate exhaust after activating",
                1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Exhaust", false);
        // Casting a second refueler should not allow activating the exhaust ability
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, refueler);
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        // Activating second refueler's exhaust ability
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Exhaust");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        // Should be no exhaust left
        checkPlayableAbility("All exhausts should be used from both refuelers",
                1, PhaseStep.END_TURN, playerA, "Exhaust", false);

        // Confirm on opponent's turn that exhaust is still not available
        checkPlayableAbility("Opponent's turn, effect should not apply",
                2, PhaseStep.END_TURN, playerA, "Exhaust", false);

        // Should be able to activate refueler's exhaust ability on next turn
        checkPlayableAbility("Should be able to activate exhaust on our next turn",
                3, PhaseStep.PRECOMBAT_MAIN, playerA, "Exhaust", true);
        // Activate refueler's exhaust ability
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Exhaust");
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN);
        // Only one exhaust should have been available
        checkPlayableAbility("Already activated exhaust, should not be able to activate again",
                3, PhaseStep.PRECOMBAT_MAIN, playerA, "Exhaust", false);
        setStopAt(3, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }


}
