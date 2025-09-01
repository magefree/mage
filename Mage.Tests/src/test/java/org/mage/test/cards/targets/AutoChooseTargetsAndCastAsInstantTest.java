package org.mage.test.cards.targets;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2, JayDi85
 */
public class AutoChooseTargetsAndCastAsInstantTest extends CardTestPlayerBase {

    private void run_WithAutoSelection(int selectedTargets, int possibleTargets) {
        addCard(Zone.BATTLEFIELD, playerB, "Island");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);
        // The next sorcery card you cast this turn can be cast as though it had flash.
        // Draw a card.
        addCard(Zone.HAND, playerB, "Quicken"); // {U}
        // Devoid (This card has no color.)
        // Target opponent exiles two cards from their hand and loses 2 life.
        addCard(Zone.HAND, playerB, "Witness the End"); // {3}{B}

        addCard(Zone.HAND, playerA, "Silvercoat Lion", possibleTargets);

        castSpell(1, PhaseStep.UPKEEP, playerB, "Quicken", true);
        castSpell(1, PhaseStep.UPKEEP, playerB, "Witness the End", playerA);

        // it uses auto-choose logic inside, so disable strict mode
        // logic for possible targets with min/max = 2:
        // 0, 1, 2 - auto-choose all possible targets
        // 3+ - AI choose best targets
        setStrictChooseMode(false);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Quicken", 1);
        assertGraveyardCount(playerB, "Witness the End", 1);

        assertExileCount("Silvercoat Lion", selectedTargets);

        assertLife(playerA, 18);
        assertLife(playerB, 20);
    }

    @Test
    public void test_AutoChoose_0_of_0() {
        run_WithAutoSelection(0, 0);
    }

    @Test
    public void test_AutoChoose_1_of_1() {
        run_WithAutoSelection(1, 1);
    }

    @Test
    public void test_AutoChoose_2_of_2() {
        run_WithAutoSelection(2, 2);
    }

    @Test
    public void test_AutoChoose_2_of_5() {
        run_WithAutoSelection(2, 5);
    }
}
