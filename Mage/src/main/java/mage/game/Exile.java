
package mage.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import mage.cards.Card;
import mage.util.Copyable;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Exile implements Serializable, Copyable<Exile> {

    private static final UUID PERMANENT = UUID.randomUUID();

    private Map<UUID, ExileZone> exileZones = new HashMap<>();

    public Exile() {
        createZone(PERMANENT, "Permanent");
    }

    public Exile(final Exile exile) {
        for (Entry<UUID, ExileZone> entry : exile.exileZones.entrySet()) {
            exileZones.put(entry.getKey(), entry.getValue().copy());
        }
    }

    public Collection<ExileZone> getExileZones() {
        return exileZones.values();
    }

    public ExileZone getPermanentExile() {
        return exileZones.get(PERMANENT);
    }

    public void add(UUID id, String name, Card card) {
        createZone(id, name).add(card);
    }

    public void add(Card card) {
        exileZones.get(PERMANENT).add(card);
    }

    public ExileZone createZone(UUID id, String name) {
        return createZone(id, name + " - Exile", false);
    }

    private ExileZone createZone(UUID id, String name, boolean hidden) {
        exileZones.putIfAbsent(id, new ExileZone(id, name, hidden));
        return exileZones.get(id);
    }

    public ExileZone getExileZone(UUID id) {
        return exileZones.get(id);
    }

    public Card getCard(UUID cardId, Game game) {
        for (ExileZone exile : exileZones.values()) {
            if (exile.contains(cardId)) {
                return game.getCard(cardId);
            }
        }
        return null;
    }

    public List<Card> getAllCards(Game game) {
        List<Card> cards = new ArrayList<>();
        for (ExileZone exile : exileZones.values()) {
            cards.addAll(exile.getCards(game));
        }
        return cards;
    }

    public boolean removeCard(Card card, Game game) {
        for (ExileZone exile : exileZones.values()) {
            if (exile.contains(card.getId())) {
                return exile.remove(card.getId());
            }
        }
        return false;
    }

    @Override
    public Exile copy() {
        return new Exile(this);
    }

    public boolean containsId(UUID cardId, Game game) {
        for (Card card : getAllCards(game)) {
            if (card.getId().equals(cardId)) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        for (ExileZone exile : exileZones.values()) {
            exile.clear();
        }
    }
}
