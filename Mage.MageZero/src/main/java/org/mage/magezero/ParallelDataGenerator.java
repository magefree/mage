package org.mage.magezero;

import mage.cards.decks.Deck;
import mage.cards.decks.DeckCardLists;
import mage.cards.decks.importer.DeckImporter;
import mage.cards.repository.CardInfo;
import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.game.*;
import mage.game.match.Match;
import mage.game.match.MatchOptions;
import mage.game.mulligan.MulliganType;
import mage.player.ai.*;
import mage.player.ai.encoder.FeatureMap;
import mage.player.ai.encoder.Features;
import mage.player.ai.encoder.LabeledState;
import mage.player.ai.encoder.StateEncoder;
import mage.player.ai.RemoteModelEvaluator;
import mage.players.Player;
import mage.util.RandomUtil;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLongArray;


/**
 * Base class for all RL data generators. see <a href="https://github.com/WillWroble/MageZero">MageZero repo</a>
 * for how to use
 *
 */
public class ParallelDataGenerator {

    // ================================== FILE PATHS ==================================
    protected static String SEEN_FEATURES_PATH = "seenFeatures.ser";
    protected static String FEATURE_TABLE_OUT = "FeatureTable.txt";
    protected static String WINRATE_OUT = "WinRates.txt";

    // ================================== GLOBAL FIELDS ==================================
    private final FeatureMap seenFeatures = new FeatureMap();
    public final AtomicInteger gameCount = new AtomicInteger(0);
    public final AtomicInteger winCount = new AtomicInteger(0);
    protected RemoteModelEvaluator remoteModelEvaluatorA = null;
    protected RemoteModelEvaluator remoteModelEvaluatorB = null;
    private final BlockingQueue<GameResult> LSQueue = new ArrayBlockingQueue<>(32);
    private final AtomicBoolean stop = new AtomicBoolean(false);
    private String deckNameA;
    private String deckNameB;

    protected static Map<String, DeckCardLists> loadedDecks = new HashMap<>(); // deck's cache
    protected static Map<String, CardInfo> loadedCardInfo = new HashMap<>(); // db card's cache
    private static final int maxGameTime = 20;



    protected static Logger logger = Logger.getLogger(ParallelDataGenerator.class);



    private static class GameResult {
        private final List<LabeledState> statesA;
        private final List<LabeledState> statesB;
        private final boolean didPlayerAWin;
        public GameResult(List<LabeledState> statesA, List<LabeledState> statesB, boolean didPlayerAWin) {
            this.statesA = statesA; this.statesB = statesB; this.didPlayerAWin = didPlayerAWin;
        }
        public List<LabeledState> getStatesA() { return statesA; }
        public List<LabeledState> getStatesB() { return statesB; }
        public boolean didPlayerAWin() { return didPlayerAWin; }
    }
    /**
     * run once
     */
    protected void loadAllFiles() {
        //reset writer thread
        stop.set(false);

        //reset counts
        winCount.set(0);
        gameCount.set(0);


        String modelUrlA = "http://" + Config.INSTANCE.server.host + ":" + Config.INSTANCE.server.port;
        try {
            remoteModelEvaluatorA = new RemoteModelEvaluator(modelUrlA);
        } catch (Exception e) {
            logger.warn("Failed to establish connection to network model A; falling back to offline mode");
            remoteModelEvaluatorA = null;
        }
        String modelUrlB = "http://" + Config.INSTANCE.server.host + ":" + Config.INSTANCE.server.opponentPort;
        try {
            remoteModelEvaluatorB = new RemoteModelEvaluator(modelUrlB);
        } catch (Exception e) {
            logger.warn("Failed to establish connection to network model B; falling back to offline mode");
            remoteModelEvaluatorB = null;
        }
        Features.useFeatureMap = Config.INSTANCE.logging.logFeatureHash;
    }
    public void print_known_feature_map() {
        try {
            FeatureMap fm = FeatureMap.loadFromFile(SEEN_FEATURES_PATH);
            fm.printFeatureTable(FEATURE_TABLE_OUT);
        } catch (IOException | ClassNotFoundException e) {
            logger.warn("couldn't load feature table");
            e.printStackTrace();
        }
    }
    public void test_single_game() {
        test_single_game(System.nanoTime());
    }
    /**
     * test function to run a single game for debugging purposes without saving any data.
     */
    public void test_single_game(long seed) {
        logger.info("\n=========================================");
        logger.info("       RUNNING SINGLE DEBUG GAME         ");
        logger.info("=========================================");
        loadAllFiles();
        ComputerPlayerMCTS2.SHOW_THREAD_INFO = true;


        // Use a thread-safe random number generator for the seed.
        logger.info("Using seed: " + seed);
        try {
            runSingleGame(seed);
        } catch (ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }
    public void generateData() {



        loadAllFiles();
        ComputerPlayerMCTS2.SHOW_THREAD_INFO = true;
        LabeledStateWriter fwA;
        LabeledStateWriter fwB;
        Thread writer;

        deckNameA = extractDeckName(Config.INSTANCE.playerA.deckPath);
        deckNameB = extractDeckName(Config.INSTANCE.playerB.deckPath);

        String fileA = "data/playerA/" + deckNameA + "_vs_" + deckNameB + ".hdf5";
        String fileB = "data/playerB/" + deckNameB + "_vs_" + deckNameA + ".hdf5";
        if(!Config.INSTANCE.playerA.outputFile.isEmpty()) {
            fileA = Config.INSTANCE.playerA.outputFile;
        }
        if(!Config.INSTANCE.playerB.outputFile.isEmpty()) {
            fileB = Config.INSTANCE.playerB.outputFile;
        }

        try {
            fwA = new LabeledStateWriter(fileA);
            fwB = new LabeledStateWriter(fileB);
            writer = getThread(fwA, fwB);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        logger.info("=========================================");
        logger.info("   STARTING DATA GENERATION     ");
        logger.info("=========================================");


        runSimulations(Config.INSTANCE.training.games);

        //end writer thread
        stop.set(true);
        try {
            writer.join();
        } catch (InterruptedException ignored) { Thread.currentThread().interrupt(); }

        saveFeatureMap(seenFeatures, SEEN_FEATURES_PATH);

        logger.info("Processing " + fwA.batchStates + " states.");
        logger.info("Final unique feature count from dataset: " + fwA.batchFeatures.size());


        if(Config.INSTANCE.logging.writeFinalWR) {
            writeResults(WINRATE_OUT, "WR with " + deckNameA + " vs " +
                    deckNameB + ": " + winCount.get() * 1.0 / gameCount.get() + " in " + gameCount.get() + " games");
        }


    }

    @NotNull
    private Thread getThread(LabeledStateWriter fwA, LabeledStateWriter fwB) {
        Thread writer = new Thread(() -> {
            try {
                do {
                    GameResult batch = LSQueue.poll(200, TimeUnit.MILLISECONDS);
                    if (batch != null) {
                        for (LabeledState s : batch.getStatesA()) fwA.writeRecord(s);
                        for (LabeledState s : batch.getStatesB()) fwB.writeRecord(s);
                        fwA.flush();
                        fwB.flush();
                    }
                } while (!stop.get() || !LSQueue.isEmpty());
            } catch (Exception e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            } finally {
                try { fwA.close(); } catch (Exception ignore) {}
                try { fwB.close(); } catch (Exception ignore) {}
            }
        }, "lz-writer");
        writer.start();
        return writer;
    }


    private void runSimulations(int numGames) {
        int availableCores = Runtime.getRuntime().availableProcessors();
        int poolSize = Config.INSTANCE.training.threads;
        logger.info(String.format("Simulating %d games. Using thread pool of size %d on %d available cores.", numGames, poolSize, availableCores));

        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        List<Callable<Boolean>> tasks = new ArrayList<>();
        AtomicLongArray startNs = new AtomicLongArray(numGames);

        for (int i = 0; i < numGames; i++) {
            final int idx = i;
            tasks.add(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    startNs.set(idx, System.nanoTime());
                    GameResult out = runSingleGame();
                    LSQueue.put(out);
                    return out.didPlayerAWin;
                }
            });
        }
        int wins = 0;
        int successfulGames = 0;
        int failedGames = 0;
        try {
            List<Future<Boolean>> futures = new ArrayList<>();
            for (Callable<Boolean> task : tasks) {
                futures.add(executor.submit(task));
            }
            executor.shutdown();
            for (int i = 0; i < futures.size(); i++) {
                Future<Boolean> future = futures.get(i);
                try {
                    // future.get() will block until the task is complete.
                    long start = startNs.get(i);
                    long remainingMs = start == 0 ? TimeUnit.MINUTES.toMillis(maxGameTime) : TimeUnit.MINUTES.toMillis(maxGameTime) - TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
                    Boolean result = future.get(Math.max(remainingMs, 0), TimeUnit.MILLISECONDS);
                    if (result) {
                        wins++;
                    }
                    successfulGames++;

                } catch (TimeoutException e) {
                    future.cancel(true);
                    failedGames++;
                    logger.error("Game timed out after " + maxGameTime + " minutes, cancelling.");
                } catch (ExecutionException e) {
                    failedGames++;
                    logger.error("A game simulation failed and its result will be ignored. Cause: " + e.getCause());
                    e.getCause().printStackTrace();
                }
                // The loop continues to the next future, ignoring the failed one.
            }
        } catch (InterruptedException e) {
            logger.error("Main simulation thread was interrupted. Shutting down.");
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        logger.info("--- Simulation Summary ---");
        logger.info(String.format("Total requested: %d games", numGames));
        logger.info(String.format("Successful: %d", successfulGames));
        logger.info(String.format("Failed: %d", failedGames));
        logger.info(String.format("Player A win rate: %.2f%% (%d/%d)", (100.0 * wins / successfulGames), wins, successfulGames));
    }
    private GameResult runSingleGame() throws ExecutionException {
        long seed = ThreadLocalRandom.current().nextLong();
        return runSingleGame(seed);
    }
    private GameResult runSingleGame(long gameSeed) throws ExecutionException {
        try {

            Game game;
            StateEncoder threadEncoderA = new StateEncoder();
            StateEncoder threadEncoderB = new StateEncoder();

            // Use a thread-safe random number generator for the seed.
            logger.info("Using seed: " + gameSeed);
            RandomUtil.setSeed(gameSeed);



            // All game objects are local to this thread to prevent race conditions.
            MatchOptions matchOptions = new MatchOptions("test match", "test game type", false);
            Match localMatch = new TwoPlayerMatch(matchOptions);
            game = new TwoPlayerDuel(MultiplayerAttackOption.LEFT, RangeOfInfluence.ONE, MulliganType.GAME_DEFAULT.getMulligan(0), 60, 20, 7);
            Player playerA = createLocalPlayer(game, "PlayerA", Config.INSTANCE.playerA.deckPath, localMatch);
            Player playerB = createLocalPlayer(game, "PlayerB", Config.INSTANCE.playerB.deckPath, localMatch);


            configurePlayer(playerA, threadEncoderA, threadEncoderB);
            configurePlayer(playerB, threadEncoderB, threadEncoderA);
            threadEncoderA.setAgent(playerA.getId());
            threadEncoderA.setOpponent(playerB.getId());
            threadEncoderA.perfectInfo = Config.INSTANCE.playerA.hiddenInfo.opponentHand;
            threadEncoderB.setAgent(playerB.getId());
            threadEncoderB.setOpponent(playerA.getId());
            threadEncoderB.perfectInfo = Config.INSTANCE.playerB.hiddenInfo.opponentHand;

            // Based on CardTestPlayerAPIImpl.java, this is the correct thread-safe
            // way to configure and run a game simulation.
            GameOptions options = new GameOptions();
            options.testMode = true;
            options.stopOnTurn = Config.INSTANCE.training.maxTurns;
            options.stopAtStep = PhaseStep.END_TURN;
            game.setGameOptions(options);


            // Start the game simulation. This is a blocking call that will run the game to completion.
            if(Config.INSTANCE.goesFirst.equals("player_a")) {
                game.setStartingPlayerId(playerA.getId());
            } else if(Config.INSTANCE.goesFirst.equals("player_b")) {
                game.setStartingPlayerId(playerB.getId());
            } else {
                int dieRoll;
                if(gameCount.get()==0) {
                    dieRoll = (int)(Thread.currentThread().getId()%2);
                } else {
                    dieRoll = gameCount.get() % 2;
                }
                if(dieRoll==0) {
                    logger.info("Player A won the die roll");
                    game.setStartingPlayerId(playerA.getId());
                } else {
                    logger.info("Player B won the die roll");
                    game.setStartingPlayerId(playerB.getId());
                }
            }
            game.start(null);
            boolean playerAWon = playerA.hasWon();
            //merge to the final features
            synchronized (seenFeatures) {
                seenFeatures.merge(threadEncoderA.featureMap);
            }
            if(playerA.hasWon()) winCount.incrementAndGet();
            logger.info("Game #" + gameCount.incrementAndGet() + " completed successfully");
            logger.info("Current WR: " + winCount.get()*1.0/gameCount.get());
            List<LabeledState> statesA = generateLabeledStatesForGame(threadEncoderA, playerAWon, Config.INSTANCE.playerA.mcts.tdDiscount);
            List<LabeledState> statesB = generateLabeledStatesForGame(threadEncoderB, !playerAWon, Config.INSTANCE.playerB.mcts.tdDiscount);
            return new GameResult(statesA, statesB, playerAWon);
        } catch (Exception e) {
            logger.error("Caught an internal AI/Game exception in a worker thread. Ignoring this game. Cause: " + e.getMessage());
            e.printStackTrace();
            throw new ExecutionException("Worker thread failed - ignoring", e);
        }
    }

    protected void configurePlayer(Player player, StateEncoder encoder, StateEncoder opponentEncoder) {
        if (player.getRealPlayer() instanceof ComputerPlayerMCTS2) {
            ComputerPlayerMCTS2 mcts2  = (ComputerPlayerMCTS2) player.getRealPlayer();
            mcts2.stateEncoder = encoder;
            if(player.getName().equals("PlayerA")) {
                mcts2.nn = remoteModelEvaluatorA;
                mcts2.noPolicyPriority = !Config.INSTANCE.playerA.priors.priority;
                mcts2.noPolicyTarget = !Config.INSTANCE.playerA.priors.target;
                mcts2.noPolicyUse = !Config.INSTANCE.playerA.priors.binary;
                mcts2.noPolicyOpponent = !Config.INSTANCE.playerA.priors.opponent;
                mcts2.noNoise = !Config.INSTANCE.playerA.noise.enabled;
                mcts2.allowMulligans = Config.INSTANCE.playerA.gameplay.mulligans;
                mcts2.searchBudget = Config.INSTANCE.playerA.mcts.searchBudget;
                mcts2.searchTimeout = (double) Config.INSTANCE.playerA.mcts.timeoutMs /1000;
                mcts2.autoTap = !Config.INSTANCE.playerA.gameplay.manualTap;
                mcts2.priorTemp = Config.INSTANCE.playerA.priors.priorTemperature;
                mcts2.allowDuplicates = !Config.INSTANCE.playerA.mcts.pruneDuplicateStates;
                if(remoteModelEvaluatorA == null || Config.INSTANCE.playerA.mcts.offlineMode) mcts2.offlineMode = true;
            } else {
                mcts2.nn = remoteModelEvaluatorB;
                mcts2.noPolicyPriority = !Config.INSTANCE.playerB.priors.priority;
                mcts2.noPolicyTarget = !Config.INSTANCE.playerB.priors.target;
                mcts2.noPolicyUse = !Config.INSTANCE.playerB.priors.binary;
                mcts2.noPolicyOpponent = !Config.INSTANCE.playerB.priors.opponent;
                mcts2.noNoise = !Config.INSTANCE.playerB.noise.enabled;
                mcts2.allowMulligans = Config.INSTANCE.playerB.gameplay.mulligans;
                mcts2.searchBudget = Config.INSTANCE.playerB.mcts.searchBudget;
                mcts2.searchTimeout = (double) Config.INSTANCE.playerB.mcts.timeoutMs /1000;
                mcts2.autoTap = !Config.INSTANCE.playerB.gameplay.manualTap;
                mcts2.priorTemp = Config.INSTANCE.playerB.priors.priorTemperature;
                mcts2.allowDuplicates = !Config.INSTANCE.playerB.mcts.pruneDuplicateStates;
                if(remoteModelEvaluatorB == null || Config.INSTANCE.playerB.mcts.offlineMode) mcts2.offlineMode = true;
            }
        } else if (player.getRealPlayer() instanceof ComputerPlayer8) {
            ComputerPlayer8 cp8 = (ComputerPlayer8) player.getRealPlayer();
            cp8.setEncoder(opponentEncoder);
            cp8.autoTap = true;
            if(player.getName().equals("PlayerA")) {
                cp8.allowMulligans = Config.INSTANCE.playerA.gameplay.mulligans;
            } else {
                cp8.allowMulligans = Config.INSTANCE.playerB.gameplay.mulligans;
            }
        } else  {
            logger.warn("unexpected player type" + player.getRealPlayer().getClass().getName());
        }
    }
    private List<LabeledState> generateLabeledStatesForGame(StateEncoder encoder, boolean didPlayerAWin, double tdDiscount) {
        int N = encoder.labeledStates.size();

        double discountedFuture = didPlayerAWin ? 1.0 : -1.0;
        for (int i = N-1; i >= 0; i--) {
            discountedFuture = (tdDiscount * discountedFuture) + (encoder.labeledStates.get(i).stateScore*(1- tdDiscount));
            encoder.labeledStates.get(i).resultLabel = discountedFuture;
        }
        return encoder.labeledStates;

    }
    public static String extractDeckName(String deckPath) {
        // Handle both forward and backslashes
        int lastSlash = Math.max(deckPath.lastIndexOf('\\'), deckPath.lastIndexOf('/'));
        String fileName = deckPath.substring(lastSlash + 1);

        // Remove the .dck extension if present
        if (fileName.toLowerCase().endsWith(".dck")) {
            fileName = fileName.substring(0, fileName.length() - 4);
        }

        return fileName;
    }
    void saveFeatureMap(FeatureMap fm, String filePath) {
        FeatureMap baseMap = new FeatureMap();
        try {
            baseMap = FeatureMap.loadFromFile(filePath);
        } catch (IOException | ClassNotFoundException e) {
            logger.warn("couldn't load feature map");
        }
        try {
            int initialFeatureSize = baseMap.getFeatureCount();
            int initialIndexSize = baseMap.getIndexCount();
            logger.info("Initial feature count: " + initialFeatureSize);
            logger.info("Initial index count: " + initialIndexSize);
            baseMap.merge(fm);
            logger.info("Global unique feature count: " + baseMap.getFeatureCount());
            logger.info("Global index count: " + baseMap.getIndexCount());
            logger.info("Features added: " + (baseMap.getFeatureCount() - initialFeatureSize));
            logger.info("Indices added: " + (baseMap.getIndexCount() - initialIndexSize));
            baseMap.saveToFile(filePath);
        } catch (IOException e) {
            logger.warn("couldn't save feature map");
        }
        try {
            baseMap.printFeatureTable(FEATURE_TABLE_OUT);
        } catch (IOException e) {
            logger.warn("couldn't print feature table to text file");
        }
    }
    protected Player createLocalPlayer(Game game, String name, String deckPath, Match match) throws GameException {
        Player player = createPlayer(name, game.getRangeOfInfluence());
        player.setTestMode(true);


        logger.debug("Loading deck...");
        DeckCardLists list;
        if (loadedDecks.containsKey(deckPath)) {
            list = loadedDecks.get(deckPath);
        } else {
            list = DeckImporter.importDeckFromFile(deckPath, true);
            loadedDecks.put(deckPath, list);
        }
        Deck deck = Deck.load(list, false, false, loadedCardInfo);
        logger.debug("Done!");
        if (deck.getMaindeckCards().size() < 40) {
            logger.error(deckPath);
            throw new IllegalArgumentException("Couldn't load deck, deck size=" + deck.getMaindeckCards().size());
        }

        game.loadCards(deck.getCards(), player.getId());
        game.loadCards(deck.getSideboard(), player.getId());
        game.addPlayer(player, deck);
        match.addPlayer(player, deck); // fake match

        return player;
    }
    public void writeResults(String filePath, String results) {

        try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(
                Paths.get(filePath),
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,   // create if it doesn't exist
                StandardOpenOption.APPEND))) // append if it does
        {
            out.println(results);

        } catch (IOException ex) {
            logger.error("Error while writing results: " + ex.getMessage(), ex);
        }
    }
    // This is the correct override to use for choosing our AI types.
    protected Player createPlayer(String name, RangeOfInfluence rangeOfInfluence) {
        if (name.equals("PlayerA")) {
            if(Config.INSTANCE.playerA.type.equals("minimax")) {
                ComputerPlayer8 t8 = new ComputerPlayer8(name, RangeOfInfluence.ONE, 6);
                return t8;
            }
            if(Config.INSTANCE.playerA.type.equals("mcts")) {
                ComputerPlayerMCTS2 mcts2 = new ComputerPlayerMCTS2(name, RangeOfInfluence.ONE, 6);
                return mcts2;
            }

        } else {
            if(Config.INSTANCE.playerB.type.equals("minimax")) {
                ComputerPlayer8 t8 = new ComputerPlayer8(name, RangeOfInfluence.ONE, 6);
                return t8;
            }
            if(Config.INSTANCE.playerB.type.equals("mcts")) {
                ComputerPlayerMCTS2 mcts2 = new ComputerPlayerMCTS2(name, RangeOfInfluence.ONE, 6);
                return mcts2;
            }
        }
        logger.error("unsupported player type");
        return null;
    }
}