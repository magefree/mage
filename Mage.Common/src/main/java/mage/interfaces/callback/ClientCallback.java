package mage.interfaces.callback;

import mage.remote.traffic.ZippedObject;
import mage.utils.CompressUtil;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ClientCallback implements Serializable {

    private UUID objectId;
    private Object data;
    private ClientCallbackMethod method;
    private int messageId;

    public ClientCallback(ClientCallbackMethod method, UUID objectId, Object data) {
        this(method, objectId, data, true);
    }

    public ClientCallback(ClientCallbackMethod method, UUID objectId, Object data, boolean useCompress) {
        this.method = method;
        this.objectId = objectId;
        this.setData(data, useCompress);
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
        if (this.data instanceof ZippedObject) {
            throw new IllegalStateException("Client data must be decompressed first");
        }
        return data;
    }

    public void setData(Object data, boolean useCompress) {
        if (!useCompress || data == null || data instanceof ZippedObject) {
            this.data = data;
        } else {
            this.data = CompressUtil.compress(data);
        }
    }

    public void decompressData() {
        if (this.data instanceof ZippedObject) {
            this.data = CompressUtil.decompress(this.data);
        }
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
