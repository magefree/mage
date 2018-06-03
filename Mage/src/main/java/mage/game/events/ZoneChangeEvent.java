

package mage.game.events;

import mage.constants.Zone;
import mage.game.permanent.Permanent;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ZoneChangeEvent extends GameEvent {

    private Zone fromZone;
    private Zone toZone;
    private Permanent target;

    public ZoneChangeEvent(Permanent target, UUID sourceId, UUID playerId, Zone fromZone, Zone toZone) {
        super(EventType.ZONE_CHANGE, target.getId(), sourceId, playerId);
        this.fromZone = fromZone;
        this.toZone = toZone;
        this.target = target;
    }

    public ZoneChangeEvent(Permanent target, UUID sourceId, UUID playerId, Zone fromZone, Zone toZone, List<UUID> appliedEffects) {
        super(EventType.ZONE_CHANGE, target.getId(), sourceId, playerId);
        this.fromZone = fromZone;
        this.toZone = toZone;
        this.target = target;
        if (appliedEffects != null) {
            this.appliedEffects = appliedEffects;
        }
    }

    public ZoneChangeEvent(UUID targetId, UUID sourceId, UUID playerId, Zone fromZone, Zone toZone) {
        super(EventType.ZONE_CHANGE, targetId, sourceId, playerId);
        this.fromZone = fromZone;
        this.toZone = toZone;
    }

    public ZoneChangeEvent(UUID targetId, UUID sourceId, UUID playerId, Zone fromZone, Zone toZone, List<UUID> appliedEffects) {
        super(EventType.ZONE_CHANGE, targetId, sourceId, playerId);
        this.fromZone = fromZone;
        this.toZone = toZone;
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
}
