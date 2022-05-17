package org.mage.test.cards.abilities.oneshot.counterspell;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class SecondGuessTest extends CardTestPlayerBase {

    /**
     * Counter target spell that's the second spell cast this turn.
     */
    @Test
    public void testCounterFirstSpell() {
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Second Guess");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        checkPlayableAbility("can't counter lightning bolt", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Second", false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 17);

        assertHandCount(playerA, 1);
    }

    @Test
    public void testCounterSecondSpell() {
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.HAND, playerA, "Shock", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        
        addCard(Zone.HAND, playerA, "Second Guess");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Second Guess", "Shock");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 17);

        assertHandCount(playerA, 0);
        assertGraveyardCount(playerA, 3);
    }

    @Test
    public void testCounterThirdSpell() {
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);
        addCard(Zone.HAND, playerA, "Shock", 1);
            addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Second Guess");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", playerB);
        checkPlayableAbility("can't counter lightning bolt", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Second", false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 12);

        assertHandCount(playerA, 1);
        assertGraveyardCount(playerA, 3);
    }

    /**
     * Tests that spell are counted for all players
     */
    @Test
    public void testOverallCount() {
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Second Guess");

        addCard(Zone.HAND, playerB, "Shock");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Shock", playerA);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Second Guess", "Shock");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 17);

        assertHandCount(playerA, 0);
        assertGraveyardCount(playerA, 2);
        assertHandCount(playerB, 0);
        assertGraveyardCount(playerB, 1);
    }
}
