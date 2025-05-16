package mage.utils.testers;

import mage.abilities.Ability;
import mage.choices.Choice;
import mage.choices.ChoiceHintType;
import mage.choices.ChoiceImpl;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Part of testable game dialogs
 * <p>
 * Helper class to create additional options in cheat menu - allow to test any game dialogs with any settings
 * Allow to call game dialogs from you or from your opponent, e.g. from AI player
 * <p>
 * All existing human's choose dialogs (search by waitForResponse):
 * <p>
 * Support of single dialogs (can be called inside effects):
 * [x] choose(target)
 * [x] choose(cards)
 * [x] choose(choice)
 * [x] chooseTarget(target)
 * [x] chooseTarget(cards)
 * [x] chooseTargetAmount
 * [x] chooseUse
 * [x] choosePile
 * [x] announceX
 * [ ] getAmount // TODO: implement
 * [ ] getMultiAmountWithIndividualConstraints // TODO: implement
 * <p>
 * Support of priority dialogs (can be called by game engine, some can be implemented in theory):
 * --- priority
 * --- playManaHandling
 * --- activateSpecialAction
 * --- activateAbility
 * --- chooseAbilityForCast
 * --- chooseLandOrSpellAbility
 * --- chooseMode
 * --- chooseMulligan
 * --- chooseReplacementEffect
 * --- chooseTriggeredAbility
 * --- selectAttackers
 * --- selectBlockers
 * --- selectCombatGroup (part of selectBlockers)
 * <p>
 * Support of outdated dialogs (not used anymore)
 * --- announceRepetitions (part of removed macro feature)
 *
 * @author JayDi85
 */
public class TestableDialogsRunner {

    private final List<TestableDialog> dialogs = new ArrayList<>();

    static final int LAST_SELECTED_GROUP_ID = 997;
    static final int LAST_SELECTED_DIALOG_ID = 998;

    // for better UX - save last selected options, so can return to it later
    // it's ok to have it global, cause test mode for local and single user environment
    static String lastSelectedGroup = null;
    static TestableDialog lastSelectedDialog = null;


    public TestableDialogsRunner() {
        ChooseTargetTestableDialog.register(this);
        ChooseCardsTestableDialog.register(this);
        ChooseUseTestableDialog.register(this);
        ChooseChoiceTestableDialog.register(this);
        ChoosePileTestableDialog.register(this);
        ChooseAmountTestableDialog.register(this);
        AnnounceXTestableDialog.register(this);
    }

    void registerDialog(TestableDialog dialog) {
        this.dialogs.add(dialog);
    }

    public void selectAndShowTestableDialog(Player player, Ability source, Game game, Player opponent) {
        // select group or fast links
        List<String> groups = this.dialogs.stream()
                .map(TestableDialog::getGroup)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        Choice choice = prepareSelectGroupChoice(groups);
        player.choose(Outcome.Benefit, choice, game);
        String needGroup = null;
        TestableDialog needDialog = null;
        if (choice.getChoiceKey() != null) {
            int needIndex = Integer.parseInt(choice.getChoiceKey());
            if (needIndex == LAST_SELECTED_GROUP_ID && lastSelectedGroup != null) {
                // fast link to group
                needGroup = lastSelectedGroup;
            } else if (needIndex == LAST_SELECTED_DIALOG_ID && lastSelectedDialog != null) {
                // fast link to dialog
                needGroup = lastSelectedDialog.getGroup();
                needDialog = lastSelectedDialog;
            } else if (needIndex < groups.size()) {
                // group
                needGroup = groups.get(needIndex);
            }
        }
        if (needGroup == null) {
            return;
        }

        // select dialog
        if (needDialog == null) {
            choice = prepareSelectDialogChoice(needGroup);
            player.choose(Outcome.Benefit, choice, game);
            if (choice.getChoiceKey() != null) {
                int needIndex = Integer.parseInt(choice.getChoiceKey());
                if (needIndex < this.dialogs.size()) {
                    needDialog = this.dialogs.get(needIndex);
                }
            }
        }
        if (needDialog == null) {
            return;
        }

        // all fine, can show it and finish
        lastSelectedGroup = needGroup;
        lastSelectedDialog = needDialog;
        List<String> resInfo = needDialog.showDialog(player, source, game, opponent);
        needDialog.showResult(player, game, String.join("<br>", resInfo));
    }

    private Choice prepareSelectGroupChoice(List<String> groups) {
        // try to choose group or fast links

        Choice choice = new ChoiceImpl(false);
        choice.setMessage("Choose dialogs group to run");

        // main groups
        int recNumber = 0;
        for (int i = 0; i < groups.size(); i++) {
            recNumber++;
            String group = groups.get(i);
            choice.withItem(
                    String.valueOf(i),
                    String.format("%02d. %s", recNumber, group),
                    recNumber,
                    ChoiceHintType.TEXT,
                    String.join("<br>", group)
            );
        }

        // fast link to last group
        String lastGroupInfo = String.format(" -> last group: %s", lastSelectedGroup == null ? "not used" : lastSelectedGroup);
        choice.withItem(
                String.valueOf(LAST_SELECTED_GROUP_ID),
                lastGroupInfo,
                -2,
                ChoiceHintType.TEXT,
                lastGroupInfo
        );

        // fast link to last dialog
        String lastDialogName = (lastSelectedDialog == null ? "not used" : String.format("%s - %s",
                lastSelectedDialog.getName(), lastSelectedDialog.getDescription()));
        String lastDialogInfo = String.format(" -> last dialog: %s", lastDialogName);
        choice.withItem(
                String.valueOf(LAST_SELECTED_DIALOG_ID),
                lastDialogInfo,
                -1,
                ChoiceHintType.TEXT,
                lastDialogInfo
        );

        return choice;
    }

    private Choice prepareSelectDialogChoice(String needGroup) {
        Choice choice = new ChoiceImpl(false);
        choice.setMessage("Choose game dialog to run from " + needGroup);
        int recNumber = 0;
        for (int i = 0; i < this.dialogs.size(); i++) {
            TestableDialog dialog = this.dialogs.get(i);
            if (!dialog.getGroup().equals(needGroup)) {
                continue;
            }
            recNumber++;
            String info = String.format("%s - %s - %s", dialog.getGroup(), dialog.getName(), dialog.getDescription());
            choice.withItem(
                    String.valueOf(i),
                    String.format("%02d. %s", recNumber, info),
                    recNumber,
                    ChoiceHintType.TEXT,
                    String.join("<br>", info)
            );
        }
        return choice;
    }
}

