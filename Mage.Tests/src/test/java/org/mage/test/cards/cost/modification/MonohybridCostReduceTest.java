package org.mage.test.cards.cost.modification;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class MonohybridCostReduceTest extends CardTestPlayerBase {

    @Test
    public void test_CostReduction_First() {
        // monohybrid supports with some limitation -- it reduce first hybrid cost, see https://github.com/magefree/mage/issues/6130

        // Artifact spells you cast cost {1} less to cast.
        addCard(Zone.BATTLEFIELD, playerA, "Etherium Sculptor");
        //
        // Reaper King
        addCard(Zone.HAND, playerA, "Reaper King"); // {2/W}{2/U}{2/B}{2/R}{2/G}
        // Add {C}
        addCard(Zone.BATTLEFIELD, playerA, "Blinkmoth Nexus", 2 * 5 - 1); // one less to test cost reduction

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reaper King");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }
}
