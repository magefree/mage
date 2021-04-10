package org.mage.test.cards.targets;

import mage.constants.MultiAmountType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author JayDi85
 */

public class TargetMultiAmountTest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_DefaultValues() {
        // default values must be assigned from first to last by minimum values
        assertDefaultValues("", 0, 0, 0);
        //
        assertDefaultValues("0", 1, 0, 0);
        assertDefaultValues("0 0", 2, 0, 0);
        assertDefaultValues("0 0 0", 3, 0, 0);
        //
        assertDefaultValues("1", 1, 1, 1);
        assertDefaultValues("1 0", 2, 1, 1);
        assertDefaultValues("1 0 0", 3, 1, 1);
        //
        assertDefaultValues("1", 1, 1, 2);
        assertDefaultValues("1 0", 2, 1, 2);
        assertDefaultValues("1 0 0", 3, 1, 2);
        //
        assertDefaultValues("2", 1, 2, 2);
        assertDefaultValues("2 0", 2, 2, 2);
        assertDefaultValues("2 0 0", 3, 2, 2);
        //
        assertDefaultValues("2", 1, 2, 10);
        assertDefaultValues("2 0", 2, 2, 10);
        assertDefaultValues("2 0 0", 3, 2, 10);
    }

    private void assertDefaultValues(String need, int count, int min, int max) {
        List<Integer> defaultValues = MultiAmountType.prepareDefaltValues(count, min, max);
        String current = defaultValues
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" "));
        Assert.assertEquals("default values", need, current);
        Assert.assertTrue("default values must be good", MultiAmountType.isGoodValues(defaultValues, count, min, max));
    }

    @Test
    public void test_GoodValues() {
        // good values are checking in test_DefaultValues, it's an additional
        List<Integer> list = MultiAmountType.prepareDefaltValues(3, 0, 0);

        // count (0, 0, 0)
        Assert.assertFalse("count", MultiAmountType.isGoodValues(list, 0, 0, 0));
        Assert.assertFalse("count", MultiAmountType.isGoodValues(list, 1, 0, 0));
        Assert.assertFalse("count", MultiAmountType.isGoodValues(list, 2, 0, 0));
        Assert.assertTrue("count", MultiAmountType.isGoodValues(list, 3, 0, 0));
        Assert.assertFalse("count", MultiAmountType.isGoodValues(list, 4, 0, 0));

        // min (0, 1, 1)
        list.set(0, 0);
        list.set(1, 1);
        list.set(2, 1);
        Assert.assertTrue("min", MultiAmountType.isGoodValues(list, 3, 0, 100));
        Assert.assertTrue("min", MultiAmountType.isGoodValues(list, 3, 1, 100));
        Assert.assertTrue("min", MultiAmountType.isGoodValues(list, 3, 2, 100));
        Assert.assertFalse("min", MultiAmountType.isGoodValues(list, 3, 3, 100));
        Assert.assertFalse("min", MultiAmountType.isGoodValues(list, 3, 4, 100));

        // max (0, 1, 1)
        list.set(0, 0);
        list.set(1, 1);
        list.set(2, 1);
        Assert.assertFalse("max", MultiAmountType.isGoodValues(list, 3, 0, 0));
        Assert.assertFalse("max", MultiAmountType.isGoodValues(list, 3, 0, 1));
        Assert.assertTrue("max", MultiAmountType.isGoodValues(list, 3, 0, 2));
        Assert.assertTrue("max", MultiAmountType.isGoodValues(list, 3, 0, 3));
        Assert.assertTrue("max", MultiAmountType.isGoodValues(list, 3, 0, 4));
    }

    @Test
    public void test_Parse() {
        // parse must use correct values on good data or default values on broken data

        // simple parse without data check
        assertParse("", 3, 1, 3, "", false);
        assertParse("1", 3, 1, 3, "1", false);
        assertParse("0 0 0", 3, 1, 3, "0 0 0", false);
        assertParse("1 0 3", 3, 1, 3, "1 0 3", false);
        assertParse("0 5 0 6", 3, 1, 3, "1,text 5 4. 6", false);

        // parse with data check - good data
        assertParse("1 0 2", 3, 0, 3, "1 0 2", true);

        // parse with data check - broken data (must return defalt - 1 0 0)
        assertParse("1 0 0", 3, 1, 3, "", true);
        assertParse("1 0 0", 3, 1, 3, "1", true);
        assertParse("1 0 0", 3, 1, 3, "0 0 0", true);
        assertParse("1 0 0", 3, 1, 3, "1 0 3", true);
        assertParse("1 0 0", 3, 1, 3, "1,text 4.", true);
    }

    private void assertParse(String need, int count, int min, int max, String answerToParse, Boolean returnDefaultOnError) {
        List<Integer> parsedValues = MultiAmountType.parseAnswer(answerToParse, count, min, max, returnDefaultOnError);
        String current = parsedValues
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" "));
        Assert.assertEquals("parsed values", need, current);
        if (returnDefaultOnError) {
            Assert.assertTrue("parsed values must be good", MultiAmountType.isGoodValues(parsedValues, count, min, max));
        }
    }

    @Test
    public void test_Manamorphose_Normal() {
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
        assertAllCommandsUsed();
    }
}