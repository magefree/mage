package mage.cards.decks;

import mage.cards.Card;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class DeckValidator implements Serializable {

    protected String name;
    protected String shortName;
    protected List<DeckValidatorError> errorsList = new ArrayList<>();

    public DeckValidator(String name) {
        setName(name);
    }

    public DeckValidator(String name, String shortName) {
        setName(name, shortName);
    }

    public abstract boolean validate(Deck deck);

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    protected void setName(String name) {
        this.name = name;
        this.shortName = name.contains("-") ? name.substring(name.indexOf("-") + 1).trim() : name;
    }

    protected void setName(String name, String shortName) {
        this.name = name;
        this.shortName = shortName;
    }

    protected void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public List<DeckValidatorError> getErrorsList() {
        return this.errorsList;
    }

    /**
     * Get errors list sorted by error type and texts
     *
     * @return
     */
    public List<DeckValidatorError> getErrorsListSorted() {
        List<DeckValidatorError> list = new ArrayList<>(this.getErrorsList());

        Collections.sort(list, new Comparator<DeckValidatorError>() {
            @Override
            public int compare(DeckValidatorError e1, DeckValidatorError e2) {
                int res = 0;

                // sort by error type
                Integer order1 = e1.getErrorType().getSortOrder();
                Integer order2 = e2.getErrorType().getSortOrder();
                res = order2.compareTo(order1);

                // sort by group
                if (res != 0) {
                    res = e2.getGroup().compareTo(e1.getGroup());
                }

                // sort by message
                if (res != 0) {
                    res = e2.getMessage().compareTo(e1.getMessage());
                }

                return res;
            }
        });

        return list;
    }

    public String getErrorsListInfo() {
        // for tests
        return this.errorsList.stream()
                .map(e -> e.getGroup() + "=" + e.getMessage())
                .collect(Collectors.joining(", "));
    }

    public void addError(DeckValidatorErrorType errorType, String group, String message) {
        this.errorsList.add(new DeckValidatorError(errorType, group, message));
    }

    public boolean errorsListContainsGroup(String group) {
        return this.errorsList.stream().anyMatch(e -> e.getGroup().equals(group));
    }

    public boolean isPartlyValid() {
        return errorsList.size() == 0 || !errorsList.stream().anyMatch(e -> !e.getErrorType().isPartlyLegal());
    }

    protected void countCards(Map<String, Integer> counts, Collection<Card> cards) {
        for (Card card : cards) {
            if (counts.containsKey(card.getName())) {
                counts.put(card.getName(), counts.get(card.getName()) + 1);
            } else {
                counts.put(card.getName(), 1);
            }
        }
    }

    public int getEdhPowerLevel(Deck deck) {
        return 0;
    }

    public abstract int getDeckMinSize();

    public abstract int getSideboardMinSize();
}
