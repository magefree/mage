package mage.filter.predicate;

import mage.abilities.Ability;

import java.util.UUID;

/**
 * @param <T>
 * @author North
 */
public class ObjectSourcePlayer<T> {

    protected final T object;
    protected final UUID playerId;
    protected final UUID sourceId;
    protected final Ability source;

    public ObjectSourcePlayer(T object, UUID sourceControllerId, Ability source) {
        this.object = object;
        this.playerId = sourceControllerId;
        this.sourceId = source != null ? source.getSourceId() : null;
        this.source = source;
    }

    public T getObject() {
        return object;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public UUID getSourceId() {
        return sourceId;
    }

    public Ability getSource() {
        return source;
    }
}
