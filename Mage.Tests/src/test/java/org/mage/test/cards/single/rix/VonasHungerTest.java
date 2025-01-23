package org.mage.test.cards.single.rix;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author JayDi85
 */
public class VonasHungerTest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_NoAscend_Normal() {
        // Ascend (If you control ten or more permanents, you get the city's blessing for the rest of the game.)
        // Each opponent sacrifices a creature.
        // If you have the city's blessing, instead each opponent sacrifices half the creatures they control rounded up.
        addCard(Zone.HAND, playerA, "Vona's Hunger"); // {2}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 5);

        // opponent must sacrifice 1
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vona's Hunger");
        setChoice(playerB, "Grizzly Bears"); // to sacrifice

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, 5 - 1);
    }

    @Test
    public void test_NoAscend_AI() {
        // Ascend (If you control ten or more permanents, you get the city's blessing for the rest of the game.)
        // Each opponent sacrifices a creature.
        // If you have the city's blessing, instead each opponent sacrifices half the creatures they control rounded up.
        addCard(Zone.HAND, playerA, "Vona's Hunger"); // {2}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 5);

        // AI must sacrifice 1
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vona's Hunger");
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, 5 - 1);
    }

    @Test
    public void test_Ascend_Normal() {
        // Ascend (If you control ten or more permanents, you get the city's blessing for the rest of the game.)
        // Each opponent sacrifices a creature.
        // If you have the city's blessing, instead each opponent sacrifices half the creatures they control rounded up.
        addCard(Zone.HAND, playerA, "Vona's Hunger"); // {2}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 5);

        // opponent must sacrifice 3
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vona's Hunger");
        setChoice(playerB, "Grizzly Bears^Grizzly Bears^Grizzly Bears"); // to sacrifice

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, 5 - 3);
    }

    @Test
    public void test_Ascend_AI() {
        // Ascend (If you control ten or more permanents, you get the city's blessing for the rest of the game.)
        // Each opponent sacrifices a creature.
        // If you have the city's blessing, instead each opponent sacrifices half the creatures they control rounded up.
        addCard(Zone.HAND, playerA, "Vona's Hunger"); // {2}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 5);

        // AI must sacrifice 3
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vona's Hunger");
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, 5 - 3);
    }
}
