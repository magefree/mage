

package mage.game;

import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.util.Copyable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author nantuko
 */
public class LookedAt extends HashMap<String, Cards> implements Serializable, Copyable<LookedAt> {

    public LookedAt() {
    }

    protected LookedAt(final LookedAt lookedAt) {
        for (Map.Entry<String, Cards> entry : lookedAt.entrySet()) {
            this.put(entry.getKey(), entry.getValue().copy());
        }
    }

    public void add(String name, Card card) {
        this.createLookedAt(name).add(card);
    }

    public void add(String name, Cards cards) {
        if (!this.containsKey(name)) {
            createLookedAt(name);
        }
        this.put(name, cards.copy());
    }

    public Cards createLookedAt(String name) {
        putIfAbsent(name, new CardsImpl());
        return this.get(name);
    }

    public void reset() {
        this.clear();
    }

    public Card getCard(UUID cardId, Game game) {
        for (Cards cards : this.values()) {
            if (cards.contains(cardId))
                return game.getCard(cardId);
        }
        return null;
    }

    @Override
    public LookedAt copy() {
        return new LookedAt(this);
    }
}
