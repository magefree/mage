package org.mage.test.AI.basic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author JayDi85
 */
public class GainControlAITest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_GainControl_Manual() {
        // You control enchanted land.
        addCard(Zone.HAND, playerA, "Annex", 1); // {2}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);

        // take control
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Annex", "Swamp");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Island", 4);
        assertPermanentCount(playerA, "Swamp", 1);
        assertPermanentCount(playerB, "Swamp", 0);
    }

    @Test
    public void test_GainControl_AI_Single() {
        // You control enchanted land.
        addCard(Zone.HAND, playerA, "Annex", 1); // {2}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);

        // take control by AI
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Island", 4);
        assertPermanentCount(playerA, "Swamp", 1);
        assertPermanentCount(playerB, "Swamp", 0);
    }

    @Test
    public void test_GainControl_AI_MostValuable() {
        // You control enchanted land.
        addCard(Zone.HAND, playerA, "Annex", 1); // {2}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Badlands", 1);

        // take control by AI (selects most valueable enemy card)
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Island", 4);
        assertPermanentCount(playerA, "Swamp", 0);
        assertPermanentCount(playerA, "Badlands", 1);
        assertPermanentCount(playerB, "Swamp", 1);
        assertPermanentCount(playerB, "Badlands", 0);
    }
}
