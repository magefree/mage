package mage.remote;

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

import javax.swing.*;
import java.io.*;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.*;
import java.util.*;
import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeUnit;

/**
 * Network: client side session
 *
 * @author BetaSteward_at_googlemail.com, JayDi85
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
    private RemotingTask lastRemotingTask = null;
    private static final int PING_CYCLES = 10;
    private final LinkedList<Long> pingTime = new LinkedList<>();
    private String pingInfo = "";
    private static boolean debugMode = false;

    private boolean canceled = false;
    private boolean jsonLogActive = false;
    private String lastError = "";

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

    // RemotingTask - do server side works in background and return result, can be canceled at any time
    public abstract class RemotingTask {

        SwingWorker<Boolean, Object> worker = null;
        Throwable lastError = null;

        abstract public boolean work() throws Throwable;

        boolean doWork() throws Throwable {
            worker = new SwingWorker<Boolean, Object>() {
                @Override
                protected Boolean doInBackground() {
                    try {
                        return work();
                    } catch (Throwable t) {
                        lastError = t;
                        return false;
                    }
                }
            };
            worker.execute();

            boolean res = worker.get();
            if (lastError != null) {
                throw lastError;
            }
            return res;
        }

        public void cancel() {
            if (worker != null) {
                worker.cancel(true);
            }
        }
    }

    private void showMessageToUser(String message) {
        if (message.contains("free port for use")) {
            message += " (try to close and restart a client app)";
        }
        client.showMessage("Remote task error. " + message);
    }

    private boolean doRemoteWorkAndHandleErrors(boolean closeConnectionOnFinish, boolean mustWaitServerMessageOnFail,
                                                RemotingTask remoting) {
        // execute remote task and wait result, can be canceled
        lastRemotingTask = remoting;
        try {
            boolean res = remoting.doWork();
            if (!res && mustWaitServerMessageOnFail) {
                // server send detail error as separate message by existing connection,
                // so you need wait some time before disconnect
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    logger.fatal("waiting of error message had failed", e);
                    Thread.currentThread().interrupt();
                }
            }
            return res;
        } catch (InterruptedException | CancellationException t) {
            // was canceled by user, nothing to show
        } catch (MalformedURLException ex) {
            logger.fatal("Connect: wrong server address", ex);
            showMessageToUser(ex.getMessage());
        } catch (UndeclaredThrowableException ex) {
            String addMessage = "";
            Throwable cause = ex.getCause();
            if (cause instanceof InvocationFailureException) {
                InvocationFailureException exep = (InvocationFailureException) cause;
                if (exep.getCause() instanceof IOException) {
                    if ((exep.getCause().getMessage() != null) && (exep.getCause().getMessage().startsWith("Field hash null is not available on current")
                            || exep.getCause().getMessage().endsWith("end of file"))) {
                        addMessage = "Probably the server version is not compatible with the client. ";
                    }
                } else {
                    logger.error("Connect: unknown server error", exep.getCause());
                }
            } else if (cause instanceof NoSuchMethodException) {
                // NoSuchMethodException is thrown on an invocation of an unknown JBoss remoting
                // method, so it's likely to be because of a version incompatibility.
                addMessage = "The following method is not available in the server, probably the "
                        + "server version is not compatible with the client: " + cause.getMessage();
            }
            if (addMessage.isEmpty()) {
                logger.fatal("Connect: unknown error", ex);
            }
            showMessageToUser(addMessage + (ex.getMessage() != null ? ex.getMessage() : ""));
        } catch (IOException ex) {
            logger.fatal("Connect: unknown IO error", ex);
            String addMessage = "";
            if (ex.getMessage() != null && ex.getMessage().startsWith("Unable to perform invocation")) {
                addMessage = "Maybe the server version is not compatible. ";
            }
            showMessageToUser(addMessage + (ex.getMessage() != null ? ex.getMessage() : ""));
        } catch (MageVersionException ex) {
            logger.warn("Connect: wrong versions");
            connectStop(false);
            if (!canceled) {
                showMessageToUser(ex.getMessage());
            }
        } catch (CannotConnectException ex) {
            if (!canceled) {
                handleCannotConnectException(ex);
            }
        } catch (Throwable t) {
            logger.fatal("Connect: FAIL", t);
            connectStop(false);
            if (!canceled) {
                showMessageToUser(t.getMessage());
            }
        } finally {
            lastRemotingTask = null;
            if (closeConnectionOnFinish) {
                connectStop(false); // it's ok on mutiple calls
            }
        }
        return false;
    }

    @Override
    public synchronized boolean sendAuthRegister(final Connection connection) {
        return doRemoteConnection(connection) && doRemoteWorkAndHandleErrors(true, true, new RemotingTask() {
            @Override
            public boolean work() throws Throwable {
                logger.info("Registration: username " + getUserName() + " for email " + getEmail());
                boolean result = server.authRegister(sessionId, connection.getUsername(), connection.getPassword(), connection.getEmail());
                logger.info("Registration: " + (result ? "DONE, check your email for new password" : "FAIL"));
                return result;
            }
        });
    }

    @Override
    public synchronized boolean sendAuthSendTokenToEmail(final Connection connection) {
        return doRemoteConnection(connection) && doRemoteWorkAndHandleErrors(true, true, new RemotingTask() {
            @Override
            public boolean work() throws Throwable {
                logger.info("Auth request: requesting auth token for username " + getUserName() + " to email " + getEmail());
                boolean result = server.authSendTokenToEmail(sessionId, connection.getEmail());
                logger.info("Auth request: " + (result ? "DONE, check your email for auth token" : "FAIL"));
                return result;
            }
        });
    }

    @Override
    public synchronized boolean sendAuthResetPassword(final Connection connection) {
        return doRemoteConnection(connection) && doRemoteWorkAndHandleErrors(true, true, new RemotingTask() {
            @Override
            public boolean work() throws Throwable {
                logger.info("Password reset: reseting password for username " + getUserName());
                boolean result = server.authResetPassword(sessionId, connection.getEmail(), connection.getAuthToken(), connection.getPassword());
                logger.info("Password reset: " + (result ? "DONE, now you can login with new password" : "FAIL"));
                return result;
            }
        });
    }

    @Override
    public synchronized boolean connectStart(final Connection connection) {
        return doRemoteConnection(connection) && doRemoteWorkAndHandleErrors(false, true, new RemotingTask() {
            @Override
            public boolean work() throws Throwable {
                setLastError("");
                logger.info("Logging: as username " + getUserName() + " to server " + connection.getHost() + ':' + connection.getPort());
                boolean result;

                if (connection.getAdminPassword() == null) {
                    // for backward compatibility. don't remove twice call - first one does nothing but for version checking
                    result = server.connectUser(connection.getUsername(), connection.getPassword(), sessionId, client.getVersion(), connection.getUserIdStr());
                } else {
                    result = server.connectAdmin(connection.getAdminPassword(), sessionId, client.getVersion());
                }

                if (result) {
                    serverState = server.getServerState();
                    if (serverState == null) {
                        throw new MageVersionException(client.getVersion(), null);
                    }

                    // client side check for incompatible versions
                    if (client.getVersion().compareTo(serverState.getVersion()) != 0) {
                        throw new MageVersionException(client.getVersion(), serverState.getVersion());
                    }

                    if (!connection.getUsername().equals("Admin")) {
                        server.connectSetUserData(connection.getUsername(), sessionId, connection.getUserData(), client.getVersion().toString(), connection.getUserIdStr());
                    }

                    logger.info("Logging: DONE");
                    client.connected(getUserName() + '@' + connection.getHost() + ':' + connection.getPort() + ' ');
                    return true;
                }

                logger.info("Logging: FAIL");
                return false;
            }
        });
    }

    @Override
    public Optional<String> getServerHostname() {
        return isConnected() ? Optional.of(connection.getHost()) : Optional.empty();
    }

    @Override
    public boolean connectAbort() {
        canceled = true;
        if (lastRemotingTask != null) {
            lastRemotingTask.cancel();
        }
        return true;
    }

    private boolean doRemoteConnection(final Connection connection) {
        // connect to server and setup all data, can be canceled
        if (isConnected()) {
            connectStop(true);
        }
        this.connection = connection;
        this.canceled = false;
        sessionState = SessionState.CONNECTING;
        lastRemotingTask = new RemotingTask() {
            @Override
            public boolean work() throws Throwable {
                logger.info("Connect: connecting to server " + connection.getHost() + ':' + connection.getPort());

                System.setProperty("http.nonProxyHosts", "code.google.com");
                System.setProperty("socksNonProxyHosts", "code.google.com");

                // clear previous values
                System.clearProperty("socksProxyHost");
                System.clearProperty("socksProxyPort");
                System.clearProperty("http.proxyHost");
                System.clearProperty("http.proxyPort");

                if (connection.getProxyType() != Connection.ProxyType.NONE) {
                    logger.info("Connect: using proxy " + connection.getProxyHost() + ":" + connection.getProxyPort());
                }
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
                logger.info("Connect: DONE");
                return true;
            }
        };

        boolean result;
        try {
            result = doRemoteWorkAndHandleErrors(false, false, lastRemotingTask);
        } finally {
            lastRemotingTask = null;
        }

        if (result) {
            return true;
        } else {
            connectStop(false);
            return false;
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
        client.showMessage("Unable connect to server. " + message);
        if (logger.isTraceEnabled()) {
            logger.trace("StackTrace", t);
        }
    }

    /**
     * @param askForReconnect - true = connection was lost because of error and
     *                        ask the user if they want to try to reconnect
     */
    @Override
    public synchronized void connectStop(boolean askForReconnect) {
        if (isConnected()) {
            logger.info("Disconnecting...");
            sessionState = SessionState.DISCONNECTING;
        }
        if (connection == null || sessionState == SessionState.DISCONNECTED) {
            return;
        }

        try {
            if (callbackClient != null && callbackClient.isConnected()) {
                callbackClient.removeListener(callbackHandler);
                callbackClient.disconnect();
            }
            TransporterClient.destroyTransporterClient(server);
        } catch (Throwable ex) {
            logger.fatal("Disconnecting FAIL", ex);
        }

        if (sessionState == SessionState.DISCONNECTING || sessionState == SessionState.CONNECTING) {
            sessionState = SessionState.DISCONNECTED;
            serverState = null;
            logger.info("Disconnecting DONE");
            if (askForReconnect) {
                client.showError("Network error. You have been disconnected from " + connection.getHost());
            }
            client.disconnected(askForReconnect); // MageFrame with check to reconnect
            pingTime.clear();
        }
    }

    @Override
    public synchronized void connectReconnect(Throwable throwable) {
        client.disconnected(true);
    }

    @Override
    public synchronized boolean sendFeedback(String title, String type, String message, String email) {
        if (isConnected()) {
            try {
                server.serverAddFeedbackMessage(sessionId, connection.getUsername(), title, type, message, email);
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
            try {
                client.processCallback((ClientCallback) callback.getCallbackObject());
            } catch (Exception ex) {
                logger.error("handleCallback error", ex);
            }

        }
    }

    class ClientConnectionListener implements ConnectionListener {
        // http://docs.jboss.org/jbossremoting/2.5.3.SP1/html/chapter-connection-failure.html

        @Override
        public void handleConnectionException(Throwable throwable, Client client) {
            logger.info("Connect: lost connection to server.", throwable);
            connectReconnect(throwable);
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
    public Boolean isServerReady() {
        // Is server works fine, possible use cases:
        // - client connected by network, but can't process register/login process due errors like wrong username
        // - client connected to broken server that has a wrong config or broken/miss libraries
        return isConnected() && serverState != null && serverState.getGameTypes().size() > 0;
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
                return server.serverGetMainRoomId();
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
                return Optional.of(server.chatFindByRoom(roomId));
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
                return Optional.of(server.chatFindByTable(tableId));
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
                return Optional.of(server.chatFindByGame(gameId));
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
                return Optional.of(server.roomGetTableById(roomId, tableId));
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
                server.roomWatchTable(sessionId, roomId, tableId);
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
                server.roomWatchTournament(sessionId, tableId);
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
                return server.roomJoinTable(sessionId, roomId, tableId, playerName, playerType, skill, deckList, password);
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
                return server.roomJoinTournament(sessionId, roomId, tableId, playerName, playerType, skill, deckList, password);
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
                return server.roomGetAllTables(roomId);
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
                return server.roomGetFinishedMatches(roomId);
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
                return server.roomGetUsers(roomId);
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
                return server.tournamentFindById(tournamentId);
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
                return Optional.of(server.chatFindByTournament(tournamentId));
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
                return server.sendDraftCardPick(draftId, sessionId, cardId, hiddenCards);
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
                server.sendDraftCardMark(draftId, sessionId, cardId);
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return null;
    }
    
    @Override
    public boolean setBoosterLoaded(UUID draftId) {
        try {
            if (isConnected()) {
                server.draftSetBoosterLoaded(draftId, sessionId);
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return false;
    }

    @Override
    public boolean joinChat(UUID chatId) {
        try {
            if (isConnected()) {
                server.chatJoin(chatId, sessionId, connection.getUsername());
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
                server.chatLeave(chatId, sessionId);
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
                server.chatSendMessage(chatId, connection.getUsername(), message);
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
                server.adminSendBroadcastMessage(sessionId, message);
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
                server.gameJoin(gameId, sessionId);
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
                server.draftJoin(draftId, sessionId);
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
                server.tournamentJoin(tournamentId, sessionId);
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
                return server.gameWatchStart(gameId, sessionId);
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
                server.replayInit(gameId, sessionId);
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
                return server.roomCreateTable(sessionId, roomId, matchOptions);
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
                return server.roomCreateTournament(sessionId, roomId, tournamentOptions);
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
                return server.tableIsOwner(sessionId, roomId, tableId);
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
                server.tableRemove(sessionId, roomId, tableId);

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
                server.adminTableRemove(sessionId, tableId);
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
                server.tableSwapSeats(sessionId, roomId, tableId, seatNum1, seatNum2);
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
            if (isConnected() && server.roomLeaveTableOrTournament(sessionId, roomId, tableId)) {
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
                return (server.matchStart(sessionId, roomId, tableId));
            }
        } catch (MageException ex) {
            handleMageException(ex);
        }
        return false;
    }

    @Override
    public boolean startTournament(UUID roomId, UUID tableId) {
        try {
            if (isConnected() && server.tournamentStart(sessionId, roomId, tableId)) {
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
                return server.deckSubmit(sessionId, tableId, deck);
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
                server.deckSave(sessionId, tableId, deck);
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
                server.matchQuit(gameId, sessionId);
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
                server.tournamentQuit(tournamentId, sessionId);
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
                server.draftQuit(draftId, sessionId);
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
                server.gameWatchStop(gameId, sessionId);
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
                server.replayStart(gameId, sessionId);
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
                server.replayStop(gameId, sessionId);
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
                server.replayNext(gameId, sessionId);
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
                server.replayPrevious(gameId, sessionId);
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
                server.replaySkipForward(gameId, sessionId, moves);
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
    public boolean cheatShow(UUID gameId, UUID playerId) {
        try {
            if (isConnected()) {
                server.cheatShow(gameId, sessionId, playerId);
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
                return server.adminGetUsers(sessionId);
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
                return (List<String>) CompressUtil.decompress(server.serverGetPromotionMessages(sessionId));
            }
        } catch (MageException ex) {
            handleMageException(ex);
        } catch (Throwable t) {
            handleThrowable(t);
        }
        return new ArrayList<>();
    }

    @Override
    public boolean sendAdminDisconnectUser(String userSessionId) {
        try {
            if (isConnected()) {
                server.adminDisconnectUser(sessionId, userSessionId);
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
    public boolean sendAdminEndUserSession(String userSessionId) {
        try {
            if (isConnected()) {
                server.adminEndUserSession(sessionId, userSessionId);
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
    public boolean sendAdminMuteUserChat(String userName, long durationMinutes) {
        try {
            if (isConnected()) {
                server.adminMuteUser(sessionId, userName, durationMinutes);
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
    public boolean sendAdminActivateUser(String userName, boolean active) {
        try {
            if (isConnected()) {
                server.adminActivateUser(sessionId, userName, active);
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
    public boolean sendAdminToggleActivateUser(String userName) {
        try {
            if (isConnected()) {
                server.adminToggleActivateUser(sessionId, userName);
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
    public boolean sendAdminLockUser(String userName, long durationMinute) {
        try {
            if (isConnected()) {
                server.adminLockUser(sessionId, userName, durationMinute);
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

        // ignore interrupted exceptions -- it's connection problem or user's close
        if (t instanceof InterruptedException) {
            //logger.error("Connection error: was interrupted", t);
            Thread.currentThread().interrupt();
            return;
        }

        if (t instanceof RuntimeException) {
            RuntimeException re = (RuntimeException) t;
            if (t.getCause() instanceof InterruptedException) {
                //logger.error("Connection error: was interrupted by runtime exception", t.getCause());
                Thread.currentThread().interrupt();
                return;
            }
        }

        logger.fatal("Connection error: other", t);
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
                server.connectSetUserData(connection.getUsername(), sessionId, userData, null, null);
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
            // ping must work after login only, all other actions are single call (example: register new user)
            // sessionId fills on connection
            // serverState fills on good login
            if (isConnected() && sessionId != null && serverState != null) {
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
                pingInfo = lastPing + " (avg: " + (milliSeconds > 0 ? milliSeconds + "ms" : "<1ms") + ')';
            }
            return true;
        } catch (MageException ex) {
            handleMageException(ex);
            connectStop(true);
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

    private void setLastError(String error) {
        lastError = error;
    }

    @Override
    public String getLastError() {
        return lastError;
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
