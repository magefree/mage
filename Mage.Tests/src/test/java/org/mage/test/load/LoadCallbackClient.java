package org.mage.test.load;

import mage.interfaces.callback.CallbackClient;
import mage.interfaces.callback.ClientCallback;
import org.apache.log4j.Logger;

/**
 * @author noxx
 */
public class LoadCallbackClient implements CallbackClient {

    private static final transient Logger log = Logger.getLogger(LoadCallbackClient.class);
    
    @Override
    public void processCallback(ClientCallback callback) {
        //TODO
        log.info(callback.getMethod());
    }
}
