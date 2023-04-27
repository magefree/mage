package org.mage.test.cards.single.ice;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class ZursWeirdingTest extends CardTestPlayerBase {

    private static final String weirding = "Zur's Weirding";

    @Test
    public void testYes() {
        addCard(Zone.BATTLEFIELD, playerA, weirding);

        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 - 2);
        assertGraveyardCount(playerB, 1);
        assertHandCount(playerB, 0);
    }

    @Test
    public void testNo() {
        addCard(Zone.BATTLEFIELD, playerA, weirding);

        setChoice(playerA, false);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertGraveyardCount(playerB, 0);
        assertHandCount(playerB, 1);
    }
}
