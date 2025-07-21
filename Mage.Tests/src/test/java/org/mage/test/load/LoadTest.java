package org.mage.test.load;

import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckCardLists;
import mage.cards.decks.importer.DeckImporter;
import mage.cards.repository.CardScanner;
import mage.constants.*;
import mage.game.match.MatchOptions;
import mage.players.PlayerType;
import mage.remote.Connection;
import mage.remote.MageRemoteException;
import mage.remote.Session;
import mage.remote.SessionImpl;
import mage.util.RandomUtil;
import mage.util.ThreadUtils;
import mage.util.XmageThreadFactory;
import mage.view.*;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.utils.DeckTestUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

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
    private static final String TEST_USER_NAME_GLOBAL_PREFIX = "t_";
    private static final Boolean TEST_SHOW_GAME_LOGS_AS_HTML = false; // html is original format with full data, but can be too bloated
    private static final String TEST_AI_GAME_MODE = "Freeform Commander Free For All";
    private static final String TEST_AI_DECK_TYPE = "Variant Magic - Freeform Commander";
    private static final String TEST_AI_RANDOM_DECK_SETS = ""; // sets list for random generated decks (GRN,ACR for specific sets, empty for all sets, PELP for lands only - communication test)
    private static final String TEST_AI_RANDOM_DECK_COLORS_FOR_EMPTY_GAME = "GR";  // colors list for deck generation, empty for all colors
    private static final String TEST_AI_RANDOM_DECK_COLORS_FOR_AI_GAME = "WUBRG";
    private static String TEST_AI_CUSTOM_DECK_PATH_1 = ""; // custom deck file instead random for player 1 (empty for random)
    private static String TEST_AI_CUSTOM_DECK_PATH_2 = ""; // custom deck file instead random for player 2 (empty for random)

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
        LoadPlayer monitor = new LoadPlayer("mon", "mon");
        Assert.assertTrue(monitor.session.isConnected());
        int startUsersCount = monitor.getAllRoomUsers().size();
        int minimumSleepTime = 2000;

        // user 1
        LoadPlayer player1 = new LoadPlayer("1", "p1 ");
        Thread.sleep(minimumSleepTime);
        Assert.assertEquals("Can't see users count change 1", startUsersCount + 1, monitor.getAllRoomUsers().size());
        Assert.assertNotNull("Can't find user 1", monitor.findUser(player1.userName));

        // user 2
        LoadPlayer player2 = new LoadPlayer("2", "p2 ");
        Thread.sleep(minimumSleepTime);
        Assert.assertEquals("Can't see users count change 2", startUsersCount + 2, monitor.getAllRoomUsers().size());
        Assert.assertNotNull("Can't find user 2", monitor.findUser(player2.userName));

        player1.disconnect();
        player2.disconnect();
        monitor.disconnect();
    }

    @Test
    @Ignore
    public void test_TwoUsersPlayGameUntilEnd() {

        // monitor other players
        LoadPlayer monitor = new LoadPlayer("mon", "mon");

        // users
        LoadPlayer player1 = new LoadPlayer("user1", "p1");
        LoadPlayer player2 = new LoadPlayer("user2", "p2");

        // game by user 1
        GameTypeView gameType = prepareGameType(player1.session);
        MatchOptions gameOptions = createSimpleGameOptionsForBots(gameType, player1.session);
        TableView game = player1.session.createTable(player1.roomID, gameOptions);
        UUID tableId = game.getTableId();
        Assert.assertEquals(player1.userName, game.getControllerName());

        DeckCardLists deckList = loadGameDeck(1, TEST_AI_RANDOM_DECK_COLORS_FOR_EMPTY_GAME, true, TEST_AI_RANDOM_DECK_SETS);
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
            logger.info(checkGame.get().getTableState());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }

        player1.disconnect();
        player2.disconnect();
        monitor.disconnect();
    }

    public void playTwoAIGame(String gameName, Integer taskNumber, TasksProgress tasksProgress, long randomSeed, String deckColors, String deckAllowedSets, LoadTestGameResult gameResult) {
        Assert.assertFalse("need deck colors", deckColors.isEmpty());

        // monitor and game source
        LoadPlayer monitor = new LoadPlayer("mon", true, gameName + ", mon");

        // game by monitor
        GameTypeView gameType = prepareGameType(monitor.session);
        MatchOptions gameOptions = createSimpleGameOptionsForAI(gameType, monitor.session, gameName);
        TableView game = monitor.session.createTable(monitor.roomID, gameOptions);
        UUID tableId = game.getTableId();

        // deck load
        RandomUtil.setSeed(randomSeed);
        DeckCardLists deckList1 = loadGameDeck(1, deckColors, deckAllowedSets.equals("PELP"), deckAllowedSets);
        DeckCardLists deckList2 = loadGameDeck(2, deckColors, deckAllowedSets.equals("PELP"), deckAllowedSets);

        // join AI
        Assert.assertTrue(monitor.session.joinTable(monitor.roomID, tableId, "ai_1", PlayerType.COMPUTER_MAD, 5, deckList1, ""));
        Assert.assertTrue(monitor.session.joinTable(monitor.roomID, tableId, "ai_2", PlayerType.COMPUTER_MAD, 5, deckList2, ""));

        // match start
        Assert.assertTrue(monitor.session.startMatch(monitor.roomID, tableId));

        // playing until game over
        gameResult.start();
        boolean startToWatching = false;
        Date lastActivity = new Date();
        while (true) {
            GameView gameView = monitor.client.getLastGameView();

            TableView checkGame = monitor.getTable(tableId).orElse(null);
            TableState state = (checkGame == null ? null : checkGame.getTableState());

            String finishInfo = "";
            if (state == TableState.FINISHED) {
                finishInfo = gameView == null || gameView.getStep() == null ? "??" : gameView.getStep().getStepShortText().toLowerCase(Locale.ENGLISH);
            }
            tasksProgress.update(taskNumber, finishInfo, gameView == null ? 0 : gameView.getTurn());
            String globalProgress = tasksProgress.getInfo();
            monitor.client.updateGlobalProgress(globalProgress);

            // disconnected by unknown reason TODO: research the reason
            if (!monitor.session.isConnected()) {
                logger.error(monitor.userName + " disconnected from server on game " + gameView);
                if (gameView != null) {
                    gameResult.finish(gameView);
                }
                break;
            }

            if (gameView != null && checkGame != null) {
                logger.info(globalProgress + ", " + checkGame.getTableName() + ": ---");
                logger.info(String.format("%s, %s: turn %d, step %s, state %s",
                        globalProgress,
                        checkGame.getTableName(),
                        gameView.getTurn(),
                        gameView.getStep().toString(),
                        state
                ));
            }

            if (state == TableState.FINISHED) {
                gameResult.finish(gameView);
                break;
            }

            if (!startToWatching && state == TableState.DUELING) {
                Assert.assertTrue(monitor.session.watchGame(checkGame.getGames().iterator().next()));
                startToWatching = true;
            }

            if (gameView != null && checkGame != null) {
                gameView.getPlayers()
                        .stream()
                        .sorted(Comparator.comparing(PlayerView::getName))
                        .forEach(p -> {
                            String activeInfo = "";
                            if (Objects.equals(gameView.getActivePlayerId(), p.getPlayerId())) {
                                activeInfo = " (active)";
                            }
                            logger.info(String.format("%s, %s, status: %s - Life=%d; Lib=%d; CRs=%d%s;",
                                    globalProgress,
                                    checkGame.getTableName(),
                                    p.getName(),
                                    p.getLife(),
                                    p.getLibraryCount(),
                                    p.getBattlefield().values().stream().filter(CardView::isCreature).mapToInt(x -> 1).sum(),
                                    activeInfo
                            ));
                        });
                logger.info(globalProgress + ", " + checkGame.getTableName() + ": ---");
            }

            // ping to keep active session
            if ((new Date().getTime() - lastActivity.getTime()) / 1000 > 10) {
                monitor.session.ping();
                lastActivity = new Date();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }

        // all done, can disconnect now
        monitor.disconnect();
    }

    @Test
    @Ignore
    public void test_TwoAIPlayGame_One() {
        LoadTestGameResultsList gameResults = new LoadTestGameResultsList();
        long randomSeed = RandomUtil.nextInt();
        LoadTestGameResult gameResult = gameResults.createGame(0, "test game", randomSeed);
        TasksProgress tasksProgress = new TasksProgress();
        tasksProgress.update(1, "", 0);
        playTwoAIGame("Single AI game", 1, tasksProgress, randomSeed, "WGUBR", TEST_AI_RANDOM_DECK_SETS, gameResult);

        printGameResults(gameResults);
    }

    @Test
    @Ignore
    public void test_TwoAIPlayGame_Debug() {
        // usage:
        // - run test_TwoAIPlayGame_Multiple
        // - wait some freeze games and stop
        // - find active table and game in server's games history
        // - set deck files here
        // - run single game and debug on server side like stack dump
        long randomSeed = 1708140198; // 0 for random
        TEST_AI_CUSTOM_DECK_PATH_1 = ".\\deck_player_1.dck";
        TEST_AI_CUSTOM_DECK_PATH_2 = ".\\deck_player_2.dck";

        LoadTestGameResultsList gameResults = new LoadTestGameResultsList();
        if (randomSeed == 0) {
            randomSeed = RandomUtil.nextInt();
        }
        LoadTestGameResult gameResult = gameResults.createGame(0, "test game", randomSeed);
        TasksProgress tasksProgress = new TasksProgress();
        tasksProgress.update(1, "", 0);
        playTwoAIGame("Single AI game", 1, tasksProgress, randomSeed, "WGUBR", TEST_AI_RANDOM_DECK_SETS, gameResult);

        printGameResults(gameResults);
    }

    @Test
    @Ignore
    public void test_TwoAIPlayGame_Multiple() {
        // play multiple AI games with CLIENT side code (catch every GameView changes from the server)

        int singleGameSID = 0; // set sid for same deck games, set 0 for random decks

        int runTotalGames = 10;
        int runMaxParallelGames = 5; // use 1 to run one by one (warning, it's limited by COMPUTER_MAX_THREADS_FOR_SIMULATIONS)

        ExecutorService executerService;
        if (runMaxParallelGames > 1) {
            executerService = Executors.newFixedThreadPool(runMaxParallelGames, new XmageThreadFactory(ThreadUtils.THREAD_PREFIX_TESTS_AI_VS_AI_GAMES));
        } else {
            executerService = Executors.newSingleThreadExecutor(new XmageThreadFactory(ThreadUtils.THREAD_PREFIX_TESTS_AI_VS_AI_GAMES));
        }

        // save random seeds for repeated results (in decks generating)
        List<Integer> seedsList = new ArrayList<>();
        if (singleGameSID != 0) {
            for (int i = 1; i <= runTotalGames; i++) {
                seedsList.add(singleGameSID);
            }
        } else {
            for (int i = 1; i <= runTotalGames; i++) {
                seedsList.add(RandomUtil.nextInt());
            }
        }

        LoadTestGameResultsList gameResults = new LoadTestGameResultsList();
        try {
            TasksProgress tasksProgress = new TasksProgress();
            List<Future> gameTasks = new ArrayList<>();
            for (int i = 0; i < seedsList.size(); i++) {
                int gameIndex = i;
                tasksProgress.update(gameIndex + 1, "", 0);
                long randomSeed = seedsList.get(i);
                logger.info("Game " + (i + 1) + " of " + seedsList.size() + ", RANDOM seed: " + randomSeed);
                Future gameTask = executerService.submit(() -> {
                    String gameName = String.format("AI game #%02d", gameIndex + 1);
                    LoadTestGameResult gameResult = gameResults.createGame(gameIndex + 1, gameName, randomSeed);
                    playTwoAIGame(gameName, gameIndex + 1, tasksProgress, randomSeed, TEST_AI_RANDOM_DECK_COLORS_FOR_AI_GAME, TEST_AI_RANDOM_DECK_SETS, gameResult);
                });
                gameTasks.add(gameTask);

                if (runMaxParallelGames <= 1) {
                    // run one by one
                    gameTask.get();
                }
            }
            if (runMaxParallelGames > 1) {
                // run parallel
                executerService.shutdown();
                Assert.assertTrue("running too long", executerService.awaitTermination(1, TimeUnit.HOURS));
            }

            // check errors
            int errorsCount = 0;
            for (Future task : gameTasks) {
                try {
                    task.get();
                } catch (InterruptedException | ExecutionException e) {
                    errorsCount++;
                    logger.error(e, e);
                }
            }
            if (errorsCount > 0) {
                Assert.fail(String.format("Found %d critical errors in running games, see logs above", errorsCount));
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e, e);
            Assert.fail("Game stops too early: " + e);
        }

        printGameResults(gameResults);
    }

    @Test
    @Ignore
    public void test_GameThread() {
        // simple game thread to the end

        LoadGame game = new LoadGame(
                "game",
                "u",
                loadGameDeck(1, TEST_AI_RANDOM_DECK_COLORS_FOR_EMPTY_GAME, true, TEST_AI_RANDOM_DECK_SETS),
                loadGameDeck(2, TEST_AI_RANDOM_DECK_COLORS_FOR_EMPTY_GAME, true, TEST_AI_RANDOM_DECK_SETS)
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
                "u",
                loadGameDeck(1, TEST_AI_RANDOM_DECK_COLORS_FOR_EMPTY_GAME, true, TEST_AI_RANDOM_DECK_SETS),
                loadGameDeck(2, TEST_AI_RANDOM_DECK_COLORS_FOR_EMPTY_GAME, true, TEST_AI_RANDOM_DECK_SETS)
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
                "u",
                loadGameDeck(1, TEST_AI_RANDOM_DECK_COLORS_FOR_EMPTY_GAME, false, TEST_AI_RANDOM_DECK_SETS),
                loadGameDeck(2, TEST_AI_RANDOM_DECK_COLORS_FOR_EMPTY_GAME, false, TEST_AI_RANDOM_DECK_SETS)
        );

        game.gameStart();
        game.gameWaitToStop();
        Assert.assertEquals("finished", game.gameResult);
    }

    @Test
    @Ignore
    public void test_GameThreadWithConcede() {
        // simple game thread with concede

        LoadGame game = new LoadGame(
                "game",
                "u",
                loadGameDeck(1, TEST_AI_RANDOM_DECK_COLORS_FOR_EMPTY_GAME, true, TEST_AI_RANDOM_DECK_SETS),
                loadGameDeck(2, TEST_AI_RANDOM_DECK_COLORS_FOR_EMPTY_GAME, true, TEST_AI_RANDOM_DECK_SETS)
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
        // for load testing only (example: memory usage, max games limit, network usage)
        // play multiple EMPTY games with SERVER side only (without AI),
        // all players on the server side, you don't get any GameView updates here

        final int MAX_GAMES = 10; // games to run
        final boolean START_GAMES_AT_ONCE = true; // set true to run ALL games parallel (e.g. test max parallel limit)

        Instant startTime = Instant.now();

        // creating
        logger.info("creating " + MAX_GAMES + " games...");
        ArrayList<LoadGame> gamesList = new ArrayList<>();
        for (int i = 1; i <= MAX_GAMES; i++) {
            LoadGame game = new LoadGame(
                    "game" + i,
                    "u" + i,
                    loadGameDeck(1, TEST_AI_RANDOM_DECK_COLORS_FOR_EMPTY_GAME, true, TEST_AI_RANDOM_DECK_SETS),
                    loadGameDeck(2, TEST_AI_RANDOM_DECK_COLORS_FOR_EMPTY_GAME, true, TEST_AI_RANDOM_DECK_SETS)
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
        MatchOptions options = new MatchOptions(gameName, gameTypeView.getName(), true);

        options.getPlayerTypes().add(playersType);
        options.getPlayerTypes().add(playersType);

        Assert.assertTrue("Can't find game type on the server: " + TEST_AI_DECK_TYPE,
                Arrays.asList(session.getDeckTypes()).contains(TEST_AI_DECK_TYPE));
        options.setDeckType(TEST_AI_DECK_TYPE);
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

    private static class TasksProgress {

        private String info;
        private final Map<Integer, String> finishes = new LinkedHashMap<>(); // game number, finish on step
        private final Map<Integer, Integer> turns = new LinkedHashMap<>(); // game number, current turn

        synchronized public void update(Integer taskNumber, String newFinish, Integer newTurn) {
            String oldFinish = this.finishes.getOrDefault(taskNumber, "");
            Integer oldTurn = this.turns.getOrDefault(taskNumber, 0);
            if (!this.finishes.containsKey(taskNumber)
                    || !Objects.equals(oldFinish, newFinish)
                    || !Objects.equals(oldTurn, newTurn)) {
                this.finishes.put(taskNumber, newFinish);
                this.turns.put(taskNumber, newTurn);
                updateInfo();
            }
        }

        private void updateInfo() {
            // example: progress 33% [20.cd, 21.__, 17.__], AI game #09: ---

            int completed = this.finishes.values().stream().mapToInt(x -> x.isEmpty() ? 0 : 1).sum();
            int completedPercent = this.finishes.size() == 0 ? 0 : completed * 100 / this.finishes.size();

            String res = this.finishes.keySet().stream()
                    .map(taskNumber -> {
                        String turn = String.format("%02d", this.turns.getOrDefault(taskNumber, 0));
                        String finishInfo = this.finishes.getOrDefault(taskNumber, "");
                        if (finishInfo.isEmpty()) {
                            // active
                            return turn + ".__";
                        } else {
                            // done
                            return turn + "." + finishInfo;
                        }
                    })
                    .collect(Collectors.joining(", "));
            this.info = String.format("progress %d%% [%s]", completedPercent, res);
        }

        public String getInfo() {
            return this.info;
        }
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

        public LoadPlayer(String userPrefix, String logsPrefix) {
            this(userPrefix, false, logsPrefix);
        }

        public LoadPlayer(String userPrefix, boolean joinGameChat, String logsPrefix) {
            this.userName = TEST_USER_NAME_GLOBAL_PREFIX + userPrefix + "_" + RandomUtil.nextInt(10000);
            this.connection = createSimpleConnection(this.userName);
            this.client = new SimpleMageClient(joinGameChat, logsPrefix, TEST_SHOW_GAME_LOGS_AS_HTML);
            this.session = new SessionImpl(this.client);

            this.session.connectStart(this.connection);
            this.client.setSession(this.session);
            this.roomID = this.session.getMainRoomId();

            Assert.assertTrue("client must be connected to server", this.session.isConnected());
            Assert.assertTrue("client must get server data", this.session.isServerReady());
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
            GameTypeView gameType = prepareGameType(this.session);
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
            this.session.connectStop(false, false);
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
                    loadGameDeck(1, TEST_AI_RANDOM_DECK_COLORS_FOR_EMPTY_GAME, true, TEST_AI_RANDOM_DECK_SETS),
                    loadGameDeck(2, TEST_AI_RANDOM_DECK_COLORS_FOR_EMPTY_GAME, true, TEST_AI_RANDOM_DECK_SETS)
            );
        }

        public LoadGame(String gameName, String playerPrefix, DeckCardLists deck1, DeckCardLists deck2) {
            this.gameName = gameName;

            player1 = new LoadPlayer(playerPrefix + "_" + 1, playerPrefix + "_1");
            player1.setDeckList(deck1);

            player2 = new LoadPlayer(playerPrefix + "_" + 2, playerPrefix + "_2");
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

    private static class LoadTestGameResult {
        int index;
        String name;
        long randomSeed;
        Date timeStarted;
        Date timeEnded = null;
        GameView finalGameView = null;

        public LoadTestGameResult(int index, String name, long randomSeed) {
            this.index = index;
            this.name = name;
            this.randomSeed = randomSeed;
        }

        public void start() {
            this.timeStarted = new Date();
        }

        public void finish(GameView finalGameView) {
            this.timeEnded = new Date();
            this.finalGameView = finalGameView;
        }

        public int getLife1() {
            return finalGameView == null ? 0 : this.finalGameView.getPlayers().get(0).getLife();
        }

        public int getLife2() {
            return finalGameView == null ? 0 : this.finalGameView.getPlayers().get(1).getLife();
        }

        public int getCreaturesCount1() {
            return finalGameView == null ? 0 : this.finalGameView.getPlayers().get(0).getBattlefield().values()
                    .stream()
                    .filter(CardView::isCreature)
                    .mapToInt(x -> 1)
                    .sum();
        }

        public int getCreaturesCount2() {
            return finalGameView == null ? 0 : this.finalGameView.getPlayers().get(1).getBattlefield().values()
                    .stream()
                    .filter(CardView::isCreature)
                    .mapToInt(x -> 1)
                    .sum();
        }

        public int getTurn() {
            return finalGameView == null ? 0 : this.finalGameView.getTurn();
        }

        public String getTurnInfo() {
            int turn = finalGameView == null ? 0 : this.finalGameView.getTurn();
            String stepInfo = finalGameView == null ? "??" : this.finalGameView.getStep().getStepShortText().toLowerCase(Locale.ENGLISH);
            return String.format("%02d.%s", turn, stepInfo);
        }

        public int getDurationMs() {
            return finalGameView == null ? 0 : ((int) ((this.timeEnded.getTime() - this.timeStarted.getTime())));
        }

        public int getTotalErrorsCount() {
            return finalGameView == null ? 0 : this.finalGameView.getTotalErrorsCount();
        }

        public int getTotalEffectsCount() {
            return finalGameView == null ? 0 : this.finalGameView.getTotalEffectsCount();
        }

        public int getGameCycle() {
            return finalGameView == null ? 0 : this.finalGameView.getGameCycle();
        }
    }

    private static class LoadTestGameResultsList extends HashMap<Integer, LoadTestGameResult> {

        // index, name, random sid, game cycle, errors, effects, turn, life p1, life p2, creatures p1, creatures p2, =time, sec, ~time, sec
        private static final String tableFormatHeader = "|%-10s|%-15s|%-20s|%-10s|%-10s|%-10s|%-10s|%-10s|%-10s|%-15s|%-15s|%-15s|%-15s|%n";
        private static final String tableFormatData = "|%-10s|%15s|%20s|%10s|%10s|%10s|%10s|%10s|%10s|%15s|%15s|%15s|%15s|%n";

        public LoadTestGameResult createGame(int index, String name, long randomSeed) {
            if (this.containsKey(index)) {
                throw new IllegalArgumentException("Game with index " + index + " already exists");
            }
            LoadTestGameResult res = new LoadTestGameResult(index, name, randomSeed);
            this.put(index, res);
            return res;
        }

        public void printResultHeader() {
            List<String> data = Arrays.asList(
                    "index",
                    "name",
                    "random sid",
                    "game cycles",
                    "errors",
                    "effects",
                    "turn",
                    "life p1",
                    "life p2",
                    "creatures p1",
                    "creatures p2",
                    "=time, sec",
                    "~time, sec"
            );
            System.out.printf(tableFormatHeader, data.toArray());
        }

        public void printResultData() {
            this.values().forEach(this::printResultData);
        }

        public void printResultData(LoadTestGameResult gameResult) {
            List<String> data = Arrays.asList(
                    String.valueOf(gameResult.index), //"index",
                    gameResult.name, //"name",
                    String.valueOf(gameResult.randomSeed), // "random sid",
                    String.valueOf(gameResult.getGameCycle()), // "game cycles",
                    String.valueOf(gameResult.getTotalErrorsCount()), // "errors",
                    String.valueOf(gameResult.getTotalEffectsCount()), // "effects",
                    gameResult.getTurnInfo(), //"turn",
                    String.valueOf(gameResult.getLife1()), //"life p1",
                    String.valueOf(gameResult.getLife2()), //"life p2",
                    String.valueOf(gameResult.getCreaturesCount1()), //"creatures p1",
                    String.valueOf(gameResult.getCreaturesCount2()), //"creatures p2",
                    String.format("%.3f", (float) gameResult.getDurationMs() / 1000), //"time, sec",
                    String.format("%.3f", ((float) gameResult.getDurationMs() / 1000) / gameResult.getTurn()) //"per turn, sec"
            );
            System.out.printf(tableFormatData, data.toArray());
        }

        public void printResultTotal() {
            List<String> data = Arrays.asList(
                    "TOTAL/AVG", //"index",
                    String.valueOf(this.size()), //"name",
                    "total, secs: " + String.format("%.3f", (float) this.getTotalDurationMs() / 1000), // "random sid",
                    "~" + this.getAvgGameCycle(), // game cycles
                    "=" + this.getTotalErrorsCount(), // errors
                    "~" + this.getAvgEffectsCount(), // effects
                    "~" + this.getAvgTurn(), // turn
                    "~" + this.getAvgLife1(), // life p1
                    "~" + this.getAvgLife2(), // life p2
                    "~" + this.getAvgCreaturesCount1(), // creatures p1
                    "~" + this.getAvgCreaturesCount2(), // creatures p2
                    "~" + String.format("%.3f", (float) this.getAvgDurationMs() / 1000), // time, sec
                    "~" + String.format("%.3f", (float) this.getAvgDurationPerTurnMs() / 1000) // time per turn, sec
            );
            System.out.printf(tableFormatData, data.toArray());
        }

        private int getTotalErrorsCount() {
            return this.values().stream().mapToInt(LoadTestGameResult::getTotalErrorsCount).sum();
        }

        private int getAvgGameCycle() {
            return this.size() == 0 ? 0 : this.values().stream().mapToInt(LoadTestGameResult::getGameCycle).sum() / this.size();
        }

        private int getAvgEffectsCount() {
            return this.size() == 0 ? 0 : this.values().stream().mapToInt(LoadTestGameResult::getTotalEffectsCount).sum() / this.size();
        }

        private int getAvgTurn() {
            return this.size() == 0 ? 0 : this.values().stream().mapToInt(LoadTestGameResult::getTurn).sum() / this.size();
        }

        private int getAvgLife1() {
            return this.size() == 0 ? 0 : this.values().stream().mapToInt(LoadTestGameResult::getLife1).sum() / this.size();
        }

        private int getAvgLife2() {
            return this.size() == 0 ? 0 : this.values().stream().mapToInt(LoadTestGameResult::getLife2).sum() / this.size();
        }

        private int getAvgCreaturesCount1() {
            return this.size() == 0 ? 0 : this.values().stream().mapToInt(LoadTestGameResult::getCreaturesCount1).sum() / this.size();
        }

        private int getAvgCreaturesCount2() {
            return this.size() == 0 ? 0 : this.values().stream().mapToInt(LoadTestGameResult::getCreaturesCount2).sum() / this.size();
        }

        private int getTotalDurationMs() {
            return this.values().stream().mapToInt(LoadTestGameResult::getDurationMs).sum();
        }

        private int getAvgDurationMs() {
            return this.size() == 0 ? 0 : this.values().stream().mapToInt(LoadTestGameResult::getDurationMs).sum() / this.size();
        }

        private int getAvgDurationPerTurnMs() {
            int turns = getAvgTurn();
            return turns == 0 ? 0 : getAvgDurationMs() / getAvgTurn();
        }
    }

    private GameTypeView prepareGameType(Session session) {
        GameTypeView gameType = session.getGameTypes()
                .stream()
                .filter(m -> m.getName().equals(TEST_AI_GAME_MODE))
                .findFirst()
                .orElse(null);
        Assert.assertNotNull("Can't find game type on the server: " + TEST_AI_GAME_MODE, gameType);
        return gameType;
    }

    private DeckCardLists loadGameDeck(int playerNumber, String deckColors, boolean onlyBasicLands, String allowedSets) {
        // priority for custom deck file
        DeckCardLists deckList = null;
        switch (playerNumber) {
            case 1:
                if (!TEST_AI_CUSTOM_DECK_PATH_1.isEmpty()) {
                    deckList = DeckImporter.importDeckFromFile(TEST_AI_CUSTOM_DECK_PATH_1, false);
                    Assert.assertFalse("Can't load custom deck 1 from " + TEST_AI_CUSTOM_DECK_PATH_1, deckList.getCards().isEmpty());
                }
                break;
            case 2:
                if (!TEST_AI_CUSTOM_DECK_PATH_2.isEmpty()) {
                    deckList = DeckImporter.importDeckFromFile(TEST_AI_CUSTOM_DECK_PATH_2, false);
                    Assert.assertFalse("Can't load custom deck 2 from " + TEST_AI_CUSTOM_DECK_PATH_2, deckList.getCards().isEmpty());
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported player number " + playerNumber);
        }

        if (deckList == null) {
            deckList = DeckTestUtils.buildRandomDeckAndInitCards(deckColors, onlyBasicLands, allowedSets);
        }

        return deckList;
    }

    private void printGameResults(LoadTestGameResultsList gameResults) {
        System.out.println();
        gameResults.printResultHeader();
        gameResults.printResultData();
        gameResults.printResultTotal();

        if (gameResults.getAvgTurn() == 0) {
            Assert.fail("Games can't start, make sure you are run a localhost server before running current load test");
        }
    }
}
