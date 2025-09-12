package mage.utils.testers;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;
import mage.util.DebugUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Part of testable game dialogs
 * <p>
 * Supported methods:
 * - player.choosePile()
 *
 * @author JayDi85
 */
class ChoosePileTestableDialog extends BaseTestableDialog {

    boolean isYou; // who choose - you or opponent
    int pileSize1;
    int pileSize2;

    public ChoosePileTestableDialog(boolean isYou, int pileSize1, int pileSize2) {
        super(String.format("player.choosePile(%s)", isYou ? "you" : "AI"),
                "pile sizes: " + pileSize1 + " and " + pileSize2,
                "",
                new BaseTestableResult()
        );
        this.isYou = isYou;
        this.pileSize1 = pileSize1;
        this.pileSize2 = pileSize2;
    }

    @Override
    public void showDialog(Player player, Ability source, Game game, Player opponent) {
        // TODO: it's ok to show broken title - must add html support in windows's title someday
        String mainMessage = "main <font color=green>message</font> with html" + CardUtil.getSourceLogName(game, source);

        // random piles (make sure it contain good amount of cards)
        List<Card> all = new ArrayList<>(game.getCards());
        Collections.shuffle(all);
        List<Card> pile1 = all.stream().limit(this.pileSize1).collect(Collectors.toList());
        Collections.shuffle(all);
        List<Card> pile2 = all.stream().limit(this.pileSize2).collect(Collectors.toList());

        Player choosingPlayer = this.isYou ? player : opponent;
        String chooseDebugSource = DebugUtil.getMethodNameWithSource(0, "class");
        boolean chooseRes = choosingPlayer.choosePile(Outcome.Benefit, mainMessage, pile1, pile2, game);
        List<String> res = new ArrayList<>();
        res.add(getGroup() + " - " + this.getName() + " - " + (chooseRes ? "TRUE" : "FALSE"));
        res.add(" * selected pile: " + (chooseRes ? "pile 1" : "pile 2"));

        this.getResult().onFinish(chooseDebugSource, chooseRes, res);
    }

    static public void register(TestableDialogsRunner runner) {
        List<Boolean> isYous = Arrays.asList(false, true);
        for (boolean isYou : isYous) {
            runner.registerDialog(new ChoosePileTestableDialog(isYou, 3, 5));
            runner.registerDialog(new ChoosePileTestableDialog(isYou, 10, 10));
            runner.registerDialog(new ChoosePileTestableDialog(isYou, 30, 30));
            runner.registerDialog(new ChoosePileTestableDialog(isYou, 90, 90));
            runner.registerDialog(new ChoosePileTestableDialog(isYou, 0, 10));
            runner.registerDialog(new ChoosePileTestableDialog(isYou, 10, 0));
            runner.registerDialog(new ChoosePileTestableDialog(isYou, 0, 0));
        }
    }
}
