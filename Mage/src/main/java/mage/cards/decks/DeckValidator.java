package mage.cards.decks;

import mage.cards.Card;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class DeckValidator implements Serializable {

    protected String name;
    protected String shortName;

    protected Map<String, String> invalid = new HashMap<>();

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

    public Map<String, String> getInvalid() {
        return invalid;
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
