package mage.game;

import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ExileZone extends CardsImpl {

    private final UUID id;
    private final String name;
    private boolean cleanupOnEndTurn = false; // moved cards from that zone to default on end of turn (to cleanup exile windows)
    private final Map<UUID, Cards> playerCardMap = new HashMap<>();

    public ExileZone(UUID id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    protected ExileZone(final ExileZone zone) {
        super(zone);
        this.id = zone.id;
        this.name = zone.name;
        this.cleanupOnEndTurn = zone.cleanupOnEndTurn;
        this.playerCardMap.putAll(zone.playerCardMap);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isCleanupOnEndTurn() {
        return cleanupOnEndTurn;
    }

    public void setCleanupOnEndTurn(boolean cleanupOnEndTurn) {
        this.cleanupOnEndTurn = cleanupOnEndTurn;
    }

    public void letPlayerSeeCards(UUID playerId, Card card) {
        letPlayerSeeCards(playerId, new CardsImpl(card));
    }

    public void letPlayerSeeCards(UUID playerId, Cards cards) {
        if (playerId == null || cards == null) {
            return;
        }
        playerCardMap.computeIfAbsent(playerId, k -> new CardsImpl()).addAll(cards);
    }

    public void letPlayerSeeCards(UUID playerId, Set<Card> cards) {
        if (playerId == null || cards == null) {
            return;
        }
        for (Card card : cards) {;
            playerCardMap.computeIfAbsent(playerId, k -> new CardsImpl()).add(card);
        }
    }

    public boolean isPlayerAllowedToSeeCard(UUID playerId, Card card) {
        if (playerId == null || card == null) {
            return false;
        }
        return playerCardMap.getOrDefault(playerId, new CardsImpl()).contains(card.getId());
    }

    public Map<UUID, Cards> getPlayerCardMap() {
        return playerCardMap;
    }

    public void copyCardVisibility(Card card, ExileZone targetZone) {
        for (Map.Entry<UUID, Cards> entry : playerCardMap.entrySet()) {
            Cards cards = entry.getValue();
            if (cards.contains(card.getId())) {
                targetZone.letPlayerSeeCards(entry.getKey(), card);
            }
        }
    }

    @Override
    public boolean remove(Card card) {
        boolean result = super.remove(card);
        if (result) {
            for (Cards cards : playerCardMap.values()) {
                cards.remove(card.getId());
            }
        }
        return result;
    }

    @Override
    public ExileZone copy() {
        return new ExileZone(this);
    }
}
