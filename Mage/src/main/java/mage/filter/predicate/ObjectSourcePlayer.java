
package mage.filter.predicate;

import java.util.UUID;

/**
 *
 * @author North
 * @param <T>
 */
public class ObjectSourcePlayer<T> extends ObjectPlayer<T> {

    protected final UUID sourceId;

    public ObjectSourcePlayer(T object, UUID sourceId, UUID sourceControllerId) {
        super(object, sourceControllerId);
        this.sourceId = sourceId;
    }

    public UUID getSourceId() {
        return sourceId;
    }
}
