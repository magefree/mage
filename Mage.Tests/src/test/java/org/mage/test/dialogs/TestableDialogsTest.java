package org.mage.test.dialogs;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsAllEffect;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.util.ConsoleUtil;
import mage.utils.testers.TestableDialog;
import mage.utils.testers.TestableDialogsRunner;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Try to test all possible game dialogs by TestableDialogsRunner
 * <p>
 * TODO: fill ai results for all generated dialogs
 *
 * @author JayDi85
 */
public class TestableDialogsTest extends CardTestPlayerBaseWithAIHelps {

    TestableDialogsRunner runner = new TestableDialogsRunner();
    Ability fakeAbility = new SimpleStaticAbility(new InfoEffect("fake"));

    @Test
    public void test_RunSingle_Manual() {
        prepareCards();

        runCode("run single", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            TestableDialog dialog = findDialog(runner, "target.choose(you, target)", "any 0-3");
            dialog.prepare();
            dialog.showDialog(playerA, fakeAbility, game, playerB);
        });

        // choice for 0-3
        setChoice(playerA, "Mountain");
        setChoice(playerA, "Mountain");
        setChoice(playerA, TestPlayer.CHOICE_SKIP);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // it's ok to have wrong targets message cause manual testing selected x2, not AI's x3
        assertAndPrintRunnerResults(false, false);
    }

    @Test
    public void test_RunSingle_AI() {
        prepareCards();

        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, PhaseStep.END_TURN, playerA);
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, PhaseStep.END_TURN, playerB);
        runCode("run single", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> {
            TestableDialog dialog = findDialog(runner, "target.choose(you, target)", "any 0-3");
            dialog.prepare();
            dialog.showDialog(playerA, fakeAbility, game, playerB);
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAndPrintRunnerResults(false, true);
    }

    @Test
    public void test_RunAll_AI() {
        // it's impossible to setup 700+ dialogs, so all choices made by AI
        // current AI uses only simple choices in dialogs, not simulations

        prepareCards();

        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, PhaseStep.END_TURN, playerA);
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, PhaseStep.END_TURN, playerB);
        runCode("run all", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> {
            runner.getDialogs().forEach(dialog -> {
                System.out.println(String.format("run testable dialog %d of %d (%s, %s - %s)",
                        dialog.getRegNumber(),
                        runner.getDialogs().size(),
                        dialog.getClass().getSimpleName(),
                        dialog.getGroup(),
                        dialog.getName()
                ));
                dialog.prepare();
                dialog.showDialog(playerA, fakeAbility, game, playerB);
            });
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAndPrintRunnerResults(true, true);
    }

    @Test
    @Ignore // debug only - run single dialog by reg number
    public void test_RunSingle_Debugging() {
        int needRegNumber = 5;

        prepareCards();

        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, PhaseStep.END_TURN, playerA);
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, PhaseStep.END_TURN, playerB);
        runCode("run by number", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> {
            TestableDialog dialog = findDialog(runner, needRegNumber);
            dialog.prepare();
            dialog.showDialog(playerA, fakeAbility, game, playerB);
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAndPrintRunnerResults(false, true);
    }

    private void prepareCards() {
        // runner calls dialogs for both A and B, so players must have same cards
        removeAllCardsFromLibrary(playerA);
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.HAND, playerA, "Forest", 6);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 6);
        addCard(Zone.HAND, playerB, "Forest", 6);

        runCode("restrict lands", 1, PhaseStep.UPKEEP, playerA, (info, player, game) -> {
            // restrict any lands play, so AI will keep lands in hand
            game.addEffect(new PlayAdditionalLandsAllEffect(-1), fakeAbility);
        });
    }

    private TestableDialog findDialog(TestableDialogsRunner runner, String byGroup, String byName) {
        List<TestableDialog> res = runner.getDialogs().stream()
                .filter(dialog -> dialog.getGroup().equals(byGroup))
                .filter(dialog -> dialog.getName().equals(byName))
                .collect(Collectors.toList());
        Assert.assertEquals("must found only 1 dialog", 1, res.size());
        return res.get(0);
    }

    private TestableDialog findDialog(TestableDialogsRunner runner, Integer byRegNumber) {
        List<TestableDialog> res = runner.getDialogs().stream()
                .filter(dialog -> dialog.getRegNumber().equals(byRegNumber))
                .collect(Collectors.toList());
        Assert.assertEquals("must found only 1 dialog", 1, res.size());
        return res.get(0);
    }

    private void assertAndPrintRunnerResults(boolean showFullList, boolean failOnBadResults) {
        // print table with full dialogs list and assert results

        // auto-size for columns
        int maxNumberLength = "9999".length();
        int maxGroupLength = "Group".length();
        int maxNameLength = "Name".length();
        int maxResultLength = "Result".length();
        int needTotalsSize = 0;
        for (TestableDialog dialog : runner.getDialogs()) {
            if (!showFullList && !dialog.getResult().isFinished()) {
                continue;
            }
            maxGroupLength = Math.max(maxGroupLength, dialog.getGroup().length());
            maxNameLength = Math.max(maxNameLength, dialog.getName().length());

            // resize group to keep space for bigger total message like assert res
            String resAssert = dialog.getResult().getResAssert();
            resAssert = resAssert == null ? "" : resAssert;
            needTotalsSize = Math.max(needTotalsSize, dialog.getResult().getResDebugSource().length());
            needTotalsSize = Math.max(needTotalsSize, resAssert.length());
            int currentTotalsSize = maxNumberLength + maxGroupLength + maxNameLength + maxResultLength + 9;
            if (currentTotalsSize < needTotalsSize) {
                maxGroupLength = maxGroupLength + needTotalsSize - currentTotalsSize;
            }
        }

        String rowFormat = "| %-" + maxNumberLength + "s | %-" + maxGroupLength + "s | %-" + maxNameLength + "s | %-" + maxResultLength + "s |%n";
        String horizontalBorder = "+-" +
                String.join("", Collections.nCopies(maxNumberLength, "-")) + "-+-" +
                String.join("", Collections.nCopies(maxGroupLength, "-")) + "-+-" +
                String.join("", Collections.nCopies(maxNameLength, "-")) + "-+-" +
                String.join("", Collections.nCopies(maxResultLength, "-")) + "-+";
        String totalsLeftFormat = "| %-" + (maxNumberLength + maxGroupLength + maxNameLength + maxResultLength + 9) + "s |%n";
        String totalsRightFormat = "| %" + (maxNumberLength + maxGroupLength + maxNameLength + maxResultLength + 9) + "s |%n";

        // print header row
        System.out.println(horizontalBorder);
        System.out.printf(rowFormat, "N", "Group", "Name", "Result");
        System.out.println(horizontalBorder);

        // print data rows
        String prevGroup = "";
        int totalDialogs = 0;
        int totalGood = 0;
        int totalBad = 0;
        int totalUnknown = 0;
        TestableDialog firstBadDialog = null;
        String firstBadAssert = "";
        String firstBadDebugSource = "";
        boolean usedHorizontalBorder = true; // mark that last print used horizontal border (fix duplicates)
        Map<String, String> coloredTexts = new HashMap<>(); // must colorize after string format to keep pretty table
        for (TestableDialog dialog : runner.getDialogs()) {
            if (!showFullList && !dialog.getResult().isFinished()) {
                // print only required dialogs
                continue;
            }
            totalDialogs++;
            if (!prevGroup.isEmpty() && !prevGroup.equals(dialog.getGroup())) {
                if (!usedHorizontalBorder) {
                    System.out.println(horizontalBorder);
                    usedHorizontalBorder = true;
                }
            }
            prevGroup = dialog.getGroup();

            // print dialog stats
            String status;
            coloredTexts.clear();
            String resAssert = dialog.getResult().getResAssert();
            String resDebugSource = dialog.getResult().getResDebugSource();
            String assertError = "";
            if (resAssert == null) {
                totalUnknown++;
                status = "?";
                coloredTexts.put("?", ConsoleUtil.asYellow("?"));
            } else if (resAssert.isEmpty()) {
                totalGood++;
                status = "OK";
                coloredTexts.put("OK", ConsoleUtil.asGreen("OK"));
            } else {
                totalBad++;
                status = "FAIL";
                coloredTexts.put("FAIL", ConsoleUtil.asRed("FAIL"));
                assertError = resAssert;
            }
            if (!assertError.isEmpty()) {
                if (!usedHorizontalBorder) {
                    System.out.println(horizontalBorder);
                    usedHorizontalBorder = true;
                }
            }
            System.out.print(getColoredRow(rowFormat, coloredTexts, dialog.getRegNumber(), dialog.getGroup(), dialog.getName(), status));
            usedHorizontalBorder = false;

            // print dialog error
            if (!assertError.isEmpty()) {
                coloredTexts.clear();
                coloredTexts.put(resAssert, ConsoleUtil.asRed(resAssert));
                coloredTexts.put(resDebugSource, ConsoleUtil.asRed(resDebugSource));
                String badAssert = getColoredRow(totalsRightFormat, coloredTexts, resAssert);
                String badDebugSource = getColoredRow(totalsRightFormat, coloredTexts, resDebugSource);
                if (firstBadDialog == null) {
                    firstBadDialog = dialog;
                    firstBadAssert = badAssert;
                    firstBadDebugSource = badDebugSource;
                }
                System.out.print(badAssert);
                System.out.print(badDebugSource);
                System.out.println(horizontalBorder);
                usedHorizontalBorder = true;
            }
        }
        if (!usedHorizontalBorder) {
            System.out.println(horizontalBorder);
            usedHorizontalBorder = true;
        }

        // print totals
        System.out.printf(totalsLeftFormat, "Total dialogs: " + totalDialogs);
        usedHorizontalBorder = false;
        String goodStats = String.format("%d good", totalGood);
        String badStats = String.format("%d bad", totalBad);
        String unknownStats = String.format("%d unknown", totalUnknown);
        coloredTexts.clear();
        coloredTexts.put(goodStats, String.format("%s good", ConsoleUtil.asGreen(String.valueOf(totalGood))));
        coloredTexts.put(badStats, String.format("%s bad", ConsoleUtil.asRed(String.valueOf(totalBad))));
        coloredTexts.put(unknownStats, String.format("%s unknown", ConsoleUtil.asYellow(String.valueOf(totalUnknown))));
        System.out.print(getColoredRow(totalsLeftFormat, coloredTexts, String.format("Total results: %s, %s, %s",
                goodStats, badStats, unknownStats)));
        // first error for fast access in big list
        if (totalDialogs > 1 && firstBadDialog != null) {
            System.out.println(horizontalBorder);
            System.out.print(getColoredRow(totalsRightFormat, coloredTexts, "First bad dialog: " + firstBadDialog.getRegNumber() + " (debug it by test_RunSingle_Debugging)"));
            System.out.print(getColoredRow(totalsRightFormat, coloredTexts, firstBadDialog.getName() + " - " + firstBadDialog.getDescription()));
            System.out.print(firstBadAssert);
            System.out.print(firstBadDebugSource);
        }

        // table end
        System.out.println(horizontalBorder);
        usedHorizontalBorder = true;

        if (failOnBadResults && totalBad > 0) {
            Assert.fail(String.format("Testable dialogs has %d bad results, try to fix it", totalBad));
        }
    }

    private String getColoredRow(String rowFormat, Map<String, String> coloredTexts, Object... args) {
        String line = String.format(rowFormat, args);
        for (String coloredText : coloredTexts.keySet()) {
            line = line.replace(coloredText, coloredTexts.get(coloredText));
        }
        return line;
    }
}