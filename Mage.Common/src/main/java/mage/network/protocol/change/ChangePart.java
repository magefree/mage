package mage.network.protocol.change;

/**
 * Defines chunk of update: what is updated and data.
 *
 * @author magenoxx
 */
public class ChangePart {

    private ChangeType changeType;

    private Object data;

    public ChangePart(final ChangeType changeType, Object data) {
        this.changeType = changeType;
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
