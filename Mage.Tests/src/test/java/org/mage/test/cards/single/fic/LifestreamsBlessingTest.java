package org.mage.test.cards.single.fic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class LifestreamsBlessingTest extends CardTestPlayerBase {

    private static final String blessing = "Lifestream's Blessing";
    private static final String jackal = "Trained Jackal";
    private static final String bear = "Ashcoat Bear";
    private static final String giant = "Hill Giant";

    @Test
    public void testRegular() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.BATTLEFIELD, playerA, jackal);
        addCard(Zone.BATTLEFIELD, playerB, giant);
        addCard(Zone.HAND, playerA, blessing);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, blessing);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 1);
        assertLife(playerA, 20);
    }

    @Test
    public void testFlashIn22() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6 + 2);
        addCard(Zone.BATTLEFIELD, playerA, jackal);
        addCard(Zone.BATTLEFIELD, playerB, giant);
        addCard(Zone.HAND, playerA, blessing);
        addCard(Zone.HAND, playerA, bear);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, blessing);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 1);
        assertLife(playerA, 20);
    }

    private static final String twincast = "Twincast";

    @Test
    public void testTwincast() {
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 6 + 2);
        addCard(Zone.BATTLEFIELD, playerA, jackal);
        addCard(Zone.BATTLEFIELD, playerB, giant);
        addCard(Zone.HAND, playerA, blessing);
        addCard(Zone.HAND, playerA, twincast);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, blessing);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, twincast, blessing);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 1);
        assertLife(playerA, 20);
    }

    @Test
    public void testForetell() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, jackal);
        addCard(Zone.BATTLEFIELD, playerB, giant);
        addCard(Zone.HAND, playerA, blessing);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "For");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "For");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 1 + 1);
        assertLife(playerA, 20 + 2);
    }
}
