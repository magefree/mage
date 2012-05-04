package org.mage.test.load;

import mage.interfaces.callback.CallbackClient;
import mage.interfaces.callback.ClientCallback;
import mage.remote.Session;
import mage.utils.CompressUtil;
import mage.view.TableClientMessage;
import org.apache.log4j.Logger;

/**
 * @author noxx
 */
public class LoadCallbackClient implements CallbackClient {

    private static final transient Logger log = Logger.getLogger(LoadCallbackClient.class);
    
    private Session session;
    
    @Override
    public void processCallback(ClientCallback callback) {
        //TODO
        log.info(callback.getMethod());
        callback.setData(CompressUtil.decompress(callback.getData()));
        if (callback.getMethod().equals("startGame")) {
            TableClientMessage message = (TableClientMessage) callback.getData();
            session.joinGame(message.getGameId());
        }

    }
    
    public void setSession(Session session) {
        this.session = session;
    }
}
