package org.mage.test.cards.single.arb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class SenTripletsTest extends CardTestPlayerBase {

    private static final String triplets = "Sen Triplets";
    private static final String bolt = "Lightning Bolt";
    private static final String relic = "Darksteel Relic";

    private void initTriplets() {
        addCard(Zone.BATTLEFIELD, playerA, triplets);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerB, "Taiga");
        addCard(Zone.HAND, playerB, bolt);
        addCard(Zone.HAND, playerB, relic);
        addCard(Zone.HAND, playerB, "Island");
    }

    @Test
    public void testCastSpell() {
        initTriplets();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, relic);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Island");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
        assertPermanentCount(playerA, triplets, 1);
        assertPermanentCount(playerA, relic, 1);
        assertPermanentCount(playerA, "Island", 1);
        assertHandCount(playerB, bolt, 0);
        assertHandCount(playerB, relic, 0);
        assertGraveyardCount(playerB, 1);
        assertLife(playerB, 20 - 3);
    }

    @Test
    public void testCantActivate() {
        initTriplets();

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped("Taiga", false);
    }

    @Test
    public void testCantCast() {
        initTriplets();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, bolt, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerB, bolt, 1);
        assertLife(playerA, 20);
    }
}
