package org.mage.test.cards.targets;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author JayDi85
 */

public class AtraxaGrandUnifierTest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_Play_Manual() {
        // bug example: can select any amount instead per type only
        // https://github.com/magefree/mage/issues/13964
        skipInitShuffling();
        // x2 types
        addCard(Zone.LIBRARY, playerA, "Mountain", 2);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 1);
        addCard(Zone.LIBRARY, playerB, "Lightning Bolt", 1);

        // When Atraxa, Grand Unifier enters the battlefield, reveal the top ten cards of your library.
        // For each card type, you may put a card of that type from among the revealed cards into your hand.
        // Put the rest on the bottom of your library in a random order.
        addCard(Zone.HAND, playerA, "Atraxa, Grand Unifier", 1); // {3}{G}{W}{U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Atraxa, Grand Unifier");
        setChoice(playerA, "Mountain", 1);
        //setChoice(playerA, "Mountain", 1); // enable to raise error, cause can't choose x2 land type
        setChoice(playerA, "Grizzly Bears", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Mountain", 1);
        assertHandCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void test_Play_AI() {
        // bug example: can select any amount instead per type only
        // https://github.com/magefree/mage/issues/13964
        skipInitShuffling();
        // x2 types
        addCard(Zone.LIBRARY, playerA, "Mountain", 2);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 1);
        addCard(Zone.LIBRARY, playerB, "Lightning Bolt", 1);

        // When Atraxa, Grand Unifier enters the battlefield, reveal the top ten cards of your library.
        // For each card type, you may put a card of that type from among the revealed cards into your hand.
        // Put the rest on the bottom of your library in a random order.
        addCard(Zone.HAND, playerA, "Atraxa, Grand Unifier", 1); // {3}{G}{W}{U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        //
        addCard(Zone.HAND, playerA, "Swamp", 1);

        // ai must play and choose x2 cards (land + creature)
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Swamp"); // make sure no play land after cast
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Mountain", 1);
        assertHandCount(playerA, "Grizzly Bears", 1);
    }
}