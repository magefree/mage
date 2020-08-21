package mage.choices;

import mage.util.RandomUtil;

import java.io.Serializable;
import java.util.*;

/**
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class ChoiceImpl implements Choice, Serializable {

    protected boolean chosen;
    protected final boolean required;
    protected String choice;
    protected String choiceKey;
    protected Set<String> choices = new LinkedHashSet<>();
    protected Map<String, String> keyChoices = new LinkedHashMap<>();
    protected Map<String, Integer> sortData = new LinkedHashMap<>();
    protected String message;
    protected String subMessage;
    protected boolean searchEnabled = true; // enable for all windows by default
    protected String searchText;

    public ChoiceImpl() {
        this(false);
    }

    public ChoiceImpl(boolean required) {
        this.required = required;
    }

    public ChoiceImpl(ChoiceImpl choice) {
        this.choice = choice.choice;
        this.chosen = choice.chosen;
        this.required = choice.required;
        this.message = choice.message;
        this.subMessage = choice.subMessage;
        this.searchEnabled = choice.searchEnabled;
        this.searchText = choice.searchText;
        this.choices.addAll(choice.choices);
        this.choiceKey = choice.choiceKey;
        this.keyChoices = choice.keyChoices; // list should never change for the same object so copy by reference TODO: check errors with that, it that ok? Color list is static
        this.sortData = choice.sortData;
    }

    @Override
    public boolean isChosen() {
        return chosen;
    }

    @Override
    public void clearChoice() {
        choice = null;
        choiceKey = null;
        chosen = false;
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
        this.choices = choices;
    }

    @Override
    public String getChoice() {
        return choice;
    }

    @Override
    public void setChoice(String choice) {
        if (choices.contains(choice)) {
            this.choice = choice;
            this.chosen = true;
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
    public void setChoiceByKey(String choiceKey) {
        String choiceToSet = keyChoices.get(choiceKey);
        if (choiceToSet != null) {
            this.choice = choiceToSet;
            this.choiceKey = choiceKey;
            this.chosen = true;
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
    public void setRandomChoice() {

        if (this.isKeyChoice()) {
            // key mode
            String[] vals = this.getKeyChoices().keySet().toArray(new String[0]);
            if (vals.length > 0) {
                int choiceNum = RandomUtil.nextInt(vals.length);
                this.setChoiceByKey(vals[choiceNum]);
            }
        } else {
            // string mode
            String[] vals = this.getChoices().toArray(new String[0]);
            if (vals.length > 0) {
                int choiceNum = RandomUtil.nextInt(vals.length);
                this.setChoice(vals[choiceNum]);
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
                        this.setChoiceByKey(needChoice);
                        answers.remove(needChoice);
                        return true;
                    }

                }
            }
            // no key answer found, try to macht by text starting with
            for (String needChoice : answers) {
                for (Map.Entry<String, String> currentChoice : this.getKeyChoices().entrySet()) {
                    if (currentChoice.getValue().startsWith(needChoice)) {
                        this.setChoiceByKey(currentChoice.getKey());
                        answers.remove(needChoice);
                        return true;
                    }
                }
            }
        } else {
            // string mode
            for (String needChoice : answers) {
                for (String currentChoice : this.getChoices()) {
                    if (currentChoice.equals(needChoice)) {
                        this.setChoice(needChoice);
                        answers.remove(needChoice);
                        return true;
                    }
                }
            }
        }
        return false; // can't find answer
    }
}
