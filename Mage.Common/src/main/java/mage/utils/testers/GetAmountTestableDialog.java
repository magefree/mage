package mage.utils.testers;

import mage.abilities.Ability;
import mage.game.Game;
import mage.players.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Part of testable game dialogs
 * <p>
 * Its simple dialog to get some amount (example: part of chooseTargetAmount)
 * <p>
 * Supported methods:
 * - player.getAmount()
 *
 * @author JayDi85
 */
class GetAmountTestableDialog extends BaseTestableDialog {

    boolean isYou; // who choose - you or opponent
    int min;
    int max;

    public GetAmountTestableDialog(boolean isYou, int min, int max) {
        super(String.format("player.getAmount(%s)", isYou ? "you" : "AI"),
                String.format("from %d to %d", min, max),
                "",
                new AmountTestableResult()
        );
        this.isYou = isYou;
        this.min = min;
        this.max = max;
    }

    @Override
    public void showDialog(Player player, Ability source, Game game, Player opponent) {
        Player choosingPlayer = this.isYou ? player : opponent;
        String message = "<font color=green>message</font> with html";
        int chooseRes;
        chooseRes = choosingPlayer.getAmount(this.min, this.max, message, source, game);
        List<String> res = new ArrayList<>();
        res.add(getGroup() + " - " + this.getName() + " selected " + chooseRes);

        ((AmountTestableResult) this.getResult()).onFinish(true, res, chooseRes);
    }

    static public void register(TestableDialogsRunner runner) {
        List<Boolean> isYous = Arrays.asList(false, true);
        for (boolean isYou : isYous) {
            runner.registerDialog(new GetAmountTestableDialog(isYou, 0, 0));
            runner.registerDialog(new GetAmountTestableDialog(isYou, 0, 1));
            runner.registerDialog(new GetAmountTestableDialog(isYou, 0, 3));
            runner.registerDialog(new GetAmountTestableDialog(isYou, 0, 50));
            runner.registerDialog(new GetAmountTestableDialog(isYou, 0, 500));
            runner.registerDialog(new GetAmountTestableDialog(isYou, 1, 1));
            runner.registerDialog(new GetAmountTestableDialog(isYou, 1, 3));
            runner.registerDialog(new GetAmountTestableDialog(isYou, 1, 50));
            runner.registerDialog(new GetAmountTestableDialog(isYou, 3, 3));
            runner.registerDialog(new GetAmountTestableDialog(isYou, 3, 10));
            runner.registerDialog(new GetAmountTestableDialog(isYou, 10, 10));
        }
    }
}
