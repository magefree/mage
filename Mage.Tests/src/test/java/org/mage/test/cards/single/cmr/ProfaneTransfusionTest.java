package org.mage.test.cards.single.cmr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class ProfaneTransfusionTest extends CardTestPlayerBase {

    private static final String transfusion = "Profane Transfusion";
    private static final String emperion = "Platinum Emperion";
    private static final String reflection = "Boon Reflection";
    private static final String skullcrack = "Skullcrack";

    @Test
    public void testRegular() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 9);
        addCard(Zone.HAND, playerA, transfusion);

        setLife(playerA, 24);
        setLife(playerB, 16);

        addTarget(playerA, playerA);
        addTarget(playerA, playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, transfusion);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 16);
        assertLife(playerB, 24);
        assertPermanentCount(playerA, "Phyrexian Horror Token", 1);
        assertPowerToughness(playerA, "Phyrexian Horror Token", 24 - 16, 24 - 16);
        assertGraveyardCount(playerA, transfusion, 1);
    }

    @Test
    public void testCantChange() {
        // Platinum Emperion stops life totals from changing but token is still created
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 9);
        addCard(Zone.BATTLEFIELD, playerA, emperion);
        addCard(Zone.HAND, playerA, transfusion);

        setLife(playerA, 24);
        setLife(playerB, 16);

        addTarget(playerA, playerA);
        addTarget(playerA, playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, transfusion);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 24);
        assertLife(playerB, 16);
        assertPermanentCount(playerA, "Phyrexian Horror Token", 1);
        assertPowerToughness(playerA, "Phyrexian Horror Token", 24 - 16, 24 - 16);
        assertGraveyardCount(playerA, transfusion, 1);
    }

    @Test
    public void testDoubleLife() {
        // Boon Reflection doubles life gain, which affects final difference
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 9);
        addCard(Zone.BATTLEFIELD, playerB, reflection);
        addCard(Zone.HAND, playerA, transfusion);

        setLife(playerA, 24);
        setLife(playerB, 16);

        addTarget(playerA, playerA);
        addTarget(playerA, playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, transfusion);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 16);
        assertLife(playerB, 32);
        assertPermanentCount(playerA, "Phyrexian Horror Token", 1);
        assertPowerToughness(playerA, "Phyrexian Horror Token", 32 - 16, 32 - 16);
        assertGraveyardCount(playerA, transfusion, 1);
    }

    @Test
    public void testCantGainLife() {
        // Skullcrack prevents life gain, but final difference should still be 3
        addCard(Zone.BATTLEFIELD, playerA, "Badlands", 11);
        addCard(Zone.HAND, playerA, skullcrack);
        addCard(Zone.HAND, playerA, transfusion);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, skullcrack, playerB);
        addTarget(playerA, playerA);
        addTarget(playerA, playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, transfusion);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 17);
        assertPermanentCount(playerA, "Phyrexian Horror Token", 1);
        assertPowerToughness(playerA, "Phyrexian Horror Token", 20 - 17, 20 - 17);
        assertGraveyardCount(playerA, transfusion, 1);
    }
}
