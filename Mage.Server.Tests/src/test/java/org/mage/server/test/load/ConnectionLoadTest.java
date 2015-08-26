package org.mage.server.test.load;

import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author BetaSteward
 */
public class ConnectionLoadTest extends BaseLoadTest {
    
    private static final Integer USER_COUNT = 400;

    @Test
    @Ignore
    public void connectTest() {
        connect(USER_COUNT);
        
        try {
            Thread.sleep(60000);  // wait for 1 minute -- this allows some ping requests to bounce around
        } catch (InterruptedException ex) {
        }
        
        disconnect();
    }
    
}
