package mage.game.events;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author jimga150
 */
public class LifeLostBatchEvent extends GameEvent implements BatchGameEvent<LifeLostEvent> {

    private final Set<LifeLostEvent> events = new HashSet<>();

    public LifeLostBatchEvent(LifeLostEvent event) {
        super(EventType.LOST_LIFE_BATCH, null, null, null);
        addEvent(event);
    }

    @Override
    public Set<LifeLostEvent> getEvents() {
        return events;
    }

    @Override
    public Set<UUID> getTargets() {
        return events.stream()
                .map(GameEvent::getTargetId)
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

    public int getLifeLostByPlayer(UUID playerID) {
        return events
                .stream()
                .filter(ev -> ev.getTargetId().equals(playerID))
                .mapToInt(GameEvent::getAmount)
                .sum();
    }

    public boolean isLifeLostByCombatDamage() {
        return events.stream().anyMatch(LifeLostEvent::isCombatDamage);
    }

    @Override
    @Deprecated // events can store a diff value, so search it from events list instead
    public UUID getTargetId() {
        throw new IllegalStateException("Wrong code usage. Must search value from a getEvents list or use CardUtil.getEventTargets(event)");
    }

    @Override
    @Deprecated // events can store a diff value, so search it from events list instead
    public UUID getSourceId() {
        throw new IllegalStateException("Wrong code usage. Must search value from a getEvents list.");
    }

    public void addEvent(LifeLostEvent event) {
        this.events.add(event);
    }
}
