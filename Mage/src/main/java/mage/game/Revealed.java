

package mage.game;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.util.Copyable;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class Revealed extends HashMap<String, Cards> implements Serializable, Copyable<Revealed> {

    public Revealed() {
    }

    protected Revealed(final Revealed revealed) {
        for (Map.Entry<String, Cards> entry : revealed.entrySet()) {
            this.put(entry.getKey(), entry.getValue().copy());
        }
    }

    public void add(String name, Card card) {
        this.get(name).add(card);
    }

    public void update(String name, Cards cards) {
        this.put(name, cards.copy());
    }

    public void add(String name, Cards cards) {
        if (this.containsKey(name)) {
            this.get(name).addAll(cards);
        } else {
            this.put(name, cards.copy());
        }
    }

    public Cards createRevealed(String name) {
        putIfAbsent(name, new CardsImpl());
        return this.get(name);
    }

    public Cards getRevealed(String name) {
        return this.get(name);
    }

    public void reset() {
        this.clear();
    }

    public Card getCard(UUID cardId, Game game) {
        for (Cards cards : this.values()) {
            if (cards.contains(cardId)) {
                return game.getCard(cardId);
            }
        }
        return null;
    }

    @Override
    public Revealed copy() {
        return new Revealed(this);
    }
}
