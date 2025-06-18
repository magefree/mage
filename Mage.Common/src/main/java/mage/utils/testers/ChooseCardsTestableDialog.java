package mage.utils.testers;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.Targets;
import mage.target.common.TargetCardInHand;
import mage.util.DebugUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Part of testable game dialogs
 * <p>
 * Supported methods:
 * - player.choose(cards)
 * - player.chooseTarget(cards)
 *
 * @author JayDi85
 */
class ChooseCardsTestableDialog extends BaseTestableDialog {

    TargetCard target;
    boolean isTargetChoice; // how to choose - by xxx.choose or xxx.chooseTarget
    boolean isYou; // who choose - you or opponent

    public ChooseCardsTestableDialog(boolean isTargetChoice, boolean notTarget, boolean isYou, String name, TargetCard target) {
        super(String.format("%s(%s, %s, cards)",
                        isTargetChoice ? "player.chooseTarget" : "player.choose",
                        isYou ? "you" : "AI",
                        notTarget ? "not target" : "target"), name, target.toString(),
                new TargetTestableResult());
        this.isTargetChoice = isTargetChoice;
        this.target = target.withNotTarget(notTarget);
        this.isYou = isYou;
    }

    @Override
    public void showDialog(Player player, Ability source, Game game, Player opponent) {
        TargetCard choosingTarget = this.target.copy();
        Player choosingPlayer = this.isYou ? player : opponent;

        // make sure hand go first, so user can test diff type of targets
        List<Card> all = new ArrayList<>();
        all.addAll(choosingPlayer.getHand().getCards(game));
        //all.addAll(choosingPlayer.getLibrary().getCards(game));
        Cards choosingCards = new CardsImpl(all.stream().limit(100).collect(Collectors.toList()));

        boolean chooseRes;
        String chooseDebugSource;
        if (this.isTargetChoice) {
            chooseDebugSource = DebugUtil.getMethodNameWithSource(0, "class");
            chooseRes = choosingPlayer.chooseTarget(Outcome.Benefit, choosingCards, choosingTarget, source, game);
        } else {
            chooseDebugSource = DebugUtil.getMethodNameWithSource(0, "class");
            chooseRes = choosingPlayer.choose(Outcome.Benefit, choosingCards, choosingTarget, source, game);
        }

        List<String> res = new ArrayList<>();
        if (chooseRes) {
            Targets.printDebugTargets(getGroup() + " - " + this.getName() + " - " + "TRUE", new Targets(choosingTarget), source, game, res);
        } else {
            Targets.printDebugTargets(getGroup() + " - " + this.getName() + " - " + "FALSE", new Targets(choosingTarget), source, game, res);
        }

        ((TargetTestableResult) this.getResult()).onFinish(chooseDebugSource, chooseRes, res, choosingTarget);
    }

    static public void register(TestableDialogsRunner runner) {
        // test game started with 2 players and 7 cards in hand and 1 draw
        // so it's better to use target limits like 0, 1, 3, 9, max

        FilterCard anyCard = StaticFilters.FILTER_CARD;
        FilterCard impossibleCard = new FilterCard();
        impossibleCard.add(SubType.TROOPER.getPredicate());

        List<Boolean> notTargets = Arrays.asList(false, true);
        List<Boolean> isYous = Arrays.asList(false, true);
        List<Boolean> isTargetChoices = Arrays.asList(false, true);
        for (boolean notTarget : notTargets) {
            for (boolean isYou : isYous) {
                for (boolean isTargetChoice : isTargetChoices) {
                    runner.registerDialog(new ChooseCardsTestableDialog(isTargetChoice, notTarget, isYou, "hand 0, X=0", new TargetCardInHand(0, 0, anyCard)));
                    runner.registerDialog(new ChooseCardsTestableDialog(isTargetChoice, notTarget, isYou, "hand 1", new TargetCardInHand(1, anyCard)));
                    runner.registerDialog(new ChooseCardsTestableDialog(isTargetChoice, notTarget, isYou, "hand 3", new TargetCardInHand(3, anyCard)));
                    runner.registerDialog(new ChooseCardsTestableDialog(isTargetChoice, notTarget, isYou, "hand 9", new TargetCardInHand(9, anyCard)));
                    runner.registerDialog(new ChooseCardsTestableDialog(isTargetChoice, notTarget, isYou, "hand 0-1", new TargetCardInHand(0, 1, anyCard)));
                    runner.registerDialog(new ChooseCardsTestableDialog(isTargetChoice, notTarget, isYou, "hand 0-3", new TargetCardInHand(0, 3, anyCard)));
                    runner.registerDialog(new ChooseCardsTestableDialog(isTargetChoice, notTarget, isYou, "hand 0-9", new TargetCardInHand(0, 9, anyCard)));
                    runner.registerDialog(new ChooseCardsTestableDialog(isTargetChoice, notTarget, isYou, "hand any", new TargetCardInHand(0, Integer.MAX_VALUE, anyCard)));
                    runner.registerDialog(new ChooseCardsTestableDialog(isTargetChoice, notTarget, isYou, "hand 1-3", new TargetCardInHand(1, 3, anyCard)));
                    runner.registerDialog(new ChooseCardsTestableDialog(isTargetChoice, notTarget, isYou, "hand 2-3", new TargetCardInHand(2, 3, anyCard)));
                    runner.registerDialog(new ChooseCardsTestableDialog(isTargetChoice, notTarget, isYou, "hand 2-9", new TargetCardInHand(2, 9, anyCard)));
                    runner.registerDialog(new ChooseCardsTestableDialog(isTargetChoice, notTarget, isYou, "hand 8-9", new TargetCardInHand(8, 9, anyCard)));
                    //
                    runner.registerDialog(new ChooseCardsTestableDialog(isTargetChoice, notTarget, isYou, "impossible 0, X=0", new TargetCardInHand(0, impossibleCard)));
                    runner.registerDialog(new ChooseCardsTestableDialog(isTargetChoice, notTarget, isYou, "impossible 1", new TargetCardInHand(1, impossibleCard)));
                    runner.registerDialog(new ChooseCardsTestableDialog(isTargetChoice, notTarget, isYou, "impossible 3", new TargetCardInHand(3, impossibleCard)));
                    runner.registerDialog(new ChooseCardsTestableDialog(isTargetChoice, notTarget, isYou, "impossible 0-1", new TargetCardInHand(0, 1, impossibleCard)));
                    runner.registerDialog(new ChooseCardsTestableDialog(isTargetChoice, notTarget, isYou, "impossible 0-3", new TargetCardInHand(0, 3, impossibleCard)));
                    runner.registerDialog(new ChooseCardsTestableDialog(isTargetChoice, notTarget, isYou, "impossible any", new TargetCardInHand(0, Integer.MAX_VALUE, impossibleCard)));
                }
            }
        }
    }
}
