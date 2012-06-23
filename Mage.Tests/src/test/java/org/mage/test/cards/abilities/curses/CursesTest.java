package org.mage.test.cards.abilities.curses;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class CursesTest extends CardTestPlayerBase {

    @Test
    public void testCurseOfBloodletting() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 7);
        addCard(Constants.Zone.HAND, playerA, "Curse of Bloodletting");
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt", 2);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Bloodletting", playerB);
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerA);


        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 17);
        assertLife(playerB, 14);
    }

    @Test
    public void testCurseOfEchoes() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Island", 5);
        addCard(Constants.Zone.HAND, playerA, "Curse of Echoes");
        addCard(Constants.Zone.HAND, playerB, "Jace's Ingenuity");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Echoes", playerB);
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerB, "Jace's Ingenuity");        

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 3);
        assertHandCount(playerB, 3);
    }

    @Test
    public void testCurseOfExhaustion1() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Constants.Zone.HAND, playerA, "Curse of Exhaustion");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Mountain", 2);
        addCard(Constants.Zone.HAND, playerB, "Lightning Bolt", 2);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Exhaustion", playerB);
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", playerA);
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", playerA);


        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);
        assertLife(playerA, 17);
    }

    @Test
    public void testCurseOfExhaustion2() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Constants.Zone.HAND, playerA, "Curse of Exhaustion");
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt", 2);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Exhaustion", playerB);
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);


        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 14);
    }

    @Test
    public void testCurseOfThirst1() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Constants.Zone.HAND, playerA, "Curse of Thirst");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Thirst", playerB);        

        setStopAt(2, Constants.PhaseStep.DRAW);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 19);
    }

    @Test
    public void testCurseOfThirst2() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Constants.Zone.HAND, playerA, "Curse of Thirst");
        addCard(Constants.Zone.HAND, playerA, "Curse of Bloodletting");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Bloodletting", playerB);        
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Thirst", playerB);        

        setStopAt(2, Constants.PhaseStep.DRAW);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 16);
    }

    @Test
    public void testCurseOfMisfortune1() {
        removeAllCardsFromLibrary(playerA);
        addCard(Constants.Zone.LIBRARY, playerA, "Curse of Misfortunes", 2);
        addCard(Constants.Zone.HAND, playerA, "Curse of Misfortunes");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp", 5);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Misfortunes", playerB);        

        setStopAt(3, Constants.PhaseStep.DRAW);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Curse of Misfortunes", 1);
    }

    @Test
    public void testCurseOfMisfortune2() {
        removeAllCardsFromLibrary(playerA);
        addCard(Constants.Zone.LIBRARY, playerA, "Curse of Bloodletting", 2);
        addCard(Constants.Zone.HAND, playerA, "Curse of Misfortunes");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp", 5);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Misfortunes", playerB);        

        setStopAt(3, Constants.PhaseStep.DRAW);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Curse of Misfortunes", 1);
        assertPermanentCount(playerA, "Curse of Bloodletting", 1);    }

}
