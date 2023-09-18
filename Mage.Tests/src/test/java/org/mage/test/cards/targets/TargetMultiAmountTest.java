package org.mage.test.cards.targets;

import com.google.common.collect.ImmutableList;
import mage.constants.MultiAmountType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.util.MultiAmountMessage;

import org.junit.Assert;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author JayDi85
 */

public class TargetMultiAmountTest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_DefaultValues() {
        // default values must be assigned from first to last by minimum values
        assertDefaultValuesUnconstrained("", 0, 0, 0);
        //
        assertDefaultValuesUnconstrained("0", 1, 0, 0);
        assertDefaultValuesUnconstrained("0 0", 2, 0, 0);
        assertDefaultValuesUnconstrained("0 0 0", 3, 0, 0);
        //
        assertDefaultValuesUnconstrained("1", 1, 1, 1);
        assertDefaultValuesUnconstrained("1 0", 2, 1, 1);
        assertDefaultValuesUnconstrained("1 0 0", 3, 1, 1);
        //
        assertDefaultValuesUnconstrained("1", 1, 1, 2);
        assertDefaultValuesUnconstrained("1 0", 2, 1, 2);
        assertDefaultValuesUnconstrained("1 0 0", 3, 1, 2);
        //
        assertDefaultValuesUnconstrained("2", 1, 2, 2);
        assertDefaultValuesUnconstrained("2 0", 2, 2, 2);
        assertDefaultValuesUnconstrained("2 0 0", 3, 2, 2);
        //
        assertDefaultValuesUnconstrained("2", 1, 2, 10);
        assertDefaultValuesUnconstrained("2 0", 2, 2, 10);
        assertDefaultValuesUnconstrained("2 0 0", 3, 2, 10);
        //
        // performance test
        assertDefaultValuesUnconstrained("2 0 0", 3, 2, Integer.MAX_VALUE);
    }

    private List<MultiAmountMessage> getUnconstrainedConstraints(int count) {
        return IntStream.range(0, count).mapToObj(i -> new MultiAmountMessage("", Integer.MIN_VALUE, Integer.MAX_VALUE))
                .collect(Collectors.toList());
    }

    private void assertDefaultValuesUnconstrained(String need, int count, int min, int max) {
        List<MultiAmountMessage> constraints = getUnconstrainedConstraints(count);
        List<Integer> defaultValues = MultiAmountType.prepareDefaltValues(constraints, min, max);
        String current = defaultValues
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" "));
        Assert.assertEquals("default values", need, current);
        Assert.assertTrue("default values must be good",
                MultiAmountType.isGoodValues(defaultValues, constraints, min, max));
    }

    @Test
    public void test_MaxValues() {
        // max possible values must be assigned from first to last by max possible values
        assertMaxValuesUnconstrained("", 0, 0, 0);
        //
        assertMaxValuesUnconstrained("0", 1, 0, 0);
        assertMaxValuesUnconstrained("0 0", 2, 0, 0);
        assertMaxValuesUnconstrained("0 0 0", 3, 0, 0);
        //
        assertMaxValuesUnconstrained("1", 1, 1, 1);
        assertMaxValuesUnconstrained("1 0", 2, 1, 1);
        assertMaxValuesUnconstrained("1 0 0", 3, 1, 1);
        //
        assertMaxValuesUnconstrained("2", 1, 1, 2);
        assertMaxValuesUnconstrained("1 1", 2, 1, 2);
        assertMaxValuesUnconstrained("1 1 0", 3, 1, 2);
        //
        assertMaxValuesUnconstrained("2", 1, 2, 2);
        assertMaxValuesUnconstrained("1 1", 2, 2, 2);
        assertMaxValuesUnconstrained("1 1 0", 3, 2, 2);
        //
        assertMaxValuesUnconstrained("10", 1, 2, 10);
        assertMaxValuesUnconstrained("5 5", 2, 2, 10);
        assertMaxValuesUnconstrained("4 3 3", 3, 2, 10);
        //
        assertMaxValuesUnconstrained("1 1 1 1 1 0 0 0 0 0", 10, 2, 5);
        //
        // performance test
        assertMaxValuesUnconstrained(String.valueOf(Integer.MAX_VALUE), 1, 2, Integer.MAX_VALUE);
        int part = Integer.MAX_VALUE / 3;
        String need = String.format("%d %d %d", part + 1, part, part);
        assertMaxValuesUnconstrained(need, 3, 2, Integer.MAX_VALUE);
    }

    private void assertMaxValuesUnconstrained(String need, int count, int min, int max) {
        List<MultiAmountMessage> constraints = getUnconstrainedConstraints(count);
        List<Integer> maxValues = MultiAmountType.prepareMaxValues(constraints, min, max);
        String current = maxValues
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" "));
        Assert.assertEquals("max values", need, current);
        Assert.assertTrue("max values must be good", MultiAmountType.isGoodValues(maxValues, constraints, min, max));
    }

    @Test
    public void test_GoodValues() {
        List<List<MultiAmountMessage>> constraints = ImmutableList.of(
                getUnconstrainedConstraints(0),
                getUnconstrainedConstraints(1),
                getUnconstrainedConstraints(2),
                getUnconstrainedConstraints(3),
                getUnconstrainedConstraints(4));

        // good values are checking in test_DefaultValues, it's an additional
        List<Integer> list = MultiAmountType.prepareDefaltValues(constraints.get(3), 0, 0);

        // count (0, 0, 0)
        Assert.assertFalse("count", MultiAmountType.isGoodValues(list, constraints.get(0), 0, 0));
        Assert.assertFalse("count", MultiAmountType.isGoodValues(list, constraints.get(1), 0, 0));
        Assert.assertFalse("count", MultiAmountType.isGoodValues(list, constraints.get(2), 0, 0));
        Assert.assertTrue("count", MultiAmountType.isGoodValues(list, constraints.get(3), 0, 0));
        Assert.assertFalse("count", MultiAmountType.isGoodValues(list, constraints.get(4), 0, 0));

        // min (0, 1, 1)
        list.set(0, 0);
        list.set(1, 1);
        list.set(2, 1);
        Assert.assertTrue("min", MultiAmountType.isGoodValues(list, constraints.get(3), 0, 100));
        Assert.assertTrue("min", MultiAmountType.isGoodValues(list, constraints.get(3), 1, 100));
        Assert.assertTrue("min", MultiAmountType.isGoodValues(list, constraints.get(3), 2, 100));
        Assert.assertFalse("min", MultiAmountType.isGoodValues(list, constraints.get(3), 3, 100));
        Assert.assertFalse("min", MultiAmountType.isGoodValues(list, constraints.get(3), 4, 100));

        // max (0, 1, 1)
        list.set(0, 0);
        list.set(1, 1);
        list.set(2, 1);
        Assert.assertFalse("max", MultiAmountType.isGoodValues(list, constraints.get(3), 0, 0));
        Assert.assertFalse("max", MultiAmountType.isGoodValues(list, constraints.get(3), 0, 1));
        Assert.assertTrue("max", MultiAmountType.isGoodValues(list, constraints.get(3), 0, 2));
        Assert.assertTrue("max", MultiAmountType.isGoodValues(list, constraints.get(3), 0, 3));
        Assert.assertTrue("max", MultiAmountType.isGoodValues(list, constraints.get(3), 0, 4));
    }

    @Test
    public void test_Parse() {
        // parse must use correct values on good data or default values on broken data

        // simple parse without data check
        assertParseUnconstrained("", 3, 1, 3, "", false);
        assertParseUnconstrained("1", 3, 1, 3, "1", false);
        assertParseUnconstrained("0 0 0", 3, 1, 3, "0 0 0", false);
        assertParseUnconstrained("1 0 3", 3, 1, 3, "1 0 3", false);
        assertParseUnconstrained("0 5 0 6", 3, 1, 3, "1,text 5 4. 6", false);

        // parse with data check - good data
        assertParseUnconstrained("1 0 2", 3, 0, 3, "1 0 2", true);

        // parse with data check - broken data (must return defalt - 1 0 0)
        assertParseUnconstrained("1 0 0", 3, 1, 3, "", true);
        assertParseUnconstrained("1 0 0", 3, 1, 3, "1", true);
        assertParseUnconstrained("1 0 0", 3, 1, 3, "0 0 0", true);
        assertParseUnconstrained("1 0 0", 3, 1, 3, "1 0 3", true);
        assertParseUnconstrained("1 0 0", 3, 1, 3, "1,text 4.", true);
    }

    private void assertParseUnconstrained(String need, int count, int min, int max, String answerToParse,
            Boolean returnDefaultOnError) {
        List<MultiAmountMessage> constraints = getUnconstrainedConstraints(count);
        List<Integer> parsedValues = MultiAmountType.parseAnswer(answerToParse, constraints, min, max,
                returnDefaultOnError);
        String current = parsedValues
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" "));
        Assert.assertEquals("parsed values", need, current);
        if (returnDefaultOnError) {
            Assert.assertTrue("parsed values must be good",
                    MultiAmountType.isGoodValues(parsedValues, constraints, min, max));
        }
    }

    @Test
    public void test_Mana_Manamorphose_Manual() {
        removeAllCardsFromHand(playerA);

        // Add two mana in any combination of colors.
        // Draw a card.
        addCard(Zone.HAND, playerA, "Manamorphose", 2); // {1}{R/G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2 * 2);

        // cast and select {B}{B}
        // one type of choices: wubrg order
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Manamorphose");
        setChoiceAmount(playerA, 0); // W
        setChoiceAmount(playerA, 0); // U
        setChoiceAmount(playerA, 2); // B
        setChoice(playerA, TestPlayer.CHOICE_SKIP); // skip RG
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkManaPool("after first cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "B", 2);

        // cast and select {R}{G}
        // another type of choices
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Manamorphose");
        setChoiceAmount(playerA, 0, 0, 0, 1, 1);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkManaPool("after second cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 1);
        checkManaPool("after second cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "G", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Mana_Manamorphose_AI() {
        removeAllCardsFromHand(playerA);

        // Add two mana in any combination of colors.
        // Draw a card.
        addCard(Zone.HAND, playerA, "Manamorphose", 1); // {1}{R/G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        // cast, but AI must select first manas (WU)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Manamorphose");
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkManaPool("after ai cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "W", 1);
        checkManaPool("after ai cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "U", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Damage_Boulderfall_Manual() {
        // Boulderfall deals 5 damage divided as you choose among any number of target creatures and/or players.
        addCard(Zone.HAND, playerA, "Boulderfall", 1); // {6}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Kitesail Corsair@bear", 3); // 2/1

        // distribute 4x + 1x damage (kill two creatures)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Boulderfall");
        addTargetAmount(playerA, "@bear.1", 4);
        addTargetAmount(playerA, "@bear.2", 1);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "@bear.1", 0);
        checkPermanentCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "@bear.2", 0);
        checkPermanentCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "@bear.3", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Damage_Boulderfall_AI() {
        // AI don't use multi amount dialogs like human (it's just one target amount choose/simulation)

        // Boulderfall deals 5 damage divided as you choose among any number of target creatures and/or players.
        addCard(Zone.HAND, playerA, "Boulderfall", 1); // {6}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Kitesail Corsair", 6); // 2/1

        // play card and distribute damage by game simulations for best score (kills 5x creatures)
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Kitesail Corsair", 5);
    }
}
