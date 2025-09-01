package mage.utils.testers;

import mage.abilities.Ability;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetAmount;
import mage.target.Targets;
import mage.target.common.TargetAnyTargetAmount;
import mage.util.DebugUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Part of testable game dialogs
 * <p>
 * It's a complex dialog with 2 steps: choose targets list + distribute amount between targets
 * <p>
 * Supported methods:
 * - player.chooseTarget(amount)
 *
 * @author JayDi85
 */
class ChooseAmountTestableDialog extends BaseTestableDialog {

    boolean isYou; // who choose - you or opponent
    int distributeAmount;
    int targetsMin;
    int targetsMax;

    public ChooseAmountTestableDialog(boolean isYou, String name, int distributeAmount, int targetsMin, int targetsMax) {
        super(String.format("player.chooseTarget(%s, amount)", isYou ? "you" : "AI"),
                name,
                String.format("%d between %d-%d targets", distributeAmount, targetsMin, targetsMax),
                new TargetTestableResult());
        this.isYou = isYou;
        this.distributeAmount = distributeAmount;
        this.targetsMin = targetsMin;
        this.targetsMax = targetsMax;
    }

    private ChooseAmountTestableDialog aiMustChoose(boolean resStatus, int targetsCount) {
        // TODO: AI use default distribution, improve someday
        TargetTestableResult res = ((TargetTestableResult) this.getResult());
        res.aiAssertEnabled = true;
        res.aiAssertResStatus = resStatus;
        res.aiAssertTargetsCount = targetsCount;
        return this;
    }

    @Override
    public void showDialog(Player player, Ability source, Game game, Player opponent) {
        TargetAmount choosingTarget = new TargetAnyTargetAmount(this.distributeAmount, this.targetsMin, this.targetsMax);
        Player choosingPlayer = this.isYou ? player : opponent;

        // TODO: add "damage" word in ability text, so chooseTargetAmount an show diff dialog (due inner logic - distribute damage or 1/1)
        String chooseDebugSource = DebugUtil.getMethodNameWithSource(0, "class");
        boolean chooseRes = choosingPlayer.chooseTargetAmount(Outcome.Benefit, choosingTarget, source, game);
        List<String> res = new ArrayList<>();
        if (chooseRes) {
            Targets.printDebugTargets(getGroup() + " - " + this.getName() + " - " + "TRUE", new Targets(choosingTarget), source, game, res);
        } else {
            Targets.printDebugTargets(getGroup() + " - " + this.getName() + " - " + "FALSE", new Targets(choosingTarget), source, game, res);
        }

        ((TargetTestableResult) this.getResult()).onFinish(chooseDebugSource, chooseRes, res, choosingTarget);
    }

    static public void register(TestableDialogsRunner runner) {
        // test game started with 2 players and 1 land on battlefield
        // so it's better to use target limits like 0, 1, 3, 5, max

        List<Boolean> isYous = Arrays.asList(false, true);

        // current AI will choose 1 target and assign all values to it (except with outcome.Damage)
        // TODO: add use cases for damage effects?
        for (boolean isYou : isYous) {
            // up to
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to", 0, 0, 0).aiMustChoose(false, 0));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to", 0, 0, 1).aiMustChoose(false, 0));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to", 0, 0, 3).aiMustChoose(false, 0));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to", 0, 0, 5).aiMustChoose(false, 0));
            //
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to, invalid", 1, 0, 0).aiMustChoose(false, 0));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to", 1, 0, 1).aiMustChoose(true, 1));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to", 1, 0, 3).aiMustChoose(true, 1));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to", 1, 0, 5).aiMustChoose(true, 1));
            //
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to, invalid", 2, 0, 0).aiMustChoose(false, 0));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to", 2, 0, 1).aiMustChoose(true, 1));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to", 2, 0, 3).aiMustChoose(true, 1));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to", 2, 0, 5).aiMustChoose(true, 1));
            //
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to, invalid", 3, 0, 0).aiMustChoose(false, 0));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to", 3, 0, 1).aiMustChoose(true, 1));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to", 3, 0, 3).aiMustChoose(true, 1));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to", 3, 0, 5).aiMustChoose(true, 1));
            //
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to, invalid", 5, 0, 0).aiMustChoose(false, 0));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to", 5, 0, 1).aiMustChoose(true, 1));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to", 5, 0, 3).aiMustChoose(true, 1));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to", 5, 0, 5).aiMustChoose(true, 1));

            // need target
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "need", 0, 1, 1).aiMustChoose(false, 0));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "need", 0, 1, 3).aiMustChoose(false, 0));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "need", 0, 1, 5).aiMustChoose(false, 0));
            //
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "need", 1, 1, 1).aiMustChoose(true, 1));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "need", 1, 1, 3).aiMustChoose(true, 1));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "need", 1, 1, 5).aiMustChoose(true, 1));
            //
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "need", 2, 1, 1).aiMustChoose(true, 1));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "need", 2, 1, 3).aiMustChoose(true, 1));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "need", 2, 1, 5).aiMustChoose(true, 1));
            //
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "need", 3, 1, 1).aiMustChoose(true, 1));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "need", 3, 1, 3).aiMustChoose(true, 1));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "need", 3, 1, 5).aiMustChoose(true, 1));
            //
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "need", 5, 1, 1).aiMustChoose(true, 1));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "need", 5, 1, 3).aiMustChoose(true, 1));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "need", 5, 1, 5).aiMustChoose(true, 1));
        }
    }
}
