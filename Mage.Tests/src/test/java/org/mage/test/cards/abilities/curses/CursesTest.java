package org.mage.test.cards.abilities.curses;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class CursesTest extends CardTestPlayerBase {

    @Test
    public void testCurseOfBloodletting() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);
        addCard(Zone.HAND, playerA, "Curse of Bloodletting");
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Bloodletting", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerA);


        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 17);
        assertLife(playerB, 14);
    }

    @Test
    public void testCurseOfEchoes() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 5);
        addCard(Zone.HAND, playerA, "Curse of Echoes");
        addCard(Zone.HAND, playerB, "Jace's Ingenuity");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Echoes", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Jace's Ingenuity");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 3);
        assertHandCount(playerB, 3);
    }

    @Test
    public void testCurseOfExhaustion1() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, "Curse of Exhaustion");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Exhaustion", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", playerA);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", playerA);


        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);
        assertLife(playerA, 17);
    }

    @Test
    public void testCurseOfExhaustion2() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Curse of Exhaustion");
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Exhaustion", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);


        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 14);
    }

    @Test
    public void testCurseOfThirst1() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, "Curse of Thirst");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Thirst", playerB);

        setStopAt(2, PhaseStep.DRAW);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 19);
    }

    @Test
    public void testCurseOfThirst2() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Curse of Thirst");
        addCard(Zone.HAND, playerA, "Curse of Bloodletting");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Bloodletting", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Thirst", playerB);

        setStopAt(2, PhaseStep.DRAW);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 16);
    }

    @Test
    public void testCurseOfMisfortune1() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Curse of Misfortunes", 2);
        addCard(Zone.HAND, playerA, "Curse of Misfortunes");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Misfortunes", playerB);

        setStopAt(3, PhaseStep.DRAW);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Curse of Misfortunes", 1);
    }

    @Test
    public void testCurseOfMisfortune2() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Curse of Bloodletting", 2);
        addCard(Zone.HAND, playerA, "Curse of Misfortunes");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Misfortunes", playerB);

        setStopAt(3, PhaseStep.DRAW);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Curse of Misfortunes", 1);
        assertPermanentCount(playerA, "Curse of Bloodletting", 1);    }

}
