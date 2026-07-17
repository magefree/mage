package mage.game.events;

import java.util.List;
import java.util.UUID;

import mage.abilities.Ability;
import mage.constants.Zone;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ZoneChangeEvent extends GameEvent {

    private Zone fromZone;
    private Zone toZone; // real toZone after apply some replacements effects
    private Zone originalToZone; // original toZone before any replacement effects
    private Permanent target; // moving permanent (null on moving card)
    private Ability source; // link to source ability, can be null in rare situations

    public ZoneChangeEvent(Permanent target, Ability source, UUID playerId, Zone fromZone, Zone toZone) {
        this(target, source, playerId, fromZone, toZone, null);
    }

    public ZoneChangeEvent(Permanent target, Ability source, UUID playerId, Zone fromZone, Zone toZone, List<UUID> appliedEffects) {
        super(GameEvent.EventType.ZONE_CHANGE, target.getId(), source, playerId);
        this.fromZone = fromZone;
        this.setToZone(toZone);
        this.target = target;
        this.source = source;
        if (appliedEffects != null) {
            this.appliedEffects = appliedEffects;
        }
    }

    public ZoneChangeEvent(UUID targetId, Ability source, UUID playerId, Zone fromZone, Zone toZone) {
        this(targetId, source, playerId, fromZone, toZone, null);
    }

    public ZoneChangeEvent(UUID targetId, Ability source, UUID playerId, Zone fromZone, Zone toZone, List<UUID> appliedEffects) {
        super(GameEvent.EventType.ZONE_CHANGE, targetId, source, playerId);
        this.fromZone = fromZone;
        this.setToZone(toZone);
        this.source = source;
        if (appliedEffects != null) {
            this.appliedEffects = appliedEffects;
        }
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

    /**
     * Return moved permanent. Can be null on card moving or mutate parts.
     */
    public Permanent getTarget() {
        return target;
    }

    public boolean isPermanentMoved() {
        return Zone.BATTLEFIELD.match(fromZone) && target != null;
    }

    public void setTarget(Permanent target) {
        this.target = target;
    }

    /**
     * Is dies event. Warning, cards also can trigger it without real permanent (example; mutated parts)
     */
    public boolean isDiesEvent() {
        return (toZone == Zone.GRAVEYARD && fromZone == Zone.BATTLEFIELD);
    }

    public Zone getOriginalToZone() {
        return originalToZone;
    }

    /**
     * Source ability of the event, can be null in rare cases
     */
    public Ability getSource() {
        return this.source;
    }

    @Override
    public String toString() {
        return super.toString()
                + ", from " + getFromZone() + " to " + getToZone()
                + ", " + (this.target == null ? "no target" : "target " + this.target)
                + ", " + (this.source == null ? "no source" : "source " + this.source);
    }
}
