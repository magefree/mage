package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class DrawNCardsTest extends CardTestPlayerBase {

    private static final String snacker = "Sneaky Snacker";
    private static final String mists = "Reach Through Mists";
    private static final String looting = "Faithless Looting";

    @Test
    public void testSnacker() {
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 1 + 1);
        addCard(Zone.GRAVEYARD, playerA, snacker);
        addCard(Zone.HAND, playerA, mists);
        addCard(Zone.HAND, playerA, looting);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mists);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, looting);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped(snacker, true);
    }
}
