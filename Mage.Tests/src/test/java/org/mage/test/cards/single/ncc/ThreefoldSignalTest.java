package org.mage.test.cards.single.ncc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.t.ThreefoldSignal Threefold Signal}
 * <p>
 * Each spell you cast that’s exactly three colors has replicate {3}.
 * @author Alex-Vasile
 */
public class ThreefoldSignalTest extends CardTestPlayerBase {

    private static final String threefoldSignal = "Threefold Signal";
    // R
    private static final String lightningBolt = "Lightning Bolt";
    // WUBRG
    private static final String atogatog = "Atogatog";
    // WUB
    private static final String esperSojourners = "Esper Sojourners";

    /**
     * Check that it works for three-colored spells
     */
    @Test
    public void testShouldWork() {
        addCard(Zone.BATTLEFIELD, playerA, threefoldSignal);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.HAND, playerA, esperSojourners);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, esperSojourners);
        setChoice(playerA, "Yes");
        setChoice(playerA, "No");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
        assertPermanentCount(playerA, esperSojourners, 2);
    }

    /**
     * Check that it does not trigger for spells with less than three colors.
     */
    @Test
    public void testShouldNotWork1Color() {
        addCard(Zone.BATTLEFIELD, playerA, threefoldSignal);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, lightningBolt);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lightningBolt, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
        assertLife(playerB, 17);
    }

    /**
     * Check that it does not trigger for spells with more than three colors.
     */
    @Test
    public void testShouldNotWork5Color() {
        addCard(Zone.BATTLEFIELD, playerA, threefoldSignal);
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, atogatog);
        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, atogatog);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
        assertPermanentCount(playerA, atogatog, 1);
    }

    /**
     * Check that it does not trigger for spells opponents control.
     */
    @Test
    public void testShouldNotWorkOpponent() {
        addCard(Zone.BATTLEFIELD, playerA, threefoldSignal);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        addCard(Zone.HAND, playerB, esperSojourners);

        setStrictChooseMode(true);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, esperSojourners);

        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
        assertPermanentCount(playerB, esperSojourners, 1);
    }
}
