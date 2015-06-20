package org.mage.server.test;

import java.util.List;
import mage.server.ServerMain;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author BetaSteward
 */
public class ServerTest {
    
    private static TestClient client;
    
    public ServerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ServerMain.main(new String[] {"-fastDbMode=true"});
            }
        }).start();
        client = new TestClient();
        try {
            Thread.sleep(10000);  //wait for server to startup
        } catch (InterruptedException ex) {
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void connectTest() {
        if (!client.isConnected()) {
            Assert.assertTrue("Connect failed", client.connect());
        }
    }
    
    @Test
    public void serverMessagesTest() {
        if (!client.isConnected()) {
            connectTest();
        }
        List<String> serverMessages = client.getServerMessages();
        Assert.assertEquals("Message count did not match", 3, serverMessages.size());
        Assert.assertEquals("Message 1 did not match", "This is message #1", serverMessages.get(0));
        Assert.assertEquals("Message 2 did not match", "And this is message #2", serverMessages.get(1));
    }

    @Test
    public void serverStateTest() {
        if (!client.isConnected()) {
            connectTest();
        }
        Assert.assertNotNull("ServerState was null", client.getServerState());
        Assert.assertNotNull("MainRoomId was null", client.getMainRoomId());
    }

    
}
