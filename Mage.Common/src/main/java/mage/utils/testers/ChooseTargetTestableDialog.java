package mage.utils.testers;

import mage.abilities.Ability;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.Targets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Part of testable game dialogs
 * <p>
 * Supported methods:
 * - target.choose()
 * - target.chooseTarget()
 * - player.choose(target)
 * - player.chooseTarget(target)
 *
 * @author JayDi85
 */
class ChooseTargetTestableDialog extends BaseTestableDialog {

    Target target;
    boolean isPlayerChoice; // how to choose - by player.choose or by target.choose
    boolean isTargetChoice; // how to choose - by xxx.choose or xxx.chooseTarget
    boolean isYou; // who choose - you or opponent

    public ChooseTargetTestableDialog(boolean isPlayerChoice, boolean isTargetChoice, boolean notTarget, boolean isYou, String name, Target target) {
        super(String.format("%s%s(%s, %s)",
                isPlayerChoice ? "player.choose" : "target.choose",
                isTargetChoice ? "target" : "", // chooseTarget or choose
                isYou ? "you" : "AI",
                notTarget ? "not target" : "target"), name, target.toString());
        this.isPlayerChoice = isPlayerChoice;
        this.isTargetChoice = isTargetChoice;
        this.target = target.withNotTarget(notTarget);
        this.isYou = isYou;
    }

    @Override
    public List<String> showDialog(Player player, Ability source, Game game, Player opponent) {
        Target choosingTarget = this.target.copy();
        Player choosingPlayer = this.isYou ? player : opponent;

        boolean chooseRes;
        if (this.isPlayerChoice) {
            // player.chooseXXX
            if (this.isTargetChoice) {
                chooseRes = choosingPlayer.chooseTarget(Outcome.Benefit, choosingTarget, source, game);
            } else {
                chooseRes = choosingPlayer.choose(Outcome.Benefit, choosingTarget, source, game);
            }
        } else {
            // target.chooseXXX
            if (this.isTargetChoice) {
                chooseRes = choosingTarget.chooseTarget(Outcome.Benefit, choosingPlayer.getId(), source, game);
            } else {
                chooseRes = choosingTarget.choose(Outcome.Benefit, choosingPlayer.getId(), source, game);
            }
        }

        List<String> result = new ArrayList<>();
        if (chooseRes) {
            Targets.printDebugTargets(getGroup() + " - " + this.getName() + " - " + "TRUE", new Targets(choosingTarget), source, game, result);
        } else {
            Targets.printDebugTargets(getGroup() + " - " + this.getName() + " - " + "FALSE", new Targets(choosingTarget), source, game, result);
        }
        return result;
    }

    static public void register(TestableDialogsRunner runner) {
        // test game started with 2 players and 1 land on battlefield
        // so it's better to use target limits like 0, 1, 3, 5, max

        List<Boolean> notTargets = Arrays.asList(false, true);
        List<Boolean> isYous = Arrays.asList(false, true);
        List<Boolean> isPlayerChoices = Arrays.asList(false, true);
        List<Boolean> isTargetChoices = Arrays.asList(false, true);
        for (boolean notTarget : notTargets) {
            for (boolean isYou : isYous) {
                for (boolean isTargetChoice : isTargetChoices) {
                    for (boolean isPlayerChoice : isPlayerChoices) {
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "any 0 e.g. X=0", createAnyTarget(0, 0))); // simulate X=0
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "any 1", createAnyTarget(1, 1)));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "any 3", createAnyTarget(3, 3)));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "any 5", createAnyTarget(5, 5)));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "any max", createAnyTarget(0, Integer.MAX_VALUE)));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "any 0-1", createAnyTarget(0, 1)));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "any 0-3", createAnyTarget(0, 3)));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "any 0-5", createAnyTarget(0, 5)));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "any 1-3", createAnyTarget(1, 3)));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "any 2-3", createAnyTarget(2, 3)));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "any 1-5", createAnyTarget(1, 5)));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "any 2-5", createAnyTarget(2, 5)));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "any 3-5", createAnyTarget(3, 5)));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "any 4-5", createAnyTarget(4, 5))); // impossible on 3 targets
                        //
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "impossible 0, e.g. X=0", createImpossibleTarget(0, 0)));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "impossible 1", createImpossibleTarget(1, 1)));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "impossible 3", createImpossibleTarget(3, 3)));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "impossible 0-1", createImpossibleTarget(0, 1)));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "impossible 0-3", createImpossibleTarget(0, 3)));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "impossible 1-3", createImpossibleTarget(1, 3)));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "impossible 2-3", createImpossibleTarget(2, 3)));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "impossible max", createImpossibleTarget(0, Integer.MAX_VALUE)));
                        //
                        /*
                        runner.registerDialog(new PlayerChooseTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "creatures 0, e.g. X=0", createCreatureTarget(0, 0))); // simulate X=0
                        runner.registerDialog(new PlayerChooseTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "creatures 1", createCreatureTarget(1, 1)));
                        runner.registerDialog(new PlayerChooseTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "creatures 3", createCreatureTarget(3, 3)));
                        runner.registerDialog(new PlayerChooseTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "creatures 5", createCreatureTarget(5, 5)));
                        runner.registerDialog(new PlayerChooseTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "creatures max", createCreatureTarget(0, Integer.MAX_VALUE)));
                         */
                    }
                }
            }
        }
    }
}
