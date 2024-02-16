package org.mage.test.cards.single.emn;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Quercitron
 */
public class EmrakulThePromisedEndTest extends CardTestPlayerBase {

    // Test that extra turn is added correctly when Emrakul is cast during an opponent's turn.
    @Test
    public void test_ExtraTurn_Turn2() {
        addCard(Zone.BATTLEFIELD, playerB, "Island", 20);
        // Creature cards you own that aren't on the battlefield have flash.
        addCard(Zone.HAND, playerB, "Teferi, Mage of Zhalfir");
        // When you cast Emrakul, you gain control of target opponent during that player's next turn.
        // After that turn, that player takes an extra turn.
        addCard(Zone.HAND, playerB, "Emrakul, the Promised End");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Teferi, Mage of Zhalfir",  true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Emrakul, the Promised End");
        addTarget(playerB, playerA);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertActivePlayer(playerB);
    }

    @Test
    public void test_ExtraTurn_Turn3() {
        addCard(Zone.BATTLEFIELD, playerB, "Island", 20);
        addCard(Zone.HAND, playerB, "Teferi, Mage of Zhalfir");
        addCard(Zone.HAND, playerB, "Emrakul, the Promised End");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Teferi, Mage of Zhalfir", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Emrakul, the Promised End");
        addTarget(playerB, playerA);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertActivePlayer(playerA);
    }

    @Test
    public void test_ExtraTurn_Turn4() {
        addCard(Zone.BATTLEFIELD, playerB, "Island", 20);
        addCard(Zone.HAND, playerB, "Teferi, Mage of Zhalfir");
        addCard(Zone.HAND, playerB, "Emrakul, the Promised End");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Teferi, Mage of Zhalfir", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Emrakul, the Promised End");
        addTarget(playerB, playerA);

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertActivePlayer(playerA);
    }

    @Test
    public void test_CostReduction_FromHand() {
        // {13}
        // This spell costs {1} less to cast for each card type among cards in your graveyard.
        // When you cast this spell, you gain control of target opponent during that player’s next turn. After that turn, that player takes an extra turn.
        addCard(Zone.HAND, playerA, "Emrakul, the Promised End");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 13 - 2);
        addCard(Zone.GRAVEYARD, playerA, "Balduvian Bears", 1);
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt", 1);

        checkPlayableAbility("can play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Emrakul, the Promised End", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Emrakul, the Promised End");
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Emrakul, the Promised End", 1);
    }

    @Test
    public void test_CostReduction_FromLibrary() {
        removeAllCardsFromLibrary(playerA);

        // {13}
        // This spell costs {1} less to cast for each card type among cards in your graveyard.
        // When you cast this spell, you gain control of target opponent during that player’s next turn. After that turn, that player takes an extra turn.
        addCard(Zone.LIBRARY, playerA, "Emrakul, the Promised End");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 13 - 2);
        addCard(Zone.GRAVEYARD, playerA, "Balduvian Bears", 1);
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt", 1);
        //
        // You may cast creature spells from the top of your library.
        addCard(Zone.BATTLEFIELD, playerA, "Vivien, Monsters' Advocate", 1);

        checkPlayableAbility("can play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Emrakul, the Promised End", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Emrakul, the Promised End");
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Emrakul, the Promised End", 1);
    }
}
