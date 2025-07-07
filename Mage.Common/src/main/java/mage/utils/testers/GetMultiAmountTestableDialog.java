package mage.utils.testers;

import mage.abilities.Ability;
import mage.constants.MultiAmountType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.DebugUtil;
import mage.util.MultiAmountMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Part of testable game dialogs
 * <p>
 * Its simple dialog to get distributed value between multiple options (example: part of combat damage distributing)
 * <p>
 * Supported methods:
 * - player.getMultiAmountWithIndividualConstraints()
 * - player.getMultiAmount() - simple version of constraints
 *
 * @author JayDi85
 */
class GetMultiAmountTestableDialog extends BaseTestableDialog {

    boolean isYou; // who choose - you or opponent
    int totalMin;
    int totalMax;
    List<MultiAmountMessage> amountOptions = new ArrayList<>();

    /**
     * @param options min, max, default
     */
    public GetMultiAmountTestableDialog(boolean isYou, String info, int totalMin, int totalMax, List<List<Integer>> options) {
        super(String.format("player.getMultiAmount(%s)", isYou ? "you" : "AI"),
                String.format("%s, %d options from [%d-%d]", info, options.size(), totalMin, totalMax),
                "",
                new MultiAmountTestableResult()
        );
        this.isYou = isYou;
        this.totalMin = totalMin;
        this.totalMax = totalMax;
        int optionNumber = 0;
        for (List<Integer> single : options) {
            optionNumber++;
            String mes = "<font color=green>option</font> " + optionNumber + " with html";
            this.amountOptions.add(new MultiAmountMessage(mes, single.get(0), single.get(1), single.get(2)));
        }
    }

    private GetMultiAmountTestableDialog aiMustChoose(Integer... needValues) {
        // TODO: AI use default distribution:
        // - bad effect: min possible values
        // - good effect: max possible and distributed values
        MultiAmountTestableResult res = ((MultiAmountTestableResult) this.getResult());
        res.aiAssertEnabled = true;
        res.aiAssertValues = Arrays.stream(needValues).collect(Collectors.toList());
        return this;
    }

    private GetMultiAmountTestableDialog aiMustChooseMany(Integer options, Integer perOption) {
        List<Integer> need = new ArrayList<>();
        IntStream.rangeClosed(1, options).forEach(x -> {
            need.add(perOption);
        });

        MultiAmountTestableResult res = ((MultiAmountTestableResult) this.getResult());
        res.aiAssertEnabled = true;
        res.aiAssertValues = need;
        return this;
    }

    @Override
    public void showDialog(Player player, Ability source, Game game, Player opponent) {
        Player choosingPlayer = this.isYou ? player : opponent;
        //String message = "<font color=green>message</font> with html";
        List<MultiAmountMessage> options = this.amountOptions.stream().map(MultiAmountMessage::copy).collect(Collectors.toList());
        String chooseDebugSource = DebugUtil.getMethodNameWithSource(0, "class");
        List<Integer> chooseRes = choosingPlayer.getMultiAmountWithIndividualConstraints(
                Outcome.Benefit,
                options,
                this.totalMin,
                this.totalMax,
                MultiAmountType.DAMAGE,
                game
        );

        List<String> res = new ArrayList<>();
        res.add(getGroup() + " - " + this.getName());
        int selectedIndex = -1;
        int selectedTotal = 0;
        for (Integer selectedValue : chooseRes) {
            selectedIndex++;
            selectedTotal += selectedValue;
            MultiAmountMessage option = this.amountOptions.get(selectedIndex);
            res.add(String.format("%d from [%d-%d, def %d]",
                    selectedValue,
                    option.min,
                    option.max,
                    option.defaultValue
            ));
        }
        res.add("total selected: " + selectedTotal);

        ((MultiAmountTestableResult) this.getResult()).onFinish(chooseDebugSource, true, res, chooseRes);
    }

    static public void register(TestableDialogsRunner runner) {
        List<Boolean> isYous = Arrays.asList(false, true);
        for (boolean isYou : isYous) {
            // make sure default values are valid due min/max settings

            // TODO: add bad effect for AI (must test default distribution)

            // single target
            runner.registerDialog(new GetMultiAmountTestableDialog(isYou, "one, 0 def", 0, 1, genSameOptions(1, 0, 1, 0)).aiMustChoose(1));
            runner.registerDialog(new GetMultiAmountTestableDialog(isYou, "one, 0 def", 0, 3, genSameOptions(1, 0, 3, 0)).aiMustChoose(3));
            runner.registerDialog(new GetMultiAmountTestableDialog(isYou, "one, 1 def", 1, 1, genSameOptions(1, 1, 1, 1)).aiMustChoose(1));
            runner.registerDialog(new GetMultiAmountTestableDialog(isYou, "one, 1 def", 1, 3, genSameOptions(1, 1, 3, 1)).aiMustChoose(3));
            runner.registerDialog(new GetMultiAmountTestableDialog(isYou, "one, 5 def", 0, 10, genSameOptions(1, 0, 10, 5)).aiMustChoose(10));
            runner.registerDialog(new GetMultiAmountTestableDialog(isYou, "one, 10 def", 10, 10, genSameOptions(1, 0, 10, 10)).aiMustChoose(10));
            // multiple targets
            runner.registerDialog(new GetMultiAmountTestableDialog(isYou, "many, 0 def", 0, 5, genSameOptions(3, 0, 3, 0)).aiMustChoose(2, 2, 1));
            runner.registerDialog(new GetMultiAmountTestableDialog(isYou, "many, 1 def", 1, 5, genSameOptions(3, 1, 3, 1)).aiMustChoose(2, 2, 1));
            runner.registerDialog(new GetMultiAmountTestableDialog(isYou, "many, 20 def", 0, 60, genSameOptions(3, 0, 60, 20)).aiMustChoose(20, 20, 20));
            runner.registerDialog(new GetMultiAmountTestableDialog(isYou, "many, 20 def", 60, 60, genSameOptions(3, 0, 60, 20)).aiMustChoose(20, 20, 20));
            // big lists
            runner.registerDialog(new GetMultiAmountTestableDialog(isYou, "big list", 0, 100, genSameOptions(20, 0, 100, 0)).aiMustChooseMany(20, 5));
            runner.registerDialog(new GetMultiAmountTestableDialog(isYou, "big list", 0, 100, genSameOptions(100, 0, 100, 0)).aiMustChooseMany(100, 1));
        }
    }

    private static List<List<Integer>> genSameOptions(int options, int min, int max, int def) {
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < options; i++) {
            // min, max, default
            res.add(Arrays.asList(min, max, def));
        }
        return res;
    }
}
