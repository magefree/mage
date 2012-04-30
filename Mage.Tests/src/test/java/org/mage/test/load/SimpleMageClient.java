package org.mage.test.load;

import mage.interfaces.MageClient;
import mage.interfaces.callback.CallbackClient;
import mage.interfaces.callback.ClientCallback;
import mage.utils.MageVersion;
import org.apache.log4j.Logger;

import java.util.UUID;

/**
 * For tests only
 *
 * @author noxx
 */
public class SimpleMageClient implements MageClient {

    private UUID clientId;
    private final static MageVersion version = new MageVersion(0, 8, 4, "");
    
    private static final transient Logger log = Logger.getLogger(SimpleMageClient.class);

    private static CallbackClient callbackClient;

    public SimpleMageClient() {
        clientId = UUID.randomUUID();
        callbackClient = new LoadCallbackClient();
    }

    @Override
    public UUID getId() {
        return clientId;
    }

    @Override
    public MageVersion getVersion() {
        return version;
    }

    @Override
    public void connected(String message) {
        // do nothing
    }

    @Override
    public void disconnected() {
        // do nothing
    }

    @Override
    public void showMessage(String message) {
        log.info(message);
    }

    @Override
    public void showError(String message) {
        log.error(message);
    }

    @Override
    public void processCallback(ClientCallback callback) {
        callbackClient.processCallback(callback);
    }
}
