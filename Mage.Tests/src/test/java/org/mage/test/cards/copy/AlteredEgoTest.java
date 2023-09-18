package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class AlteredEgoTest extends CardTestPlayerBase {

    @Test
    public void testAddingTheCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        // Altered Ego can't be countered.
        // You may have Altered Ego enter the battlefield as a copy of any creature on the battlefield, except it enters with an additional X +1/+1 counters on it.
        addCard(Zone.HAND, playerA, "Altered Ego"); // {X}{2}{G}{U}

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Altered Ego");
        setChoice(playerA, "X=3");
        setChoice(playerA, true); // use copy
        setChoice(playerA, "Silvercoat Lion"); // copy target

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 5, 5);
    }

    @Test
    public void testNoCreatureToCopyAvailable() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        // Altered Ego can't be countered.
        // You may have Altered Ego enter the battlefield as a copy of any creature on the battlefield, except it enters with an additional X +1/+1 counters on it.
        addCard(Zone.HAND, playerA, "Altered Ego"); // {X}{2}{G}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Altered Ego");
        setChoice(playerA, "X=3");
        setChoice(playerA, true); // use copy (but no targets for copy)

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Altered Ego", 0);
        assertGraveyardCount(playerA, "Altered Ego", 1);

    }
}
