package org.mage.test.cards.rolldice;

import mage.abilities.keyword.FlyingAbility;
import mage.constants.PhaseStep;
import mage.constants.Planes;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

import java.util.Arrays;

/**
 * @author TheElk801, JayDi85
 */
public class RollDiceTest extends CardTestPlayerBaseWithAIHelps {

    private static final String goblins = "Swarming Goblins";
    private static final String guide = "Pixie Guide";
    private static final String thumb = "Krark's Other Thumb";
    private static final String gallery = "Mirror Gallery";
    private static final String farideh = "Farideh, Devil's Chosen";

    @Test(expected = AssertionError.class)
    public void test_StrictFailWithoutSetup() {
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
        assertPermanentCount(playerA, "Goblin Token", goblinCount);
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
        assertPermanentCount(playerA, "Goblin Token", 2);
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
        assertPermanentCount(playerA, "Goblin Token", 2);
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
        assertPermanentCount(playerA, "Goblin Token", goblinCount);
    }

    @Test(expected = AssertionError.class)
    public void test_KrarksOtherThumb_1copy_MustFailOnWrongChoiceSetup() {
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
        assertPermanentCount(playerA, "Goblin Token", goblinCount);
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

    @Test
    public void test_PlanarDie_Single() {
        // Active player can roll the planar die: Whenever you roll {CHAOS}, create a 7/7 colorless Eldrazi creature with annhilator 1
        addPlane(playerA, Planes.PLANE_HEDRON_FIELDS_OF_AGADEEM);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        // first chaos
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}: Roll the planar");
        setDieRollResult(playerA, 1); // make chaos
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        // second chaos (with additional cost)
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}: Roll the planar");
        setDieRollResult(playerA, 1); // make chaos

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Eldrazi Token", 2);
        assertTappedCount("Mountain", true, 1); // cost for second planar die
    }

    @Test
    public void test_PlanarDice_OneOrMoreDieRollTriggersMustWork() {
        // Active player can roll the planar die: Whenever you roll {CHAOS}, create a 7/7 colorless Eldrazi creature with annhilator 1
        addPlane(playerA, Planes.PLANE_HEDRON_FIELDS_OF_AGADEEM);
        //
        // Whenever you roll one or more dice, Farideh, Devil's Chosen gains flying and menace until end of turn.
        // If any of those results was 10 or higher, draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Farideh, Devil's Chosen");

        checkAbility("no fly before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Farideh, Devil's Chosen", FlyingAbility.class, false);

        // roll planar die and trigger Farideh
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}: Roll the planar");
        setDieRollResult(playerA, 1); // make chaos
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        checkAbility("must be fly after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Farideh, Devil's Chosen", FlyingAbility.class, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Eldrazi Token", 1);
    }

    @Test
    public void test_PlanarDice_DieRollTrigger_MustWorkAndSeeEmptyResult_1() {
        // Active player can roll the planar die: Whenever you roll {CHAOS}, create a 7/7 colorless Eldrazi creature with annhilator 1
        addPlane(playerA, Planes.PLANE_HEDRON_FIELDS_OF_AGADEEM);
        //
        // As Hammer Jammer enters the battlefield, roll a six-sided die. Hammer Jammer enters the battlefield with a number of +1/+1 counters on it equal to the result.
        // Whenever you roll a die, remove all +1/+1 counters from Hammer Jammer, then put a number of +1/+1 counters on it equal to the result.
        addCard(Zone.HAND, playerA, "Hammer Jammer");// {3}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        // prepare 5/5 hammer
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hammer Jammer");
        setDieRollResult(playerA, 5);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPT("must have 5/5 hammer", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hammer Jammer", 5, 5);

        // roll planar die and trigger event with 0 result
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}: Roll the planar");
        setDieRollResult(playerA, 1); // make chaos
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkGraveyardCount("hammer must die", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hammer Jammer", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Eldrazi Token", 1);
    }

    @Test
    public void test_PlanarDice_DieRollTrigger_MustWorkAndSeeEmptyResult_2() {
        // Active player can roll the planar die: Whenever you roll {CHAOS}, create a 7/7 colorless Eldrazi creature with annhilator 1
        addPlane(playerA, Planes.PLANE_HEDRON_FIELDS_OF_AGADEEM);
        //
        // Whenever you roll a 5 or higher on a die, Steel Squirrel gets +X/+X until end of turn, where X is the result.
        addCard(Zone.BATTLEFIELD, playerA, "Steel Squirrel", 1); // 1/1
        //
        // Roll a six-sided die. Create a number of 1/1 red Goblin creature tokens equal to the result.
        addCard(Zone.HAND, playerA, "Box of Free-Range Goblins", 1); // {4}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);

        checkPT("no boost before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Steel Squirrel", 1, 1);

        // roll planar die and trigger event with 0 result
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}: Roll the planar");
        setDieRollResult(playerA, 7); // make blank
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPT("no boost after planar", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Steel Squirrel", 1, 1);

        // roll normal die and trigger with boost
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Box of Free-Range Goblins");
        setDieRollResult(playerA, 6);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPT("boost after normal", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Steel Squirrel", 1 + 6, 1 + 6);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Eldrazi Token", 0);
    }

    @Test
    public void test_AdditionalRoll_WithLowest() {
        // If you would roll one or more dice, instead roll that many dice plus one and ignore the lowest roll.
        addCard(Zone.BATTLEFIELD, playerA, "Barbarian Class", 2);
        //
        // Roll a six-sided die. Create a number of 1/1 red Goblin creature tokens equal to the result.
        addCard(Zone.HAND, playerA, "Box of Free-Range Goblins", 1); // {4}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);

        // roll normal die and trigger 2x additional roll
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Box of Free-Range Goblins");
        setChoice(playerA, "Barbarian Class"); // replace events
        setDieRollResult(playerA, 3); // normal roll
        setDieRollResult(playerA, 6); // additional roll - will be selected
        setDieRollResult(playerA, 5); // additional roll
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Token", 6);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_AdditionalRoll_WithBigIdea() {
        // {2}{B/R}{B/R}, {T}: Roll a six-sided dice. Create a number of 1/1 red Brainiac creature tokens equal to the result.
        // Tap three untapped Brainiacs you control: The next time you would roll a six-sided die,
        // instead roll two six-sided dice and use the total of those results.
        addCard(Zone.BATTLEFIELD, playerA, "The Big Idea", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        //
        // Roll a six-sided die. Create a number of 1/1 red Goblin creature tokens equal to the result.
        addCard(Zone.HAND, playerA, "Box of Free-Range Goblins", 1); // {4}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);

        // prepare idea cost
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{B/R}{B/R}, {T}");
        setDieRollResult(playerA, 3);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Brainiac Token", 3);

        // prepare idea effect
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tap three Brainiac");
        setChoice(playerA, "Brainiac Token", 3);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // roll and trigger idea replace event
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Box of Free-Range Goblins");
        setDieRollResult(playerA, 3); // normal roll
        setDieRollResult(playerA, 6); // additional roll - will be sums
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Token", 3 + 6);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_AdditionalRoll_WithChoose() {
        // If you would roll a die, instead roll two of those dice and ignore one of those results.
        addCard(Zone.BATTLEFIELD, playerA, "Krark's Other Thumb", 1);
        //
        // Roll a six-sided die. Create a number of 1/1 red Goblin creature tokens equal to the result.
        addCard(Zone.HAND, playerA, "Box of Free-Range Goblins", 1); // {4}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);

        // roll normal die and trigger 2x additional roll
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Box of Free-Range Goblins");
        setDieRollResult(playerA, 3); // normal roll
        setDieRollResult(playerA, 6); // additional roll
        setChoice(playerA, "6"); // keep 6 as roll result
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Token", 6);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_PlanarDice_AdditionalRoll_WithLowest_MustIgnore() {
        // If you would roll one or more dice, instead roll that many dice plus one and ignore the lowest roll.
        addCard(Zone.BATTLEFIELD, playerA, "Barbarian Class", 2);
        //
        // Active player can roll the planar die: Whenever you roll {CHAOS}, create a 7/7 colorless Eldrazi creature with annhilator 1
        addPlane(playerA, Planes.PLANE_HEDRON_FIELDS_OF_AGADEEM);

        // roll planar die, but no triggers with double roll - cause it works with numerical results (lowest)
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}: Roll the planar");
        setDieRollResult(playerA, 1); // only one roll, chaos
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Eldrazi Token", 1);
    }

    @Test
    public void test_PlanarDice_AdditionalRoll_WithChoose_MustWork() {
        // If you would roll a die, instead roll two of those dice and ignore one of those results.
        addCard(Zone.BATTLEFIELD, playerA, "Krark's Other Thumb", 1);
        //
        // Active player can roll the planar die: Whenever you roll {CHAOS}, create a 7/7 colorless Eldrazi creature with annhilator 1
        addPlane(playerA, Planes.PLANE_HEDRON_FIELDS_OF_AGADEEM);

        // roll planar die, but no triggers with second roll - cause it works with numerical results (lowest)
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}: Roll the planar");
        setDieRollResult(playerA, 4); // first roll as blank
        setDieRollResult(playerA, 1); // second roll as chaos
        setChoice(playerA, "Chaos Roll"); // must choose result
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Eldrazi Token", 1);
    }

    @Test
    public void test_PlanarDice_AdditionalRoll_WithBigIdea_MustIgnore() {
        // see consts comments about planar die size
        //Assert.assertEquals("Planar dice must be six sided", 6, GameOptions.PLANECHASE_PLANAR_DIE_TOTAL_SIDES);

        // {2}{B/R}{B/R}, {T}: Roll a six-sided dice. Create a number of 1/1 red Brainiac creature tokens equal to the result.
        // Tap three untapped Brainiacs you control: The next time you would roll a six-sided die,
        // instead roll two six-sided dice and use the total of those results.
        addCard(Zone.BATTLEFIELD, playerA, "The Big Idea", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        //
        // Active player can roll the planar die: Whenever you roll {CHAOS}, create a 7/7 colorless Eldrazi creature with annhilator 1
        addPlane(playerA, Planes.PLANE_HEDRON_FIELDS_OF_AGADEEM);

        // prepare idea cost
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{B/R}{B/R}, {T}");
        setDieRollResult(playerA, 3);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Brainiac Token", 3);

        // prepare idea effect
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tap three Brainiac");
        setChoice(playerA, "Brainiac Token", 3);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // roll planar die, but no triggers with second roll - cause it works with numerical results (sum)
        // or planar dice hasn't 6 sides
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}: Roll the planar");
        setDieRollResult(playerA, 1); // only one roll, chaos
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Eldrazi Token", 1);
    }

    @Test
    public void test_AI_AdditionalRollChoose() {
        // If you would roll a die, instead roll two of those dice and ignore one of those results.
        addCard(Zone.BATTLEFIELD, playerA, "Krark's Other Thumb", 1);
        //
        // Roll a six-sided die. Create a number of 1/1 red Goblin creature tokens equal to the result.
        addCard(Zone.HAND, playerA, "Box of Free-Range Goblins", 1); // {4}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);

        // roll normal die and trigger 2x additional roll
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Box of Free-Range Goblins");
        setDieRollResult(playerA, 3); // normal roll
        setDieRollResult(playerA, 6); // additional roll
        // AI must choose max value due good outcome
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Token", 6);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }
}
