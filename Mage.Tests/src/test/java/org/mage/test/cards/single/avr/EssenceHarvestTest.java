package org.mage.test.cards.single.avr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class EssenceHarvestTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.e.EssenceHarvest Essence Harvest} {2}{B}
     * Sorcery
     * Target player loses X life and you gain X life, where X is the greatest power among creatures you control.
     */
    private static final String harvest = "Essence Harvest";

    @Test
    public void test_no_creature() {
        addCard(Zone.HAND, playerA, harvest, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Merfolk of the Pearl Trident", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, harvest, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    @Test
    public void test_4_power() {
        addCard(Zone.HAND, playerA, harvest, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Borderland Minotaur", 1); // 4/3

        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Merfolk of the Pearl Trident", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, harvest, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 4);
        assertLife(playerB, 20 - 4);
    }
}
