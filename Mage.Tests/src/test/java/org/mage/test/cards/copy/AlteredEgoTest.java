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

    /**
     * If the chosen creature has {X} in its mana cost, that X is considered to be 0.
     * The value of X in Altered Ego's last ability will be whatever value was chosen for X while casting Altered Ego.
     * (2016-04-08)
     */
    @Test
    public void copyXCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Tropical Island", 7);
        addCard(Zone.HAND, playerA, "Endless One"); // {X}, ETB with X +1/+1 counters, 0/0
        addCard(Zone.HAND, playerB, "Altered Ego"); // {X}{2}{G}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Endless One");
        setChoice(playerA, "X=2");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Altered Ego");
        setChoice(playerB, "X=3");
        setChoice(playerB, true); // use copy
        setChoice(playerB, "Endless One"); // copy target
        setChoice(playerB, ""); // Order place counters effects

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Endless One", 1);
        assertPowerToughness(playerA, "Endless One", 2, 2);
        assertPermanentCount(playerB, "Endless One", 1);
        assertPowerToughness(playerB, "Endless One", 3, 3); //The X should not be copied
    }
}
