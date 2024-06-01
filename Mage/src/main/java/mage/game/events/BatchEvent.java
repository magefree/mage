package mage.game.events;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Special events created by game engine to track batches of events that occur simultaneously,
 * for triggers that need such information
 *
 * @author xenohedron
 */
public abstract class BatchEvent<T extends GameEvent> extends GameEvent {

    private final Set<T> events = new HashSet<>();
    private final boolean singleTargetId;
    private final boolean singleSourceId;
    private final boolean singlePlayerId;

    /**
     * @param eventType      specific type of event
     * @param singleSourceId if true, all included events must have same source id
     * @param singleTargetId if true, all included events must have same target id
     * @param singlePlayerId if true, all included events must have same player id
     * @param firstEvent     added to initialize the batch (batch is never empty)
     */
    protected BatchEvent(EventType eventType, boolean singleTargetId, boolean singleSourceId, boolean singlePlayerId, T firstEvent) {
        super(eventType,
                (singleTargetId ? firstEvent.getTargetId() : null),
                null,
                (singlePlayerId ? firstEvent.getPlayerId() : null)
        );
        this.singleTargetId = singleTargetId;
        this.singleSourceId = singleSourceId;
        this.singlePlayerId = singlePlayerId;
        this.setSourceId(singleSourceId ? firstEvent.getSourceId() : null);
        if (firstEvent instanceof BatchEvent) { // sanity check, if you need it then think twice and research carefully
            throw new UnsupportedOperationException("Wrong code usage: nesting batch events not supported");
        }
        this.addEvent(firstEvent);
    }

    /**
     * For alternate event structure logic used by ZoneChangeBatchEvent, list of events starts empty.
     */
    protected BatchEvent(EventType eventType) {
        super(eventType, null, null, null);
        this.singleTargetId = false;
        this.singleSourceId = false;
        this.singlePlayerId = false;
    }

    public void addEvent(T event) {
        if (singleTargetId && !getTargetId().equals(event.getTargetId())) {
            throw new IllegalStateException("Wrong code usage. Batch event initiated with single target id, but trying to add event with different target id");
        }
        if (singleSourceId && !getSourceId().equals(event.getSourceId())) {
            throw new IllegalStateException("Wrong code usage. Batch event initiated with single source id, but trying to add event with different source id");
        }
        if (singlePlayerId && !getPlayerId().equals(event.getPlayerId())) {
            throw new IllegalStateException("Wrong code usage. Batch event initiated with single player id, but trying to add event with different player id");
        }
        this.events.add(event);
    }

    public Set<T> getEvents() {
        return events;
    }

    public Set<UUID> getTargetIds() {
        return events.stream()
                .map(GameEvent::getTargetId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public Set<UUID> getSourceIds() {
        return events.stream()
                .map(GameEvent::getSourceId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public Set<UUID> getPlayerIds() {
        return events.stream()
                .map(GameEvent::getPlayerId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Override
    public int getAmount() {
        return events
                .stream()
                .mapToInt(GameEvent::getAmount)
                .sum();
    }

    @Override // events can store a diff value, so search it from events list instead
    public UUID getTargetId() {
        if (singleTargetId) {
            return super.getTargetId();
        }
        throw new IllegalStateException("Wrong code usage. Must search value from a getEvents list or use CardUtil.getEventTargets(event)");
    }

    @Override // events can store a diff value, so search it from events list instead
    public UUID getSourceId() {
        if (singleSourceId) {
            return super.getSourceId();
        }
        throw new IllegalStateException("Wrong code usage. Must search value from a getEvents list");
    }

    @Override // events can store a diff value, so search it from events list instead
    public UUID getPlayerId() {
        if (singlePlayerId) {
            return super.getPlayerId();
        }
        throw new IllegalStateException("Wrong code usage. Must search value from a getEvents list");
    }

}
