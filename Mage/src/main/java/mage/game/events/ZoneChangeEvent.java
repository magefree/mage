package mage.game.events;

import java.util.List;
import java.util.UUID;

import mage.constants.Zone;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ZoneChangeEvent extends GameEvent {

    private Zone fromZone;
    private Zone toZone;
    private Zone originalToZone;
    private Permanent target;

    public ZoneChangeEvent(Permanent target, UUID sourceId, UUID playerId, Zone fromZone, Zone toZone) {
        super(EventType.ZONE_CHANGE, target.getId(), sourceId, playerId);
        this.fromZone = fromZone;
        this.setToZone(toZone);
        this.target = target;
    }

    public ZoneChangeEvent(Permanent target, UUID sourceId, UUID playerId, Zone fromZone, Zone toZone, List<UUID> appliedEffects) {
        super(EventType.ZONE_CHANGE, target.getId(), sourceId, playerId);
        this.fromZone = fromZone;
        this.setToZone(toZone);
        this.target = target;
        if (appliedEffects != null) {
            this.appliedEffects = appliedEffects;
        }
    }

    public ZoneChangeEvent(UUID targetId, UUID sourceId, UUID playerId, Zone fromZone, Zone toZone) {
        super(EventType.ZONE_CHANGE, targetId, sourceId, playerId);
        this.fromZone = fromZone;
        this.setToZone(toZone);
    }

    public ZoneChangeEvent(UUID targetId, UUID sourceId, UUID playerId, Zone fromZone, Zone toZone, List<UUID> appliedEffects) {
        super(EventType.ZONE_CHANGE, targetId, sourceId, playerId);
        this.fromZone = fromZone;
        this.setToZone(toZone);
        if (appliedEffects != null) {
            this.appliedEffects = appliedEffects;
        }
    }

    public ZoneChangeEvent(Permanent target, UUID playerId, Zone fromZone, Zone toZone) {
        this(target, null, playerId, fromZone, toZone);
    }

    public ZoneChangeEvent(UUID targetId, UUID playerId, Zone fromZone, Zone toZone) {
        this(targetId, null, playerId, fromZone, toZone);
    }

    public Zone getFromZone() {
        return fromZone;
    }

    public Zone getToZone() {
        return toZone;
    }

    public void setToZone(Zone toZone) {
        this.toZone = toZone;
        if (originalToZone == null && toZone != null) {
            originalToZone = toZone;
        }
    }

    public Permanent getTarget() {
        return target;
    }

    public void setTarget(Permanent target) {
        this.target = target;
    }

    public boolean isDiesEvent() {
        return (toZone == Zone.GRAVEYARD && fromZone == Zone.BATTLEFIELD);
    }

    public Zone getOriginalToZone() {
        return originalToZone;
    }

}
