package org.mage.test.cards.single.nem;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class DeathPitOfferingTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.d.DeathPitOffering Death Pit Offering} {2}{B}{B}
     * Enchantment
     * When Death Pit Offering enters the battlefield, sacrifice all creatures you control.
     * Creatures you control get +2/+2.
     */
    private static final String offering = "Death Pit Offering";

    @Test
    public void test_Trigger() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, offering);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        // Will be sacrificed:
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2);

        // Will not be sacrificed:
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, offering);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, 4 + 1);
        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertGraveyardCount(playerA, "Grizzly Bears", 2);
        assertPermanentCount(playerB, "Grizzly Bears", 2);
    }
}
