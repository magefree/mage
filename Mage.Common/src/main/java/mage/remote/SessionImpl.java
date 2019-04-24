
package mage.remote;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import mage.MageException;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.cards.repository.ExpansionInfo;
import mage.cards.repository.ExpansionRepository;
import mage.constants.ManaType;
import mage.constants.PlayerAction;
import mage.game.GameException;
import mage.game.match.MatchOptions;
import mage.game.tournament.TournamentOptions;
import mage.interfaces.MageClient;
import mage.interfaces.MageServer;
import mage.interfaces.ServerState;
import mage.interfaces.callback.ClientCallback;
import mage.players.PlayerType;
import mage.players.net.UserData;
import mage.utils.CompressUtil;
import mage.view.*;
import org.apache.log4j.Logger;
import org.jboss.remoting.*;
import org.jboss.remoting.callback.Callback;
import org.jboss.remoting.callback.HandleCallbackException;
import org.jboss.remoting.callback.InvokerCallbackHandler;
import org.jboss.remoting.transport.bisocket.Bisocket;
import org.jboss.remoting.transport.socket.SocketWrapper;
import org.jboss.remoting.transporter.TransporterClient;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class SessionImpl implements Session {

    private enum SessionState {
        DISCONNECTED, CONNECTED, CONNECTING, DISCONNECTING, SERVER_STARTING
    }

    private static final Logger logger = Logger.getLogger(SessionImpl.class);

    private final MageClient client;

    private String sessionId;
    private MageServer server;
    private Client callbackClient;
    private CallbackHandler callbackHandler;
    private ServerState serverState;
    private SessionState sessionState = SessionState.DISCONNECTED;
    private Connection connection;
    private final static int PING_CYCLES = 10;
    private final LinkedList<Long> pingTime = new LinkedList<>();
    private String pingInfo = "";
    private static boolean debugMode = false;

    private boolean canceled = false;
    private boolean jsonLogActive = false;

    static {
        debugMode = System.getProperty("debug.mage") != null;
    }

    public SessionImpl(MageClient client) {
        this.client = client;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    // RemotingTask encapsulates a task which is involved with some JBoss Remoting. This is
    // intended to be used with handleRemotingTaskExceptions for sharing the common exception
    // handling.
    public interface RemotingTask {

        public boolean run() throws Throwable;
    }

    // handleRemotingTaskExceptions runs the given task and handles exceptions appropriately. This
    // way we can share the common exception handling.
    private boolean handleRemotingTaskExceptions(RemotingTask remoting) {
        try {
            return remoting.run();
        } catch (MalformedURLException ex) {
            logger.fatal("", ex);
            client.showMessage("Unable to connect to server. " + ex.getMessage());
        } catch (UndeclaredThrowableException ex) {
            String addMessage = "";
            Throwable cause = ex.getCause();
            if (cause instanceof InvocationFailureException) {
                InvocationFailureException exep = (InvocationFailureException) cause;
                if (exep.getCause() instanceof IOException) {
                    if (exep.getCause().getMessage().startsWith("Field hash null is not available on current")) {
                        addMessage = "Probabaly the server version is not compatible to the client. ";
                    }
                }
            } else if (cause instanceof NoSuchMethodException) {
                // NoSuchMethodException is thrown on an invocation of an unknow JBoss remoting
                // method, so it's likely to be because of a version incompatibility.
                addMessage = "The following method is not available in the server, probably the "
                        + "server version is not compatible to the client: " + cause.getMessage();
            }
            if (addMessage.isEmpty()) {
                logger.fatal("", ex);
            }
            client.showMessage("Unable to connect to server. " + addMessage + (ex.getMessage() != null ? ex.getMessage() : ""));
        } catch (IOException ex) {
            logger.fatal("", ex);
            String addMessage = "";
            if (ex.getMessage() != null && ex.getMessage().startsWith("Unable to perform invocation")) {
                addMessage = "Maybe the server version is not compatible. ";
            }
            client.showMessage("Unable to connect to server. " + addMessage + ex.getMessage() != null ? ex.getMessage() : "");
        } catch (MageVersionException ex) {
            if (!canceled) {
                client.showMessage("Unable to connect to server. " + ex.getMessage());
            }
            disconnect(false);
        } catch (CannotConnectException ex) {
            if (!canceled) {
                handleCannotConnectException(ex);
            }
        } catch (Throwable t) {
            logger.fatal("Unable to connect to server - ", t);
            if (!canceled) {
                disconnect(false);
                StringBuilder sb = new StringBuilder();
                sb.append("Unable to connect to server.\n");
                for (StackTraceElement element : t.getStackTrace()) {
                    sb.append(element.toString()).append('\n');
                }
                client.showMessage(sb.toString());
            }
        }
        return false;
    }

    @Override
    public synchronized boolean register(final Connection connection) {
        return establishJBossRemotingConnection(connection) && handleRemotingTaskExceptions(new RemotingTask() {
            @Override
            public boolean run() throws Throwable {
                logger.info("Trying to register as " + getUserName() + " to XMAGE server at " + connection.getHost() + ':' + connection.getPort());
                boolean registerResult = server.registerUser(sessionId, connection.getUsername(),
                        connection.getPassword(), connection.getEmail());
                if (registerResult) {
                    logger.info("Registered as " + getUserName() + " to MAGE server at " + connection.getHost() + ':' + connection.getPort());
                }
                return registerResult;
            }
        });
    }

    @Override
    public synchronized boolean emailAuthToken(final Connection connection) {
        return establishJBossRemotingConnection(connection) && handleRemotingTaskExceptions(new RemotingTask() {
            @Override
            public boolean run() throws Throwable {
                logger.info("Trying to ask for an auth token to " + getEmail() + " to XMAGE server at " + connection.getHost() + ':' + connection.getPort());
                boolean result = server.emailAuthToken(sessionId, connection.getEmail());
                if (result) {
                    logger.info("An auth token is emailed to " + getEmail() + " from MAGE server at " + connection.getHost() + ':' + connection.getPort());
                }
                return result;
            }
        });
    }

    @Override
    public synchronized boolean resetPassword(final Connection connection) {
        return establishJBossRemotingConnection(connection) && handleRemotingTaskExceptions(new RemotingTask() {
            @Override
            public boolean run() throws Throwable {
                logger.info("Trying reset the password in XMAGE server at " + connection.getHost() + ':' + connection.getPort());
                boolean result = server.resetPassword(sessionId, connection.getEmail(), connection.getAuthToken(), connection.getPassword());
                if (result) {
                    logger.info("Password is successfully reset in MAGE server at " + connection.getHost() + ':' + connection.getPort());
                }
                return result;
            }
        });
    }

    @Override
    public synchronized boolean connect(final Connection connection) {
        return establishJBossRemotingConnection(connection)
                && handleRemotingTaskExceptions(new RemotingTask() {
                    @Override
                    public boolean run() throws Throwable {
                        logger.info("Trying to log-in as " + getUserName() + " to XMAGE server at " + connection.getHost() + ':' + connection.getPort());
                        boolean registerResult;
                        if (connection.getAdminPassword() == null) {
                            // for backward compatibility. don't remove twice call - first one does nothing but for version checking
                            registerResult = server.connectUser(connection.getUsername(), connection.getPassword(), sessionId, client.getVersion(), connection.getUserIdStr());
                            if (registerResult) {
                                server.setUserData(connection.getUsername(), sessionId, connection.getUserData(), client.getVersion().toString(), connection.getUserIdStr());
                            }
                        } else {
                            registerResult = server.connectAdmin(connection.getAdminPassword(), sessionId, client.getVersion());
                        }
                        if (registerResult) {
                            serverState = server.getServerState();
                            if (!connection.getUsername().equals("Admin")) {
                                updateDatabase(connection.isForceDBComparison(), serverState);
                            }
                            logger.info("Logged-in as " + getUserName() + " to MAGE server at " + connection.getHost() + ':' + connection.getPort());
                            client.connected(getUserName() + '@' + connection.getHost() + ':' + connection.getPort() + ' ');
                            return true;
                        }
                        disconnect(false);
                        return false;
                    }
                });
    }

    @Override
    public Optional<String> getServerHostname() {
        return isConnected() ? Optional.of(connection.getHost()) : Optional.<String>empty();
    }

    @Override
    public boolean stopConnecting() {
        canceled = true;
        return true;
    }

    private boolean establishJBossRemotingConnection(final Connection connection) {
        if (isConnected()) {
            disconnect(true);
        }
        this.connection = connection;
        this.canceled = false;
        sessionState = SessionState.CONNECTING;
        boolean result = handleRemotingTaskExceptions(new RemotingTask() {
            @Override
            public boolean run() throws Throwable {
                logger.info("Trying to connect to XMAGE server at " + connection.getHost() + ':' + connection.getPort());

                System.setProperty("http.nonProxyHosts", "code.google.com");
                System.setProperty("socksNonProxyHosts", "code.google.com");

                // clear previous values
                System.clearProperty("socksProxyHost");
                System.clearProperty("socksProxyPort");
                System.clearProperty("http.proxyHost");
                System.clearProperty("http.proxyPort");

                switch (connection.getProxyType()) {
                    case SOCKS:
                        System.setProperty("socksProxyHost", connection.getProxyHost());
                        System.setProperty("socksProxyPort", Integer.toString(connection.getProxyPort()));
                        break;
                    case HTTP:
                        System.setProperty("http.proxyHost", connection.getProxyHost());
                        System.setProperty("http.proxyPort", Integer.toString(connection.getProxyPort()));
                        Authenticator.setDefault(new MageAuthenticator(connection.getProxyUsername(), connection.getProxyPassword()));
                        break;
                }
                InvokerLocator clientLocator = new InvokerLocator(connection.getURI());

                Map<String, String> metadata = new HashMap<>();
                /*
                 5.8.3.1.1. Write timeouts
                 The socket timeout facility offered by the JDK applies only to read operations on the socket. As of release 2.5.2,
                 the socket and bisocket (and also sslsocket and sslbisocket) transports offer a write timeout facility. When a client
                 or server is configured, in any of the usual ways, with the parameter org.jboss.remoting.transport.socket.SocketWrapper.WRITE_TIMEOUT
                 (actual value "writeTimeout") set to a positive value (in milliseconds), all write operations will time out if they do
                 not complete within the configured period. When a write operation times out, the socket upon which the write was invoked
                 will be closed, which is likely to result in a java.net.SocketException.
                 Note. A SocketException is considered to be a "retriable" exception, so, if the parameter "numberOfCallRetries" is set
                 to a value greater than 1, an invocation interrupted by a write timeout can be retried.
                 Note. The write timeout facility applies to writing of both invocations and responses. It applies to push callbacks as well.
                 */
                metadata.put(SocketWrapper.WRITE_TIMEOUT, String.valueOf(connection.getSocketWriteTimeout()));
                metadata.put("generalizeSocketException", "true");
                server = (MageServer) TransporterClient.createTransporterClient(clientLocator.getLocatorURI(), MageServer.class, metadata);

                // http://docs.jboss.org/jbossremoting/docs/guide/2.5/html_single/#d0e1057
                Map<String, String> clientMetadata = new HashMap<>();

                clientMetadata.put(SocketWrapper.WRITE_TIMEOUT, String.valueOf(connection.getSocketWriteTimeout()));
                /*  generalizeSocketException
                 *  If set to false, a failed invocation will be retried in the case of
                 *  SocketExceptions. If set to true, a failed invocation will be retried in the case of
                 *  <classname>SocketException</classname>s and also any <classname>IOException</classname>
                 *  whose message matches the regular expression
                 *  <code>^.*(?:connection.*reset|connection.*closed|broken.*pipe).*$</code>.
                 *  See also the "numberOfCallRetries" parameter, above. The default value is false.*/
                clientMetadata.put("generalizeSocketException", "true");

                /* A remoting server also has the capability to detect when a client is no longer available.
                 * This is done by estabilishing a lease with the remoting clients that connect to a server.
                 * On the client side, an org.jboss.remoting.LeasePinger periodically sends PING messages to
                 * the server, and on the server side an org.jboss.remoting.Lease informs registered listeners
                 * if the PING doesn't arrive withing the specified timeout period. */
                clientMetadata.put(Client.ENABLE_LEASE, "true");
                /*
                 When the socket client invoker makes its first invocation, it will check to see if there is an available
                 socket connection in its pool. Since is the first invocation, there will not be and will create a new socket
                 connection and use it for making the invocation. Then when finished making invocation, will return the still
                 active socket connection to the pool. As more client invocations are made, is possible for the number of
                 socket connections to reach the maximum allowed (which is controlled by 'clientMaxPoolSize' property). At this
                 point, when the next client invocation is made, it will wait up to some configured number of milliseconds, at
                 which point it will throw an org.jboss.remoting.CannotConnectException. The number of milliseconds is given by
                 the parameter MicroSocketClientInvoker.CONNECTION_WAIT (actual value "connectionWait"), with a default of
                 30000 milliseconds. Note that if more than one call retry is configured (see next paragraph),
                 the CannotConnectException will be swallowed.
                 Once the socket client invoker get an available socket connection from the pool, are not out of the woods yet.
                 For example, a network problem could cause a java.net.SocketException. There is also a possibility that the socket
                 connection, while still appearing to be valid, has "gone stale" while sitting in the pool. For example, a ServerThread
                 on the other side of the connection could time out and close its socket. If the attempt to complete an invocation
                 fails, then MicroSocketClientInvoker will make a number of attempts, according to the parameter "numberOfCallRetries",
                 with a default value of 3. Once the configured number of retries has been exhausted,
                 an org.jboss.remoting.InvocationFailureException will be thrown.
                 */
                clientMetadata.put("numberOfCallRetries", "1");

                /**
                 * I'll explain the meaning of "secondaryBindPort" and
                 * "secondaryConnectPort", and maybe that will help. The
                 * Remoting bisocket transport creates two ServerSockets on the
                 * server. The "primary" ServerSocket is used to create
                 * connections used for ordinary invocations, e.g., a request to
                 * create a JMS consumer, and the "secondary" ServerSocket is
                 * used to create "control" connections for internal Remoting
                 * messages. The port for the primary ServerSocket is configured
                 * by the "serverBindPort" parameter, and the port for the
                 * secondary ServerSocket is, by default, chosen randomly. The
                 * "secondaryBindPort" parameter can be used to assign a
                 * specific port to the secondary ServerSocket. Now, if there is
                 * a translating firewall between the client and server, the
                 * client should be given the value of the port that is
                 * translated to the actual binding port of the secondary
                 * ServerSocket. For example, your configuration will tell the
                 * secondary ServerSocket to bind to port 14000, and it will
                 * tell the client to connect to port 14001. It assumes that
                 * there is a firewall which will translate 14001 to 14000.
                 * Apparently, that's not happening.
                 */
                // secondaryBindPort - the port to which the secondary server socket is to be bound. By default, an arbitrary port is selected.
                // secondaryConnectPort - the port clients are to use to connect to the secondary server socket.
                // By default, the value of secondaryBindPort is used. secondaryConnectPort is useful if the server is behind a translating firewall.
                // Indicated the max number of threads used within oneway thread pool.
                clientMetadata.put(Client.MAX_NUM_ONEWAY_THREADS, "10");
                clientMetadata.put(Remoting.USE_CLIENT_CONNECTION_IDENTITY, "true");
                callbackClient = new Client(clientLocator, "callback", clientMetadata);

                Map<String, String> listenerMetadata = new HashMap<>();
                if (debugMode) {
                    // prevent client from disconnecting while debugging
                    listenerMetadata.put(ConnectionValidator.VALIDATOR_PING_PERIOD, "1000000");
                    listenerMetadata.put(ConnectionValidator.VALIDATOR_PING_TIMEOUT, "900000");
                } else {
                    listenerMetadata.put(ConnectionValidator.VALIDATOR_PING_PERIOD, "15000");
                    listenerMetadata.put(ConnectionValidator.VALIDATOR_PING_TIMEOUT, "13000");
                }
                callbackClient.connect(new ClientConnectionListener(), listenerMetadata);

                Map<String, String> callbackMetadata = new HashMap<>();
                callbackMetadata.put(Bisocket.IS_CALLBACK_SERVER, "true");
                if (callbackHandler == null) {
                    callbackHandler = new CallbackHandler();
                }
                callbackClient.addListener(callbackHandler, callbackMetadata);

                Set callbackConnectors = callbackClient.getCallbackConnectors(callbackHandler);
                if (callbackConnectors.size() != 1) {
                    logger.warn("There should be one callback Connector (number existing = " + callbackConnectors.size() + ')');
                }

                callbackClient.invoke(null);

                sessionId = callbackClient.getSessionId();
                sessionState = SessionState.CONNECTED;
                logger.info("Connected to MAGE server at " + connection.getHost() + ':' + connection.getPort());
                return true;
            }
        });
        if (result) {
            return true;
        }
        disconnect(false);
        return false;
    }

    private void updateDatabase(boolean forceDBComparison, ServerState serverState) {
        long cardDBVersion = CardRepository.instance.getContentVersionFromDB();
        if (forceDBComparison || serverState.getCardsContentVersion() > cardDBVersion) {
            List<String> classNames = CardRepository.instance.getClassNames();
            List<CardInfo> cards = server.getMissingCardsData(classNames);
            CardRepository.instance.addCards(cards);
            CardRepository.instance.setContentVersion(serverState.getCardsContentVersion());
            logger.info("Updating client cards DB - existing cards: " + classNames.size() + " new cards: " + cards.size()
                    + " content versions - server: " + serverState.getCardsContentVersion() + " client: " + cardDBVersion);
        }

        long expansionDBVersion = ExpansionRepository.instance.getContentVersionFromDB();
        if (forceDBComparison || serverState.getExpansionsContentVersion() > expansionDBVersion) {
            List<String> setCodes = ExpansionRepository.instance.getSetCodes();
            List<ExpansionInfo> expansions = server.getMissingExpansionData(setCodes);
            for (ExpansionInfo expansion : expansions) {
                ExpansionRepository.instance.add(expansion);
            }
            ExpansionRepository.instance.setContentVersion(serverState.getExpansionsContentVersion());
            logger.info("Updating client expansions DB - existing sets: " + setCodes.size() + " new sets: " + expansions.size()
                    + " content versions - server: " + serverState.getExpansionsContentVersion() + " client: " + expansionDBVersion);
        }
    }

    private void handleCannotConnectException(CannotConnectException ex) {
        logger.warn("Cannot connect", ex);
        Throwable t = ex.getCause();
        String message = "";
        while (t != null) {
            if (t instanceof ConnectException) {
                message = "Server is likely offline." + message;
                break;
            }
            if (t instanceof SocketException) {
                message = "Check your internet connection." + message;
                break;
            }
            if (t instanceof SocketTimeoutException) {
                message = "Server is not responding." + message;
                break;
            }
            if (t.getCause() != null && logger.isDebugEnabled()) {
                message = '\n' + t.getCause().getMessage() + message;
                logger.debug(t.getCause().getMessage());
            }

            t = t.getCause();
        }
        client.showMessage("Unable to connect to server. " + message);
        if (logger.isTraceEnabled()) {
            logger.trace("StackTrace", t);
        }
    }

    /**
     * @param askForReconnect - true = connection was lost because of error and
     * ask the user if he want to try to reconnect
     */
    @Override
    public synchronized void disconnect(boolean askForReconnect) {
        if (isConnected()) {
            logger.info("DISCONNECT (still connected)");
            sessionState = SessionState.DISCONNECTING;
        }
        if (connection == null || sessionState == SessionState.DISCONNECTED) {
            return;
        }

        try {
            callbackClient.removeListener(callbackHandler);
            callbackClient.disconnect();
            TransporterClient.destroyTransporterClient(server);
        } catch (Throwable ex) {
            logger.fatal("Error disconnecting ...", ex);
        }

        if (sessionState == SessionState.DISCONNECTING || sessionState == SessionState.CONNECTING) {
            sessionState = SessionState.DISCONNECTED;
            logger.info("Disconnected ... ");
            if (askForReconnect) {
                client.showError("Network error.  You have been disconnected from " + connection.getHost());
            }
            client.disconnected(askForReconnect); // MageFrame with check to reconnect
            pingTime.clear();
        }
    }

    @Override
    public synchronized void reconnect(Throwable throwable) {
        logger.info("RECONNECT - Connected: " + isConnected());
        client.disconnected(true);
    }

    @Override
    public synchronized boolean sendFeedback(String title, String type, String message, String email) {
        if (isConnected()) {
            try {
                server.sendFeedbackMessage(sessionId, connection.getUsername(), title, type, message, email);
                return true;
            } catch (MageException e) {
                logger.error(e);
            }
        }
        return false;
    }

    class CallbackHandler implements InvokerCallbackHandler {

        @Override
        public void handleCallback(Callback callback) throws HandleCallbackException {
            //logger.info("callback handler");
            client.processCallback((ClientCallback) callback.getCallbackObject());
        }
    }

    class ClientConnectionListener implements ConnectionListener {
        // http://docs.jboss.org/jbossremoting/2.5.3.SP1/html/chapter-connection-failure.html

        @Override
        public void handleConnectionException(Throwable throwable, Client client) {
            logger.info("connection to server lost - " + throwable.getMessage(), throwable);
            reconnect(throwable);
        }
    }

    @Override
    public boolean isConnected() {
        if (callbackClient == null) {
            return false;
        }
        return callbackClient.isConnected();
    }

    @Override
    public PlayerType[] getPlayerTypes() {
        return serverState.getPlayerTypes();
    }

    @Override
    public List<GameTypeView> getGameTypes() {
        return serverState.getGameTypes();
    }

    @Override
    public List<GameTypeView> getTournamentGameTypes() {
        return serverState.getTournamentGameTypes();
    }

    @Override
    public String[] getDeckTypes() {
        return serverState.getDeckTypes();
    }

    @Override
    public String[] getDraftCubes() {
        return serverState.getDraftCubes();
    }

    @Override
    public List<TournamentTypeView> getTournamentTypes() {
        return serverState.getTournamentTypes();
    }

    @Override
    public boolean isTestMode() {
        if (serverState != null) {
            return serverState.isTestMode();
        }
        return false;
    }

    @Override
    public UUID getMainRoomId() {
        try {
            if (isConnected()) {
                return server.getMainRoomId();
            }
        } catch (MageException ex) {
            handleMageException(ex);
        }
        return null;
    }

    @Override
    public Optional<UUID> getRoomChatId(UUID roomId) {
        try {
            if (isConnected()) {
                return server.getRoomChatId(roomId);
            }
        } catch (MageException ex) {
            handleMageException(ex);
        }
        return Optional.empty();
    }

    @Override
    public Optional<UUID> getTableChatId(UUID tableId) {
        try {
            if (isConnected()) {
                return server.getTableChatId(tableId);
            }
        } catch (MageException ex) {
            handleMageException(ex);
        }
        return Optional.empty();
    }

    @Override
    public Optional<UUID> getGameChatId(UUID gameId) {
        try {
            if (isConnected()) {
                return server.getGameChatId(gameId);
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return Optional.empty();
    }

    @Override
    public Optional<TableView> getTable(UUID roomId, UUID tableId) {
        try {
            if (isConnected()) {
                return server.getTable(roomId, tableId);
            }
        } catch (MageException ex) {
            handleMageException(ex);
        }
        return Optional.empty();
    }

    @Override
    public boolean watchTable(UUID roomId, UUID tableId) {
        try {
            if (isConnected()) {
                server.watchTable(sessionId, roomId, tableId);
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean watchTournamentTable(UUID tableId) {
        try {
            if (isConnected()) {
                server.watchTournamentTable(sessionId, tableId);
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean joinTable(UUID roomId, UUID tableId, String playerName, PlayerType playerType, int skill, DeckCardLists deckList, String password) {
        try {
            if (isConnected()) {
                // Workaround to fix Can't join table problem
                if (deckList != null) {
                    deckList.setCardLayout(null);
                    deckList.setSideboardLayout(null);
                }
                return server.joinTable(sessionId, roomId, tableId, playerName, playerType, skill, deckList, password);
            }
        } catch (GameException ex) {
            handleGameException(ex);
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean joinTournamentTable(UUID roomId, UUID tableId, String playerName, PlayerType playerType, int skill, DeckCardLists deckList, String password) {
        try {
            if (isConnected()) {
                // Workaround to fix Can't join table problem
                if (deckList != null) {
                    deckList.setCardLayout(null);
                    deckList.setSideboardLayout(null);
                }
                return server.joinTournamentTable(sessionId, roomId, tableId, playerName, playerType, skill, deckList, password);
            }
        } catch (GameException ex) {
            handleGameException(ex);
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public Collection<TableView> getTables(UUID roomId) throws MageRemoteException {
        try {
            if (isConnected()) {
                return server.getTables(roomId);
            }
        } catch (MageException ex) {
            handleMageException(ex);
            throw new MageRemoteException();
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return new ArrayList<>();
    }

    @Override
    public Collection<MatchView> getFinishedMatches(UUID roomId) throws MageRemoteException {
        try {
            if (isConnected()) {
                return server.getFinishedMatches(roomId);
            }
        } catch (MageException ex) {
            handleMageException(ex);
            throw new MageRemoteException();
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return new ArrayList<>();
    }

    @Override
    public Collection<RoomUsersView> getRoomUsers(UUID roomId) throws MageRemoteException {
        try {
            if (isConnected()) {
                return server.getRoomUsers(roomId);
            }
        } catch (MageException ex) {
            handleMageException(ex);
            throw new MageRemoteException();
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return new ArrayList<>();
    }

    @Override
    public TournamentView getTournament(UUID tournamentId) throws MageRemoteException {
        try {
            if (isConnected()) {
                return server.getTournament(tournamentId);
            }
        } catch (MageException ex) {
            handleMageException(ex);
            throw new MageRemoteException();
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return null;
    }

    @Override
    public Optional<UUID> getTournamentChatId(UUID tournamentId) {
        try {
            if (isConnected()) {
                return server.getTournamentChatId(tournamentId);
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return Optional.empty();
    }

    @Override
    public boolean sendPlayerUUID(UUID gameId, UUID data) {
        try {
            if (isConnected()) {
                ActionData actionData = new ActionData("SEND_PLAYER_UUID", gameId, getSessionId());
                actionData.value = data;
                appendJsonLog(actionData);
                server.sendPlayerUUID(gameId, sessionId, data);
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean sendPlayerBoolean(UUID gameId, boolean data) {
        try {
            if (isConnected()) {
                ActionData actionData = new ActionData("SEND_PLAYER_BOOLEAN", gameId, getSessionId());
                actionData.value = data;
                appendJsonLog(actionData);

                server.sendPlayerBoolean(gameId, sessionId, data);
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean sendPlayerInteger(UUID gameId, int data) {
        try {
            if (isConnected()) {
                ActionData actionData = new ActionData("SEND_PLAYER_INTEGER", gameId, getSessionId());
                actionData.value = data;
                appendJsonLog(actionData);

                server.sendPlayerInteger(gameId, sessionId, data);
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean sendPlayerString(UUID gameId, String data) {
        try {
            if (isConnected()) {
                ActionData actionData = new ActionData("SEND_PLAYER_STRING", gameId, getSessionId());
                actionData.value = data;
                appendJsonLog(actionData);

                server.sendPlayerString(gameId, sessionId, data);
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean sendPlayerManaType(UUID gameId, UUID playerId, ManaType data) {
        try {
            if (isConnected()) {
                ActionData actionData = new ActionData("SEND_PLAYER_MANA_TYPE", gameId, getSessionId());
                actionData.value = data;
                appendJsonLog(actionData);
                server.sendPlayerManaType(gameId, playerId, sessionId, data);
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public void appendJsonLog(ActionData actionData) {
        if (isJsonLogActive()) {
            String dir = "gamelogsJson";
            File saveDir = new File(dir);
            //Existence check
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }
            actionData.sessionId = getSessionId();
            String logFileName = dir + File.separator + "game-" + actionData.gameId + ".json";
            try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(logFileName, true)))) {
                out.println(actionData.toJson());
            } catch (IOException e) {
                logger.error("Cant write JSON game log file - " + logFileName, e);
            }
        }
    }

    @Override
    public DraftPickView sendCardPick(UUID draftId, UUID cardId, Set<UUID> hiddenCards) {
        try {
            if (isConnected()) {
                return server.sendCardPick(draftId, sessionId, cardId, hiddenCards);
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return null;
    }

    @Override
    public DraftPickView sendCardMark(UUID draftId, UUID cardId) {
        try {
            if (isConnected()) {
                server.sendCardMark(draftId, sessionId, cardId);
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return null;
    }

    @Override
    public boolean joinChat(UUID chatId) {
        try {
            if (isConnected()) {
                server.joinChat(chatId, sessionId, connection.getUsername());
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean leaveChat(UUID chatId) {
//        lock.readLock().lock();
        try {
            if (isConnected() && chatId != null) {
                server.leaveChat(chatId, sessionId);
            }
            return true;
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
//        } finally {
//            lock.readLock().unlock();
        }
        return false;
    }

    @Override
    public boolean sendChatMessage(UUID chatId, String message) {
//        lock.readLock().lock();
        try {
            if (isConnected()) {
                server.sendChatMessage(chatId, connection.getUsername(), message);
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
//        } finally {
//            lock.readLock().unlock();
        }
        return false;
    }

    @Override
    public boolean sendBroadcastMessage(String message) {
        try {
            if (isConnected()) {
                server.sendBroadcastMessage(sessionId, message);
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean joinGame(UUID gameId) {
        try {
            if (isConnected()) {
                server.joinGame(gameId, sessionId);
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean joinDraft(UUID draftId) {
        try {
            if (isConnected()) {
                server.joinDraft(draftId, sessionId);
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean joinTournament(UUID tournamentId) {
        try {
            if (isConnected()) {
                server.joinTournament(tournamentId, sessionId);
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean watchGame(UUID gameId) {
        try {
            if (isConnected()) {
                return server.watchGame(gameId, sessionId);
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean replayGame(UUID gameId) {
        try {
            if (isConnected()) {
                server.replayGame(gameId, sessionId);
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public TableView createTable(UUID roomId, MatchOptions matchOptions) {
        try {
            if (isConnected()) {
                return server.createTable(sessionId, roomId, matchOptions);
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return null;
    }

    @Override
    public TableView createTournamentTable(UUID roomId, TournamentOptions tournamentOptions) {
        try {
            if (isConnected()) {
                return server.createTournamentTable(sessionId, roomId, tournamentOptions);
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return null;
    }

    @Override
    public boolean isTableOwner(UUID roomId, UUID tableId) {
        try {
            if (isConnected()) {
                return server.isTableOwner(sessionId, roomId, tableId);
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean removeTable(UUID roomId, UUID tableId) {
        try {
            if (isConnected()) {
                server.removeTable(sessionId, roomId, tableId);

                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    /**
     * Remove table - called from admin console
     *
     * @param tableId
     * @return
     */
    @Override
    public boolean removeTable(UUID tableId) {
        try {
            if (isConnected()) {
                server.removeTable(sessionId, tableId);
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean swapSeats(UUID roomId, UUID tableId, int seatNum1, int seatNum2) {
        try {
            if (isConnected()) {
                server.swapSeats(sessionId, roomId, tableId, seatNum1, seatNum2);
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean leaveTable(UUID roomId, UUID tableId) {
        try {
            if (isConnected() && server.leaveTable(sessionId, roomId, tableId)) {
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean startMatch(UUID roomId, UUID tableId) {
        try {
            if (isConnected()) {
                return (server.startMatch(sessionId, roomId, tableId));
            }
        } catch (MageException ex) {
            handleMageException(ex);
        }
        return false;
    }

    @Override
    public boolean startTournament(UUID roomId, UUID tableId) {
        try {
            if (isConnected() && server.startTournament(sessionId, roomId, tableId)) {
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    //    @Override
//    public boolean startChallenge(UUID roomId, UUID tableId, UUID challengeId) {
//        try {
//            if (isConnected()) {
//                server.startChallenge(sessionId, roomId, tableId, challengeId);
//                return true;
//            }
//        } catch (MageException ex) {
//            handleMageException(ex);
//        } catch (Throwable t) {
//            handleThrowable(t);
//        }
//        return false;
//    }
    @Override
    public boolean submitDeck(UUID tableId, DeckCardLists deck) {
        try {
            if (isConnected()) {
                // Workaround to fix Can't join table problem
                if (deck != null) {
                    deck.setCardLayout(null);
                    deck.setSideboardLayout(null);
                }
                return server.submitDeck(sessionId, tableId, deck);
            }
        } catch (GameException ex) {
            handleGameException(ex);
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean updateDeck(UUID tableId, DeckCardLists deck) {
        try {
            if (isConnected()) {
                if (deck != null) {
                    deck.setCardLayout(null);
                    deck.setSideboardLayout(null);
                }
                server.updateDeck(sessionId, tableId, deck);
                return true;
            }
        } catch (GameException ex) {
            handleGameException(ex);
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean quitMatch(UUID gameId) {
        try {
            if (isConnected()) {
                server.quitMatch(gameId, sessionId);
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean quitTournament(UUID tournamentId) {
        try {
            if (isConnected()) {
                server.quitTournament(tournamentId, sessionId);
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean quitDraft(UUID draftId) {
        try {
            if (isConnected()) {
                server.quitDraft(draftId, sessionId);
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        }
        return false;
    }

    @Override
    public boolean sendPlayerAction(PlayerAction passPriorityAction, UUID gameId, Object data) {
        try {
            if (isConnected()) {
                ActionData actionData = new ActionData("SEND_PLAYER_ACTION", gameId, getSessionId());

                actionData.value = passPriorityAction + (data != null ? " " + data.toString() : "");
                appendJsonLog(actionData);

                server.sendPlayerAction(passPriorityAction, gameId, sessionId, data);
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean stopWatching(UUID gameId) {
        try {
            if (isConnected()) {
                server.stopWatching(gameId, sessionId);
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean startReplay(UUID gameId) {
        try {
            if (isConnected()) {
                server.startReplay(gameId, sessionId);
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean stopReplay(UUID gameId) {
        try {
            if (isConnected()) {
                server.stopReplay(gameId, sessionId);
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean nextPlay(UUID gameId) {
        try {
            if (isConnected()) {
                server.nextPlay(gameId, sessionId);
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean previousPlay(UUID gameId) {
        try {
            if (isConnected()) {
                server.previousPlay(gameId, sessionId);
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean skipForward(UUID gameId, int moves) {
        try {
            if (isConnected()) {
                server.skipForward(gameId, sessionId, moves);
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean cheat(UUID gameId, UUID playerId, DeckCardLists deckList) {
        try {
            if (isConnected()) {
                server.cheat(gameId, sessionId, playerId, deckList);
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public List<UserView> getUsers() {
        try {
            if (isConnected()) {
                return server.getUsers(sessionId);
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return null;
    }

    @Override
    public List<String> getServerMessages() {
        try {
            if (isConnected()) {
                return (List<String>) CompressUtil.decompress(server.getServerMessagesCompressed(sessionId));
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return new ArrayList<>();
    }

    @Override
    public boolean disconnectUser(String userSessionId) {
        try {
            if (isConnected()) {
                server.disconnectUser(sessionId, userSessionId);
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean endUserSession(String userSessionId) {
        try {
            if (isConnected()) {
                server.endUserSession(sessionId, userSessionId);
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean muteUserChat(String userName, long durationMinutes) {
        try {
            if (isConnected()) {
                server.muteUser(sessionId, userName, durationMinutes);
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean setActivation(String userName, boolean active) {
        try {
            if (isConnected()) {
                server.setActivation(sessionId, userName, active);
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean toggleActivation(String userName) {
        try {
            if (isConnected()) {
                server.toggleActivation(sessionId, userName);
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean lockUser(String userName, long durationMinute) {
        try {
            if (isConnected()) {
                server.lockUser(sessionId, userName, durationMinute);
                return true;
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    private void handleThrowable(Throwable t) {
        logger.fatal("Communication error", t);
        // Probably this can cause hanging the client under certain circumstances as the disconnect method is synchronized
        // so check if it's needed
        // disconnect(true);
    }

    private void handleMageException(MageException ex) {
        logger.fatal("Server error", ex);
        client.showError(ex.getMessage());
    }

    private void handleGameException(GameException ex) {
        logger.warn(ex.getMessage());
        client.showError(ex.getMessage());
    }

    @Override
    public String getUserName() {
        String username = connection.getUsername();
        return username == null ? "" : username;
    }

    private String getEmail() {
        String email = connection.getEmail();
        return email == null ? "" : email;
    }

    private String getAuthToken() {
        String authToken = connection.getAuthToken();
        return authToken == null ? "" : authToken;
    }

    @Override
    public boolean updatePreferencesForServer(UserData userData) {
        try {
            if (isConnected()) {
                server.setUserData(connection.getUsername(), sessionId, userData, null, null);
            }
            return true;
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean ping() {
        try {
            if (isConnected()) {
                long startTime = System.nanoTime();
                if (!server.ping(sessionId, pingInfo)) {
                    logger.error("Ping failed: " + this.getUserName() + " Session: " + sessionId + " to MAGE server at " + connection.getHost() + ':' + connection.getPort());
                    throw new MageException("Ping failed");
                }
                pingTime.add(System.nanoTime() - startTime);
                long milliSeconds = TimeUnit.MILLISECONDS.convert(pingTime.getLast(), TimeUnit.NANOSECONDS);
                String lastPing = milliSeconds > 0 ? milliSeconds + "ms" : "<1ms";
                if (pingTime.size() > PING_CYCLES) {
                    pingTime.poll();
                }
                long sum = 0;
                for (Long time : pingTime) {
                    sum += time;
                }
                milliSeconds = TimeUnit.MILLISECONDS.convert(sum / pingTime.size(), TimeUnit.NANOSECONDS);
                pingInfo = lastPing + " (Av: " + (milliSeconds > 0 ? milliSeconds + "ms" : "<1ms") + ')';
            }
            return true;
        } catch (MageException ex) {
            handleMageException(ex);
            disconnect(true);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public String getVersionInfo() {
        if (serverState != null) {
            return serverState.getVersion().toString();
        } else {
            return "<no server state>";
        }
    }

    @Override
    public boolean isJsonLogActive() {
        return jsonLogActive;
    }

    @Override
    public void setJsonLogActive(boolean jsonLogActive) {
        this.jsonLogActive = jsonLogActive;
    }

}

class MageAuthenticator extends Authenticator {

    private final String username;
    private final String password;

    public MageAuthenticator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password.toCharArray());
    }
}
