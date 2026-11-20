package org.mage.test.cards.single.shm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class GhastlordOfFugueTest extends CardTestPlayerBase {

    @Test
    public void test_ExileChosenCard() {
        // Ghastlord of Fugue can't be blocked.
        // Whenever Ghastlord of Fugue deals combat damage to a player, that player reveals their hand.
        // You choose a card from it. That player exiles that card.
        addCard(Zone.BATTLEFIELD, playerA, "Ghastlord of Fugue"); // 4/4
        //
        addCard(Zone.HAND, playerB, "Grizzly Bears");
        addCard(Zone.HAND, playerB, "Plains");

        attack(1, playerA, "Ghastlord of Fugue");
        setChoice(playerA, "Plains"); // any card, lands too

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 4);
        assertExileCount(playerB, "Plains", 1);
        assertHandCount(playerB, "Grizzly Bears", 1);
        assertHandCount(playerB, 1);
    }

    @Test
    public void test_EmptyHand_NothingToChoose() {
        // Ghastlord of Fugue can't be blocked.
        // Whenever Ghastlord of Fugue deals combat damage to a player, that player reveals their hand.
        // You choose a card from it. That player exiles that card.
        addCard(Zone.BATTLEFIELD, playerA, "Ghastlord of Fugue"); // 4/4

        attack(1, playerA, "Ghastlord of Fugue");
        // no choice: empty hand

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 4);
        assertExileCount(playerB, 0);
    }
}
