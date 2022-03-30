package mage.server;

import mage.view.AbilityPickerView;
import mage.view.CardsView;
import mage.view.ChatMessage;
import mage.view.DeckView;
import mage.view.DraftPickView;
import mage.view.DraftView;
import mage.view.GameClientMessage;
import mage.view.GameEndView;
import mage.view.GameView;
import mage.view.UserRequestMessage;
import mage.remote.ClientCallback;
import mage.remote.ClientCallbackImpl;
import mage.remote.messages.MessageType;
import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.DeckValidatorFactory;
import mage.cards.repository.CardScanner;
import mage.cards.repository.PluginClassloaderRegistery;
import mage.cards.repository.RepositoryUtil;
import mage.game.draft.RateCard;
import mage.game.match.MatchType;
import mage.game.tournament.TournamentType;
import mage.remote.interfaces.MageServer;
import mage.remote.Connection;
import mage.server.draft.CubeFactory;
import mage.server.game.GameFactory;
import mage.server.game.PlayerFactory;
import mage.server.managers.ManagerFactory;
import mage.server.record.UserStatsRepository;
import mage.server.tournament.TournamentFactory;
import mage.server.util.*;
import mage.server.util.config.GamePlugin;
import mage.server.util.config.Plugin;
import mage.utils.MageVersion;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.BindException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.*;
import mage.choices.Choice;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class Main {

    private static final Logger logger = Logger.getLogger(Main.class);
    private static final MageVersion version = new MageVersion(Main.class);

    private static final String testModeArg = "-testMode=";
    private static final String fastDBModeArg = "-fastDbMode=";
    private static final String adminPasswordArg = "-adminPassword=";
    /**
     * The property that holds the path to the configuration file. Defaults to "config/config.xml".
     * <p>
     * To set up a different one, start the application with the java option "-Dxmage.config.path=&lt;path&gt;"
     */
    private static final String configPathProp = "xmage.config.path";

    private static final File pluginFolder = new File("plugins");
    private static final File extensionFolder = new File("extensions");
    private static final String defaultConfigPath = Paths.get("config", "config.xml").toString();
    private static final int PING_CYCLES = 10;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static final PluginClassLoader classLoader = new PluginClassLoader();
    private static ClientCallback clientCallback;
    private static MageServer server;
    private static ManagerFactory managerFactory;

    // special test mode:
    // - fast game buttons;
    // - cheat commands;
    // - no deck validation;
    // - simplified registration and login (no password check);
    // - debug main menu for GUI and rendering testing;
    private static boolean testMode;

    private static boolean fastDbMode;
            
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        logger.info("Starting MAGE server version " + version);
        logger.info("Logging level: " + logger.getEffectiveLevel());
        logger.info("Default charset: " + Charset.defaultCharset());
        String adminPassword = "";
        for (String arg : args) {
            if (arg.startsWith(testModeArg)) {
                testMode = Boolean.valueOf(arg.replace(testModeArg, ""));
            } else if (arg.startsWith(adminPasswordArg)) {
                adminPassword = arg.replace(adminPasswordArg, "");
                adminPassword = SystemUtil.sanitize(adminPassword);
            } else if (arg.startsWith(fastDBModeArg)) {
                fastDbMode = Boolean.valueOf(arg.replace(fastDBModeArg, ""));
            }
        }

        final String configPath = Optional.ofNullable(System.getProperty(configPathProp))
                .orElse(defaultConfigPath);

        logger.info(String.format("Reading configuration from path=%s", configPath));
        final ConfigWrapper config = new ConfigWrapper(ConfigFactory.loadFromFile(configPath));


        if (config.isAuthenticationActivated()) {
            logger.info("Check authorized user DB version ...");
            if (!AuthorizedUserRepository.getInstance().checkAlterAndMigrateAuthorizedUser()) {
                logger.fatal("Failed to start server.");
                return;
            }
            logger.info("Done.");
        }

        // db init and updates checks (e.g. cleanup cards db on new version)
        RepositoryUtil.bootstrapLocalDb();
        logger.info("Done.");

        logger.info("Loading extension packages...");
        if (!extensionFolder.exists()) {
            if (!extensionFolder.mkdirs()) {
                logger.error("Could not create extensions directory.");
            }
        }
        File[] extensionDirectories = extensionFolder.listFiles();
        List<ExtensionPackage> extensions = new ArrayList<>();
        if (extensionDirectories != null) {
            for (File f : extensionDirectories) {
                if (f.isDirectory()) {
                    try {
                        logger.info(" - Loading extension from " + f);
                        extensions.add(ExtensionPackageLoader.loadExtension(f));
                    } catch (IOException e) {
                        logger.error("Could not load extension in " + f + '!', e);
                    }
                }
            }
        }
        logger.info("Done.");

        if (!extensions.isEmpty()) {
            logger.info("Registering custom sets...");
            for (ExtensionPackage pkg : extensions) {
                for (ExpansionSet set : pkg.getSets()) {
                    logger.info("- Loading " + set.getName() + " (" + set.getCode() + ')');
                    Sets.getInstance().addSet(set);
                }
                PluginClassloaderRegistery.registerPluginClassloader(pkg.getClassLoader());
            }
            logger.info("Done.");
        }

        logger.info("Loading cards...");
        if (fastDbMode) {
            CardScanner.scanned = true;
        } else {
            CardScanner.scan();
        }
        logger.info("Done.");

        // cards preload with ratings
        if (RateCard.PRELOAD_CARD_RATINGS_ON_STARTUP) {
            RateCard.bootstrapCardsAndRatings();
            logger.info("Done.");
        }

        logger.info("Updating user stats DB...");
        UserStatsRepository.instance.updateUserStats();
        logger.info("Done.");
        deleteSavedGames();

        int gameTypes = 0;
        for (GamePlugin plugin : config.getGameTypes()) {
            gameTypes++;
            GameFactory.instance.addGameType(plugin.getName(), loadGameType(plugin), loadPlugin(plugin));
        }

        int tourneyTypes = 0;
        for (GamePlugin plugin : config.getTournamentTypes()) {
            tourneyTypes++;
            TournamentFactory.instance.addTournamentType(plugin.getName(), loadTournamentType(plugin), loadPlugin(plugin));
        }

        int playerTypes = 0;
        for (Plugin plugin : config.getPlayerTypes()) {
            playerTypes++;
            PlayerFactory.instance.addPlayerType(plugin.getName(), loadPlugin(plugin));
        }

        int cubeTypes = 0;
        for (Plugin plugin : config.getDraftCubes()) {
            cubeTypes++;
            CubeFactory.instance.addDraftCube(plugin.getName(), loadPlugin(plugin));
        }

        int deckTypes = 0;
        for (Plugin plugin : config.getDeckTypes()) {
            deckTypes++;
            DeckValidatorFactory.instance.addDeckType(plugin.getName(), loadPlugin(plugin));
        }

        for (ExtensionPackage pkg : extensions) {
            for (Map.Entry<String, Class> entry : pkg.getDraftCubes().entrySet()) {
                logger.info("Loading extension: [" + entry.getKey() + "] " + entry.getValue().toString());
                cubeTypes++;
                CubeFactory.instance.addDraftCube(entry.getKey(), entry.getValue());
            }
            for (Map.Entry<String, Class> entry : pkg.getDeckTypes().entrySet()) {
                logger.info("Loading extension: [" + entry.getKey() + "] " + entry.getValue().toString());
                deckTypes++;
                DeckValidatorFactory.instance.addDeckType(entry.getKey(), entry.getValue());
            }
        }

        logger.info("Config - max seconds idle: " + config.getMaxSecondsIdle());
        logger.info("Config - max game threads: " + config.getMaxGameThreads());
        logger.info("Config - max AI opponents: " + config.getMaxAiOpponents());
        logger.info("Config - min usr name le.: " + config.getMinUserNameLength());
        logger.info("Config - max usr name le.: " + config.getMaxUserNameLength());
        logger.info("Config - min pswrd length: " + config.getMinPasswordLength());
        logger.info("Config - max pswrd length: " + config.getMaxPasswordLength());
        logger.info("Config - inv.usr name pat: " + config.getInvalidUserNamePattern());
        logger.info("Config - save game active: " + (config.isSaveGameActivated() ? "true" : "false"));
        logger.info("Config - backlog size    : " + config.getBacklogSize());
        logger.info("Config - lease period    : " + config.getLeasePeriod());
        logger.info("Config - sock wrt timeout: " + config.getSocketWriteTimeout());
        logger.info("Config - max pool size   : " + config.getMaxPoolSize());
        logger.info("Config - num accp.threads: " + config.getNumAcceptThreads());
        logger.info("Config - second.bind port: " + config.getSecondaryBindPort());
        logger.info("Config - auth. activated : " + (config.isAuthenticationActivated() ? "true" : "false"));
        logger.info("Config - mailgun api key : " + config.getMailgunApiKey());
        logger.info("Config - mailgun domain  : " + config.getMailgunDomain());
        logger.info("Config - mail smtp Host  : " + config.getMailSmtpHost());
        logger.info("Config - mail smtpPort   : " + config.getMailSmtpPort());
        logger.info("Config - mail user       : " + config.getMailUser());
        logger.info("Config - mail passw. len.: " + config.getMailPassword().length());
        logger.info("Config - mail from addre.: " + config.getMailFromAddress());
        logger.info("Config - google account  : " + config.getGoogleAccount());

        logger.info("Loaded game types: " + gameTypes
                + ", tourneys: " + tourneyTypes
                + ", players: " + playerTypes
                + ", cubes: " + cubeTypes
                + ", decks: " + deckTypes);
        if (gameTypes == 0) {
            logger.error("ERROR, can't load any game types. Check your config.xml in server's config folder (example: old jar versions after update).");
        }

        Connection connection = new Connection("&maxPoolSize=" + config.getMaxPoolSize());
        connection.setHost(config.getServerAddress());
        connection.setPort(config.getPort());
        try {
            managerFactory = new MainManagerFactory(config);
            server = new MageServerImpl(managerFactory, adminPassword, testMode);
            clientCallback = new ClientCallbackImpl(server);
            clientCallback.start(config.getPort(), config.isUseSSL());
            logger.info("Started MAGE server - listening on " + connection.toString());

            if (testMode) {
                logger.info("MAGE server running in test mode");
            }
        } catch (BindException ex) {
            logger.fatal("Failed to start server - " + config.getServerName() + " : check that another server is not already running", ex);
        } catch (Exception ex) {
            logger.fatal("Failed to start server - " + connection.toString(), ex);
        }
        
    }

    private static Class<?> loadPlugin(Plugin plugin) {
        try {
            classLoader.addURL(new File(pluginFolder, plugin.getJar()).toURI().toURL());
            logger.debug("Loading plugin: " + plugin.getClassName());
            return Class.forName(plugin.getClassName(), true, classLoader);
        } catch (ClassNotFoundException ex) {
            logger.warn(new StringBuilder("Plugin not Found: ").append(plugin.getClassName()).append(" - ").append(plugin.getJar()).append(" - check plugin folder"), ex);
        } catch (MalformedURLException ex) {
            logger.fatal("Error loading plugin " + plugin.getJar(), ex);
        }
        return null;
    }

    private static MatchType loadGameType(GamePlugin plugin) {
        try {
            classLoader.addURL(new File(pluginFolder, plugin.getJar()).toURI().toURL());
            logger.debug("Loading game type: " + plugin.getClassName());
            return (MatchType) Class.forName(plugin.getTypeName(), true, classLoader).getConstructor().newInstance();
        } catch (ClassNotFoundException ex) {
            logger.warn("Game type not found:" + plugin.getJar() + " - check plugin folder", ex);
        } catch (Exception ex) {
            logger.fatal("Error loading game type " + plugin.getJar(), ex);
        }
        return null;
    }

    private static TournamentType loadTournamentType(GamePlugin plugin) {
        try {
            classLoader.addURL(new File(pluginFolder, plugin.getJar()).toURI().toURL());
            return (TournamentType) Class.forName(plugin.getTypeName(), true, classLoader).getConstructor().newInstance();
        } catch (ClassNotFoundException ex) {
            logger.warn("Tournament type not found:" + plugin.getName() + " / " + plugin.getJar() + " - check plugin folder", ex);
        } catch (Exception ex) {
            logger.fatal("Error loading game type " + plugin.getJar(), ex);
        }
        return null;
    }

    private static void deleteSavedGames() {
        File directory = new File("saved/");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File[] files = directory.listFiles(
                (dir, name) -> name.endsWith(".game")
        );
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }

    public static MageVersion getVersion() {
        return version;
    }

    public static boolean isTestMode() {
        return testMode;
    }
    
    public static void sendChatMessage(String sessionId, UUID chatId, ChatMessage message) {
        clientCallback.sendChatMessage(sessionId, chatId, message);
    }
        
    public static void joinedTable(String sessionId, UUID roomId, UUID tableId, UUID chatId, boolean owner, boolean tournament) {
        clientCallback.joinedTable(sessionId, roomId, tableId, chatId, owner, tournament);
    }
    
    public static void gameStarted(String sessionId, UUID gameId, UUID playerId) {
        clientCallback.gameStarted(sessionId, gameId, playerId);
    }

    public static void initGame(String sessionId, UUID gameId, GameView gameView) {
        clientCallback.initGame(sessionId, gameId, gameView);
    }

    public static void gameAsk(String sessionId, UUID gameId, GameView gameView, String question, Map<String, Serializable> options) {
        clientCallback.gameAsk(sessionId, gameId, gameView, question, options);
    }
    
    public static void gameTarget(String sessionId, UUID gameId, GameView gameView, String question, CardsView cardView, Set<UUID> targets, boolean required, Map<String, Serializable> options) {
        clientCallback.gameTarget(sessionId, gameId, gameView, question, cardView, targets, required, options);
    }

    public static void gameSelect(String sessionId, UUID gameId, GameView gameView, String message, Map<String, Serializable> options) {
        clientCallback.gameSelect(sessionId, gameId, gameView, message, options);
    }

    public static void gameChooseAbility(String sessionId, UUID gameId, AbilityPickerView abilities) {
        clientCallback.gameChooseAbility(sessionId, gameId, abilities);
    }
    
    public static void gameChoosePile(String sessionId, UUID gameId, String message, CardsView pile1, CardsView pile2) {
        clientCallback.gameChoosePile(sessionId, gameId, message, pile1, pile2);
    }

    public static void gameChooseChoice(String sessionId, UUID gameId, Choice choice) {
        clientCallback.gameChooseChoice(sessionId, gameId, choice);
    }

    public static void gamePlayMana(String sessionId, UUID gameId, GameView gameView, String message, Map<String, Serializable> options) {
        clientCallback.gamePlayMana(sessionId, gameId, gameView, message, options);
    }
    
    public static void gamePlayXMana(String sessionId, UUID gameId, GameView gameView, String message) {
        clientCallback.gamePlayXMana(sessionId, gameId, gameView, message);
    }

    public static void gameSelectAmount(String sessionId, UUID gameId, GameView gameView, String message, int min, int max) {
        clientCallback.gameSelectAmount(sessionId, gameId, gameView, message, min, max);
    }
    
    public static void gameMultiAmount(String sessionId, UUID gameId,GameView gameView, Map<String, Serializable> option, List<String> messages, int min, int max) {
        clientCallback.gameMultiAmount(sessionId, gameId, gameView, option, messages, min, max);
    }

    public static void endGameInfo(String sessionId, UUID gameId, GameEndView view) {
        clientCallback.endGameInfo(sessionId, gameId, view);
    }

    public static void userRequestDialog(String sessionId, UUID gameId, UserRequestMessage userRequestMessage) {
        clientCallback.userRequestDialog(sessionId, gameId, userRequestMessage);
    }
    
    public static void gameUpdate(String sessionId, UUID gameId, GameView view) {
        clientCallback.gameUpdate(sessionId, gameId, view);
    }

    public static void gameInform(String sessionId, UUID gameId, GameClientMessage message) {
        clientCallback.gameInform(sessionId, gameId, message);
    }

    public static void gameInformPersonal(String sessionId, UUID gameId, GameClientMessage message) {
        clientCallback.gameInformPersonal(sessionId, gameId, message);
    }

    public static void gameOver(String sessionId, UUID gameId, String message) {
        clientCallback.gameOver(sessionId, gameId, message);
    }
    
    public static void gameError(String sessionId, UUID gameId, String message) {
        clientCallback.gameError(sessionId, gameId, message);
    }
    
    public static void sideboard(String sessionId, UUID tableId, DeckView deck, int time, boolean limited) {
        clientCallback.sideboard(sessionId, tableId, deck, time, limited);
    }
    
    public static void viewLimitedDeck(String sessionId, UUID tableId, DeckView deck, int time, boolean limited) {
        clientCallback.viewLimitedDeck(sessionId, tableId, deck, time, limited);
    }
    
    public static void viewSideboard(String sessionId, UUID gameId, UUID targetPlayerId) {
        clientCallback.viewSideboard(sessionId, gameId, targetPlayerId);
    }

    public static void construct(String sessionId, UUID tableId, DeckView deck, int time) {
        clientCallback.construct(sessionId, tableId, deck, time);
    }

    public static void tournamentStarted(String sessionId, UUID tournamentId, UUID playerId) {
        clientCallback.tournamentStarted(sessionId, tournamentId, playerId);
    }

    public static void showTournament(String sessionId, UUID tournamentId) {
        clientCallback.showTournament(sessionId, tournamentId);
    }
    
    public static void startDraft(String sessionId, UUID draftId, UUID playerId) {
        clientCallback.startDraft(sessionId, draftId, playerId);
    }

    public static void draftInit(String sessionId, UUID draftId, DraftPickView draftPickView) {
        clientCallback.draftInit(sessionId, draftId, draftPickView);
    }

    public static void draftUpdate(String sessionId, UUID draftId, DraftView draftView) {
        clientCallback.draftUpdate(sessionId, draftId, draftView);
    }

    public static void draftOver(String sessionId, UUID draftId) {
        clientCallback.draftOver(sessionId, draftId);
    }

    public static void draftPick(String sessionId, UUID draftId, DraftPickView draftPickView) {
        clientCallback.draftPick(sessionId, draftId, draftPickView);
    }
    
    public static void informClient(String sessionId, String title, String message, MessageType type) {
        clientCallback.informClient(sessionId, title, message, type);
    }

    public static void watchGame(String sessionId, UUID gameId, UUID chatId, GameView game) {
        clientCallback.watchGame(sessionId, gameId, chatId, game);
    }
    
    public static void replayGame(String sessionId, UUID gameId) {
        clientCallback.replayGame(sessionId, gameId);
    }
    
    public static void replayInit(String sessionId, UUID gameId, GameView gameView) {
        clientCallback.replayInit(sessionId, gameId, gameView);
    }

    public static void replayDone(String sessionId, UUID gameId, String result) {
        clientCallback.replayDone(sessionId, gameId, result);
    }

    public static void replayUpdate(String sessionId, UUID gameId, GameView gameView) {
        clientCallback.replayUpdate(sessionId, gameId, gameView);
    }
    
    public static ClientCallback getClientCallback(){
        return clientCallback;
    }
    
    public static MageServer getServer() {
        return server;
    }
}
