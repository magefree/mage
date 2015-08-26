package org.mage.server.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import mage.cards.decks.importer.DeckImporterUtil;
import mage.constants.MatchTimeLimit;
import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.constants.SkillLevel;
import mage.constants.TableState;
import mage.game.match.MatchOptions;
import mage.server.ServerMain;
import mage.view.RoomView;
import mage.view.TableView;
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
    
    private static final String USERNAME = "test_user";
    private static TestClient client;
    
    public ServerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                ServerMain.main(new String[] {"-fastDbMode=true"});
                ServerMain.main(new String[] {""});
            }
        }).start();
        try {
            waitForServer("ACTIVE");  //wait for server to startup
        } catch (InterruptedException | IOException ex) {
        }
        client = new TestClient();
        client.connect(USERNAME);
        try {
            Thread.sleep(10000);  //wait for user to join main room
        } catch (InterruptedException ex) {
        }
    }
    
    public static void waitForServer(String message) throws FileNotFoundException, IOException, InterruptedException {
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
        Assert.assertTrue("Connect failed", client.isConnected());
    }
    
    @Test
    public void serverMessagesTest() {
        if (!client.isConnected()) {
            Assert.fail("Not connected");
        }
        List<String> serverMessages = client.getServerMessages();
        Assert.assertEquals("Message count did not match", 3, serverMessages.size());
        Assert.assertEquals("Message 1 did not match", "This is message #1", serverMessages.get(0));
        Assert.assertEquals("Message 2 did not match", "And this is message #2", serverMessages.get(1));
    }

    @Test
    public void serverStateTest() {
        if (!client.isConnected()) {
            Assert.fail("Not connected");
        }
        Assert.assertNotNull("ServerState was null", client.getServerState());
        Assert.assertNotNull("MainRoomId was null", client.getMainRoomId());
    }

    @Test
    public void getRoomTest() {
        if (!client.isConnected()) {
            Assert.fail("Not connected");
        }
        RoomView room = client.getRoom(client.getMainRoomId());
        Assert.assertNotNull("RoomView was null", room);
        Assert.assertNotEquals("Room was empty", room.getRoomUsersView().getUsersView().size(), 0);
    }
    
    @Test
    public void createAndJoinTableTest() {
        if (!client.isConnected()) {
            Assert.fail("Not connected");
        }
        MatchOptions options = new MatchOptions("test game", "Two Player Duel");
        options.getPlayerTypes().add("Human");
        options.getPlayerTypes().add("Human");
        options.setDeckType("Constructed - Standard");
        options.setLimited(false);
        options.setMatchTimeLimit(MatchTimeLimit.MIN__10);
        options.setAttackOption(MultiplayerAttackOption.LEFT);
        options.setSkillLevel(SkillLevel.BEGINNER);
        options.setRange(RangeOfInfluence.ALL);
        options.setWinsNeeded(1);
        options.setRollbackTurnsAllowed(false);
        options.setFreeMulligans(1);
        options.setPassword("");
        TableView table = client.createTable(client.getMainRoomId(), options);
        Assert.assertNotNull("Table was null", table);
        Assert.assertEquals("Table size incorrect", 2, table.getSeats().size());
        Assert.assertEquals("Table state incorrect", TableState.WAITING, table.getTableState());
        boolean result = client.joinTable(client.getMainRoomId(), table.getTableId(), USERNAME, "Human", 1, DeckImporterUtil.importDeck("TestDeck.dck"), "");
        Assert.assertTrue("Unable to join table", result);
        Assert.assertTrue("Joined table didn't fire", client.isJoinedTableFired());

    }
}
