package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Is applied when the {@link Permanent} with this ability instance changes
 * zones.
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ZoneChangeTriggeredAbility extends TriggeredAbilityImpl {

    protected final Zone fromZone;
    protected final Zone toZone;

    // Required for some zone changing abilities like Persist and Undying
    protected List<UUID> mutatedSourceIds = new ArrayList<>();

    public ZoneChangeTriggeredAbility(Zone fromZone, Zone toZone, Effect effect, String triggerPhrase, boolean optional) {
        this(toZone == null ? Zone.ALL : toZone, fromZone, toZone, effect, triggerPhrase, optional);
    }

    public ZoneChangeTriggeredAbility(Zone worksInZone, Zone fromZone, Zone toZone, Effect effect, String triggerPhrase, boolean optional) {
        super(worksInZone, effect, optional);
        if (fromZone == Zone.BATTLEFIELD) {
            setLeavesTheBattlefieldTrigger(true);
        }
        this.fromZone = fromZone;
        this.toZone = toZone;
        setTriggerPhrase(triggerPhrase);
    }

    public ZoneChangeTriggeredAbility(Zone toZone, Effect effect, String triggerPhrase, boolean optional) {
        super(toZone, effect, optional);
        this.fromZone = null;
        this.toZone = toZone;
        setTriggerPhrase(triggerPhrase);
    }

    protected ZoneChangeTriggeredAbility(final ZoneChangeTriggeredAbility ability) {
        super(ability);
        this.fromZone = ability.fromZone;
        this.toZone = ability.toZone;
        this.mutatedSourceIds.addAll(ability.mutatedSourceIds);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getSourceId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if ((fromZone == null || zEvent.getFromZone() == fromZone) && (toZone == null || zEvent.getToZone() == toZone)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ZoneChangeTriggeredAbility copy() {
        return new ZoneChangeTriggeredAbility(this);
    }

    public Zone getFromZone() {
        return fromZone;
    }

    public Zone getToZone() {
        return toZone;
    }

    public void addMutatedSourceId(UUID sourceId) {
        if (!hasMutatedSourceId(sourceId)) {
            mutatedSourceIds.add(sourceId);
        }
    }

    public boolean removeMutatedSourceId(UUID sourceId) {
        return mutatedSourceIds.remove(sourceId);
    }

    public boolean hasMutatedSourceId(UUID sourceId) {
        return mutatedSourceIds.contains(sourceId);
    }

    public List<UUID> getMutatedSourceIds() {
        return new ArrayList<>(mutatedSourceIds);
    }
}
