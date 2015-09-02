package org.mage.server.test.load;

import org.junit.Test;

/**
 *
 * @author BetaSteward
 */
public class ConnectionLoadTest extends BaseLoadTest {
    
    private static final Integer USER_COUNT = 1000;

    @Test
    public void connectTest() {
        connect(USER_COUNT);
        
        logger.info("starting sleep");
        try {
            Thread.sleep(65000);  // wait for aprox. 1 minute -- this allows some ping requests to bounce around
        } catch (InterruptedException ex) {
        }
        logger.info("done sleeping");
        
        disconnect();
    }
    
}
