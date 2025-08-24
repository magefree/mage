package org.mage.test.cards.single.eoe;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class DepressurizeTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.d.Depressurize Depressurize} {1}{B}
     * Instant
     * Target creature gets -3/-0 until end of turn. Then if that creature’s power is 0 or less, destroy it.
     */
    private static final String depressurize = "Depressurize";

    @Test
    public void test_Destroy() {
        addCard(Zone.BATTLEFIELD, playerA, "Centaur Courser");
        addCard(Zone.HAND, playerA, depressurize, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, depressurize, "Centaur Courser");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, "Centaur Courser", 1);
    }

    @Test
    public void test_NoDestroy() {
        addCard(Zone.BATTLEFIELD, playerA, "Alpine Grizzly");
        addCard(Zone.HAND, playerA, depressurize, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, depressurize, "Alpine Grizzly");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Alpine Grizzly", 1);
        assertPowerToughness(playerA, "Alpine Grizzly", 4 - 3, 2);
    }
}
