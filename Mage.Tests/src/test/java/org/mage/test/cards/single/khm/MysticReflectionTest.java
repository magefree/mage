package org.mage.test.cards.single.khm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class MysticReflectionTest extends CardTestPlayerBase {

    @Test
    public void test_OnlyNextEnterBecomesCopies() {
        // Choose target nonlegendary creature. The next time one or more creatures or planeswalkers enter the
        // battlefield this turn, they enter as copies of the chosen creature instead.
        addCard(Zone.HAND, playerA, "Mystic Reflection"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        //
        // Create two 1/1 white Soldier creature tokens.
        addCard(Zone.HAND, playerA, "Raise the Alarm", 2); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mystic Reflection", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // first enter - copies
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raise the Alarm");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after first", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1 + 2);
        checkPermanentCount("after first", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Soldier Token", 0);

        // second enter - normal tokens
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raise the Alarm");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1 + 2);
        assertPermanentCount(playerA, "Soldier Token", 2);
    }
}
