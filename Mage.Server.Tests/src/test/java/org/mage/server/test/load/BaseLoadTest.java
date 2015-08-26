package org.mage.server.test.load;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
        }
        
    }
        
    protected void disconnect() {
        for (TestClient client: users.values()) {
            client.disconnect(false);
        }
        for (TestClient client: users.values()) {
            Assert.assertFalse("user did not disconnect", client.isConnected());
        }        
    }

}
