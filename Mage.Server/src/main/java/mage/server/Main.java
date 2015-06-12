/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.server;

import io.netty.channel.ChannelId;
import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import mage.MageException;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.cards.repository.CardScanner;
import mage.cards.repository.ExpansionInfo;
import mage.cards.repository.ExpansionRepository;
import mage.constants.ManaType;
import mage.constants.PlayerAction;
import mage.constants.TableState;
import mage.game.GameException;
import mage.game.Table;
import mage.game.match.MatchOptions;
import mage.game.match.MatchType;
import mage.game.tournament.TournamentOptions;
import mage.game.tournament.TournamentType;
import mage.interfaces.Action;
import mage.interfaces.ActionWithResult;
//import mage.interfaces.MageServer;
import org.mage.network.interfaces.MageServer;
import mage.interfaces.ServerState;
import mage.interfaces.callback.ClientCallback;
import mage.remote.Connection;
import mage.remote.DisconnectReason;
import mage.remote.MageVersionException;
import mage.server.draft.CubeFactory;
import mage.server.draft.DraftManager;
import mage.server.game.DeckValidatorFactory;
import mage.server.game.GameFactory;
import mage.server.game.GameManager;
import mage.server.game.GamesRoom;
import mage.server.game.GamesRoomManager;
import mage.server.game.PlayerFactory;
import mage.server.game.ReplayManager;
import mage.server.services.LogKeys;
import mage.server.services.impl.FeedbackServiceImpl;
import mage.server.services.impl.LogServiceImpl;
import mage.server.tournament.TournamentFactory;
import mage.server.tournament.TournamentManager;
import mage.server.util.ConfigSettings;
import mage.server.util.PluginClassLoader;
import mage.server.util.ServerMessagesUtil;
import mage.server.util.SystemUtil;
import mage.server.util.ThreadExecutor;
import mage.server.util.config.GamePlugin;
import mage.server.util.config.Plugin;
import mage.utils.ActionWithBooleanResult;
import mage.utils.ActionWithNullNegativeResult;
import mage.utils.ActionWithTableViewResult;
import mage.utils.CompressUtil;
import mage.utils.MageVersion;
import mage.view.ChatMessage;
import mage.view.ChatMessage.MessageColor;
import mage.view.DraftPickView;
import mage.view.GameView;
import mage.view.MatchView;
import mage.view.RoomUsersView;
import mage.view.RoomView;
import mage.view.TableView;
import mage.view.TournamentView;
import mage.view.UserDataView;
import mage.view.UserView;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.mage.network.Server;
import org.mage.network.model.MessageType;

/**
 *
 * @author BetaSteward_at_googlemail.com, noxx
 */
public class Main implements MageServer {

    private static final Logger logger = Logger.getLogger(MageServer.class);
    private static final ExecutorService callExecutor = ThreadExecutor.getInstance().getCallExecutor();
    private static final MageVersion version = new MageVersion(MageVersion.MAGE_VERSION_MAJOR, MageVersion.MAGE_VERSION_MINOR, MageVersion.MAGE_VERSION_PATCH,  MageVersion.MAGE_VERSION_MINOR_PATCH, MageVersion.MAGE_VERSION_INFO);

    private static final String testModeArg = "-testMode=";
    private static final String fastDBModeArg = "-fastDbMode=";
    private static final String adminPasswordArg = "-adminPassword=";
    private static final String pluginFolder = "plugins";

    public static final PluginClassLoader classLoader = new PluginClassLoader();

    private final ServerState state;
    private final String password;
    private final boolean testMode;

    private static Main instance;
    private static Server server;

    public Main(String password, boolean testMode) {
        this.password = password;
        this.testMode = testMode;
        ServerMessagesUtil.getInstance().getMessages();
        state = new ServerState(
                    GameFactory.getInstance().getGameTypes(),
                    TournamentFactory.getInstance().getTournamentTypes(),
                    PlayerFactory.getInstance().getPlayerTypes().toArray(new String[PlayerFactory.getInstance().getPlayerTypes().size()]),
                    DeckValidatorFactory.getInstance().getDeckTypes().toArray(new String[DeckValidatorFactory.getInstance().getDeckTypes().size()]),
                    CubeFactory.getInstance().getDraftCubes().toArray(new String[CubeFactory.getInstance().getDraftCubes().size()]),
                    testMode,
                    Main.getVersion(),
                    CardRepository.instance.getContentVersionConstant(),
                    ExpansionRepository.instance.getContentVersionConstant(),
                    GamesRoomManager.getInstance().getMainRoomId()
            );
    }
    
    public static void main(String[] args) {
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        logger.info("Starting MAGE server version " + version);
        logger.info("Logging level: " + logger.getEffectiveLevel());

        String adminPassword = "";
        boolean testMode = false;
        boolean fastDbMode = false;
        
        for (String arg: args) {
            if (arg.startsWith(testModeArg)) {
                testMode = Boolean.valueOf(arg.replace(testModeArg, ""));
            }
            else if (arg.startsWith(adminPasswordArg)) {
                adminPassword = arg.replace(adminPasswordArg, "");
                adminPassword = SystemUtil.sanitize(adminPassword);
            }
            else if (arg.startsWith(fastDBModeArg)) {
                fastDbMode = Boolean.valueOf(arg.replace(fastDBModeArg, ""));
            }
        }
        
        logger.info("Loading cards...");
        if (fastDbMode) {
            CardScanner.scanned = true;
        } else {
            CardScanner.scan();
        }
        logger.info("Done.");

        deleteSavedGames();
        ConfigSettings config = ConfigSettings.getInstance();
        for (GamePlugin plugin: config.getGameTypes()) {
            GameFactory.getInstance().addGameType(plugin.getName(), loadGameType(plugin), loadPlugin(plugin));
        }
        for (GamePlugin plugin: config.getTournamentTypes()) {
            TournamentFactory.getInstance().addTournamentType(plugin.getName(), loadTournamentType(plugin), loadPlugin(plugin));
        }
        for (Plugin plugin: config.getPlayerTypes()) {
            PlayerFactory.getInstance().addPlayerType(plugin.getName(), loadPlugin(plugin));
        }
        for (Plugin plugin: config.getDraftCubes()) {
            CubeFactory.getInstance().addDraftCube(plugin.getName(), loadPlugin(plugin));
        }
        for (Plugin plugin: config.getDeckTypes()) {
            DeckValidatorFactory.getInstance().addDeckType(plugin.getName(), loadPlugin(plugin));
        }

        logger.info("Config - max seconds idle: " + config.getMaxSecondsIdle());
        logger.info("Config - max game threads: " + config.getMaxGameThreads());
        logger.info("Config - max AI opponents: " + config.getMaxAiOpponents());
        logger.info("Config - min user name l.: " + config.getMinUserNameLength());
        logger.info("Config - max user name l.: " + config.getMaxUserNameLength());
        logger.info("Config - save game active: " + (config.isSaveGameActivated() ? "True":"false"));
        
        logger.info("Config - backlog size    : " + config.getBacklogSize());
        logger.info("Config - lease period    : " + config.getLeasePeriod());
        logger.info("Config - max pool size   : " + config.getMaxPoolSize());
        logger.info("Config - num accp.threads: " + config.getNumAcceptThreads());
        logger.info("Config - second.bind port: " + config.getSecondaryBindPort());
        
//        Connection connection = new Connection("&maxPoolSize=" + config.getMaxPoolSize());
//        connection.setHost(config.getServerAddress());
//        connection.setPort(config.getPort());
        try {
            instance = new Main(adminPassword, testMode);
            server = new Server(instance);
            server.start(config.getPort(), config.isUseSSL());
            // Parameter: serializationtype => jboss
//            InvokerLocator serverLocator = new InvokerLocator(connection.getURI());                
//            if (!isAlreadyRunning(serverLocator)) {
//                server = new MageTransporterServer(serverLocator, new MageServerImpl(adminPassword, testMode), MageServer.class.getName(), new MageServerInvocationHandler());
//                server.start();
//                logger.info("Started MAGE server - listening on " + connection.toString());
//                
//                if (testMode) {
//                    logger.info("MAGE server running in test mode");
//                }
//                initStatistics();
//            }
//            else {
//                logger.fatal("Unable to start MAGE server - another server is already started");
//            }
//        } catch (IOException ex) {
//            logger.fatal("Failed to start server - " + connection.toString(), ex);
        } catch (Exception ex) {
            logger.fatal("Failed to start server - " + config.getServerAddress(), ex);
        }

    }

    public static Main getInstance() {
        return instance;
    }
    
    @Override
    public boolean registerClient(Connection connection, String sessionId, MageVersion version, String host)  {
        try {
            if (version.compareTo(Main.getVersion()) != 0) {
                logger.info("MageVersionException: userName=" + connection.getUsername() + ", version=" + version);
                LogServiceImpl.instance.log(LogKeys.KEY_WRONG_VERSION, connection.getUsername(), version.toString(), Main.getVersion().toString(), sessionId);
                String message = "Wrong client version " + version + ", expecting version " + Main.getVersion() + ". \r\n\r\nPlease download needed version from http://XMage.de or http://www.slightlymagic.net/forum/viewforum.php?f=70";
                server.informClient(sessionId, "Wrong version", message, MessageType.ERROR);
                return false;
            }
            return SessionManager.getInstance().registerUser(sessionId, connection, host);
        } catch (MageException ex) {
//            if (ex instanceof MageVersionException) {
//                throw (MageVersionException)ex;
//            }
            handleException(sessionId, ex);
        }
        return false;
    }

//    @Override
//    public boolean setUserData(final String userName, final String sessionId, final UserDataView userDataView) throws MageException {
//        return executeWithResult("setUserData", sessionId, new ActionWithBooleanResult() {
//            @Override
//            public Boolean execute() throws MageException {
//                return SessionManager.getInstance().setUserData(userName, sessionId, userDataView);
//            }
//        });
//    }
//
//    @Override
//    public boolean registerAdmin(String password, String sessionId, MageVersion version) throws MageException {
//        try {
//            if (version.compareTo(Main.getVersion()) != 0) {
//                throw new MageException("Wrong client version " + version + ", expecting version " + Main.getVersion());
//            }
//            if (!password.equals(this.password)) {
//                throw new MageException("Wrong password");
//            }
//            return SessionManager.getInstance().registerAdmin(sessionId);
//        } catch (Exception ex) {
//            handleException(ex);
//        }
//        return false;
//    }
//
    @Override
    public TableView createTable(final String sessionId, final UUID roomId, final MatchOptions options) {
//        return executeWithResult("createTable", sessionId, new ActionWithTableViewResult() {
//            @Override
//            public TableView execute() throws MageException {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                TableView table = GamesRoomManager.getInstance().getRoom(roomId).createTable(userId, options);
                if (logger.isDebugEnabled()) {
                    User user = UserManager.getInstance().getUser(userId);
                    if (user != null) {
                        logger.debug("TABLE created - tableId: " + table.getTableId() + " " + table.getTableName());
                        logger.debug("- " + user.getName() + " userId: " + user.getId());
                        logger.debug("- chatId: " + TableManager.getInstance().getChatId(table.getTableId()));                        
                    }
                }
                LogServiceImpl.instance.log(LogKeys.KEY_TABLE_CREATED, sessionId, userId.toString(), table.getTableId().toString());
                return table;
//            }
//        });
    }
//
//    @Override
//    public TableView createTournamentTable(final String sessionId, final UUID roomId, final TournamentOptions options) throws MageException {
//        return executeWithResult("createTournamentTable", sessionId, new ActionWithTableViewResult() {
//            @Override
//            public TableView execute() throws MageException {
//                try {
//                    UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
//                    // check AI players max
//                    String maxAiOpponents = ConfigSettings.getInstance().getMaxAiOpponents();
//                    if (maxAiOpponents != null) {
//                        int max = Integer.parseInt(maxAiOpponents);
//                        int aiPlayers = 0;
//                        for (String playerType : options.getPlayerTypes()) {
//                            if (!playerType.equals("Human")) {
//                                aiPlayers++;
//                            }
//                        }
//                        if (aiPlayers > max) {
//                            User user = UserManager.getInstance().getUser(userId);
//                            if (user != null) {
//                                user.showUserMessage("Create tournament", "It's only allowed to use a maximum of " + max + " AI players.");
//                            }
//                            throw new MageException("No message");
//                        }
//                    }
//                    TableView table = GamesRoomManager.getInstance().getRoom(roomId).createTournamentTable(userId, options);
//                    logger.debug("Tournament table " + table.getTableId() + " created");
//                    LogServiceImpl.instance.log(LogKeys.KEY_TOURNAMENT_TABLE_CREATED, sessionId, userId.toString(), table.getTableId().toString());
//                    return table;
//                } catch (Exception ex) {
//                    handleException(ex);
//                }
//                return null;
//            }
//        });
//    }
//
    @Override
    public void removeTable(final String sessionId, final UUID roomId, final UUID tableId) {
//        execute("removeTable", sessionId, new Action() {
//            @Override
//            public void execute() {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                TableManager.getInstance().removeTable(userId, tableId);
//            }
//        });
    }
//
    @Override
    public boolean joinTable(final String sessionId, final UUID roomId, final UUID tableId, final String name, final String playerType, final int skill, final DeckCardLists deckList, final String password) {
//        return executeWithResult("joinTable", sessionId, new ActionWithBooleanResult() {
//            @Override
//            public Boolean execute() throws MageException {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                logger.debug(name + " joins tableId: " + tableId);
                if (userId == null) {
                    logger.fatal("Got no userId from sessionId" + sessionId + " tableId" + tableId);
                    return false;
                }                
                boolean ret = GamesRoomManager.getInstance().getRoom(roomId).joinTable(userId, tableId, name, playerType, skill, deckList, password);
                return ret;
//            }
//        });
    }
//
//    @Override
//    public boolean joinTournamentTable(final String sessionId, final UUID roomId, final UUID tableId, final String name, final String playerType, final int skill, final DeckCardLists deckList, final String password) throws MageException, GameException {
//        return executeWithResult("joinTournamentTable", sessionId, new ActionWithBooleanResult() {
//            @Override
//            public Boolean execute() throws MageException {
//                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
//                if (logger.isTraceEnabled()) {
//                    User user = UserManager.getInstance().getUser(userId);
//                    if (user != null) {
//                        logger.trace("join tourn. tableId: " + tableId + " " + name);
//                    }
//                }                
//                if (userId == null) {
//                    logger.fatal("Got no userId from sessionId" + sessionId + " tableId" + tableId);
//                    return false;
//                }                 
//                boolean ret = GamesRoomManager.getInstance().getRoom(roomId).joinTournamentTable(userId, tableId, name, playerType, skill, deckList, password);
//                return ret;
//            }
//        });
//    }
//
//    @Override
//    public boolean submitDeck(final String sessionId, final UUID tableId, final DeckCardLists deckList) throws MageException, GameException {
//        return executeWithResult("submitDeck", sessionId, new ActionWithBooleanResult() {
//            @Override
//            public Boolean execute() throws MageException {
//                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
//                boolean ret = TableManager.getInstance().submitDeck(userId, tableId, deckList);
//                logger.debug("Session " + sessionId + " submitted deck");
//                return ret;
//            }
//        });
//    }
//
//    @Override
//    public void updateDeck(final String sessionId, final UUID tableId, final DeckCardLists deckList) throws MageException, GameException {
//        execute("updateDeck", sessionId, new Action() {
//            @Override
//            public void execute() throws MageException {
//                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
//                TableManager.getInstance().updateDeck(userId, tableId, deckList);
//                logger.trace("Session " + sessionId + " updated deck");
//            }
//        });
//    }
//
    @Override
    //FIXME: why no sessionId here???
    public RoomView getRoom(UUID roomId) {
//        try {
            GamesRoom room = GamesRoomManager.getInstance().getRoom(roomId);
            if (room != null) {
                return new RoomView(room.getRoomUsersInfo(), room.getTables(), room.getFinished());
            } else {
                return null;
            }
//        }
//        catch (Exception ex) {
//            handleException(ex);
//        }
//        return null;
    }
//
//    @Override
//    //FIXME: why no sessionId here???
//    public List<MatchView> getFinishedMatches(UUID roomId) throws MageException {
//        try {
//            GamesRoom room = GamesRoomManager.getInstance().getRoom(roomId);
//            if (room != null) {
//                return room.getFinished();
//            } else {
//                return null;
//            }
//        }
//        catch (Exception ex) {
//            handleException(ex);
//        }
//        return null;
//    }
//
//    @Override
//    public List<RoomUsersView> getRoomUsers(UUID roomId) throws MageException {
//        try {
//            GamesRoom room = GamesRoomManager.getInstance().getRoom(roomId);
//            if (room != null) {
//                return room.getRoomUsersInfo();
//            } else {
//                return null;
//            }
//        }
//        catch (Exception ex) {
//            handleException(ex);
//        }
//        return null;
//    }
//
    @Override
    //FIXME: why no sessionId here???
    public TableView getTable(UUID roomId, UUID tableId) {
//        try {
            GamesRoom room = GamesRoomManager.getInstance().getRoom(roomId);
            if (room != null) {
                return room.getTable(tableId);
            } else {
                return null;
            }
//        }
//        catch (Exception ex) {
//            handleException(ex);
//        }
//        return null;
    }
//
//    @Override
//    public boolean ping(String sessionId, String pingInfo) {
//        return SessionManager.getInstance().extendUserSession(sessionId, pingInfo);
//    }

    public void pingClient(String sessionId) {
        server.pingClient(sessionId);
    }

    @Override
    public void pingTime(long milliSeconds, String sessionId) {
        SessionManager.getInstance().recordPingTime(sessionId, milliSeconds);
    }

//
//    @Override
//    public void deregisterClient(final String sessionId) throws MageException {
//        execute("deregisterClient", sessionId, new Action() {
//            @Override
//            public void execute() {
//                SessionManager.getInstance().disconnect(sessionId, true);
//                logger.debug("Client deregistered ...");
//            }
//        });
//    }
//
//    @Override
//    public boolean startMatch(final String sessionId, final UUID roomId, final UUID tableId) throws MageException {
//        if (!TableManager.getInstance().getController(tableId).changeTableState(TableState.STARTING)) {
//            return false;
//        }
//        execute("startMatch", sessionId, new Action() {
//            @Override
//            public void execute() {
//                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
//                TableManager.getInstance().startMatch(userId, roomId, tableId);
//            }
//        });
//        return true;
//    }
//
//    @Override
//    public void startChallenge(final String sessionId, final UUID roomId, final UUID tableId, final UUID challengeId) throws MageException {
//        execute("startChallenge", sessionId, new Action() {
//            @Override
//            public void execute() {
//                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
//                TableManager.getInstance().startChallenge(userId, roomId, tableId, challengeId);
//            }
//        });
//    }
//
//    @Override
//    public boolean startTournament(final String sessionId, final UUID roomId, final UUID tableId) throws MageException {
//        if (!TableManager.getInstance().getController(tableId).changeTableState(TableState.STARTING)) {
//            return false;
//        }
//        execute("startTournament", sessionId, new Action() {
//            @Override
//            public void execute() {
//                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
//                TableManager.getInstance().startTournament(userId, roomId, tableId);
//            }
//        });
//        return true;
//    }
//
//    @Override
//    //FIXME: why no sessionId here???
//    public TournamentView getTournament(UUID tournamentId) throws MageException {
//        try {
//            return TournamentManager.getInstance().getTournamentView(tournamentId);
//        }
//        catch (Exception ex) {
//            handleException(ex);
//        }
//        return null;
//    }
//
//    @Override
//    //FIXME: why no sessionId here???
//    public void sendChatMessage(final UUID chatId, final String userName, final String message) throws MageException {
//        try {
//            callExecutor.execute(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        ChatManager.getInstance().broadcast(chatId, userName, StringEscapeUtils.escapeHtml4(message), MessageColor.BLUE);
//                    }
//                }
//            );
//        }
//        catch (Exception ex) {
//            handleException(ex);
//        }
//    }

    @Override
    public void receiveChatMessage(final UUID chatId, final String sessionId, final String message) {
//        execute("receiveChatMessage", sessionId, new Action() {
//            @Override
//            public void execute() {
                User user = SessionManager.getInstance().getUser(sessionId);
                if (user != null)
                    ChatManager.getInstance().broadcast(chatId, user, StringEscapeUtils.escapeHtml4(message), MessageColor.BLUE);
//            }
//        });
//        }
//        catch (Exception ex) {
//            handleException(sessionId, ex);
//        }
    }
    
    public void sendChatMessage(String sessionId, UUID chatId, ChatMessage message) {
        server.sendChatMessage(sessionId, chatId, message);
    }
    
    @Override
    public void joinChat(final UUID chatId, final String sessionId) {
//        execute("joinChat", sessionId, new Action() {
//            @Override
//            public void execute() {
                Session session = SessionManager.getInstance().getSession(sessionId);
                if (session != null)
                    ChatManager.getInstance().joinChat(chatId, session.getUserId());
//            }
//        });
    }

    @Override
    public void leaveChat(final UUID chatId, final String sessionId) {
//        execute("leaveChat", sessionId, new Action() {
//            @Override
//            public void execute() {
                Session session = SessionManager.getInstance().getSession(sessionId);
                if (session != null)
                    ChatManager.getInstance().leaveChat(chatId, session.getUserId());
//            }
//        });
    }

//    @Override
//    //FIXME: why no sessionId here???
//    public UUID getMainRoomId() throws MageException {
//        try {
//            return GamesRoomManager.getInstance().getMainRoomId();
//        }
//        catch (Exception ex) {
//            handleException(ex);
//        }
//        return null;
//    }
//
    @Override
//    //FIXME: why no sessionId here???
    public UUID getRoomChatId(UUID roomId) {
//        try {
            return GamesRoomManager.getInstance().getRoom(roomId).getChatId();
//        }
//        catch (Exception ex) {
//            handleException(ex);
//        }
//        return null;
    }
//
//    @Override
//    public boolean isTableOwner(final String sessionId, UUID roomId, final UUID tableId) throws MageException {
//        return executeWithResult("isTableOwner", sessionId, new ActionWithBooleanResult() {
//            @Override
//            public Boolean execute() {
//                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
//                return TableManager.getInstance().isTableOwner(tableId, userId);
//            }
//        });
//    }
//
    @Override
    public void swapSeats(final String sessionId, final UUID roomId, final UUID tableId, final int seatNum1, final int seatNum2) {
//        execute("swapSeats", sessionId, new Action() {
//            @Override
//            public void execute() {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                TableManager.getInstance().swapSeats(tableId, userId, seatNum1, seatNum2);
//            }
//        });
    }
//
    @Override
    public boolean leaveTable(final String sessionId, final UUID roomId, final UUID tableId) {
        TableState tableState = TableManager.getInstance().getController(tableId).getTableState();
        if (!tableState.equals(TableState.WAITING) && !tableState.equals(TableState.READY_TO_START)) {
            // table was already started, so player can't leave anymore now
            return false;
        }
//        execute("leaveTable", sessionId, new Action() {
//            @Override
//            public void execute() {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                GamesRoomManager.getInstance().getRoom(roomId).leaveTable(userId, tableId);
//            }
//        });
        return true;
    }
//
//    @Override
//    //FIXME: why no sessionId here???
//    public UUID getTableChatId(UUID tableId) throws MageException {
//        try {
//            return TableManager.getInstance().getChatId(tableId);
//        }
//        catch (Exception ex) {
//            handleException(ex);
//        }
//        return null;
//    }
//
//    @Override
//    public void joinGame(final UUID gameId, final String sessionId) throws MageException {
//        execute("joinGame", sessionId, new Action() {
//            @Override
//            public void execute() {
//                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
//                GameManager.getInstance().joinGame(gameId, userId);
//            }
//        });
//    }
//
//    @Override
//    public void joinDraft(final UUID draftId, final String sessionId) throws MageException {
//        execute("joinDraft", sessionId, new Action() {
//            @Override
//            public void execute() {
//                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
//                DraftManager.getInstance().joinDraft(draftId, userId);
//            }
//        });
//    }
//
//    @Override
//    public void joinTournament(final UUID tournamentId, final String sessionId) throws MageException {
//        execute("joinTournament", sessionId, new Action() {
//            @Override
//            public void execute() {
//                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
//                TournamentManager.getInstance().joinTournament(tournamentId, userId);
//            }
//        });
//    }
//
//    @Override
//    //FIXME: why no sessionId here???
//    public UUID getGameChatId(UUID gameId) throws MageException {
//        try {
//            return GameManager.getInstance().getChatId(gameId);
//        }
//        catch (Exception ex) {
//            handleException(ex);
//        }
//        return null;
//    }
//
//    @Override
//    //FIXME: why no sessionId here???
//    public UUID getTournamentChatId(UUID tournamentId) throws MageException {
//        try {
//            return TournamentManager.getInstance().getChatId(tournamentId);
//        }
//        catch (Exception ex) {
//            handleException(ex);
//        }
//        return null;
//    }
//
//    @Override
//    public void sendPlayerUUID(final UUID gameId, final String sessionId, final UUID data) throws MageException {
//        execute("sendPlayerUUID", sessionId, new Action() {
//            @Override
//            public void execute() {
//                User user = SessionManager.getInstance().getUser(sessionId);
//                if (user != null) {
////                    logger.warn("sendPlayerUUID gameId=" + gameId + " sessionId=" + sessionId + " username=" + user.getName());
//                    user.sendPlayerUUID(gameId, data);
//                } else {
//                    logger.warn("Your session expired: gameId=" + gameId + ", sessionId=" + sessionId);
//                }                 
//            }
//        });
//    }
//
//    @Override
//    public void sendPlayerString(final UUID gameId, final String sessionId, final String data) throws MageException {
//        execute("sendPlayerString", sessionId, new Action() {
//            @Override
//            public void execute() {
//                User user = SessionManager.getInstance().getUser(sessionId);
//                if (user != null) {
//                    user.sendPlayerString(gameId, data);
//                } else {
//                    logger.warn("Your session expired: gameId=" + gameId + ", sessionId=" + sessionId);
//                }                  
//            }
//        });
//    }
//
//    @Override
//    public void sendPlayerManaType(final UUID gameId, final UUID playerId, final String sessionId, final ManaType data) throws MageException {
//        execute("sendPlayerManaType", sessionId, new Action() {
//            @Override
//            public void execute() {
//                User user = SessionManager.getInstance().getUser(sessionId);
//                if (user != null) {
//                    user.sendPlayerManaType(gameId, playerId, data);
//                } else {
//                    logger.warn("Your session expired: gameId=" + gameId + ", sessionId=" + sessionId);
//                }
//            }
//        });
//    }
//
//    @Override
//    public void sendPlayerBoolean(final UUID gameId, final String sessionId, final Boolean data) throws MageException {
//        execute("sendPlayerBoolean", sessionId, new Action() {
//            @Override
//            public void execute() {
//                User user = SessionManager.getInstance().getUser(sessionId);
//                if (user != null) {
//                    user.sendPlayerBoolean(gameId, data);
//                } else {
//                    logger.warn("Your session expired: gameId=" + gameId + ", sessionId=" + sessionId);
//                }                
//            }
//        });
//    }
//
//    @Override
//    public void sendPlayerInteger(final UUID gameId, final String sessionId, final Integer data) throws MageException {
//        execute("sendPlayerInteger", sessionId, new Action() {
//            @Override
//            public void execute() {
//                User user = SessionManager.getInstance().getUser(sessionId);
//                if (user != null) {
//                    user.sendPlayerInteger(gameId, data);
//                } else {
//                    logger.warn("Your session expired: gameId=" + gameId + ", sessionId=" + sessionId);
//                }
//            }
//        });
//    }
//
//    @Override
//    public DraftPickView sendCardPick(final UUID draftId, final String sessionId, final UUID cardPick, final Set<UUID> hiddenCards) throws MageException {
//        return executeWithResult("sendCardPick", sessionId, new ActionWithNullNegativeResult<DraftPickView>() {
//            @Override
//            public DraftPickView execute() {
//                Session session = SessionManager.getInstance().getSession(sessionId);
//                if (session != null) {
//                    return DraftManager.getInstance().sendCardPick(draftId, session.getUserId(), cardPick, hiddenCards);
//                } else{
//                    logger.error("Session not found sessionId: "+ sessionId + "  draftId:" + draftId);
//                }
//                return null;
//            }
//        });
//    }
//
//    @Override
//    public void sendCardMark(final UUID draftId, final String sessionId, final UUID cardPick) throws MageException {
//        execute("sendCardMark", sessionId, new Action() {
//            @Override
//            public void execute() {
//                Session session = SessionManager.getInstance().getSession(sessionId);
//                if (session != null) {
//                    DraftManager.getInstance().sendCardMark(draftId, session.getUserId(), cardPick);
//                } else{
//                    logger.error("Session not found sessionId: "+ sessionId + "  draftId:" + draftId);
//                }
//            }
//        });
//    }
//
//    @Override
//    public void quitMatch(final UUID gameId, final String sessionId) throws MageException {
//        execute("quitMatch", sessionId, new Action() {
//            @Override
//            public void execute() {
//                Session session = SessionManager.getInstance().getSession(sessionId);
//                if (session != null) {
//                    GameManager.getInstance().quitMatch(gameId, session.getUserId());
//                } else{
//                    logger.error("Session not found sessionId: "+ sessionId + "  gameId:" +gameId);
//                }
//            }
//        });
//    }
//
//    @Override
//    public void quitTournament(final UUID tournamentId, final String sessionId) throws MageException {
//        execute("quitTournament", sessionId, new Action() {
//            @Override
//            public void execute() {
//                Session session = SessionManager.getInstance().getSession(sessionId);
//                if (session != null) {
//                    TournamentManager.getInstance().quit(tournamentId, session.getUserId());
//                }else{
//                    logger.error("Session not found sessionId: "+ sessionId + "  tournamentId:" + tournamentId);
//                }
//            }
//        });
//    }
//
//    @Override
//    public void quitDraft(final UUID draftId, final String sessionId) throws MageException {
//        execute("quitDraft", sessionId, new Action() {
//            @Override
//            public void execute() {
//                Session session = SessionManager.getInstance().getSession(sessionId);
//                if (session == null) {
//                    logger.error("Session not found sessionId: "+ sessionId + "  draftId:" + draftId);
//                    return;
//                }
//                UUID tableId = DraftManager.getInstance().getControllerByDraftId(draftId).getTableId();
//                Table table = TableManager.getInstance().getTable(tableId);
//                if (table.isTournament()) {
//                    UUID tournamentId = table.getTournament().getId();
//                    TournamentManager.getInstance().quit(tournamentId, session.getUserId());
//                }
//            }
//        });
//    }
//
//    @Override
//    public void sendPlayerAction(final PlayerAction playerAction, final UUID gameId, final String sessionId, final Object data) throws MageException {
//        execute("sendPlayerAction", sessionId, new Action() {
//            @Override
//            public void execute() {
//                Session session = SessionManager.getInstance().getSession(sessionId);
//                if (session == null) {
//                    logger.error("Session not found sessionId: "+ sessionId + "  gameId:" + gameId);
//                    return;
//                }
//                GameManager.getInstance().sendPlayerAction(playerAction, gameId, session.getUserId(), data);
//            }
//        });
//    }
//            
//    @Override
//    public boolean watchTable(final String sessionId, final UUID roomId, final UUID tableId) throws MageException {
//        return executeWithResult("setUserData", sessionId, new ActionWithBooleanResult() {
//            @Override
//            public Boolean execute() throws MageException {
//                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
//                return GamesRoomManager.getInstance().getRoom(roomId).watchTable(userId, tableId);
//            }
//        });
//    }
//
//    @Override
//    public boolean watchTournamentTable(final String sessionId, final UUID tableId) throws MageException {
//        return executeWithResult("setUserData", sessionId, new ActionWithBooleanResult() {
//            @Override
//            public Boolean execute() throws MageException {
//                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
//                return TableManager.getInstance().watchTable(userId, tableId);
//            }
//        });
//    }
//
//    @Override
//    public void watchGame(final UUID gameId, final String sessionId) throws MageException {
//        execute("watchGame", sessionId, new Action() {
//            @Override
//            public void execute() {
//                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
//                GameManager.getInstance().watchGame(gameId, userId);
//            }
//        });
//    }
//
//    @Override
//    public void stopWatching(final UUID gameId, final String sessionId) throws MageException {
//        execute("stopWatching", sessionId, new Action() {
//            @Override
//            public void execute() {
//                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
//                User user = UserManager.getInstance().getUser(userId);
//                if (user != null) {
//                    GameManager.getInstance().stopWatching(gameId, userId);
//                    user.removeGameWatchInfo(gameId);
//                }
//
//            }
//        });
//    }
//
//    @Override
//    public void replayGame(final UUID gameId, final String sessionId) throws MageException {
//        execute("replayGame", sessionId, new Action() {
//            @Override
//            public void execute() {
//                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
//                ReplayManager.getInstance().replayGame(gameId, userId);
//            }
//        });
//    }
//
//    @Override
//    public void startReplay(final UUID gameId, final String sessionId) throws MageException {
//        execute("startReplay", sessionId, new Action() {
//            @Override
//            public void execute() {
//                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
//                ReplayManager.getInstance().startReplay(gameId, userId);
//            }
//        });
//    }
//
//    @Override
//    public void stopReplay(final UUID gameId, final String sessionId) throws MageException {
//        execute("stopReplay", sessionId, new Action() {
//            @Override
//            public void execute() {
//                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
//                ReplayManager.getInstance().stopReplay(gameId, userId);
//            }
//        });
//    }
//
//    @Override
//    public void nextPlay(final UUID gameId, final String sessionId) throws MageException {
//        execute("nextPlay", sessionId, new Action() {
//            @Override
//            public void execute() {
//                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
//                ReplayManager.getInstance().nextPlay(gameId, userId);
//            }
//        });
//    }
//
//    @Override
//    public void previousPlay(final UUID gameId, final String sessionId) throws MageException {
//        execute("previousPlay", sessionId, new Action() {
//            @Override
//            public void execute() {
//                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
//                ReplayManager.getInstance().previousPlay(gameId, userId);
//            }
//        });
//    }
//
//    @Override
//    public void skipForward(final UUID gameId, final String sessionId, final int moves) throws MageException {
//        execute("skipForward", sessionId, new Action() {
//            @Override
//            public void execute() {
//                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
//                ReplayManager.getInstance().skipForward(gameId, userId, moves);
//            }
//        });
//    }
//
    @Override
    public ServerState getServerState() {
//        try {
            return state;
//        }
//        catch (Exception ex) {
//            handleException(ex);
//        }
//        return null;
    }
//
//    @Override
//    public void cheat(final UUID gameId, final String sessionId, final UUID playerId, final DeckCardLists deckList) throws MageException {
//        execute("cheat", sessionId, new Action() {
//            @Override
//            public void execute() {
//                if (testMode) {
//                    UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
//                    GameManager.getInstance().cheat(gameId, userId, playerId, deckList);
//                }
//            }
//        });
//    }
//
//    @Override
//    public boolean cheat(final UUID gameId, final String sessionId, final UUID playerId, final String cardName) throws MageException {
//        return executeWithResult("cheatOne", sessionId, new ActionWithBooleanResult() {
//            @Override
//            public Boolean execute() {
//                if (testMode) {
//                    UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
//                    return GameManager.getInstance().cheat(gameId, userId, playerId, cardName);
//                }
//                return false;
//            }
//        });
//     }

    public void handleException(String sessionId, Exception ex) {
        if (!ex.getMessage().equals("No message")) {
            logger.fatal("", ex);
            server.informClient(sessionId, "Server error", ex.getMessage(), MessageType.ERROR);
//            throw new MageException("Server error: " + ex.getMessage());
        }
    }

//    @Override
//    public GameView getGameView(final UUID gameId, final String sessionId, final UUID playerId) throws MageException {
//         return executeWithResult("getGameView", sessionId, new ActionWithNullNegativeResult<GameView>() {
//             @Override
//             public GameView execute() throws MageException {
//                 UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
//                 return GameManager.getInstance().getGameView(gameId, userId, playerId);
//             }
//        });
//    }

//    /**
//     * Get user data for admin console
//     *
//     * @param sessionId
//     * @return
//     * @throws MageException
//     */
//    @Override
//    public List<UserView> getUsers(String sessionId) throws MageException {
//        return executeWithResult("getUsers", sessionId, new ActionWithNullNegativeResult<List<UserView>>() {
//            @Override
//            public List<UserView> execute() throws MageException {
//                List<UserView> users = new ArrayList<>();
//                for (User user : UserManager.getInstance().getUsers()) {
//
//                    users.add(new UserView(user.getName(), user.getHost(), user.getSessionId(), user.getConnectionTime(), user.getGameInfo()));
//                }
//                return users;
//            }
//        }, true);
//    }
//

    @Override
    public void disconnect(String sessionId, DisconnectReason reason) {
        SessionManager.getInstance().disconnect(sessionId, reason);
    }
    
//    @Override
//    public void disconnectUser(final String sessionId, final String userSessionId) throws MageException {
//        execute("disconnectUser", sessionId, new Action() {
//            @Override
//            public void execute() {
//                SessionManager.getInstance().disconnectUser(sessionId, userSessionId);
//            }
//        });
//    }
//
//    @Override
//    public void endUserSession(final String sessionId, final String userSessionId) throws MageException {
//        execute("endUserSession", sessionId, new Action() {
//            @Override
//            public void execute() {
//                SessionManager.getInstance().endUserSession(sessionId, userSessionId);
//            }
//        });
//    }

//    /**
//     * Admin console - Remove table 
//     * 
//     * @param sessionId
//     * @param tableId
//     * @throws MageException 
//     */
//    @Override
//    public void removeTable(final String sessionId, final UUID tableId) throws MageException {
//        execute("removeTable", sessionId, new Action() {
//            @Override
//            public void execute() {
//                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
//                TableManager.getInstance().removeTable(userId, tableId);
//            }
//        });
//    }
//
    
    @Override
    public List<String> getServerMessages() {
        return ServerMessagesUtil.getInstance().getMessages();
    }

//    @Override
//    public Object getServerMessagesCompressed(String sessionId) throws MageException {
//        return executeWithResult("getGameView", sessionId, new ActionWithNullNegativeResult<Object>() {
//            @Override
//             public Object execute() throws MageException {
//                 return CompressUtil.compress(ServerMessagesUtil.getInstance().getMessages());
//             }
//        });
//    }
//
//    @Override
//    public void sendFeedbackMessage(final String sessionId, final String username, final String title, final String type, final String message, final String email) throws MageException {
//        if (title != null && message != null) {
//            execute("sendFeedbackMessage", sessionId, new Action() {
//                @Override
//                public void execute() {
//                    String host = SessionManager.getInstance().getSession(sessionId).getHost();
//                    FeedbackServiceImpl.instance.feedback(username, title, type, message, email, host);
//                    LogServiceImpl.instance.log(LogKeys.KEY_FEEDBACK_ADDED, sessionId, username, host);
//                }
//            });
//        }
//    }
//    
//    @Override
//    public void sendBroadcastMessage(final String sessionId, final String message) throws MageException {
//        if (message != null) {
//            execute("sendBroadcastMessage", sessionId, new Action() {
//                @Override
//                public void execute() {
//                    for (User user : UserManager.getInstance().getUsers()) {
//                        if (message.toLowerCase(Locale.ENGLISH).startsWith("warn")) {
//                            user.fireCallback(new ClientCallback("serverMessage", null, new ChatMessage("SERVER", message, null, MessageColor.RED)));
//                        } else {
//                            user.fireCallback(new ClientCallback("serverMessage", null, new ChatMessage("SERVER", message, null, MessageColor.BLUE)));
//                        }
//                    }
//                }
//            }, true);
//        }
//    }
    
    @Override
    public void receiveBroadcastMessage(String title, String message, String sessionId) {
        if (SessionManager.getInstance().isAdmin(sessionId)) {
            if (message.toLowerCase(Locale.ENGLISH).startsWith("warn")) {
                server.informClients(title, message, MessageType.WARNING);
            } else {
                server.informClients(title, message, MessageType.INFORMATION);
            }
        }
    }

    public void informClient(String sessionId, String title, String message, MessageType type) {
        server.informClient(sessionId, title, message, type);
    }
    
//    protected void execute(final String actionName, final String sessionId, final Action action, boolean checkAdminRights) throws MageException {
//        if (checkAdminRights) {
//            if (!SessionManager.getInstance().isAdmin(sessionId)) {
//                LogServiceImpl.instance.log(LogKeys.KEY_NOT_ADMIN, actionName, sessionId);
//                return;
//            }
//        }
//        execute(actionName, sessionId, action);
//    }
//
//    protected void execute(final String actionName, final String sessionId, final Action action) {
//        if (SessionManager.getInstance().isValidSession(sessionId)) {
//            try {
//                callExecutor.execute(
//                    new Runnable() {
//                        @Override
//                        public void run() {
//                            if (SessionManager.getInstance().isValidSession(sessionId)) {
//                                try {
//                                    action.execute();
//                                } catch (MageException me) {
//                                    throw new RuntimeException(me);
//                                }
//                            } else {
//                                LogServiceImpl.instance.log(LogKeys.KEY_NOT_VALID_SESSION_INTERNAL, actionName, sessionId);
//                            }
//                        }
//                    }
//                );
//            }
//            catch (Exception ex) {                
//                handleException(sessionId, ex);
//            }
//        } else {
//            LogServiceImpl.instance.log(LogKeys.KEY_NOT_VALID_SESSION, actionName, sessionId);
//        }
//    }

//    protected <T> T executeWithResult(String actionName, final String sessionId, final ActionWithResult<T> action, boolean checkAdminRights) throws MageException {
//        if (checkAdminRights) {
//            if (!SessionManager.getInstance().isAdmin(sessionId)) {
//                LogServiceImpl.instance.log(LogKeys.KEY_NOT_ADMIN, actionName, sessionId);
//                return action.negativeResult();
//            }
//        }
//        return executeWithResult(actionName, sessionId, action);
//    }

    //TODO: also run in threads with future task
//    protected <T> T executeWithResult(String actionName, final String sessionId, final ActionWithResult<T> action) throws MageException {
//        if (SessionManager.getInstance().isValidSession(sessionId)) {
//            try {
//                return action.execute();
//            } catch (Exception ex) {
//                handleException(ex);
//            }
//        } else {
//            LogServiceImpl.instance.log(LogKeys.KEY_NOT_VALID_SESSION, actionName, sessionId);
//        }
//        return action.negativeResult();
//    }

//    @Override
//    public List<ExpansionInfo> getMissingExpansionData(List<String> codes) {
//        List<ExpansionInfo> result = new ArrayList<>();
//        for (ExpansionInfo expansionInfo : ExpansionRepository.instance.getAll()) {
//            if (!codes.contains(expansionInfo.getCode())) {
//                result.add(expansionInfo);
//            }
//        }
//        return result;
//    }
//
//    @Override
//    public List<CardInfo> getMissingCardsData(List<String> classNames) {
//        return CardRepository.instance.getMissingCards(classNames);
//    }

    private static Class<?> loadPlugin(Plugin plugin) {
        try {
            classLoader.addURL(new File(pluginFolder + "/" + plugin.getJar()).toURI().toURL());
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
            classLoader.addURL(new File(pluginFolder + "/" + plugin.getJar()).toURI().toURL());
            logger.debug("Loading game type: " + plugin.getClassName());
            return (MatchType) Class.forName(plugin.getTypeName(), true, classLoader).newInstance();
        } catch (ClassNotFoundException ex) {
            logger.warn("Game type not found:" + plugin.getJar() + " - check plugin folder", ex);
        } catch (MalformedURLException | InstantiationException | IllegalAccessException ex) {
            logger.fatal("Error loading game type " + plugin.getJar(), ex);
        }
        return null;
    }

    private static TournamentType loadTournamentType(GamePlugin plugin) {
        try {
            classLoader.addURL(new File(pluginFolder + "/" + plugin.getJar()).toURI().toURL());
            logger.debug("Loading tournament type: " + plugin.getClassName());
            return (TournamentType) Class.forName(plugin.getTypeName(), true, classLoader).newInstance();
        } catch (ClassNotFoundException ex) {
            logger.warn("Tournament type not found:" + plugin.getName() + " / "+ plugin.getJar() + " - check plugin folder", ex);
        } catch (MalformedURLException | InstantiationException | IllegalAccessException ex) {
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
            new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".game");
                }
            }
        );
        for (File file : files)
        {
            file.delete();
        }
    }

    public static MageVersion getVersion() {
        return version;
    }

    public boolean isTestMode() {
        return testMode;
    }

    public void joinedTable(String sessionId, UUID roomId, UUID tableId, UUID chatId, boolean owner, boolean tournament) {
        server.joinedTable(sessionId, roomId, tableId, chatId, owner, tournament);
    }

}
