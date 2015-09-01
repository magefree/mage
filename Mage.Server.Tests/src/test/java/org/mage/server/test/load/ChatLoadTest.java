package org.mage.server.test.load;

import org.junit.Ignore;
import org.junit.Test;
import org.mage.server.test.TestClient;

/**
 *
 * @author BetaSteward
 */
public class ChatLoadTest extends BaseLoadTest {
    
    private static final Integer USER_COUNT = 200;
    private static final int NUM_TESTS = 50;

    private static final StringBuilder sb = new StringBuilder();

    private static final char[] symbols;
    static {
        StringBuilder tmp = new StringBuilder();
        for (char ch = '0'; ch <= '9'; ++ch)
          tmp.append(ch);
        for (char ch = 'a'; ch <= 'z'; ++ch)
          tmp.append(ch);
        for (char ch = 'A'; ch <= 'Z'; ++ch)
          tmp.append(ch);
        symbols = tmp.toString().toCharArray();
    }   
    
    @Test
    @Ignore
    public void chat() {
        connect(USER_COUNT);
        
        for (TestClient client: users.values()) {
            client.joinMainChat();
        }
        
        Object[] clients = users.values().toArray();
        for (int i = 0; i < NUM_TESTS; i++) {
            TestClient client = (TestClient)clients[rng.nextInt(clients.length)];
            String message = randomString();
            logger.info("Sending chat message#:" + i + " message:" + message);
            client.sendChatMessage(client.getServerState().getMainRoomId(), message);
            pause(50, 500);  // wait 50 to 500 ms
        }
        
        disconnect();
    }
    
    private String randomString() {
        sb.setLength(0);
        for (int i = 0; i < rng.nextInt(100); i++) {
            sb.append(symbols[rng.nextInt(symbols.length)]);
        }
        return sb.toString();
    }
    
}
