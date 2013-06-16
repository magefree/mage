package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class MiracleTest extends CardTestPlayerBase {

    /**
     * Tests miracle alternative cost
     */
    @Test
    public void testMiracleCost() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.LIBRARY, playerA, "Terminus");
        addCard(Zone.HAND, playerA, "Think Twice");
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // check Terminus was played
        assertPermanentCount(playerB, "Elite Vanguard", 0);
    }

    /**
     * Tests working on extra turn
     */
    @Test
    public void testMiracleOnExtraTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.LIBRARY, playerA, "Terminus");
        addCard(Zone.LIBRARY, playerA, "Temporal Mastery");
        addCard(Zone.HAND, playerA, "Think Twice");
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        // check Terminus was played
        assertPermanentCount(playerB, "Elite Vanguard", 0);
    }

}
