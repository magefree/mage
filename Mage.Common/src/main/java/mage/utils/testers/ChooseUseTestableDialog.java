package mage.utils.testers;

import mage.abilities.Ability;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;
import mage.util.DebugUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Part of testable game dialogs
 * <p>
 * Supported methods:
 * - player.chooseUse()
 *
 * @author JayDi85
 */
class ChooseUseTestableDialog extends BaseTestableDialog {

    boolean isYou; // who choose - you or opponent
    String trueText;
    String falseText;
    String messageMain;
    String messageAdditional;

    public ChooseUseTestableDialog(boolean isYou, String name, String trueText, String falseText, String messageMain, String messageAdditional) {
        super(String.format("player.chooseUse(%s)", isYou ? "you" : "AI"),
                name + buildName(trueText, falseText, messageMain, messageAdditional),
                "",
                new BaseTestableResult()
        );
        this.isYou = isYou;
        this.trueText = trueText;
        this.falseText = falseText;
        this.messageMain = messageMain;
        this.messageAdditional = messageAdditional;
    }

    private static String buildName(String trueText, String falseText, String messageMain, String messageAdditional) {
        String buttonsInfo = (trueText == null ? "default" : "custom") + "/" + (falseText == null ? "default" : "custom");
        String messagesInfo = (messageMain == null ? "-" : "main") + "/" + (messageAdditional == null ? "-" : "additional");
        return String.format("buttons: %s, messages: %s", buttonsInfo, messagesInfo);
    }

    @Override
    public void showDialog(Player player, Ability source, Game game, Player opponent) {
        Player choosingPlayer = this.isYou ? player : opponent;
        String chooseDebugSource = DebugUtil.getMethodNameWithSource(0, "class");
        boolean chooseRes = choosingPlayer.chooseUse(
                Outcome.Benefit,
                messageMain,
                messageAdditional == null ? null : messageAdditional + CardUtil.getSourceLogName(game, source),
                trueText,
                falseText,
                source,
                game
        );
        List<String> res = new ArrayList<>();
        res.add(chooseRes ? "TRUE" : "FALSE");

        this.getResult().onFinish(chooseDebugSource, chooseRes, res);
    }

    static public void register(TestableDialogsRunner runner) {
        List<Boolean> isYous = Arrays.asList(false, true);
        String trueButton = "true button";
        String falseButton = "false button";
        String mainMessage = "main <font color=green>message</font> with html";
        String additionalMessage = "additional main <font color=red>message</font> with html";
        for (boolean isYou : isYous) {
            runner.registerDialog(new ChooseUseTestableDialog(isYou, "", null, null, mainMessage, additionalMessage));
            runner.registerDialog(new ChooseUseTestableDialog(isYou, "", trueButton, falseButton, mainMessage, additionalMessage));
            runner.registerDialog(new ChooseUseTestableDialog(isYou, "", null, falseButton, mainMessage, additionalMessage));
            runner.registerDialog(new ChooseUseTestableDialog(isYou, "", trueButton, null, mainMessage, additionalMessage));
            runner.registerDialog(new ChooseUseTestableDialog(isYou, "error ", trueButton, falseButton, null, additionalMessage));
            runner.registerDialog(new ChooseUseTestableDialog(isYou, "", trueButton, falseButton, mainMessage, null));
            runner.registerDialog(new ChooseUseTestableDialog(isYou, "error ", trueButton, falseButton, null, null));
        }
    }
}
