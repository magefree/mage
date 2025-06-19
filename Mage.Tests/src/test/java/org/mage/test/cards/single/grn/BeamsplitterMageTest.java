package org.mage.test.cards.single.grn;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class BeamsplitterMageTest extends CardTestPlayerBase {
    /**
     * 2/2
     * <p>
     * Whenever you cast an instant or sorcery spell that targets only Beamsplitter Mage,
     * if you control one or more creatures that spell could target, choose one of those
     * creatures. Copy that spell. The copy targets the chosen creature.
     */
    private static final String bsm = "Beamsplitter Mage";

    /**
     * Target creature gets +1/+1 until end of turn.
     * Target creature gets +1/+1 until end of turn.
     * Target creature gets +1/+1 until end of turn.
     */
    private static final String seeds = "Seeds of Strength";

    private static final String lion = "Silvercoat Lion";
    private static final String duelist = "Deft Duelist";
    private static final String bolt = "Lightning Bolt";

    @Test
    public void testLightningBolt() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerA, duelist);
        addCard(Zone.BATTLEFIELD, playerA, bsm);
        addCard(Zone.HAND, playerA, bolt);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, bsm);
        setChoice(playerA, lion); // target for copied bolt

        setStrictChooseMode(true);
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

        // put x3 targets to bsm and copy it to lion
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, seeds);
        addTarget(playerA, bsm);
        addTarget(playerA, bsm);
        addTarget(playerA, bsm);
        setChoice(playerA, lion);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, bsm, 2 + 3, 2 + 3);
        assertPowerToughness(playerA, lion, 2 + 3, 2 + 3);
    }
}
