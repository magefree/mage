

package mage.interfaces.callback;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ClientCallback implements Serializable {

    private UUID objectId;
    private Object data;
    private ClientCallbackMethod method;
    private int messageId;

    public ClientCallback() {}

    public ClientCallback(ClientCallbackMethod method, UUID objectId, Object data) {
        this.method = method;
        this.objectId = objectId;
        this.data = data;
    }

    public ClientCallback(ClientCallbackMethod method, UUID objectId) {
        this(method, objectId, null);
    }

    public void clear() {
        method = null;
        data = null;
    }

    public UUID getObjectId() {
        return objectId;
    }

    public void setObjectId(UUID objectId) {
        this.objectId = objectId;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ClientCallbackMethod getMethod() {
        return method;
    }

    public void setMethod(ClientCallbackMethod method) {
        this.method = method;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getMessageId() {
        return messageId;
    }

}
