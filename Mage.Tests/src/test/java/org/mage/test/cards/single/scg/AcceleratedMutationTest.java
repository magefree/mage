package org.mage.test.cards.single.scg;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class AcceleratedMutationTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.a.AcceleratedMutation Accelerated Mutation} {3}{G}{G}
     * Instant
     * Target creature gets +X/+X until end of turn, where X is the greatest mana value among permanents you control.
     */
    private static final String mutation = "Accelerated Mutation";

    @Test
    public void test_simple() {
        addCard(Zone.HAND, playerA, mutation);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Centaur Courser"); // {2}{G} 3/3
        addCard(Zone.BATTLEFIELD, playerA, "Ingenuity Engine"); // {7} Artifact

        addCard(Zone.BATTLEFIELD, playerB, "Arboretum Elemental"); // {7}{G}{G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mutation, "Centaur Courser");
        checkPT("10/10 Courser", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Centaur Courser", 10, 10);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // check effect stop end of turn
        assertPowerToughness(playerA, "Centaur Courser", 3, 3);
    }
}
