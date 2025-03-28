package mage.game;

import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;

import java.util.UUID;
import java.util.Map;
import java.util.HashMap;

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

    public boolean isPlayerAllowedToSeeCard(UUID playerId, Card card) {
        if (playerId == null || card == null) {
            return false;
        }
        return playerCardMap.getOrDefault(playerId, new CardsImpl()).contains(card.getId());
    }

    public Map<UUID, Cards> getPlayerCardMap() {
        return playerCardMap;
    }

    @Override
    public ExileZone copy() {
        return new ExileZone(this);
    }
}
