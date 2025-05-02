package mage.choices;

import mage.game.Game;
import mage.util.CardUtil;
import mage.util.RandomUtil;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Game's choose dialog to select one item from a list
 * <p>
 * Support GUI related features like headers, sorting, tooltips/popups
 *
 * @author JayDi85
 */
public class ChoiceImpl implements Choice {

    private static final Logger logger = Logger.getLogger(Choice.class);

    protected boolean chosenNormal;
    protected boolean chosenSpecial;
    protected boolean required;
    protected String choice;
    protected String choiceKey;
    protected Set<String> choices = new LinkedHashSet<>();
    protected Map<String, String> keyChoices = new LinkedHashMap<>();
    protected Map<String, Integer> sortData = new LinkedHashMap<>();
    protected Map<String, List<String>> hintData = new LinkedHashMap<>(); // value -> [type, hint]
    protected String message;
    protected String subMessage;
    protected boolean searchEnabled = true; // enable for all windows by default
    protected String searchText;
    protected ChoiceHintType hintType;

    // special button with #-answer
    // warning, only for human's GUI, not AI
    protected boolean specialEnabled = false;
    protected boolean specialCanBeEmpty = false; // enable if you want to select "nothing", but not cancel
    protected String specialText = "";
    protected String specialHint = "";

    protected boolean manaColorChoice = false; // set true to allow automatic choosing with Outcome.PutManaInPool

    public ChoiceImpl(boolean required) {
        this(required, ChoiceHintType.TEXT);
    }

    /**
     * @param hintType enable card popup hint (each value will be used as card/dungeon name)
     */
    public ChoiceImpl(boolean required, ChoiceHintType hintType) {
        this.required = required;
        this.hintType = hintType;
    }

    protected ChoiceImpl(final ChoiceImpl choice) {
        this.choice = choice.choice;
        this.chosenNormal = choice.chosenNormal;
        this.chosenSpecial = choice.chosenSpecial;
        this.required = choice.required;
        this.message = choice.message;
        this.subMessage = choice.subMessage;
        this.searchEnabled = choice.searchEnabled;
        this.searchText = choice.searchText;
        this.hintType = choice.hintType;
        this.choices.addAll(choice.choices);
        this.choiceKey = choice.choiceKey;
        this.keyChoices = choice.keyChoices; // list should never change for the same object so copy by reference TODO: check errors with that, it that ok? Color list is static
        this.sortData = choice.sortData;
        this.specialEnabled = choice.specialEnabled;
        this.specialCanBeEmpty = choice.specialCanBeEmpty;
        this.specialText = choice.specialText;
        this.specialHint = choice.specialHint;
        this.manaColorChoice = choice.manaColorChoice;
    }

    @Override
    public boolean isChosen() {
        return chosenNormal || chosenSpecial;
    }

    @Override
    public boolean isChosenSpecial() {
        return chosenSpecial;
    }

    @Override
    public void clearChoice() {
        this.choice = null;
        this.choiceKey = null;
        this.chosenNormal = false;
        this.chosenSpecial = false;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getSubMessage() {
        return subMessage;
    }

    @Override
    public void setSubMessage(String subMessage) {
        this.subMessage = subMessage;
    }

    @Override
    public Set<String> getChoices() {
        return choices;
    }

    @Override
    public void setChoices(Set<String> choices) {
        CardUtil.checkSetParamForSerializationCompatibility(choices);
        this.choices = choices;
        protectFromEmptyChoices();
    }

    @Override
    public String getChoice() {
        return choice;
    }

    @Override
    public void setChoice(String choice, boolean isSpecial) {
        if (choices.contains(choice)) {
            this.choice = choice;
            this.chosenNormal = true;
            this.chosenSpecial = isSpecial;
        }

        if (isSpecial && this.specialCanBeEmpty && (choice == null || choice.isEmpty())) {
            clearChoice();
            this.chosenNormal = false;
            this.chosenSpecial = true;
        }
    }

    @Override
    public boolean isRequired() {
        return this.required;
    }

    @Override
    public ChoiceImpl copy() {
        return new ChoiceImpl(this);
    }

    @Override
    public Map<String, String> getKeyChoices() {
        return keyChoices;
    }

    @Override
    public void setKeyChoices(Map<String, String> choices) {
        keyChoices = choices;
        protectFromEmptyChoices();
    }

    @Override
    public String getChoiceKey() {
        return choiceKey;
    }

    @Override
    public String getChoiceValue() {
        if ((keyChoices != null) && (keyChoices.containsKey(choiceKey))) {
            return keyChoices.get(choiceKey);
        } else {
            return null;
        }
    }

    @Override
    public void setChoiceByKey(String choiceKey, boolean isSpecial) {
        String choiceToSet = keyChoices.get(choiceKey);
        if (choiceToSet != null) {
            this.choice = choiceToSet;
            this.choiceKey = choiceKey;
            this.chosenNormal = true;
            this.chosenSpecial = isSpecial;
        }

        if (isSpecial && this.specialCanBeEmpty && (choiceKey == null || choiceKey.isEmpty())) {
            clearChoice();
            this.chosenNormal = false;
            this.chosenSpecial = true;
        }
    }

    @Override
    public boolean isKeyChoice() {
        return !keyChoices.isEmpty();
    }

    @Override
    public boolean isSearchEnabled() {
        return this.searchEnabled;
    }

    @Override
    public void setSearchEnabled(boolean isEnabled) {
        this.searchEnabled = isEnabled;
    }

    @Override
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    @Override
    public String getSearchText() {
        return this.searchText;
    }

    @Override
    public boolean isSortEnabled() {
        return (this.sortData != null) && !this.sortData.isEmpty();
    }

    @Override
    public void setSortData(Map<String, Integer> sortData) {
        this.sortData = sortData;
    }

    @Override
    public Map<String, Integer> getSortData() {
        return this.sortData;
    }

    @Override
    public Map<String, List<String>> getHintData() {
        return this.hintData;
    }

    @Override
    public Choice withItem(String key, String value, Integer sort, ChoiceHintType hintType, String hintValue) {
        this.keyChoices.put(key, value);
        if (sort != null) {
            this.sortData.put(key, sort);
        }
        if (hintType != null) {
            this.hintData.put(key, Arrays.asList(hintType.toString(), hintValue));
        }
        return this;
    }

    @Override
    public void setHintData(Map<String, List<String>> hintData) {
        // runtime check
        hintData.forEach((key, info) -> {
            try {
                ChoiceHintType hintType = ChoiceHintType.valueOf(info.get(0));
                if (hintType == ChoiceHintType.GAME_OBJECT) {
                    UUID objectId = UUID.fromString(info.get(1));
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Wrong code usage: hints info must contains valid data, but found - " + info);
            }
        });

        this.hintData = hintData;
    }

    @Override
    public void setRandomChoice() {

        if (this.isKeyChoice()) {
            // key mode
            String[] vals = this.getKeyChoices().keySet().toArray(new String[0]);
            if (vals.length > 0) {
                int choiceNum = RandomUtil.nextInt(vals.length);
                this.setChoiceByKey(vals[choiceNum], false);
            }
        } else {
            // string mode
            String[] vals = this.getChoices().toArray(new String[0]);
            if (vals.length > 0) {
                int choiceNum = RandomUtil.nextInt(vals.length);
                this.setChoice(vals[choiceNum], false);
            }
        }
    }

    @Override
    public boolean setChoiceByAnswers(List<String> answers, boolean removeSelectAnswerFromList) {
        // select by answers
        if (this.isKeyChoice()) {
            // keys mode
            for (String needChoice : answers) {
                for (Map.Entry<String, String> currentChoice : this.getKeyChoices().entrySet()) {
                    if (currentChoice.getKey().equals(needChoice)) {
                        if (removeSelectAnswerFromList) {
                            this.setChoiceByKey(needChoice, false);
                            answers.remove(needChoice);
                        }
                        return true;
                    }

                }
            }
            // no key answer found, try to match by text starting with
            for (String needChoice : answers) {
                for (Map.Entry<String, String> currentChoice : this.getKeyChoices().entrySet()) {
                    String choiceValue = currentChoice.getValue();
                    // Clean any html part (for easier unit test matching)
                    String cleanedChoiceValue = choiceValue.replaceAll("<[^<>]*>", "");
                    if (choiceValue.startsWith(needChoice) || cleanedChoiceValue.startsWith(needChoice)) {
                        if (removeSelectAnswerFromList) {
                            this.setChoiceByKey(currentChoice.getKey(), false);
                            answers.remove(needChoice);
                        }
                        return true;
                    }
                }
            }
        } else {
            // string mode
            for (String needChoice : answers) {
                for (String currentChoice : this.getChoices()) {
                    // Clean any html part (for easier unit test matching)
                    String cleanedChoiceValue = currentChoice.replaceAll("<[^<>]*>", "");
                    if (currentChoice.equals(needChoice) || cleanedChoiceValue.equals(needChoice)) {
                        if (removeSelectAnswerFromList) {
                            this.setChoice(needChoice, false);
                            answers.remove(needChoice);
                        }
                        return true;
                    }
                }
            }
        }
        return false; // can't find answer
    }

    @Override
    public void setSpecial(boolean enabled, boolean canBeEmpty, String text, String hint) {
        this.specialEnabled = enabled;
        this.specialCanBeEmpty = canBeEmpty;
        this.specialText = text;
        this.specialHint = hint;
    }

    @Override
    public boolean isSpecialEnabled() {
        return this.specialEnabled;
    }

    @Override
    public boolean isSpecialCanBeEmpty() {
        return this.specialCanBeEmpty;
    }

    @Override
    public String getSpecialText() {
        return this.specialText;
    }

    @Override
    public String getSpecialHint() {
        return this.specialHint;
    }

    @Override
    public ChoiceHintType getHintType() {
        return this.hintType;
    }

    @Override
    public boolean isManaColorChoice() {
        return manaColorChoice;
    }

    @Override
    public ChoiceImpl setManaColorChoice(boolean manaColorChoice) {
        this.manaColorChoice = manaColorChoice;
        return this;
    }

    private void protectFromEmptyChoices() {
        // if there are no choices then required must be disabled to allow user to close a dialog
        // example: database error on too low memory, see Brain Pry and 500 Mb server

        // normal situation
        if (!this.required) {
            return;
        }

        // special checkbox can allow empty choices
        if (this.specialEnabled && this.specialCanBeEmpty) {
            return;
        }

        if (this.choices.isEmpty() && this.keyChoices.isEmpty()) {
            // it can be a server problems or wrong card code
            this.required = false;
            logger.error("Empty choice dialog in " + this.getClass().getCanonicalName(), new Throwable());
        }
    }

    @Override
    public void onChooseStart(Game game, UUID choosingPlayerId) {
        // nothing to do
    }

    @Override
    public void onChooseEnd(Game game, UUID choosingPlayerId, String choiceResult) {
        // nothing to do
    }
}
