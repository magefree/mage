package mage.filter.predicate;

import java.util.UUID;

/**
 * @param <T>
 * @author North
 */
public class ObjectSourcePlayer<T> {

    protected final T object;
    protected final UUID playerId;
    protected final UUID sourceId;

    public ObjectSourcePlayer(T object, UUID sourceId, UUID sourceControllerId) {
        this.object = object;
        this.playerId = sourceControllerId;
        this.sourceId = sourceId;
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
}
