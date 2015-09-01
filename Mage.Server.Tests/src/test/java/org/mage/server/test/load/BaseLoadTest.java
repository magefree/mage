package org.mage.server.test.load;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import mage.server.ServerMain;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.mage.server.test.TestClient;

/**
 *
 * @author BetaSteward
 */
public abstract class BaseLoadTest {
    
    protected static final Logger logger = Logger.getLogger(BaseLoadTest.class);
    protected static final Random rng = new Random();
    
    protected Map<String, TestClient> users = new HashMap<>();

    @BeforeClass
    public static void setUpClass() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ServerMain.main(new String[] {"-fastDbMode=true"});
//                ServerMain.main(new String[] {""});
            }
        }).start();
        try {
            waitForServer("ACTIVE");  //wait for server to startup
        } catch (InterruptedException | IOException ex) {
        }
    }
    
    private static void waitForServer(String message) throws FileNotFoundException, IOException, InterruptedException {
        FileReader fr = new FileReader("mageserver.log");
        BufferedReader br = new BufferedReader(fr);

        do {    //read until end-of-file
            String line = br.readLine();
            if (line == null) {
                break;
            }
        } while (true); 

        do {    //read only new lines
            String line = br.readLine();
            if (line == null) {
                Thread.sleep(1000);
            } 
            else {
                if (line.contains(message))
                    break;
            }
        } while (true);
    }

    protected void connect(int numUsers) {
        for (int i = 0; i < numUsers; i++) {
            String username = "player" + i;
            TestClient client = new TestClient();
            client.connect(username);
            users.put(username, client);
            pause(10, 50);  // wait 10 to 50 ms
        }
        
    }
        
    protected void disconnect() {
        for (TestClient client: users.values()) {
            client.disconnect(false);
            pause(10, 50);  // wait 10 to 50 ms
        }
        logger.info("Finished disconnecting");
        for (TestClient client: users.values()) {
            Assert.assertFalse("user did not disconnect", client.isConnected());
        }        
    }
    
    protected void pause(int min, int max) {
        try {
            Thread.sleep(rng.nextInt(max - min) + min);
        } catch (InterruptedException ex) {
        }        
    }

}
