package mage.utils.testers;

import mage.abilities.Ability;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetAmount;
import mage.target.Targets;
import mage.target.common.TargetAnyTargetAmount;

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
                String.format("%d between %d-%d targets", distributeAmount, targetsMin, targetsMax));
        this.isYou = isYou;
        this.distributeAmount = distributeAmount;
        this.targetsMin = targetsMin;
        this.targetsMax = targetsMax;
    }

    @Override
    public List<String> showDialog(Player player, Ability source, Game game, Player opponent) {
        TargetAmount choosingTarget = new TargetAnyTargetAmount(this.distributeAmount, this.targetsMin, this.targetsMax);
        Player choosingPlayer = this.isYou ? player : opponent;

        // TODO: add "damage" word in ability text, so chooseTargetAmount an show diff dialog (due inner logic - distribute damage or 1/1)
        boolean chooseRes = choosingPlayer.chooseTargetAmount(Outcome.Benefit, choosingTarget, source, game);
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

        List<Boolean> isYous = Arrays.asList(false, true);

        for (boolean isYou : isYous) {
            // up to
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to", 0, 0, 0));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to", 0, 0, 1));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to", 0, 0, 3));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to", 0, 0, 5));
            //
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to, invalid", 1, 0, 0));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to", 1, 0, 1));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to", 1, 0, 3));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to", 1, 0, 5));
            //
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to, invalid", 2, 0, 0));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to", 2, 0, 1));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to", 2, 0, 3));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to", 2, 0, 5));
            //
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to, invalid", 3, 0, 0));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to", 3, 0, 1));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to", 3, 0, 3));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to", 3, 0, 5));
            //
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to, invalid", 5, 0, 0));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to", 5, 0, 1));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to", 5, 0, 3));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "up to", 5, 0, 5));

            // need target
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "need", 0, 1, 1));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "need", 0, 1, 3));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "need", 0, 1, 5));
            //
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "need", 1, 1, 1));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "need", 1, 1, 3));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "need", 1, 1, 5));
            //
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "need", 2, 1, 1));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "need", 2, 1, 3));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "need", 2, 1, 5));
            //
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "need", 3, 1, 1));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "need", 3, 1, 3));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "need", 3, 1, 5));
            //
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "need", 5, 1, 1));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "need", 5, 1, 3));
            runner.registerDialog(new ChooseAmountTestableDialog(isYou, "need", 5, 1, 5));
        }
    }
}
