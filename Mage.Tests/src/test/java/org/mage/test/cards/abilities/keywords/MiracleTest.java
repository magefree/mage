package org.mage.test.cards.abilities.keywords;

import mage.Constants;
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
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Constants.Zone.LIBRARY, playerA, "Terminus");
        addCard(Constants.Zone.HAND, playerA, "Think Twice");
        skipInitShuffling();

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Elite Vanguard", 1);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        // check Terminus was played
        assertPermanentCount(playerB, "Elite Vanguard", 0);
    }

    /**
     * Tests working on extra turn
     */
    @Test
    public void testMiracleOnExtraTurn() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Constants.Zone.LIBRARY, playerA, "Terminus");
        addCard(Constants.Zone.LIBRARY, playerA, "Temporal Mastery");
        addCard(Constants.Zone.HAND, playerA, "Think Twice");
        skipInitShuffling();

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Elite Vanguard", 1);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");

        setStopAt(2, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        // check Terminus was played
        assertPermanentCount(playerB, "Elite Vanguard", 0);
    }

}
