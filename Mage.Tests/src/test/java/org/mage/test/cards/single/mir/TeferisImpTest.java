package org.mage.test.cards.single.mir;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class TeferisImpTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.t.TeferisImp} <br>
     * Teferi's Imp {2}{U} <br>
     * Creature — Imp <br>
     * Flying <br>
     * Phasing <br>
     * Whenever Teferi’s Imp phases out, discard a card. <br>
     * Whenever Teferi’s Imp phases in, draw a card. <br>
     * 1/1
     */
    private static final String imp = "Teferi's Imp";

    @Test
    public void test_Phasing_triggers() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, imp);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, "Grizzly Bears", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, imp);

        checkHandCount("before discard", 2, PhaseStep.END_TURN, playerA, 2);
        // Beginning of turn 3 -- imp phases out. trigger to discard.
        setChoice(playerA, "Grizzly Bears"); // choose to discard one of the Bears
        waitStackResolved(3, PhaseStep.UPKEEP);
        checkHandCount("after discard", 3, PhaseStep.UPKEEP, playerA, 1);

        checkHandCount("before draw", 4, PhaseStep.END_TURN, playerA, 2);
        // Beginning of turn 5 -- imp phases in. trigger to draw.
        waitStackResolved(5, PhaseStep.UPKEEP);
        checkHandCount("after draw", 5, PhaseStep.UPKEEP, playerA, 3);

        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Grizzly Bears", 1);
    }
}
