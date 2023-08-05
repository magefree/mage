package mage.game;

import mage.cards.CardsImpl;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ExileZone extends CardsImpl {

    private UUID id;
    private String name;
    private boolean hidden;
    private boolean cleanupOnEndTurn = false; // moved cards from that zone to default on end of turn (to cleanup exile windows)

    public ExileZone(UUID id, String name) {
        this(id, name, false);
    }

    public ExileZone(UUID id, String name, boolean hidden) {
        this(id, name, false, false);
    }

    public ExileZone(UUID id, String name, boolean hidden, boolean cleanupOnEndTurn) {
        super();
        this.id = id;
        this.name = name;
        this.hidden = hidden;
        this.cleanupOnEndTurn = cleanupOnEndTurn;
    }

    protected ExileZone(final ExileZone zone) {
        super(zone);
        this.id = zone.id;
        this.name = zone.name;
        this.hidden = zone.hidden;
        this.cleanupOnEndTurn = zone.cleanupOnEndTurn;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isHidden() {
        return hidden;
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
