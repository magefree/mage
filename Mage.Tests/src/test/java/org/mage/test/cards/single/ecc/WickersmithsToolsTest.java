package org.mage.test.cards.single.ecc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author MaxFreedomPollard
 */
public class WickersmithsToolsTest extends CardTestPlayerBase {

    private static final String tools = "Wickersmith's Tools";

    /**
     * {5}, {T}, Sacrifice this artifact: Create X tapped 2/2 colorless Scarecrow artifact creature
     * tokens, where X is the number of charge counters on this artifact.
     */
    @Test
    public void testScarecrowTokensEnterTapped() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, tools);

        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, tools, CounterType.CHARGE, 3);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{5}, {T}, Sacrifice");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, tools, 1);
        assertPermanentCount(playerA, "Scarecrow Token", 3);
        assertTappedCount("Scarecrow Token", true, 3);
    }
}
