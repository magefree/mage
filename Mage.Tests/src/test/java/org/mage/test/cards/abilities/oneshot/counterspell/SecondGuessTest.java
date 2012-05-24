package org.mage.test.cards.abilities.oneshot.counterspell;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class SecondGuessTest extends CardTestPlayerBase {

    @Test
    public void testCounterFirstSpell() {
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Constants.Zone.HAND, playerA, "Second Guess");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Second Guess", "Lightning Bolt");

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 17);

        assertHandCount(playerA, 1);
    }

    @Test
    public void testCounterSecondSpell() {
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Constants.Zone.HAND, playerA, "Shock", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Constants.Zone.HAND, playerA, "Second Guess");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", playerB);
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Second Guess", "Lightning Bolt");

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 17);

        assertHandCount(playerA, 0);
        assertGraveyardCount(playerA, 3);
    }

    @Test
    public void testCounterThirdSpell() {
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt", 2);
        addCard(Constants.Zone.HAND, playerA, "Shock", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Constants.Zone.HAND, playerA, "Second Guess");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", playerB);
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Second Guess", "Shock");

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 12);

        assertHandCount(playerA, 0);
        assertGraveyardCount(playerA, 4);
    }

    /**
     * Tests that spell are counted for all players
     */
    @Test
    public void testOverallCount() {
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Constants.Zone.HAND, playerA, "Second Guess");

        addCard(Constants.Zone.HAND, playerB, "Shock");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Mountain");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerB, "Shock", playerA);
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Second Guess", "Shock");

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 17);

        assertHandCount(playerA, 0);
        assertGraveyardCount(playerA, 2);
        assertHandCount(playerB, 0);
        assertGraveyardCount(playerB, 1);
    }
}
