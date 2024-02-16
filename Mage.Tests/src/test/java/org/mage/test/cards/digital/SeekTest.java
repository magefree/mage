package org.mage.test.cards.digital;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class SeekTest extends CardTestPlayerBase {

    private static final String lookout = "Skyshroud Lookout";
    private static final String elf = "Cylian Elf";

    @Test
    public void testSeek() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, lookout);
        addCard(Zone.LIBRARY, playerA, elf);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lookout);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, lookout, 1);
        assertHandCount(playerA, 1);
        assertHandCount(playerA, elf, 1);
    }
}
