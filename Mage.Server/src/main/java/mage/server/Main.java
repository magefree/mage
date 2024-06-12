package mage.server;

import mage.cards.ExpansionSet;
import mage.cards.RateCard;
import mage.cards.Sets;
import mage.cards.decks.DeckValidatorFactory;
import mage.cards.repository.CardScanner;
import mage.cards.repository.PluginClassloaderRegistery;
import mage.cards.repository.RepositoryUtil;
import mage.game.match.MatchType;
import mage.game.tournament.TournamentType;
import mage.interfaces.MageServer;
import mage.remote.Connection;
import mage.remote.SessionImpl;
import mage.server.draft.CubeFactory;
import mage.server.game.GameFactory;
import mage.server.game.PlayerFactory;
import mage.server.managers.ConfigSettings;
import mage.server.managers.ManagerFactory;
import mage.server.record.UserStatsRepository;
import mage.server.tournament.TournamentFactory;
import mage.server.util.ConfigFactory;
import mage.server.util.ConfigWrapper;
import mage.server.util.PluginClassLoader;
import mage.server.util.ServerMessagesUtil;
import mage.server.util.config.GamePlugin;
import mage.server.util.config.Plugin;
import mage.utils.MageVersion;
import mage.utils.SystemUtil;
import org.apache.log4j.Logger;
import org.jboss.remoting.*;
import org.jboss.remoting.callback.InvokerCallbackHandler;
import org.jboss.remoting.callback.ServerInvokerCallbackHandler;
import org.jboss.remoting.transport.Connector;
import org.jboss.remoting.transport.bisocket.BisocketServerInvoker;
import org.jboss.remoting.transport.socket.SocketWrapper;
import org.jboss.remoting.transporter.TransporterClient;
import org.jboss.remoting.transporter.TransporterServer;
import org.w3c.dom.Element;

import javax.management.MBeanServer;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public final class Main {

    private static final Logger logger = Logger.getLogger(Main.class);
    private static final MageVersion version = new MageVersion(Main.class);

    // Server threads:
    // - worker threads: creates for each connection, controls by maxPoolSize;
    // - acceptor threads: processing requests to start a new connection, controls by numAcceptThreads;
    // - backlog threads: processing waiting queue if maxPoolSize reached, controls by backlogSize;
    // Usage hints:
    // - if maxPoolSize reached then new clients will freeze in connection dialog until backlog queue overflow;
    // - so for active server must increase maxPoolSize to big value like "max online * 10" or enable worker idle timeout
    // - worker idle time will free unused worker thread, so new client can connect;
    private static final int SERVER_WORKER_THREAD_IDLE_TIMEOUT_SECS = 5 * 60; // no needs to config, must be enabled for all

    // arg settings can be setup by run script or IDE's program arguments like -xxx=yyy
    // prop settings can be setup by -Dxxx=yyy in the launcher
    // priority: default setting -> prop setting -> arg setting
    private static final String testModeArg = "-testMode=";
    private static final String testModeProp = "xmage.testMode";
    private static final String detailsModeArg = "-detailsMode=";
    private static final String detailsModeProp = "xmage.detailsMode";
    private static final String adminPasswordArg = "-adminPassword=";
    private static final String adminPasswordProp = "xmage.adminPassword";
    private static final String configPathProp = "xmage.config.path";

    private static final File pluginFolder = new File("plugins");
    private static final File extensionFolder = new File("extensions");
    private static final String defaultConfigPath = Paths.get("config", "config.xml").toString();

    public static final PluginClassLoader classLoader = new PluginClassLoader();
    private static TransporterServer server;

    // Special test mode:
    // - fast game buttons;
    // - cheat commands;
    // - no deck validation;
    // - load any deck in sideboarding;
    // - simplified registration and login (no password check);
    // - debug main menu for GUI and rendering testing (must use -debug arg for client app);
    private static boolean testMode;
    private static boolean detailsMode;

    public static void main(String[] args) {
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        logger.info("Starting MAGE SERVER version: " + version);
        logger.info("Java version: " + System.getProperty("java.version"));
        logger.info("Logging level: " + logger.getEffectiveLevel());
        logger.info("Default charset: " + Charset.defaultCharset());
        String adminPassword = "";

        // enable test mode by default for developer build (if you run it from source code)
        testMode |= version.isDeveloperBuild();
        detailsMode = false;

        // settings by properties (-Dxxx=yyy from a launcher)
        if (System.getProperty(testModeProp) != null) {
            testMode = Boolean.parseBoolean(System.getProperty(testModeProp));
        }
        if (System.getProperty(adminPasswordProp) != null) {
            adminPassword = SystemUtil.sanitize(System.getProperty(adminPasswordProp));
        }
        if (System.getProperty(detailsModeProp) != null) {
            detailsMode = Boolean.parseBoolean(System.getProperty(detailsModeProp));
        }
        final String configPath;
        if (System.getProperty(configPathProp) != null) {
            configPath = System.getProperty(configPathProp);
        } else {
            configPath = defaultConfigPath;
        }

        // settings by program arguments (-xxx=yyy from a command line)
        for (String arg : args) {
            if (arg.startsWith(testModeArg)) {
                testMode = Boolean.parseBoolean(arg.replace(testModeArg, ""));
            } else if (arg.startsWith(adminPasswordArg)) {
                adminPassword = arg.replace(adminPasswordArg, "");
                adminPassword = SystemUtil.sanitize(adminPassword);
            }
            if (arg.startsWith(detailsModeArg)) {
                detailsMode = Boolean.parseBoolean(arg.replace(detailsModeArg, ""));
            }
        }

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
        CardScanner.scan();
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
        logger.info("Config - users registration: " + (config.isAuthenticationActivated() ? "true" : "false"));
        logger.info("Config - users anon: " + (!config.isAuthenticationActivated() ? "true" : "false"));
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
        final ManagerFactory managerFactory = new MainManagerFactory(config);
        try {
            // Parameter: serializationtype => jboss
            InvokerLocator serverLocator = new InvokerLocator(connection.getURI());
            if (!isAlreadyRunning(config, serverLocator)) {
                server = new MageTransporterServer(
                        managerFactory,
                        serverLocator,
                        new MageServerImpl(managerFactory, adminPassword, testMode, detailsMode),
                        MageServer.class.getName(),
                        new MageServerInvocationHandler(managerFactory)
                );
                server.start();
                logger.info("Started MAGE server - listening on " + connection.toString());

                if (testMode) {
                    logger.info("MAGE server running in test mode");
                }
                initStatistics();
            } else {
                logger.fatal("Unable to start MAGE server - another server is already started");
            }
        } catch (Exception ex) {
            logger.fatal("Failed to start server - " + connection.toString(), ex);
        }
    }

    static void initStatistics() {
        ServerMessagesUtil.instance.setStartDate(System.currentTimeMillis());
    }

    static boolean isAlreadyRunning(ConfigSettings config, InvokerLocator serverLocator) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put(SocketWrapper.WRITE_TIMEOUT, String.valueOf(config.getSocketWriteTimeout()));
        metadata.put("generalizeSocketException", "true");
        try {
            MageServer testServer = (MageServer) TransporterClient.createTransporterClient(serverLocator.getLocatorURI(), MageServer.class, metadata);
            if (testServer != null) {
                testServer.getServerState(); // check connection
                return true;
            }
        } catch (Throwable t) {
            // assume server is not running
        }
        return false;
    }

    /**
     * Network, server side: connection monitoring and error processing
     */
    static class MageServerConnectionListener implements ConnectionListener {

        private final ManagerFactory managerFactory;

        public MageServerConnectionListener(ManagerFactory managerFactory) {
            this.managerFactory = managerFactory;
        }

        @Override
        public void handleConnectionException(Throwable throwable, Client client) {
            String sessionId = client.getSessionId();
            Session session = managerFactory.sessionManager().getSession(sessionId).orElse(null);
            if (session == null) {
                logger.debug("Connection error, session not found : " + sessionId + " - " + throwable);
                return;
            }

            // fill by user's info
            StringBuilder sessionInfo = new StringBuilder();
            User user = managerFactory.userManager().getUser(session.getUserId()).orElse(null);
            if (user != null) {
                sessionInfo.append(user.getName()).append(" [").append(user.getGameInfo()).append(']');
            } else {
                sessionInfo.append("[no user]");
            }
            sessionInfo.append(" at ").append(session.getHost()).append(", sessionId: ").append(session.getId());

            // check disconnection reason
            // lease ping is inner jboss feature to check connection status
            // xmage ping is app's feature to check connection and send additional data like ping statistics
            if (throwable instanceof ClientDisconnectedException) {
                // client called a disconnect command (full disconnect without tables keep)
                // no need to keep session
                logger.info("CLIENT DISCONNECTED - " + sessionInfo);
                logger.debug("- cause: client called disconnect command");
                managerFactory.sessionManager().disconnect(client.getSessionId(), DisconnectReason.DisconnectedByUser, true);
            } else if (throwable == null) {
                // lease timeout (ping), so server lost connection with a client
                // must keep tables
                logger.info("LOST CONNECTION - " + sessionInfo);
                logger.debug("- cause: lease expired");
                managerFactory.sessionManager().disconnect(client.getSessionId(), DisconnectReason.LostConnection, true);
            } else {
                // unknown error
                // must keep tables
                logger.info("LOST CONNECTION - " + sessionInfo);
                logger.debug("- cause: unknown error - " + throwable);
                managerFactory.sessionManager().disconnect(client.getSessionId(), DisconnectReason.LostConnection, true);
            }
        }
    }

    /**
     * Network, server side: data transport layer
     */
    static class MageTransporterServer extends TransporterServer {

        protected Connector connector;

        public MageTransporterServer(ManagerFactory managerFactory, InvokerLocator locator, Object target, String subsystem, MageServerInvocationHandler serverInvocationHandler) throws Exception {
            super(locator, target, subsystem);
            connector.addInvocationHandler("callback", serverInvocationHandler); // commands processing

            // connection monitoring and errors processing
            connector.setLeasePeriod(managerFactory.configSettings().getLeasePeriod());
            connector.addConnectionListener(new MageServerConnectionListener(managerFactory));
        }

        @Override
        protected Connector getConnector(InvokerLocator locator, Map config, Element xmlConfig) throws Exception {
            Connector c = super.getConnector(locator, config, xmlConfig);
            this.connector = c;
            return c;
        }
    }

    /**
     * Network, server side: commands layer for execute (creates one thread per each client connection)
     */
    static class MageServerInvocationHandler implements ServerInvocationHandler {

        private final ManagerFactory managerFactory;

        public MageServerInvocationHandler(ManagerFactory managerFactory) {
            this.managerFactory = managerFactory;
        }

        @Override
        public void setMBeanServer(MBeanServer server) {
            /**
             * An MBean is a managed Java object, similar to a JavaBeans
             * component, that follows the design patterns set forth in the JMX
             * specification. An MBean can represent a device, an application,
             * or any resource that needs to be managed. MBeans expose a
             * management interface that consists of the following:
             *
             * A set of readable or writable attributes, or both. A set of
             * invokable operations. A self-description.
             *
             */
            if (server != null) {
                logger.info("Default domain: " + server.getDefaultDomain());
            }
        }

        @Override
        public void setInvoker(ServerInvoker invoker) {
            // connection settings
            ((BisocketServerInvoker) invoker).setSecondaryBindPort(managerFactory.configSettings().getSecondaryBindPort());
            ((BisocketServerInvoker) invoker).setBacklog(managerFactory.configSettings().getBacklogSize());
            ((BisocketServerInvoker) invoker).setNumAcceptThreads(managerFactory.configSettings().getNumAcceptThreads());
            ((BisocketServerInvoker) invoker).setIdleTimeout(SERVER_WORKER_THREAD_IDLE_TIMEOUT_SECS);
        }

        @Override
        public void addListener(InvokerCallbackHandler callbackHandler) {
            // on client connect: remember client session
            // TODO: add ban by IP here?
            // need creates on first call, required by ping
            // but can be removed and re-creates on reconnection (if server contains another user instance)
            ServerInvokerCallbackHandler handler = (ServerInvokerCallbackHandler) callbackHandler;
            try {
                String sessionId = handler.getClientSessionId();
                managerFactory.sessionManager().createSession(sessionId, callbackHandler);
            } catch (Throwable ex) {
                logger.fatal("", ex);
            }
        }

        @Override
        public Object invoke(final InvocationRequest invocation) throws Throwable {
            // on command:
            // called for every client connecting to the server (after add Listener)
            // TODO: add ban by IP here?
            // save client ip-address
            String sessionId = invocation.getSessionId();
            Map map = invocation.getRequestPayload();
            String host;
            if (map != null) {
                InetAddress clientAddress = (InetAddress) invocation.getRequestPayload().get(Remoting.CLIENT_ADDRESS);
                host = clientAddress.getHostAddress();
            } else {
                host = "localhost";
            }
            Optional<Session> session = managerFactory.sessionManager().getSession(sessionId);
            if (!session.isPresent()) {
                logger.error("Session not found : " + sessionId);
            } else {
                session.get().setHost(host);
            }
            return null;
        }

        @Override
        public void removeListener(InvokerCallbackHandler callbackHandler) {
            // on client disconnect: remember client session
            // if user want to keep session then sessionId will be faked here
            ServerInvokerCallbackHandler handler = (ServerInvokerCallbackHandler) callbackHandler;
            String sessionId = handler.getClientSessionId();
            DisconnectReason reason = DisconnectReason.DisconnectedByUser;
            if (SessionImpl.KEEP_MY_OLD_SESSION.equals(sessionId)) {
                // no need in additional code, server will keep original session until inactive timeout
                reason = DisconnectReason.DisconnectedByUserButKeepTables;
            }
            managerFactory.sessionManager().disconnect(sessionId, reason, true);
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
}
