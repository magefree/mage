package org.mage.test.cards.rolldice;

import mage.abilities.keyword.FlyingAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public class RollDiceTest extends CardTestPlayerBase {

    private static final String goblins = "Swarming Goblins";
    private static final String guide = "Pixie Guide";
    private static final String thumb = "Krark's Other Thumb";
    private static final String gallery = "Mirror Gallery";
    private static final String farideh = "Farideh, Devil's Chosen";

    @Test(expected = AssertionError.class)
    public void testStrictFailWithoutSetup() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, goblins);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, goblins);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    private void runGoblinTest(int roll, int goblinCount) {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, goblins);

        setDieRollResult(playerA, roll);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, goblins);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, goblins, 1);
        assertPermanentCount(playerA, "Goblin", goblinCount);
    }

    @Test
    public void test_GoblinRoll_1() {
        runGoblinTest(1, 1);
    }

    @Test
    public void test_GoblinRoll_9() {
        runGoblinTest(9, 1);
    }

    @Test
    public void test_GoblinRoll_10() {
        runGoblinTest(10, 2);
    }

    @Test
    public void test_GoblinRoll_19() {
        runGoblinTest(19, 2);
    }

    @Test
    public void test_GoblinRoll_20() {
        runGoblinTest(20, 3);
    }

    @Test
    public void test_PixieGuide_1() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, guide);
        addCard(Zone.HAND, playerA, goblins);

        setDieRollResult(playerA, 9);
        setDieRollResult(playerA, 10);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, goblins);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, goblins, 1);
        assertPermanentCount(playerA, guide, 1);
        assertPermanentCount(playerA, "Goblin", 2);
    }

    @Test
    public void test_PixieGuide_2() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, guide, 2);
        addCard(Zone.HAND, playerA, goblins);

        setChoice(playerA, guide); // choose replacement effect
        setDieRollResult(playerA, 9);
        setDieRollResult(playerA, 9);
        setDieRollResult(playerA, 10);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, goblins);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, goblins, 1);
        assertPermanentCount(playerA, guide, 2);
        assertPermanentCount(playerA, "Goblin", 2);
    }

    private void runKrarksOtherThumbTest(int choice, int thumbCount, int goblinCount, int... rolls) {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, thumb, thumbCount);
        addCard(Zone.BATTLEFIELD, playerA, gallery);
        addCard(Zone.HAND, playerA, goblins);

        for (int i = 0; i < thumbCount - 1; i++) {
            setChoice(playerA, thumb); // choose replacement effect
        }
        for (int roll : rolls) {
            setDieRollResult(playerA, roll);
        }
        if (Arrays.stream(rolls).distinct().count() > 1) {
            setChoice(playerA, "" + choice);
        }
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, goblins);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, goblins, 1);
        assertPermanentCount(playerA, gallery, 1);
        assertPermanentCount(playerA, thumb, thumbCount);
        assertPermanentCount(playerA, "Goblin", goblinCount);
    }

    @Test(expected = AssertionError.class)
    public void test_KrarksOtherThumb_1copy_ChooseFailure() {
        runKrarksOtherThumbTest(8, 1, 1, 9, 10);
    }

    @Test
    public void test_KrarksOtherThumb_1copy_ChooseLower() {
        runKrarksOtherThumbTest(9, 1, 1, 9, 10);
    }

    @Test
    public void test_KrarksOtherThumb_1copy_ChooseHigher() {
        runKrarksOtherThumbTest(10, 1, 2, 9, 10);
    }

    @Test
    public void test_KrarksOtherThumb_1copy_SameRoll() {
        runKrarksOtherThumbTest(10, 1, 2, 10, 10);
    }

    @Test
    public void test_KrarksOtherThumb_2copies_ChooseLowest() {
        runKrarksOtherThumbTest(8, 2, 1, 8, 9, 10, 11);
    }

    @Test
    public void test_KrarksOtherThumb_2copies_ChooseMedium() {
        runKrarksOtherThumbTest(9, 2, 1, 8, 9, 10, 11);
    }

    @Test
    public void test_KrarksOtherThumb_2copies_ChooseHighest() {
        runKrarksOtherThumbTest(11, 2, 2, 8, 9, 10, 11);
    }

    @Test
    public void test_KrarksOtherThumb_2copies_SameRoll() {
        runKrarksOtherThumbTest(8, 2, 1, 8, 8, 8, 8);
    }

    private void runFaridehTest(int goblinCount, int handCount, int roll) {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, farideh);
        addCard(Zone.HAND, playerA, goblins);

        setDieRollResult(playerA, roll);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, goblins);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, goblins, 1);
        assertPermanentCount(playerA, "Goblin", goblinCount);
        assertAbility(playerA, farideh, FlyingAbility.getInstance(), true);
        assertHandCount(playerA, handCount);
    }

    @Test
    public void test_FaridehDevilsChosen_NoDraw() {
        runFaridehTest(1, 0, 9);
    }

    @Test
    public void test_FaridehDevilsChosen_Draw() {
        runFaridehTest(2, 1, 10);
    }
}
