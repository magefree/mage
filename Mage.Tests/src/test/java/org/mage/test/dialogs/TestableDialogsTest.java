package org.mage.test.dialogs;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.utils.testers.TestableDialog;
import mage.utils.testers.TestableDialogsRunner;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
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
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.HAND, playerA, "Forest", 6);

        runCode("run single", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            TestableDialog dialog = findDialogs(runner, "target.choose(you, target)", "any 0-3").get(0);
            Assert.assertNotNull(dialog);
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

        printRunnerResults(false, false);
    }

    @Test
    public void test_RunSingle_AI() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.HAND, playerA, "Forest", 6);

        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, PhaseStep.END_TURN, playerA);
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, PhaseStep.END_TURN, playerB);
        runCode("run single", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> {
            TestableDialog dialog = findDialogs(runner, "target.choose(you, target)", "any 0-3").get(0);
            Assert.assertNotNull(dialog);
            dialog.prepare();
            dialog.showDialog(playerA, fakeAbility, game, playerB);
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        printRunnerResults(false, true);
    }

    @Test
    @Ignore // TODO: enable and fix all failed dialogs
    public void test_RunAll_AI() {
        // it's impossible to setup 700+ dialogs, so all choices made by AI
        // current AI uses only simple choices in dialogs, not simulations

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.HAND, playerA, "Forest", 6);

        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, PhaseStep.END_TURN, playerA);
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, PhaseStep.END_TURN, playerB);
        AtomicInteger dialogNumber = new AtomicInteger();
        runCode("run all", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> {
            runner.getDialogs().forEach(dialog -> {
                dialogNumber.incrementAndGet();
                System.out.println(String.format("run testable dialog %d of %d (%s, %s - %s)",
                        dialogNumber.get(),
                        runner.getDialogs().size(),
                        dialog.getClass().getSimpleName(),
                        dialog.getGroup(),
                        dialog.getName()
                ));
                dialog.showDialog(playerA, fakeAbility, game, playerB);
            });
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        printRunnerResults(true, true);
    }

    private List<TestableDialog> findDialogs(TestableDialogsRunner runner, String byGroup, String byName) {
        return runner.getDialogs().stream()
                .filter(d -> d.getGroup().equals(byGroup))
                .filter(d -> d.getName().equals(byName))
                .collect(Collectors.toList());
    }

    private void printRunnerResults(boolean showFullList, boolean assertGoodResults) {
        // print text table with full dialogs list and results

        // found table sizes
        int maxGroupLength = "Group".length();
        int maxNameLength = "Name".length();
        for (TestableDialog dialog : runner.getDialogs()) {
            if (!showFullList && !dialog.getResult().isFinished()) {
                continue;
            }
            maxGroupLength = Math.max(maxGroupLength, dialog.getGroup().length());
            maxNameLength = Math.max(maxNameLength, dialog.getName().length());
        }
        int maxResultLength = "Result".length();

        String rowFormat = "| %-" + maxGroupLength + "s | %-" + maxNameLength + "s | %-" + maxResultLength + "s |%n";
        String horizontalBorder = "+-" +
                String.join("", Collections.nCopies(maxGroupLength, "-")) + "-+-" +
                String.join("", Collections.nCopies(maxNameLength, "-")) + "-+-" +
                String.join("", Collections.nCopies(maxResultLength, "-")) + "-+";

        // header
        System.out.println(horizontalBorder);
        System.out.printf(rowFormat, "Group", "Name", "Result");
        System.out.println(horizontalBorder);

        // data
        String prevGroup = "";
        int totalDialogs = 0;
        int totalGood = 0;
        int totalBad = 0;
        int totalUnknown = 0;
        for (TestableDialog dialog : runner.getDialogs()) {
            if (!showFullList && !dialog.getResult().isFinished()) {
                continue;
            }
            totalDialogs++;
            if (!prevGroup.isEmpty() && !prevGroup.equals(dialog.getGroup())) {
                System.out.println(horizontalBorder);
            }
            prevGroup = dialog.getGroup();
            String status;
            if (dialog.getResult().getResAssert() == null) {
                status = asYellow("?");
                totalUnknown++;
            } else if (dialog.getResult().getResAssert()) {
                totalGood++;
                status = asGreen("OK");
            } else {
                totalBad++;
                status = asRed("FAIL");
            }
            System.out.printf(rowFormat, dialog.getGroup(), dialog.getName(), status);
        }
        System.out.println(horizontalBorder);

        // totals
        System.out.printf("| %-" + (maxGroupLength + maxNameLength + maxResultLength + 6) + "s |%n",
                "Total dialogs: " + totalDialogs);
        System.out.printf("| %-" + (maxGroupLength + maxNameLength + maxResultLength + 6) + "s |%n",
                String.format("Total results: %s good, %s bad, %s unknown",
                        asGreen(String.valueOf(totalGood)),
                        asRed(String.valueOf(totalBad)),
                        asYellow(String.valueOf(totalUnknown))
                )
        );
        System.out.println(horizontalBorder);

        if (assertGoodResults && totalBad > 0) {
            Assert.fail(String.format("Testable dialogs has %d bad results, try to fix it", totalBad));
        }
    }

    private String asRed(String text) {
        return "\u001B[31m" + text + "\u001B[0m";
    }

    private String asGreen(String text) {
        return "\u001B[32m" + text + "\u001B[0m";
    }

    private String asYellow(String text) {
        return "\u001B[33m" + text + "\u001B[0m";
    }
}