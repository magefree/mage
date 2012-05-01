package org.mage.test.load;

import mage.Constants;
import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckCardLists;
import mage.game.match.MatchOptions;
import mage.player.ai.ComputerPlayer;
import mage.remote.Connection;
import mage.remote.Session;
import mage.remote.SessionImpl;
import mage.sets.Sets;
import mage.view.GameTypeView;
import mage.view.TableView;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Intended to test Mage server under different load patterns.
 *
 * These tests do use server started separately, so Mage server should be started before running them.
 * In case you want to debug these tests, use -Ddebug.mage that would disable client-server request timeout.
 *
 * Then it's also better to use -Xms256M -Xmx512M JVM options for these stests.
 *
 * @author noxx
 */
public class LoadTest {

    /**
     * Logger for tests
     */
    private static final Logger log = Logger.getLogger(LoadTest.class);

    /**
     * First player's username
     */
    private static final String TEST_USER_NAME = "player";

    /**
     * Second player's username
     */
    private static final String TEST_USER_NAME_2 = "opponent";

    /**
     * Server connection setting.
     */
    private static final String TEST_SERVER = "localhost";

    /**
     * Server connection setting.
     */
    private static final int TEST_PORT = 17171;

    /**
     * Server connection setting.
     */
    private static final String TEST_PROXY_TYPE = "None";

    /**
     * Determines how many times test will be executed in a row.
     */
    private static final int EXECUTION_COUNT = 100;

    /**
     * Tests connecting with two players, creating game and starting it.
     *
     * Executes the test EXECUTION_COUNT times.
     *
     * @throws Exception
     */
    @Test
    @Ignore
    public void testStartGame() throws Exception {
        DeckCardLists deckList = createDeck();

        for (int i = 0; i < EXECUTION_COUNT; i++) {
            Connection connection = createConnection(TEST_USER_NAME + i);
    
            SimpleMageClient mageClient = new SimpleMageClient();
            Session session = new SessionImpl(mageClient);
    
            session.connect(connection);
            UUID roomId = session.getMainRoomId();
    
            GameTypeView gameTypeView = session.getGameTypes().get(0);
            log.info("Game type view: " + gameTypeView.getName());
            MatchOptions options = createGameOptions(gameTypeView, session);
    
            TableView table = session.createTable(roomId, options);

            if (!session.joinTable(roomId, table.getTableId(), TEST_USER_NAME + i, "Human", 1, deckList)) {
                log.error("Error while joining table");
                Assert.assertTrue("Error while joining table", false);
                return;
            }
    
            /*** Connect with a second player ***/
            Connection connection2 = createConnection(TEST_USER_NAME_2 + i);
            SimpleMageClient mageClient2 = new SimpleMageClient();
            Session session2 = new SessionImpl(mageClient2);
            session2.connect(connection2);
            UUID roomId2 = session2.getMainRoomId();
    
            // connect to the table with the same deck
            if (!session2.joinTable(roomId2, table.getTableId(), TEST_USER_NAME_2 + i, "Human", 1, deckList)) {
                log.error("Error while joining table");
                Assert.assertTrue("Error while joining table", false);
                return;
            }
    
            /*** Start game ***/
            session.startGame(roomId, table.getTableId());
            
            Thread.sleep(100);
        }
    }

    /**
     * Creates connection to the server.
     * Server should run independently.
     *
     * @param username
     * @return
     */
    private Connection createConnection(String username) {
        Connection connection = new Connection();
        connection.setUsername(username);
        connection.setHost(TEST_SERVER);
        connection.setPort(TEST_PORT);
        Connection.ProxyType proxyType = Connection.ProxyType.valueByText(TEST_PROXY_TYPE);
        connection.setProxyType(proxyType);
        return connection;
    }

    /**
     * Returns random deck.
     * Converts deck returned by {@link #generateRandomDeck} method to {@link DeckCardLists} format.
     *
     * @return
     */
    private DeckCardLists createDeck() {
        DeckCardLists deckList = new DeckCardLists();
        Deck deck = generateRandomDeck();
        for (Card card : deck.getCards()) {
            ExpansionSet set = Sets.findSet(card.getExpansionSetCode());
            String cardName = set.findCardName(card.getCardNumber());
            deckList.getCards().add(cardName);
        }
        return deckList;
    }

    /**
     * Creates game options with two human players.
     *
     * @param gameTypeView
     * @param session
     * @return
     */
    private MatchOptions createGameOptions(GameTypeView gameTypeView, Session session) {
        MatchOptions options = new MatchOptions("Test game", gameTypeView.getName());

        options.getPlayerTypes().add("Human");
        options.getPlayerTypes().add("Human");

        options.setDeckType(session.getDeckTypes()[0]);
        options.setLimited(false);
        options.setAttackOption(Constants.MultiplayerAttackOption.MULTIPLE);
        options.setRange(Constants.RangeOfInfluence.ALL);
        options.setWinsNeeded(1);
        return options;
    }

    /**
     * Generates random deck in {@link Deck} format.
     * Uses {B}{R} as deck colors.
     *
     * @return
     */
    private Deck generateRandomDeck() {
        String selectedColors = "BR";
        List<Constants.ColoredManaSymbol> allowedColors = new ArrayList<Constants.ColoredManaSymbol>();
        log.info("Building deck with colors: " + selectedColors);
        for (int i = 0; i < selectedColors.length(); i++) {
            char c = selectedColors.charAt(i);
            allowedColors.add(Constants.ColoredManaSymbol.lookup(c));
        }
        List<Card> cardPool = Sets.generateRandomCardPool(45, allowedColors);
        return ComputerPlayer.buildDeck(cardPool, allowedColors);
    }
}
