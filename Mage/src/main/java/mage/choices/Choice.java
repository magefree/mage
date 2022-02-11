package mage.choices;

import mage.util.Copyable;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public interface Choice extends Serializable, Copyable<Choice> {

    String getMessage();

    void setMessage(String message);

    String getSubMessage();

    void setSubMessage(String subMessage);

    void clearChoice();

    boolean isChosen();

    boolean isChosenSpecial();

    boolean isRequired();

    // special mode for all choices
    void setSpecial(boolean enabled, boolean canBeEmpty, String text, String hint);

    boolean isSpecialEnabled();

    boolean isSpecialCanBeEmpty();

    String getSpecialText();

    String getSpecialHint();

    ChoiceHintType getHintType();

    // string choice
    void setChoices(Set<String> choices);

    Set<String> getChoices();

    void setChoice(String choice, boolean isSpecial);

    String getChoice();

    default void setChoice(String choice) {
        setChoice(choice, false);
    }

    // key-value choice
    boolean isKeyChoice();

    void setKeyChoices(Map<String, String> choices);

    Map<String, String> getKeyChoices();

    void setChoiceByKey(String choiceKey, boolean isSpecial);

    String getChoiceKey();

    String getChoiceValue();

    default void setChoiceByKey(String choiceKey) {
        setChoiceByKey(choiceKey, false);
    }

    // search
    boolean isSearchEnabled();

    void setSearchEnabled(boolean isEnabled);

    void setSearchText(String searchText);

    String getSearchText();

    // sorting
    boolean isSortEnabled();

    void setSortData(Map<String, Integer> sortData);

    Map<String, Integer> getSortData();

    // random choice (for AI usage)
    void setRandomChoice();

    boolean setChoiceByAnswers(List<String> answers, boolean removeSelectAnswerFromList);
}
