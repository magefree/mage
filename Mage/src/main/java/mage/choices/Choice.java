

package mage.choices;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public interface Choice {

    String getMessage();
    void setMessage(String message);

    String getSubMessage();
    void setSubMessage(String subMessage);

    void clearChoice();
    boolean isChosen();
    boolean isRequired();

    Choice copy();

    // string choice
    void setChoices(Set<String> choices);
    Set<String> getChoices();
    void setChoice(String choice);
    String getChoice();

    // key-value choice
    boolean isKeyChoice();
    void setKeyChoices(Map<String, String> choices);
    Map<String,String> getKeyChoices();
    void setChoiceByKey(String choiceKey);
    String getChoiceKey();
    String getChoiceValue();

    // search
    boolean isSearchEnabled();
    void setSearchEnabled(boolean isEnabled);
    void setSearchText(String searchText);
    String getSearchText();

    // sorting
    boolean isSortEnabled();
    void setSortData(Map<String, Integer> sortData);
    Map<String, Integer> getSortData();

    // random choice
    void setRandomChoice();
    boolean setChoiceByAnswers(List<String> answers, boolean removeSelectAnswerFromList);
}
