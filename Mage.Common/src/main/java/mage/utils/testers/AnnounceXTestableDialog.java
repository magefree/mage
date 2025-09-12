package mage.utils.testers;

import mage.abilities.Ability;
import mage.game.Game;
import mage.players.Player;
import mage.util.DebugUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Part of testable game dialogs
 * <p>
 * Supported methods:
 * - player.announceX()
 *
 * @author JayDi85
 */
class AnnounceXTestableDialog extends BaseTestableDialog {

    boolean isYou; // who choose - you or opponent
    boolean isMana; // reason - for mana payment or another value
    int min;
    int max;

    public AnnounceXTestableDialog(boolean isYou, boolean isMana, int min, int max) {
        super(String.format("player.announceX(%s)", isYou ? "you" : "AI"),
                String.format("%s from %d to %d", isMana ? "mana" : "cost", min, max), "",
                new AmountTestableResult());
        this.isYou = isYou;
        this.isMana = isMana;
        this.min = min;
        this.max = max;
    }

    private AnnounceXTestableDialog aiMustChoose(int minAmount, int maxAmount) {
        // require min/max cause AI logic uses random choices
        AmountTestableResult res = ((AmountTestableResult) this.getResult());
        res.aiAssertEnabled = true;
        res.aiAssertMinAmount = minAmount;
        res.aiAssertMaxAmount = maxAmount;
        return this;
    }

    @Override
    public void showDialog(Player player, Ability source, Game game, Player opponent) {
        Player choosingPlayer = this.isYou ? player : opponent;
        String message = "<font color=green>message</font> with html";
        String chooseDebugSource = DebugUtil.getMethodNameWithSource(0, "class");
        int chooseRes = choosingPlayer.announceX(this.min, this.max, message, game, source, this.isMana);
        List<String> res = new ArrayList<>();
        res.add(getGroup() + " - " + this.getName() + " selected " + chooseRes);

        ((AmountTestableResult) this.getResult()).onFinish(chooseDebugSource, true, res, chooseRes);
    }

    static public void register(TestableDialogsRunner runner) {
        List<Boolean> isYous = Arrays.asList(false, true);
        List<Boolean> isManas = Arrays.asList(false, true);
        for (boolean isYou : isYous) {
            for (boolean isMana : isManas) {
                runner.registerDialog(new AnnounceXTestableDialog(isYou, isMana, 0, 0).aiMustChoose(0, 0));
                runner.registerDialog(new AnnounceXTestableDialog(isYou, isMana, 0, 1).aiMustChoose(0, 1));
                runner.registerDialog(new AnnounceXTestableDialog(isYou, isMana, 0, 3).aiMustChoose(0, 3));
                runner.registerDialog(new AnnounceXTestableDialog(isYou, isMana, 0, 50).aiMustChoose(0, 50));
                runner.registerDialog(new AnnounceXTestableDialog(isYou, isMana, 0, 500).aiMustChoose(0, 500));
                runner.registerDialog(new AnnounceXTestableDialog(isYou, isMana, 1, 1).aiMustChoose(1, 1));
                runner.registerDialog(new AnnounceXTestableDialog(isYou, isMana, 1, 3).aiMustChoose(1, 3));
                runner.registerDialog(new AnnounceXTestableDialog(isYou, isMana, 1, 50).aiMustChoose(1, 50));
                runner.registerDialog(new AnnounceXTestableDialog(isYou, isMana, 3, 3).aiMustChoose(3, 3));
                runner.registerDialog(new AnnounceXTestableDialog(isYou, isMana, 3, 10).aiMustChoose(3, 10));
                runner.registerDialog(new AnnounceXTestableDialog(isYou, isMana, 10, 10).aiMustChoose(10, 10));
            }
        }
    }
}
