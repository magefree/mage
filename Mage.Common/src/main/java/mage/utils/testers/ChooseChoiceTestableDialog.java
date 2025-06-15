package mage.utils.testers;

import mage.abilities.Ability;
import mage.choices.*;
import mage.constants.Outcome;
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
 * - player.choose(choice)
 *
 * @author JayDi85
 */
class ChooseChoiceTestableDialog extends BaseTestableDialog {

    boolean isYou; // who choose - you or opponent
    Choice choice;

    public ChooseChoiceTestableDialog(boolean isYou, String name, Choice choice) {
        super(String.format("player.choose(%s, choice)", isYou ? "you" : "AI"),
                name,
                choice.getClass().getSimpleName(),
                new ChoiceTestableResult()
        );
        this.isYou = isYou;
        this.choice = choice;
    }

    @Override
    public void showDialog(Player player, Ability source, Game game, Player opponent) {
        Player choosingPlayer = this.isYou ? player : opponent;
        Choice dialog = this.choice.copy();
        String chooseDebugSource = DebugUtil.getMethodNameWithSource(0, "class");
        boolean chooseRes = choosingPlayer.choose(Outcome.Benefit, dialog, game);

        List<String> res = new ArrayList<>();
        res.add(getGroup() + " - " + this.getName() + " - " + (chooseRes ? "TRUE" : "FALSE"));
        res.add("");
        String choice;
        if (dialog.isKeyChoice()) {
            String key = dialog.getChoiceKey();
            choice = dialog.getKeyChoices().getOrDefault(key, null);
            res.add(String.format("* selected key: %s (%s)", key, choice));
        } else {
            choice = dialog.getChoice();
            res.add(String.format("* selected value: %s", choice));
        }

        ((ChoiceTestableResult) this.getResult()).onFinish(chooseDebugSource, chooseRes, res, choice);
    }

    static public void register(TestableDialogsRunner runner) {
        // TODO: add require option
        // TODO: add ChoiceImpl with diff popup hints
        List<Boolean> isYous = Arrays.asList(false, true);
        for (boolean isYou : isYous) {
            runner.registerDialog(new ChooseChoiceTestableDialog(isYou, "", new ChoiceBasicLandType()));
            runner.registerDialog(new ChooseChoiceTestableDialog(isYou, "", new ChoiceCardType()));
            runner.registerDialog(new ChooseChoiceTestableDialog(isYou, "", new ChoiceColor()));
            runner.registerDialog(new ChooseChoiceTestableDialog(isYou, "", new ChoiceColorOrArtifact()));
            runner.registerDialog(new ChooseChoiceTestableDialog(isYou, "", new ChoiceCreatureType(null, null))); // TODO: must be dynamic to pass game/source
            runner.registerDialog(new ChooseChoiceTestableDialog(isYou, "", new ChoiceLandType()));
            runner.registerDialog(new ChooseChoiceTestableDialog(isYou, "", new ChoiceLeftOrRight()));
            runner.registerDialog(new ChooseChoiceTestableDialog(isYou, "", new ChoicePlaneswalkerType())); // TODO: must be dynamic to pass game/source
        }
    }
}
