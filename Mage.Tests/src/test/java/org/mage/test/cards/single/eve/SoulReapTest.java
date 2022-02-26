package org.mage.test.cards.single.eve;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class SoulReapTest extends CardTestPlayerBase {
    private static final String reap = "Soul Reap";
    private static final String lion = "Silvercoat Lion";
    private static final String rats = "Muck Rats";

    @Test
    public void t() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.HAND, playerA, reap);
        addCard(Zone.HAND, playerA, rats);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rats);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, reap, lion);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, lion, 0);
        assertPermanentCount(playerA, rats, 1);
        assertGraveyardCount(playerA, lion, 1);
        assertGraveyardCount(playerA, reap, 1);
        assertLife(playerA, 20 - 3);
    }

    @Test
    public void t2() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.HAND, playerA, reap);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, reap, lion);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, lion, 0);
        assertGraveyardCount(playerA, lion, 1);
        assertGraveyardCount(playerA, reap, 1);
        assertLife(playerA, 20);
    }
}
