package org.mage.test.load;

import java.util.UUID;
import mage.interfaces.MageClient;
import mage.interfaces.callback.ClientCallback;
import mage.remote.Session;
import mage.utils.MageVersion;
import mage.view.GameView;
import org.apache.log4j.Logger;

/**
 * For tests only
 *
 * @author noxx, JayDi85
 */
public class SimpleMageClient implements MageClient {

    private final UUID clientId;
    private static final MageVersion version = new MageVersion(MageVersion.MAGE_VERSION_MAJOR, MageVersion.MAGE_VERSION_MINOR, MageVersion.MAGE_VERSION_PATCH, MageVersion.MAGE_VERSION_MINOR_PATCH, MageVersion.MAGE_VERSION_INFO);

    private static final Logger log = Logger.getLogger(SimpleMageClient.class);

    private final LoadCallbackClient callbackClient;

    public SimpleMageClient() {
        clientId = UUID.randomUUID();
        callbackClient = new LoadCallbackClient();
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
    public void disconnected(boolean errorCall) {
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
        try {
            callbackClient.processCallback(callback);
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
        }
    }

    public void setSession(Session session) {
        ((LoadCallbackClient) callbackClient).setSession(session);
    }

    public boolean isGameOver() {
        return ((LoadCallbackClient) callbackClient).isGameOver();
    }

    public void setConcede(boolean needToConcede) {
        this.callbackClient.setConcede(needToConcede);
    }

    public String getLastGameResult() {
        return this.callbackClient.getLastGameResult();
    }

    public GameView getLastGameView() {
        return this.callbackClient.getLastGameView();
    }
}
