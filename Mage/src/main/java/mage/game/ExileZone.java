

package mage.game;

import java.util.UUID;

import mage.cards.CardsImpl;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ExileZone extends CardsImpl {

    private UUID id;
    private String name;
    private boolean hidden;

    public ExileZone(UUID id, String name) {
        this(id, name, false);
    }

    public ExileZone(UUID id, String name, boolean hidden) {
        super();
        this.id = id;
        this.name = name;
        this.hidden = hidden;
    }

    public ExileZone(final ExileZone zone) {
        super(zone);
        this.id = zone.id;
        this.name = zone.name;
        this.hidden = zone.hidden;
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

    @Override
    public ExileZone copy() {
        return new ExileZone(this);
    }
}
