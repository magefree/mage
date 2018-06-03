
package mage.filter.predicate;

import java.util.UUID;

/**
 *
 * @author North
 * @param <T>
 */
public class ObjectPlayer<T> {

    protected final T object;
    protected final UUID playerId;

    public ObjectPlayer(T object, UUID playerId) {
        this.object = object;
        this.playerId = playerId;
    }

    public T getObject() {
        return object;
    }

    public UUID getPlayerId() {
        return playerId;
    }
}
