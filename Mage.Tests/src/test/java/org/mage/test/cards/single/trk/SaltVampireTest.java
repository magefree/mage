package org.mage.test.cards.single.trk;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class SaltVampireTest extends CardTestPlayerBase {

    @Test
    public void test_ExileChosenNonlandCard_ThenDraw() {
        // When this creature enters, look at target opponent's hand. You may choose a nonland card from it.
        // If you do, that player exiles that card, then draws a card.
        addCard(Zone.HAND, playerA, "Salt Vampire"); // {1}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        addCard(Zone.HAND, playerB, "Grizzly Bears");
        addCard(Zone.HAND, playerB, "Plains");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Salt Vampire");
        addTarget(playerA, playerB); // target opponent
        setChoice(playerA, "Grizzly Bears"); // nonland card to exile

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerB, "Grizzly Bears", 1);
        assertHandCount(playerB, "Plains", 1);
        assertHandCount(playerB, 1 + 1); // land + drawn card
        assertHandCount(playerA, 0);
    }

    @Test
    public void test_MayChooseNothing_NoDraw() {
        // When this creature enters, look at target opponent's hand. You may choose a nonland card from it.
        // If you do, that player exiles that card, then draws a card.
        addCard(Zone.HAND, playerA, "Salt Vampire"); // {1}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        addCard(Zone.HAND, playerB, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Salt Vampire");
        addTarget(playerA, playerB); // target opponent
        setChoice(playerA, TestPlayer.CHOICE_SKIP); // choose nothing

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerB, 0);
        assertHandCount(playerB, 1);
    }

    @Test
    public void test_OnlyLandsInHand_NothingToChoose() {
        // When this creature enters, look at target opponent's hand. You may choose a nonland card from it.
        // If you do, that player exiles that card, then draws a card.
        addCard(Zone.HAND, playerA, "Salt Vampire"); // {1}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        addCard(Zone.HAND, playerB, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Salt Vampire");
        addTarget(playerA, playerB); // target opponent
        // no choice: lands can't be chosen

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerB, 0);
        assertHandCount(playerB, 2);
    }

    @Test
    public void test_EmptyHand_NothingToChoose() {
        // When this creature enters, look at target opponent's hand. You may choose a nonland card from it.
        // If you do, that player exiles that card, then draws a card.
        addCard(Zone.HAND, playerA, "Salt Vampire"); // {1}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Salt Vampire");
        addTarget(playerA, playerB); // target opponent
        // no choice: empty hand

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Salt Vampire", 1);
        assertExileCount(playerB, 0);
        assertHandCount(playerB, 0);
    }
}
