
package mage.cards.decks;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import mage.cards.Card;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class DeckValidator implements Serializable {

    protected String name;

    protected Map<String, String> invalid = new HashMap<>();

    public DeckValidator(String name) {
        this.name = name;
    }

    public abstract boolean validate(Deck deck);

    public String getName() {
        return name;
    }

    public Map<String, String> getInvalid() {
        return invalid;
    }

    protected void countCards(Map<String, Integer> counts, Collection<Card> cards) {
        for (Card card: cards) {
            if (counts.containsKey(card.getName())) {
                counts.put(card.getName(), counts.get(card.getName()) + 1);
            }
            else {
                counts.put(card.getName(), 1);
            }
        }
    }

    public int getEdhPowerLevel(Deck deck) {
        return 0;
    }
}
