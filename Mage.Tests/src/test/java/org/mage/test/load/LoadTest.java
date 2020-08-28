package org.mage.test.load;

import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardScanner;
import mage.constants.*;
import mage.game.match.MatchOptions;
import mage.players.PlayerType;
import mage.remote.Connection;
import mage.remote.MageRemoteException;
import mage.remote.Session;
import mage.remote.SessionImpl;
import mage.util.RandomUtil;
import mage.view.*;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.utils.DeckTestUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Intended to test Mage server under different load patterns.
 * <p>
 * These tests do use server started separately, so Mage server should be
 * started before running them. In case you want to debug these tests, use
 * -Ddebug.mage that would disable client-server request timeout.
 * <p>
 * Then it's also better to use -Xms256M -Xmx512M JVM options for these tests.
 *
 * @author JayDi85
 */
public class LoadTest {

    private static final Logger logger = Logger.getLogger(LoadTest.class);

    private static final String TEST_SERVER = "localhost";
    private static final int TEST_PORT = 17171;
    private static final String TEST_PROXY_TYPE = "None";
    private static final String TEST_USER_NAME = "user";

    @BeforeClass
    public static void initDatabase() {
        // recreate missing cards db
        CardScanner.scan();
    }

    @Test
    public void test_CreateRandomDeck() {

        Deck deck;

        deck = DeckTestUtils.buildRandomDeck("G", false);
        Assert.assertNotNull(deck);
        for (Card card : deck.getCards()) {
            Assert.assertNotNull(card);
            Assert.assertTrue("card " + card.getName() + " color " + card.getColorIdentity().toString() + " must be in G",
                    card.getColorIdentity().isGreen());
        }

        deck = DeckTestUtils.buildRandomDeck("U", false);
        Assert.assertNotNull(deck);
        for (Card card : deck.getCards()) {
            Assert.assertNotNull(card);
            Assert.assertTrue("card " + card.getName() + " color " + card.getColorIdentity().toString() + " must be in U",
                    card.getColorIdentity().isBlue());
        }

        deck = DeckTestUtils.buildRandomDeck("BR", false);
        Assert.assertNotNull(deck);
        for (Card card : deck.getCards()) {
            Assert.assertNotNull(card);
            Assert.assertTrue("card " + card.getName() + " color " + card.getColorIdentity().toString() + " must be in BR",
                    card.getColorIdentity().isBlack() || card.getColorIdentity().isRed());
        }

        deck = DeckTestUtils.buildRandomDeck("BUG", false);
        Assert.assertNotNull(deck);
        for (Card card : deck.getCards()) {
            Assert.assertNotNull(card);
            Assert.assertTrue("card " + card.getName() + " color " + card.getColorIdentity().toString() + " must be in BUG",
                    card.getColorIdentity().isBlack() || card.getColorIdentity().isBlue() || card.getColorIdentity().isGreen());
        }

        // lands
        deck = DeckTestUtils.buildRandomDeck("UR", true);
        Assert.assertNotNull(deck);
        for (Card card : deck.getCards()) {
            Assert.assertNotNull(card);
            Assert.assertTrue("card " + card.getName() + " color " + card.getColorIdentity().toString() + " must be in UR",
                    card.getColorIdentity().isBlue() || card.getColorIdentity().isRed());
            Assert.assertEquals("card " + card.getName() + " must be basic land ", Rarity.LAND, card.getRarity());
        }

        deck = DeckTestUtils.buildRandomDeck("B", true);
        Assert.assertNotNull(deck);
        for (Card card : deck.getCards()) {
            Assert.assertNotNull(card);
            Assert.assertTrue("card " + card.getName() + " color " + card.getColorIdentity().toString() + " must be in B", card.getColorIdentity().isBlack());
            Assert.assertEquals("card " + card.getName() + " must be basic land ", Rarity.LAND, card.getRarity());
        }

        // allowed sets
        deck = DeckTestUtils.buildRandomDeck("B", true, "GRN");
        Assert.assertNotNull(deck);
        for (Card card : deck.getCards()) {
            Assert.assertNotNull(card);
            Assert.assertTrue("card " + card.getName() + " color " + card.getColorIdentity().toString() + " must be in B", card.getColorIdentity().isBlack());
            Assert.assertEquals("card " + card.getName() + " have wrong set code " + card.getExpansionSetCode(), "GRN", card.getExpansionSetCode());
        }
    }

    @Test
    @Ignore
    public void test_UsersConnectToServer() throws Exception {

        // simple connection to server
        // monitor other players
        LoadPlayer monitor = new LoadPlayer("monitor");
        Assert.assertTrue(monitor.session.isConnected());
        int startUsersCount = monitor.getAllRoomUsers().size();
        int minimumSleepTime = 2000;

        // user 1
        LoadPlayer player1 = new LoadPlayer("1");
        Thread.sleep(minimumSleepTime);
        Assert.assertEquals("Can't see users count change 1", startUsersCount + 1, monitor.getAllRoomUsers().size());
        Assert.assertNotNull("Can't find user 1", monitor.findUser(player1.userName));

        // user 2
        LoadPlayer player2 = new LoadPlayer("2");
        Thread.sleep(minimumSleepTime);
        Assert.assertEquals("Can't see users count change 2", startUsersCount + 2, monitor.getAllRoomUsers().size());
        Assert.assertNotNull("Can't find user 2", monitor.findUser(player2.userName));
    }

    @Test
    @Ignore
    public void test_TwoUsersPlayGameUntilEnd() {

        // monitor other players
        LoadPlayer monitor = new LoadPlayer("monitor");

        // users
        LoadPlayer player1 = new LoadPlayer("1");
        LoadPlayer player2 = new LoadPlayer("2");

        // game by user 1
        GameTypeView gameType = player1.session.getGameTypes().get(0);
        MatchOptions gameOptions = createSimpleGameOptionsForBots(gameType, player1.session);
        TableView game = player1.session.createTable(player1.roomID, gameOptions);
        UUID tableId = game.getTableId();
        Assert.assertEquals(player1.userName, game.getControllerName());

        DeckCardLists deckList = DeckTestUtils.buildRandomDeckAndInitCards("GR", true);
        Optional<TableView> checkGame;

        /*
        for(DeckCardInfo info: deckList.getCards()) {
            logger.info(info.getCardName());
        }*/
        // before connect
        checkGame = monitor.getTable(tableId);
        Assert.assertTrue(checkGame.isPresent());
        Assert.assertEquals(2, checkGame.get().getSeats().size());
        Assert.assertEquals("", checkGame.get().getSeats().get(0).getPlayerName());
        Assert.assertEquals("", checkGame.get().getSeats().get(1).getPlayerName());

        // connect user 1
        Assert.assertTrue(player1.session.joinTable(player1.roomID, tableId, player1.userName, PlayerType.HUMAN, 1, deckList, ""));
        checkGame = monitor.getTable(tableId);
        Assert.assertTrue(checkGame.isPresent());
        Assert.assertEquals(2, checkGame.get().getSeats().size());
        Assert.assertEquals(player1.userName, checkGame.get().getSeats().get(0).getPlayerName());
        Assert.assertEquals("", checkGame.get().getSeats().get(1).getPlayerName());

        // connect user 2
        Assert.assertTrue(player2.session.joinTable(player2.roomID, tableId, player2.userName, PlayerType.HUMAN, 1, deckList, ""));
        checkGame = monitor.getTable(tableId);
        Assert.assertTrue(checkGame.isPresent());
        Assert.assertEquals(2, checkGame.get().getSeats().size());
        Assert.assertEquals(player1.userName, checkGame.get().getSeats().get(0).getPlayerName());
        Assert.assertEquals(player2.userName, checkGame.get().getSeats().get(1).getPlayerName());

        // match start
        Assert.assertTrue(player1.session.startMatch(player1.roomID, tableId));

        // playing until game over
        while (!player1.client.isGameOver() && !player2.client.isGameOver()) {
            checkGame = monitor.getTable(tableId);
            logger.warn(checkGame.get().getTableState());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public void playTwoAIGame(String gameName, String deckColors, String deckAllowedSets) {
        Assert.assertFalse("need deck colors", deckColors.isEmpty());
        Assert.assertFalse("need allowed sets", deckAllowedSets.isEmpty());

        // monitor and game source
        LoadPlayer monitor = new LoadPlayer("monitor", true);

        // game by monitor
        GameTypeView gameType = monitor.session.getGameTypes().get(0);
        MatchOptions gameOptions = createSimpleGameOptionsForAI(gameType, monitor.session, gameName);
        TableView game = monitor.session.createTable(monitor.roomID, gameOptions);
        UUID tableId = game.getTableId();

        DeckCardLists deckList = DeckTestUtils.buildRandomDeckAndInitCards(deckColors, false, deckAllowedSets);
        Optional<TableView> checkGame;

        // join AI
        Assert.assertTrue(monitor.session.joinTable(monitor.roomID, tableId, "ai_1", PlayerType.COMPUTER_MAD, 5, deckList, ""));
        Assert.assertTrue(monitor.session.joinTable(monitor.roomID, tableId, "ai_2", PlayerType.COMPUTER_MAD, 5, deckList, ""));

        // match start
        Assert.assertTrue(monitor.session.startMatch(monitor.roomID, tableId));

        // playing until game over
        boolean startToWatching = false;
        while (true) {
            GameView gameView = monitor.client.getLastGameView();

            checkGame = monitor.getTable(tableId);
            TableState state = checkGame.get().getTableState();

            logger.warn(checkGame.get().getTableName()
                    + (gameView != null ? ", turn " + gameView.getTurn() + ", " + gameView.getStep().toString() : "")
                    + (gameView != null ? ", active " + gameView.getActivePlayerName() : "")
                    + ", " + state);

            if (state == TableState.FINISHED) {
                break;
            }

            if (!startToWatching && state == TableState.DUELING) {
                Assert.assertTrue(monitor.session.watchGame(checkGame.get().getGames().iterator().next()));
                startToWatching = true;
            }

            if (gameView != null) {
                for (PlayerView p : gameView.getPlayers()) {
                    logger.info(p.getName() + " - Life=" + p.getLife() + "; Lib=" + p.getLibraryCount());
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Test
    @Ignore
    public void test_TwoAIPlayGame_One() {
        playTwoAIGame("Single AI game", "GR", "GRN");
    }

    @Test
    @Ignore
    public void test_TwoAIPlayGame_Multiple() {

        int singleGameSID = 0; // for one game test with same deck
        int gamesAmount = 1000; // multiple run of one game test

        // save random seeds for repeated results (in decks generating)
        List<Integer> seedsList = new ArrayList<>();
        if (singleGameSID != 0) {
            for (int i = 1; i <= gamesAmount; i++) {
                seedsList.add(singleGameSID);
            }
        } else {
            for (int i = 1; i <= gamesAmount; i++) {
                seedsList.add(RandomUtil.nextInt());
            }
        }

        for (int i = 0; i <= seedsList.size() - 1; i++) {
            long randomSeed = seedsList.get(i);
            logger.info("Game " + (i + 1) + " of " + seedsList.size() + ", RANDOM seed: " + randomSeed);
            RandomUtil.setSeed(randomSeed);
            playTwoAIGame("AI game #" + (i + 1), "WGUBR", "ELD");
        }
    }

    @Test
    @Ignore
    public void test_GameThread() {
        // simple game thread to the end

        LoadGame game = new LoadGame(
                "game",
                "thread",
                DeckTestUtils.buildRandomDeckAndInitCards("GR", true, ""),
                DeckTestUtils.buildRandomDeckAndInitCards("GR", true, "")
        );
        game.gameStart();

        while (game.isPlaying()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }

        Assert.assertEquals("finished", game.gameResult);
    }

    @Test
    @Ignore
    public void test_GameThreadWithAbort() {
        // simple game thread to abort

        LoadGame game = new LoadGame(
                "game",
                "thread",
                DeckTestUtils.buildRandomDeckAndInitCards("GR", true, ""),
                DeckTestUtils.buildRandomDeckAndInitCards("GR", true, "")
        );
        game.gameStart();
        game.gameEnd(true); // abort -- close client thread
        Assert.assertEquals("aborted", game.gameResult);
    }

    @Test
    @Ignore
    public void test_GameThreadWithRealCards() {
        // simple game thread to the end with creatures cards

        LoadGame game = new LoadGame(
                "game",
                "thread",
                DeckTestUtils.buildRandomDeckAndInitCards("GR", false, ""),
                DeckTestUtils.buildRandomDeckAndInitCards("GR", false, "")
        );

        game.gameStart();
        game.gameWaitToStop();
        Assert.assertEquals("finished", game.gameResult);
    }

    @Test
    @Ignore
    public void test_GameThreadWithConcede() {
        // simple game thread with with concede

        LoadGame game = new LoadGame(
                "game",
                "thread",
                DeckTestUtils.buildRandomDeckAndInitCards("GR", true, ""),
                DeckTestUtils.buildRandomDeckAndInitCards("GR", true, "")
        );
        game.gameStart();

        try {
            Thread.sleep(3000);
        } catch (Throwable e) {
            //
        }
        game.gameConcede(1);
        game.gameWaitToStop();
        Assert.assertEquals("finished", game.gameResult);
        Assert.assertEquals("lose", game.player1.lastGameResult);
        Assert.assertEquals("win", game.player2.lastGameResult);
    }

    @Test
    @Ignore
    public void test_MultipleGames() {
        // multiple games until finish
        final int MAX_GAMES = 50; // games to run
        final boolean START_GAMES_AT_ONCE = true; // set true to run ALL games parallel (e.g. test max parallel limit)

        Instant startTime = Instant.now();

        // creating
        logger.info("creating " + MAX_GAMES + " games...");
        ArrayList<LoadGame> gamesList = new ArrayList<>();
        for (int i = 1; i <= MAX_GAMES; i++) {
            LoadGame game = new LoadGame(
                    "game" + i,
                    "game" + i,
                    DeckTestUtils.buildRandomDeckAndInitCards("GR", true, ""),
                    DeckTestUtils.buildRandomDeckAndInitCards("GR", true, "")
            );
            gamesList.add(game);

            if (!START_GAMES_AT_ONCE) {
                game.gameStart();
            }
        }
        logger.info("created " + gamesList.size() + " games");

        if (START_GAMES_AT_ONCE) {
            // running
            logger.info("start all " + gamesList.size() + "games at once...");
            for (LoadGame game : gamesList) {
                game.gameStart();
            }
            logger.info("started " + gamesList.size() + " games");
        }

        // waiting all games
        while (true) {
            boolean isComplete = true;
            for (LoadGame game : gamesList) {
                isComplete = isComplete && !game.isPlaying();
            }

            if (isComplete) {
                break;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }
        logger.info("completed " + gamesList.size() + " games");

        Instant endTime = Instant.now();
        logger.info("total time: " + ChronoUnit.SECONDS.between(startTime, endTime) + " secs");

        // check statuses
        ArrayList<String> errors = new ArrayList<>();
        for (LoadGame game : gamesList) {
            if (!"finished".equals(game.gameResult)) {
                errors.add(game.gameName + ": not finished, got " + game.gameResult);
            }
        }

        if (errors.size() > 0) {
            System.out.println("Not all games finished, found " + errors.size() + " errors: ");
            for (String s : errors) {
                System.out.println(s);
            }
            Assert.fail("Not all games finished");
        }
    }

    private Connection createSimpleConnection(String username) {
        Connection con = new Connection();
        con.setUsername(username);
        con.setHost(TEST_SERVER);
        con.setPort(TEST_PORT);
        Connection.ProxyType proxyType = Connection.ProxyType.valueByText(TEST_PROXY_TYPE);
        con.setProxyType(proxyType);
        return con;
    }

    private MatchOptions createSimpleGameOptions(String gameName, GameTypeView gameTypeView, Session session, PlayerType playersType) {
        MatchOptions options = new MatchOptions(gameName, gameTypeView.getName(), false, 2);

        options.getPlayerTypes().add(playersType);
        options.getPlayerTypes().add(playersType);

        options.setDeckType(session.getDeckTypes()[0]);
        options.setLimited(false);
        options.setAttackOption(MultiplayerAttackOption.MULTIPLE);
        options.setRange(RangeOfInfluence.ALL);
        options.setWinsNeeded(1);
        options.setMatchTimeLimit(MatchTimeLimit.MIN__15);
        return options;
    }

    private MatchOptions createSimpleGameOptionsForBots(GameTypeView gameTypeView, Session session) {
        return createSimpleGameOptions("Bots test game", gameTypeView, session, PlayerType.HUMAN);
    }

    private MatchOptions createSimpleGameOptionsForAI(GameTypeView gameTypeView, Session session, String gameName) {
        return createSimpleGameOptions(gameName, gameTypeView, session, PlayerType.COMPUTER_MAD);
    }

    private class LoadPlayer {

        String userName;
        Connection connection;
        SimpleMageClient client;
        Session session;
        UUID roomID;
        UUID createdTableID;
        UUID connectedTableID;
        DeckCardLists deckList;
        String lastGameResult = "";

        public LoadPlayer(String userPrefix) {
            this(userPrefix, false);
        }

        public LoadPlayer(String userPrefix, boolean joinGameChat) {
            this.userName = TEST_USER_NAME + "_" + userPrefix + "_" + RandomUtil.nextInt(10000);
            this.connection = createSimpleConnection(this.userName);
            this.client = new SimpleMageClient(joinGameChat);
            this.session = new SessionImpl(this.client);

            this.session.connect(this.connection);
            this.client.setSession(this.session);
            this.roomID = this.session.getMainRoomId();
        }

        public ArrayList<UsersView> getAllRoomUsers() {
            ArrayList<UsersView> res = new ArrayList<>();
            try {
                for (RoomUsersView roomUsers : this.session.getRoomUsers(this.roomID)) {
                    res.addAll(roomUsers.getUsersView());
                }
            } catch (MageRemoteException e) {
                logger.error(e);
            }
            return res;
        }

        public UsersView findUser(String userName) {
            for (UsersView user : this.getAllRoomUsers()) {
                if (user.getUserName().equals(userName)) {
                    return user;
                }
            }
            return null;
        }

        public Optional<TableView> getTable(UUID tableID) {
            return this.session.getTable(this.roomID, tableID);
        }

        public UUID createNewTable() {
            GameTypeView gameType = this.session.getGameTypes().get(0);
            MatchOptions gameOptions = createSimpleGameOptionsForBots(gameType, this.session);
            TableView game = this.session.createTable(this.roomID, gameOptions);
            this.createdTableID = game.getTableId();
            Assert.assertEquals(this.userName, game.getControllerName());

            connectToTable(this.createdTableID);

            return this.createdTableID;
        }

        public void connectToTable(UUID tableID) {
            Assert.assertTrue(this.session.joinTable(this.roomID, tableID, this.userName, PlayerType.HUMAN, 1, this.deckList, ""));
            this.connectedTableID = tableID;
        }

        public void startMatch() {
            Assert.assertNotNull(this.createdTableID);
            Assert.assertTrue(this.session.startMatch(this.roomID, this.createdTableID));
        }

        public void setDeckList(DeckCardLists deckList) {
            this.deckList = deckList;
        }

        public void disconnect() {
            this.session.disconnect(false);
        }

        public void concede() {
            this.client.setConcede(true);
        }
    }

    private class LoadGame {

        String gameName = null;
        Thread runningThread = null;
        LoadPlayer player1 = null;
        LoadPlayer player2 = null;
        Boolean abort = false;
        UUID tableID = null;
        String gameResult = null;

        public LoadGame(String gameName, String playerPrefix) {
            this(gameName, playerPrefix,
                    DeckTestUtils.buildRandomDeckAndInitCards("GR", true, ""),
                    DeckTestUtils.buildRandomDeckAndInitCards("GR", true, "")
            );
        }

        public LoadGame(String gameName, String playerPrefix, DeckCardLists deck1, DeckCardLists deck2) {
            this.gameName = gameName;

            player1 = new LoadPlayer(playerPrefix + "_" + 1);
            player1.setDeckList(deck1);

            player2 = new LoadPlayer(playerPrefix + "_" + 2);
            player2.setDeckList(deck2);
        }

        public void gameStart() {

            this.abort = false;

            runningThread = new Thread(() -> {
                try {
                    this.tableID = this.player1.createNewTable();
                    Assert.assertNotNull(this.tableID);
                    this.player2.connectToTable(this.tableID);
                    this.gameResult = "prepared";

                    this.player1.startMatch();
                    this.gameResult = "started";

                    // playing until game over or abort
                    while (!abort && (!player1.client.isGameOver() || !player2.client.isGameOver())) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            logger.error(e.getMessage(), e);
                        }
                    }

                    // game results
                    if (abort) {
                        this.gameResult = "aborted";
                    } else {
                        this.gameResult = "finished";
                    }
                    player1.lastGameResult = player1.client.getLastGameResult();
                    player2.lastGameResult = player2.client.getLastGameResult();
                } catch (Throwable e) {
                    this.gameResult = "error";
                    logger.fatal("Game thread " + this.gameName + " was stopped by error");
                    e.printStackTrace();
                }

                // disconnect on end
                this.player1.disconnect();
                this.player2.disconnect();

                // clean up after game
                this.runningThread = null;
                this.tableID = null;
            });

            runningThread.start();
        }

        public void gameEnd() {
            gameEnd(false);
        }

        public void gameEnd(Boolean waitToStop) {
            this.abort = true;

            if (waitToStop) {
                gameWaitToStop();
            }
        }

        public void gameConcede(int playerNumber) {
            switch (playerNumber) {
                case 1:
                    this.player1.concede();
                    break;
                case 2:
                    this.player2.concede();
                    break;
            }
        }

        public void gameWaitToStop() {
            while (this.runningThread != null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }

        public Boolean isPlaying() {
            return (this.runningThread != null);
        }
    }
}
