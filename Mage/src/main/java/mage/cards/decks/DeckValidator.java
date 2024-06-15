package mage.cards.decks;

import mage.cards.Card;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class DeckValidator implements Serializable {

    public static final HashSet<String> MAIN_BASIC_LAND_NAMES = new HashSet<>(Arrays.asList(
            "Plains",
            "Island",
            "Swamp",
            "Mountain",
            "Forest"
    ));

    public static final HashSet<String> ADDITIONAL_BASIC_LAND_NAMES = new HashSet<>(Arrays.asList(
            "Wastes",
            "Snow-Covered Plains",
            "Snow-Covered Island",
            "Snow-Covered Swamp",
            "Snow-Covered Mountain",
            "Snow-Covered Forest",
            "Snow-Covered Wastes"
    ));

    public static final HashSet<String> ALL_BASIC_LAND_NAMES = new HashSet<>();
    {
        ALL_BASIC_LAND_NAMES.addAll(MAIN_BASIC_LAND_NAMES);
        ALL_BASIC_LAND_NAMES.addAll(ADDITIONAL_BASIC_LAND_NAMES);
    }

    protected static final Map<String, Integer> maxCopiesMap = new HashMap<>();

    static {
        MAIN_BASIC_LAND_NAMES.forEach(s -> maxCopiesMap.put(s, Integer.MAX_VALUE));
        ADDITIONAL_BASIC_LAND_NAMES.forEach(s -> maxCopiesMap.put(s, Integer.MAX_VALUE));
        maxCopiesMap.put("Relentless Rats", Integer.MAX_VALUE);
        maxCopiesMap.put("Shadowborn Apostle", Integer.MAX_VALUE);
        maxCopiesMap.put("Rat Colony", Integer.MAX_VALUE);
        maxCopiesMap.put("Persistent Petitioners", Integer.MAX_VALUE);
        maxCopiesMap.put("Dragon's Approach", Integer.MAX_VALUE);
        maxCopiesMap.put("Slime Against Humanity", Integer.MAX_VALUE);
        maxCopiesMap.put("Once More with Feeling", 1);
        maxCopiesMap.put("Seven Dwarves", 7);
        maxCopiesMap.put("Nazgul", 9);
    }

    protected String name;
    protected String shortName;
    protected List<DeckValidatorError> errorsList = new ArrayList<>();

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

    protected void setName(String name, String shortName) {
        this.name = name;
        if (shortName != null) {
            this.shortName = shortName;
        } else {
            this.shortName = name.contains("-") ? name.substring(name.indexOf("-") + 1).trim() : name;
        }
    }

    protected void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public List<DeckValidatorError> getErrorsList() {
        return this.errorsList;
    }

    public List<DeckValidatorError> getErrorsListSorted() {
        return getErrorsListSorted(Integer.MAX_VALUE);
    }

    /**
     * Get errors list sorted by error type and texts
     *
     * @return
     */
    public List<DeckValidatorError> getErrorsListSorted(int maxErrors) {
        List<DeckValidatorError> list = new ArrayList<>(this.getErrorsList());

        list.sort(new Comparator<DeckValidatorError>() {
            @Override
            public int compare(DeckValidatorError e1, DeckValidatorError e2) {
                int res = 0;

                // sort by error type
                Integer order1 = e1.getErrorType().getSortOrder();
                Integer order2 = e2.getErrorType().getSortOrder();
                res = order1.compareTo(order2);

                // sort by group
                if (res == 0) {
                    res = e1.getGroup().compareTo(e2.getGroup());
                }

                // sort by message
                if (res == 0) {
                    res = e1.getMessage().compareTo(e2.getMessage());
                }

                return res;
            }
        });

        if (list.size() <= maxErrors) {
            return list;
        } else {
            int otherErrorsCount = list.size() - maxErrors;
            list = list.stream().limit(maxErrors).collect(Collectors.toList());
            list.add(new DeckValidatorError(DeckValidatorErrorType.OTHER, "...",
                    "and more " + otherErrorsCount + " error" + (otherErrorsCount > 1 ? "s" : ""), null));
        }

        return list;
    }

    public String getErrorsListInfo() {
        // for tests
        return this.errorsList.stream()
                .map(e -> e.getGroup() + "=" + e.getMessage())
                .collect(Collectors.joining(", "));
    }

    /**
     * @param isCardError group contains card name that can be selected as wrong card
     */
    public void addError(DeckValidatorErrorType errorType, String group, String message, boolean isCardError) {
        addError(errorType, group, message, (isCardError ? group : null));
    }

    public void addError(DeckValidatorErrorType errorType, String group, String message) {
        addError(errorType, group, message, null);
    }

    private void addError(DeckValidatorErrorType errorType, String group, String message, String cardName) {
        this.errorsList.add(new DeckValidatorError(errorType, group, message, cardName));
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

    protected static final int getMaxCopies(String name, int defaultAmount) {
        return maxCopiesMap.getOrDefault(name, defaultAmount);
    }
}
