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
        // Put all creatures on the bottom of their owners' libraries.
        addCard(Zone.LIBRARY, playerA, "Terminus");
        // Draw a card.
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

    /**
     * Test that you can cast a card by miracle if you don't put it back to library before casting
     */
    @Test
    public void testMiracleWillWorkFromHand() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.LIBRARY, playerA, "Plains");
        addCard(Zone.LIBRARY, playerA, "Forest");
        addCard(Zone.LIBRARY, playerA, "Thunderous Wrath"); // must be the top most card
        addCard(Zone.HAND, playerA, "Brainstorm");
        skipInitShuffling();

        castSpell(1, PhaseStep.UPKEEP, playerA, "Brainstorm");
        addTarget(playerA, "Forest");
        addTarget(playerA, "Plains");
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.DRAW);
        execute();

        assertGraveyardCount(playerA, "Brainstorm", 1);
        assertHandCount(playerA, "Thunderous Wrath", 0);
        assertGraveyardCount(playerA, "Thunderous Wrath", 1);
        assertHandCount(playerA, 0);
        // check Thunderous Wrath was played
        assertLife(playerA, 20);
        assertLife(playerB, 15);

    }
}
