package mage.utils.testers;

import mage.abilities.Ability;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.Targets;
import mage.util.DebugUtil;

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
                        isTargetChoice ? "Target" : "", // chooseTarget or choose
                        isYou ? "you" : "AI",
                        notTarget ? "not target" : "target"),
                name,
                target.toString(),
                new TargetTestableResult()
        );
        this.isPlayerChoice = isPlayerChoice;
        this.isTargetChoice = isTargetChoice;
        this.target = target.withNotTarget(notTarget);
        this.isYou = isYou;
    }

    @Override
    public void showDialog(Player player, Ability source, Game game, Player opponent) {
        Target choosingTarget = this.target.copy();
        Player choosingPlayer = this.isYou ? player : opponent;

        boolean chooseRes;
        String chooseDebugSource;
        if (this.isPlayerChoice) {
            // player.chooseXXX
            if (this.isTargetChoice) {
                chooseDebugSource = DebugUtil.getMethodNameWithSource(0, "class");
                chooseRes = choosingPlayer.chooseTarget(Outcome.Benefit, choosingTarget, source, game);
            } else {
                chooseDebugSource = DebugUtil.getMethodNameWithSource(0, "class");
                chooseRes = choosingPlayer.choose(Outcome.Benefit, choosingTarget, source, game);
            }
        } else {
            // target.chooseXXX
            if (this.isTargetChoice) {
                chooseDebugSource = DebugUtil.getMethodNameWithSource(0, "class");
                chooseRes = choosingTarget.chooseTarget(Outcome.Benefit, choosingPlayer.getId(), source, game);
            } else {
                chooseDebugSource = DebugUtil.getMethodNameWithSource(0, "class");
                chooseRes = choosingTarget.choose(Outcome.Benefit, choosingPlayer.getId(), source, game);
            }
        }

        List<String> res = new ArrayList<>();
        if (chooseRes) {
            Targets.printDebugTargets(getGroup() + " - " + this.getName() + " - " + "TRUE", new Targets(choosingTarget), source, game, res);
        } else {
            Targets.printDebugTargets(getGroup() + " - " + this.getName() + " - " + "FALSE", new Targets(choosingTarget), source, game, res);
        }

        ((TargetTestableResult) this.getResult()).onFinish(chooseDebugSource, chooseRes, res, choosingTarget);
    }

    private ChooseTargetTestableDialog aiMustChoose(boolean resStatus, int targetsCount) {
        TargetTestableResult res = ((TargetTestableResult) this.getResult());
        res.aiAssertEnabled = true;
        res.aiAssertResStatus = resStatus;
        res.aiAssertTargetsCount = targetsCount;
        return this;
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
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "any 0 e.g. X=0", createAnyTarget(0, 0)).aiMustChoose(false, 0)); // simulate X=0
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "any 1", createAnyTarget(1, 1)).aiMustChoose(true, 1));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "any 3", createAnyTarget(3, 3)).aiMustChoose(true, 3));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "any 5", createAnyTarget(5, 5)).aiMustChoose(true, 5));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "any max", createAnyTarget(0, Integer.MAX_VALUE)).aiMustChoose(true, 6 + 1)); // 6 own cards + 1 own player
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "any 0-1", createAnyTarget(0, 1)).aiMustChoose(true, 1));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "any 0-3", createAnyTarget(0, 3)).aiMustChoose(true, 3));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "any 0-5", createAnyTarget(0, 5)).aiMustChoose(true, 5));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "any 1-3", createAnyTarget(1, 3)).aiMustChoose(true, 3));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "any 2-3", createAnyTarget(2, 3)).aiMustChoose(true, 3));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "any 1-5", createAnyTarget(1, 5)).aiMustChoose(true, 5));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "any 2-5", createAnyTarget(2, 5)).aiMustChoose(true, 5));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "any 3-5", createAnyTarget(3, 5)).aiMustChoose(true, 5));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "any 4-5", createAnyTarget(4, 5)).aiMustChoose(true, 5));
                        //
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "impossible 0, e.g. X=0", createImpossibleTarget(0, 0)).aiMustChoose(false, 0));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "impossible 1", createImpossibleTarget(1, 1)).aiMustChoose(false, 0));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "impossible 3", createImpossibleTarget(3, 3)).aiMustChoose(false, 0));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "impossible 0-1", createImpossibleTarget(0, 1)).aiMustChoose(false, 0));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "impossible 0-3", createImpossibleTarget(0, 3)).aiMustChoose(false, 0));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "impossible 1-3", createImpossibleTarget(1, 3)).aiMustChoose(false, 0));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "impossible 2-3", createImpossibleTarget(2, 3)).aiMustChoose(false, 0));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "impossible max", createImpossibleTarget(0, Integer.MAX_VALUE)).aiMustChoose(false, 0));
                        //
                        // additional tests for 2 possible options limitation
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "player 0", createPlayerTarget(0, 0, notTarget)).aiMustChoose(false, 0));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "player 0-1", createPlayerTarget(0, 1, notTarget)).aiMustChoose(true, 1));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "player 0-2", createPlayerTarget(0, 2, notTarget)).aiMustChoose(true, 1));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "player 0-5", createPlayerTarget(0, 5, notTarget)).aiMustChoose(true, 1));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "player 1", createPlayerTarget(1, 1, notTarget)).aiMustChoose(true, 1));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "player 1-2", createPlayerTarget(1, 2, notTarget)).aiMustChoose(true, 1));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "player 1-5", createPlayerTarget(1, 5, notTarget)).aiMustChoose(true, 1));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "player 2", createPlayerTarget(2, 2, notTarget)).aiMustChoose(true, 2));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "player 2-5", createPlayerTarget(2, 5, notTarget)).aiMustChoose(true, 2));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "player 3", createPlayerTarget(3, 3, notTarget)).aiMustChoose(false, 0));
                        runner.registerDialog(new ChooseTargetTestableDialog(isPlayerChoice, isTargetChoice, notTarget, isYou, "player 3-5", createPlayerTarget(3, 5, notTarget)).aiMustChoose(false, 0));
                    }
                }
            }
        }
    }
}
