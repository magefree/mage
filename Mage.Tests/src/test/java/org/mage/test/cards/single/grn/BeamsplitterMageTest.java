package org.mage.test.cards.single.grn;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class BeamsplitterMageTest extends CardTestPlayerBase {
    private static final String bsm = "Beamsplitter Mage";
    private static final String lion = "Silvercoat Lion";
    private static final String duelist = "Deft Duelist";
    private static final String bolt = "Lightning Bolt";
    private static final String seeds = "Seeds of Strength";

    @Test
    public void testLightningBolt() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerA, duelist);
        addCard(Zone.BATTLEFIELD, playerA, bsm);
        addCard(Zone.HAND, playerA, bolt);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, bsm);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, bsm, 0);
        assertPermanentCount(playerA, lion, 0);
        assertPermanentCount(playerA, duelist, 1);
        assertGraveyardCount(playerA, bsm, 1);
        assertGraveyardCount(playerA, lion, 1);
        assertGraveyardCount(playerA, duelist, 0);
        assertGraveyardCount(playerA, bolt, 1);
    }

    @Test
    public void testSeedsOfStrength() {
        addCard(Zone.BATTLEFIELD, playerA, "Savannah", 2);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerA, duelist);
        addCard(Zone.BATTLEFIELD, playerA, bsm);
        addCard(Zone.HAND, playerA, seeds);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, seeds, bsm);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, bsm, 5, 5);
        assertPowerToughness(playerA, lion, 5, 5);
    }
}
