package mage.choices;

import mage.game.Game;
import mage.players.Player;
import mage.util.Copyable;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

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

    /**
     * For AI, mana auto-payment from choose color dialogs
     */
    boolean isManaColorChoice();

    Choice setManaColorChoice(boolean manaColorChoice);

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

    // custom hints
    void setHintData(Map<String, List<String>> hintData);

    Map<String, List<String>> getHintData();

    // builder

    /**
     * Fast add single key item. Use null value to ignore sort or hint data
     */
    Choice withItem(String key, String value, Integer sort, ChoiceHintType hintType, String hintValue);

    // random choice (for AI usage)
    void setRandomChoice();

    boolean setChoiceByAnswers(List<String> answers, boolean removeSelectAnswerFromList);

    /**
     * Run additional code before player start to choose (example: add info and hints for choosing player)
     */
    void onChooseStart(Game game, UUID choosingPlayerId);

    void onChooseEnd(Game game, UUID choosingPlayerId, String choiceResult);
}
