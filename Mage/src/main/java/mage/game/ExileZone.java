package mage.game;

import mage.cards.CardsImpl;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ExileZone extends CardsImpl {

    private final UUID id;
    private final String name;
    private boolean cleanupOnEndTurn = false; // moved cards from that zone to default on end of turn (to cleanup exile windows)

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

    @Override
    public ExileZone copy() {
        return new ExileZone(this);
    }
}
