package mage.server;

import mage.MageException;
import mage.cards.decks.DeckCardLists;
import mage.cards.decks.DeckValidatorFactory;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.cards.repository.ExpansionInfo;
import mage.cards.repository.ExpansionRepository;
import mage.constants.ManaType;
import mage.constants.PlayerAction;
import mage.constants.TableState;
import mage.game.Table;
import mage.game.match.MatchOptions;
import mage.game.tournament.TournamentOptions;
import mage.interfaces.Action;
import mage.interfaces.ActionWithResult;
import mage.remote.interfaces.MageServer;
import mage.interfaces.ServerState;
import mage.players.PlayerType;
import mage.players.net.UserData;
import mage.server.draft.CubeFactory;
import mage.server.game.GameFactory;
import mage.server.game.GamesRoom;
import mage.server.game.PlayerFactory;
import mage.server.managers.ManagerFactory;
import mage.server.services.impl.FeedbackServiceImpl;
import mage.server.tournament.TournamentFactory;
import mage.server.util.ServerMessagesUtil;
import mage.server.util.SystemUtil;
import mage.utils.*;
import mage.view.*;
import mage.view.ChatMessage.MessageColor;
import mage.remote.Connection;
import mage.remote.messages.MessageType;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;

import javax.management.timer.Timer;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.io.Serializable;

import static mage.server.Main.getVersion;

/**
 * @author BetaSteward_at_googlemail.com, noxx
 */
public class MageServerImpl implements MageServer {

    private static final Logger logger = Logger.getLogger(MageServerImpl.class);
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int PING_CYCLES = 10;

    private final ManagerFactory managerFactory;
    private final String adminPassword;
    private final boolean testMode;
    private final ExecutorService callExecutor;
    private final ServerState state;
    private final LinkedList<Long> pingTime = new LinkedList<>();
    private final LinkedHashMap<String, String> activeAuthTokens = new LinkedHashMap<String, String>() {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
            // Keep the latest 1024 auth tokens in memory.
            return size() > 1024;
        }
    };
    
    private String pingInfo = "";

    public MageServerImpl(ManagerFactory managerFactory, String adminPassword, boolean testMode) {
        this.managerFactory = managerFactory;
        this.adminPassword = adminPassword;
        this.testMode = testMode;
        this.callExecutor = managerFactory.threadExecutor().getCallExecutor();
        ServerMessagesUtil.instance.getMessages();
        state = new ServerState(
                GameFactory.instance.getGameTypes(),
                TournamentFactory.instance.getTournamentTypes(),
                PlayerFactory.instance.getPlayerTypes().toArray(new PlayerType[PlayerFactory.instance.getPlayerTypes().size()]),
                DeckValidatorFactory.instance.getDeckTypes().toArray(new String[DeckValidatorFactory.instance.getDeckTypes().size()]),
                CubeFactory.instance.getDraftCubes().toArray(new String[CubeFactory.instance.getDraftCubes().size()]),
                testMode,
                getVersion(),
                CardRepository.instance.getContentVersionConstant(),
                ExpansionRepository.instance.getContentVersionConstant(),
                managerFactory.gamesRoomManager().getMainRoomId()
        );
    }

    @Override
    public String registerUser(final Connection connection, final String sessionId, final MageVersion version, String host) {
        if (version.compareTo(getVersion()) != 0) {
            logger.info("MageVersionException: userName=" + connection.getUsername() + ", version=" + version);
            return "MageVersionException: userName=" + connection.getUsername() + ", version=" + version;
        }
        return managerFactory.sessionManager().registerUser(sessionId, connection, host);
    }

    // generateAuthToken returns a uniformly distributed 6-digits string.
    static private String generateAuthToken() {
        return String.format("%06d", RANDOM.nextInt(1000000));
    }

    @Override
    public boolean emailAuthToken(Connection connection){
        if (!managerFactory.configSettings().isAuthenticationActivated()) {
            return false;
        }

        AuthorizedUser authorizedUser = AuthorizedUserRepository.getInstance().getByEmail(connection.getEmail());
        if (authorizedUser == null) {
            logger.info("Auth token is requested for " + connection.getEmail() + " but there's no such user in DB");
            return false;
        }

        String authToken = generateAuthToken();
        activeAuthTokens.put(connection.getEmail(), authToken);
        String subject = "XMage Password Reset Auth Token";
        String text = "Use this auth token to reset " + authorizedUser.name + "'s password: " + authToken + '\n'
                + "It's valid until the next server restart.";
        boolean success;
        if (!managerFactory.configSettings().getMailUser().isEmpty()) {
            success = managerFactory.mailClient().sendMessage(connection.getEmail(), subject, text);
        } else {
            success = managerFactory.mailgunClient().sendMessage(connection.getEmail(), subject, text);
        }
        if (!success) {
            return false;
        }
        return true;
    }

    @Override
    public boolean resetPassword(Connection connection) {
        if (!managerFactory.configSettings().isAuthenticationActivated()) {
            return false;
        }
        
        // multi-step reset:
        // - send auth token
        // - check auth token to confirm reset
        
        String storedAuthToken = activeAuthTokens.get(connection.getEmail());
        if (storedAuthToken == null || !storedAuthToken.equals(connection.getAuthToken())) {
            logger.info("Invalid auth token " + connection.getAuthToken() + " is sent for " + connection.getEmail());
            return false;
        }

        AuthorizedUser authorizedUser = AuthorizedUserRepository.getInstance().getByEmail(connection.getEmail());
        if (authorizedUser == null) {
            logger.info("Auth token is valid, but the user with email address " + connection.getEmail() + " is no longer in the DB");
            return false;
        }

        // recreate user with new password
        AuthorizedUserRepository.getInstance().remove(authorizedUser.getName());
        AuthorizedUserRepository.getInstance().add(authorizedUser.getName(), connection.getPassword(), connection.getEmail());
        activeAuthTokens.remove(connection.getEmail());
        return true;
    }

    @Override
    public boolean connectUser(final Connection connection, final String sessionId, MageVersion version, String host)  {
        if (version.compareTo(Main.getVersion()) != 0) {
            logger.info("MageVersionException: userName=" + connection.getUsername() + ", version=" + version + " sessionId=" + sessionId);
            return false;
        }
        else {
            return managerFactory.sessionManager().connectUser(sessionId, connection, host);
        }
    }

    @Override
    public void setPreferences(final String sessionId, final UserData userData, final String clientVersion, final String userIdStr) {
        execute("setPreferences", sessionId, () -> managerFactory.sessionManager().setUserData(sessionId, userData, clientVersion, userIdStr));
    }

    @Override
    public TableView createTable(final String sessionId, final UUID roomId, final MatchOptions options) {
        return executeWithResult("createTable", sessionId, new MyActionWithTableViewResult(sessionId, options, roomId));
    }

    @Override
    public TableView createTournamentTable(final String sessionId, final UUID roomId, final TournamentOptions options) {
        return executeWithResult("createTournamentTable", sessionId, new ActionWithTableViewResult() {
            @Override
            public TableView execute() throws MageException {
                try {
                    Optional<Session> session = managerFactory.sessionManager().getSession(sessionId);
                    if (!session.isPresent()) {
                        logger.error("Session to found : " + sessionId);
                        return null;
                    }
                    UUID userId = session.get().getUserId();
                    Optional<User> _user = managerFactory.userManager().getUser(userId);
                    if (!_user.isPresent()) {
                        logger.error("User for session not found. session = " + sessionId);
                        return null;
                    }
                    User user = _user.get();

                    // check if user can create another table
                    int notStartedTables = user.getNumberOfNotStartedTables();
                    if (notStartedTables > 1) {
                        user.showUserMessage("Create table", "You have already " + notStartedTables + " not started tables. You can't create another.");
                        throw new MageException("No message");
                    }

                    // check AI players max
                    String maxAiOpponents = managerFactory.configSettings().getMaxAiOpponents();
                    if (maxAiOpponents != null) {
                        int aiPlayers = 0;
                        for (PlayerType playerType : options.getPlayerTypes()) {
                            if (playerType != PlayerType.HUMAN) {
                                aiPlayers++;
                            }
                        }
                        int max = Integer.parseInt(maxAiOpponents);
                        if (aiPlayers > max) {
                            user.showUserMessage("Create tournament", "It's only allowed to use a maximum of " + max + " AI players.");
                            throw new MageException("No message");
                        }
                    }

                    // check if the user satisfies the quitRatio requirement.
                    int quitRatio = options.getQuitRatio();
                    if (quitRatio < user.getTourneyQuitRatio()) {
                        String message = new StringBuilder("Your quit ratio ").append(user.getTourneyQuitRatio())
                                .append("% is higher than the table requirement ").append(quitRatio).append('%').toString();
                        user.showUserMessage("Create tournament", message);
                        throw new MageException("No message");
                    }

                    // check if the user satisfies the minimumRating requirement.
                    int minimumRating = options.getMinimumRating();
                    int userRating;
                    if (options.getMatchOptions().isLimited()) {
                        userRating = user.getUserData().getLimitedRating();
                    } else {
                        userRating = user.getUserData().getConstructedRating();
                    }
                    if (userRating < minimumRating) {
                        String message = new StringBuilder("Your rating ").append(userRating)
                                .append(" is lower than the table requirement ").append(minimumRating).toString();
                        user.showUserMessage("Create tournament", message);
                        throw new MageException("No message");
                    }
                    Optional<GamesRoom> room = managerFactory.gamesRoomManager().getRoom(roomId);
                    if (!room.isPresent()) {

                    } else {
                        TableView table = room.get().createTournamentTable(userId, options);
                        logger.debug("Tournament table " + table.getTableId() + " created");
                        return table;
                    }
                } catch (Exception ex) {
                    handleException(ex);
                }
                return null;
            }
        });
    }

    @Override
    public boolean joinTable(final String sessionId, final UUID roomId, final UUID tableId, final String name, final PlayerType playerType, final int skill, final DeckCardLists deckList, final String password) {
        UUID userId = managerFactory.sessionManager().getSession(sessionId).get().getUserId();
        logger.debug(name + " joins tableId: " + tableId);
        if (userId == null) {
            logger.fatal("Got no userId from sessionId" + sessionId + " tableId" + tableId);
            return false;
        }
        boolean ret = managerFactory.gamesRoomManager().getRoom(roomId).get().joinTable(userId, tableId, name, playerType, skill, deckList, password);
        return ret;
    }

    @Override
    public boolean joinTournamentTable(final String sessionId, final UUID roomId, final UUID tableId, final String name, final PlayerType playerType, final int skill, final DeckCardLists deckList, final String password) {
        return executeWithResult("joinTournamentTable", sessionId, new ActionWithBooleanResult() {
            @Override
            public Boolean execute()  {
                Optional<Session> session = managerFactory.sessionManager().getSession(sessionId);
                if (!session.isPresent()) {
                    return false;
                }
                UUID userId = session.get().getUserId();
                if (logger.isTraceEnabled()) {
                    Optional<User> user = managerFactory.userManager().getUser(userId);
                    user.ifPresent(user1 -> logger.trace("join tourn. tableId: " + tableId + ' ' + name));
                }
                if (userId == null) {
                    logger.fatal("Got no userId from sessionId" + sessionId + " tableId" + tableId);
                    return false;
                }
                Optional<GamesRoom> room = managerFactory.gamesRoomManager().getRoom(roomId);
                if (room.isPresent()) {
                    return room.get().joinTournamentTable(userId, tableId, name, playerType, skill, deckList, password);
                }
                return null;

            }
        });
    }

    @Override
    public boolean submitDeck(final String sessionId, final UUID tableId, final DeckCardLists deckList) {
        return executeWithResult("submitDeck", sessionId, new ActionWithBooleanResult() {
            @Override
            public Boolean execute() throws MageException {
                Optional<Session> session = managerFactory.sessionManager().getSession(sessionId);
                if (!session.isPresent()) {
                    return false;
                } else {
                    UUID userId = session.get().getUserId();
                    boolean ret = managerFactory.tableManager().submitDeck(userId, tableId, deckList);
                    logger.debug("Session " + sessionId + " submitted deck");
                    return ret;
                }
            }
        });
    }

    @Override
    public void updateDeck(final String sessionId, final UUID tableId, final DeckCardLists deckList) {
        execute("updateDeck", sessionId, () -> {
            Optional<Session> session = managerFactory.sessionManager().getSession(sessionId);
            if (!session.isPresent()) {
                logger.error("Session not found : " + sessionId);

            } else {
                UUID userId = session.get().getUserId();
                managerFactory.tableManager().updateDeck(userId, tableId, deckList);
                logger.trace("Session " + sessionId + " updated deck");
            }
        });
    }

    @Override
    public List<TableView> getTables(String sessionId, UUID roomId) {
        return executeWithResult("getTables", sessionId, new ActionWithResult<List<TableView>>() {
            @Override
            public List<TableView> execute() {
                try {
                    Optional<GamesRoom> room = managerFactory.gamesRoomManager().getRoom(roomId);
                    if (room.isPresent()) {
                        return room.get().getTables();
                    } else {
                        return new ArrayList<>();
                    }
                } catch (Exception ex) {
                    handleException(ex);
                }
                return null;
            }
            @Override
            public List<TableView> negativeResult() {
                return null;
            }
        });
    }

    @Override
    public TableView getTable(String sessionId, UUID roomId, UUID tableId) {
        return executeWithResult("getTable", sessionId, new ActionWithTableViewResult() {
            @Override
            public TableView execute() {
                try {
                    Optional<GamesRoom> room = managerFactory.gamesRoomManager().getRoom(roomId);
                    return room.flatMap(r -> r.getTable(tableId)).orElse(null);
                } catch (Exception ex) {
                    handleException(ex);
                }
                return null;
            }
        });
    }

    @Override
    public boolean ping(String sessionId, Connection connection) {
        try {
            if (sessionId != null) {
                long startTime = System.nanoTime();
                if (!managerFactory.sessionManager().extendUserSession(sessionId, pingInfo)) {
                    logger.error("Ping failed: " + connection.getUsername() + " Session: " + sessionId + " to MAGE server at " + connection.getHost() + ':' + connection.getPort());
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
        } catch (Exception ex) {
            handleException(ex);
        } 
        return false;
    }

    @Override
    public boolean startMatch(final String sessionId, final UUID roomId, final UUID tableId) {
        Optional<TableController> controller = managerFactory.tableManager().getController(tableId);
        if (!controller.isPresent()) {
            logger.error("table not found : " + tableId);
            return false;
        }
        if (!controller.get().changeTableStateToStarting()) {
            return false;
        }
        execute("startMatch", sessionId, () -> {
            Optional<Session> session = managerFactory.sessionManager().getSession(sessionId);
            if (!session.isPresent()) {
                logger.error("Session not found : " + sessionId);
            } else {
                UUID userId = session.get().getUserId();
                managerFactory.tableManager().startMatch(userId, roomId, tableId);
            }
        });
        return true;
    }

    @Override
    public boolean startTournament(final String sessionId, final UUID roomId, final UUID tableId) {
        Optional<TableController> controller = managerFactory.tableManager().getController(tableId);
        if (!controller.isPresent()) {
            logger.error("table not found : " + tableId);
            return false;
        }
        if (!controller.get().changeTableStateToStarting()) {
            return false;
        }
        execute("startTournament", sessionId, () -> {
            Optional<Session> session = managerFactory.sessionManager().getSession(sessionId);
            if (!session.isPresent()) {
                logger.error("Session not found : " + sessionId);
            } else {
                UUID userId = session.get().getUserId();
                managerFactory.tableManager().startTournament(userId, roomId, tableId);
            }
        });
        return true;
    }

    @Override
    public TournamentView getTournament(final String sessionId, final UUID tournamentId) {
        return executeWithResult("getTournament", sessionId, new ActionWithResult<TournamentView>() {
            @Override
            public TournamentView execute() {
                try {
                    return managerFactory.tournamentManager().getTournamentView(tournamentId);
                } catch (Exception ex) {
                    handleException(ex);
                }
                return null;
            }
            @Override
            public TournamentView negativeResult() {
                return null;
            }
        });
    }

    @Override
    public void joinChat(final UUID chatId, final String sessionId) {
        execute("joinChat", sessionId, () -> {
            managerFactory.sessionManager().getSession(sessionId).ifPresent(session -> {
                UUID userId = session.getUserId();
                managerFactory.chatManager().joinChat(chatId, userId);
            });
        });
    }

    @Override
    public void leaveChat(final UUID chatId, final String sessionId) {
        execute("leaveChat", sessionId, () -> {
            if (chatId != null) {
                managerFactory.sessionManager().getSession(sessionId).ifPresent(session -> {
                    UUID userId = session.getUserId();
                    managerFactory.chatManager().leaveChat(chatId, userId);
                });
            }
        });
    }

    @Override
    public UUID getMainRoomId(String sessionId) {
        return executeWithResult("getMainRoomId", sessionId, new ActionWithUUIDResult() {
            @Override
            public UUID execute() {
                try {
                    return managerFactory.gamesRoomManager().getMainRoomId();
                } catch (Exception ex) {
                    handleException(ex);
                }
                return null;
            }
        });
    }

    @Override
    public UUID getRoomChatId(String sessionId, UUID roomId) {
        return executeWithResult("getRoomChatId", sessionId, new ActionWithUUIDResult() {
            @Override
            public UUID execute()  {
                try {
                    Optional<GamesRoom> room = managerFactory.gamesRoomManager().getRoom(roomId);
                    if (!room.isPresent()) {
                        logger.error("roomId not found : " + roomId);
                        return null;
                    }
                    return room.get().getChatId();
                } catch (Exception ex) {
                    handleException(ex);
                }
                return null;
            }
        });
    }

    @Override
    public void swapSeats(final String sessionId, final UUID roomId, final UUID tableId, final int seatNum1, final int seatNum2) {
        execute("swapSeats", sessionId, () -> {
            managerFactory.sessionManager().getSession(sessionId).ifPresent(session -> {
                UUID userId = session.getUserId();
                managerFactory.tableManager().swapSeats(tableId, userId, seatNum1, seatNum2);
            });
        });
    }

    @Override
    public boolean leaveTable(final String sessionId, final UUID roomId, final UUID tableId) {
        Optional<TableController> tableController = managerFactory.tableManager().getController(tableId);
        if (tableController.isPresent()) {
            TableState tableState = tableController.get().getTableState();
            if (tableState != TableState.WAITING && tableState != TableState.READY_TO_START) {
                // table was already started, so player can't leave anymore now
                return false;
            }
            execute("leaveTable", sessionId, () -> {
                managerFactory.sessionManager().getSession(sessionId).ifPresent(session -> {
                    UUID userId = session.getUserId();
                    managerFactory.gamesRoomManager().getRoom(roomId).ifPresent(room ->
                            room.leaveTable(userId, tableId));

                });
            });
        } else {
            // this can happen if a game ends and a player quits XMage or a match nearly at the same time as the game ends
            logger.trace("table not found : " + tableId);
        }
        return true;
    }

    @Override
    public UUID joinGame(final UUID gameId, final String sessionId) {
        return executeWithResult("joinGame", sessionId, new ActionWithUUIDResult() {
            @Override
            public UUID execute()  {
                Optional<Session> session = managerFactory.sessionManager().getSession(sessionId);
                if(session.isPresent()){
                    UUID userId = session.get().getUserId();
                    managerFactory.gameManager().joinGame(gameId, userId);
                    Optional<UUID> chatId =  managerFactory.gameManager().getChatId(gameId);
                    if (chatId.isPresent()) {
                        return chatId.get();
                    }
                }
                return null;
            }
        });
    }

    @Override
    public void joinDraft(final UUID draftId, final String sessionId) {
        execute("joinDraft", sessionId, () -> {
            managerFactory.sessionManager().getSession(sessionId).ifPresent(session -> {
                UUID userId = session.getUserId();
                managerFactory.draftManager().joinDraft(draftId, userId);
            });
        });
    }

    @Override
    public void joinTournament(final UUID tournamentId, final String sessionId) {
        execute("joinTournament", sessionId, () -> {
            Optional<Session> session = managerFactory.sessionManager().getSession(sessionId);
            if (!session.isPresent()) {
                logger.error("Session not found : " + sessionId);
            } else {
                UUID userId = session.get().getUserId();
                managerFactory.tournamentManager().joinTournament(tournamentId, userId);
            }
        });
    }

    @Override
    public UUID getTournamentChatId(String sessionId, UUID tournamentId) {
        return executeWithResult("getTournamentChatId", sessionId, new ActionWithUUIDResult() {
            @Override
            public UUID execute() {
                try {
                    return managerFactory.tournamentManager().getChatId(tournamentId).orElse(null);
                } catch (Exception ex) {
                    handleException(ex);
                }
                return null;
            }
        });
    }

    @Override
    public void sendPlayerUUID(final UUID gameId, final String sessionId, final UUID data) {
        execute("sendPlayerUUID", sessionId, () -> {
            Optional<User> user = managerFactory.sessionManager().getUser(sessionId);
            if (user.isPresent()) {
                user.get().sendPlayerUUID(gameId, data);
            } else {
                logger.warn("Your session expired: gameId=" + gameId + ", sessionId=" + sessionId);
            }
        });
    }

    @Override
    public void sendPlayerString(final UUID gameId, final String sessionId, final String data) {
        execute("sendPlayerString", sessionId, () -> {
            Optional<User> user = managerFactory.sessionManager().getUser(sessionId);
            if (user.isPresent()) {
                user.get().sendPlayerString(gameId, data);
            } else {
                logger.warn("Your session expired: gameId=" + gameId + ", sessionId=" + sessionId);
            }
        });
    }

    @Override
    public void sendPlayerManaType(final UUID gameId, final UUID playerId, final String sessionId, final ManaType data) {
        execute("sendPlayerManaType", sessionId, () -> {
            Optional<User> user = managerFactory.sessionManager().getUser(sessionId);
            if (user.isPresent()) {
                user.get().sendPlayerManaType(gameId, playerId, data);
            } else {
                logger.warn("Your session expired: gameId=" + gameId + ", sessionId=" + sessionId);
            }
        });
    }

    @Override
    public void sendPlayerBoolean(final UUID gameId, final String sessionId, final Boolean data) {
        execute("sendPlayerBoolean", sessionId, () -> {
            Optional<User> user = managerFactory.sessionManager().getUser(sessionId);
            if (user.isPresent()) {
                user.get().sendPlayerBoolean(gameId, data);
            } else {
                logger.warn("Your session expired: gameId=" + gameId + ", sessionId=" + sessionId);
            }
        });
    }

    @Override
    public void sendPlayerInteger(final UUID gameId, final String sessionId, final Integer data) {
        execute("sendPlayerInteger", sessionId, () -> {
            Optional<User> user = managerFactory.sessionManager().getUser(sessionId);
            if (user.isPresent()) {
                user.get().sendPlayerInteger(gameId, data);
            } else {
                logger.warn("Your session expired: gameId=" + gameId + ", sessionId=" + sessionId);
            }
        });
    }

    @Override
    public DraftPickView pickCard(final UUID draftId, final String sessionId, final UUID cardPick, final Set<UUID> hiddenCards) {
        Session session = managerFactory.sessionManager().getSession(sessionId).get();
        if (session != null) {
            return managerFactory.draftManager().sendCardPick(draftId, session.getUserId(), cardPick, hiddenCards);
        } else {
            logger.error("Session not found sessionId: " + sessionId + "  draftId:" + draftId);
        }
        return null;
    }

    @Override
    public void markCard(final UUID draftId, final String sessionId, final UUID cardPick) {
        Session session = managerFactory.sessionManager().getSession(sessionId).get();
        if (session != null) {
            managerFactory.draftManager().sendCardMark(draftId, session.getUserId(), cardPick);
        } else {
            logger.error("Session not found sessionId: " + sessionId + "  draftId:" + draftId);
        }
    }
    
    @Override
    public void setBoosterLoaded(final UUID draftId, final String sessionId) throws MageException {
        execute("setBoosterLoaded", sessionId, () -> {
            managerFactory.sessionManager().getSession(sessionId).ifPresent(session -> {
                UUID userId = session.getUserId();
                managerFactory.draftManager().setBoosterLoaded(draftId, userId);
            });
        });
    }

    @Override
    public void quitMatch(final UUID gameId, final String sessionId) {
        execute("quitMatch", sessionId, () -> {
            try {
                callExecutor.execute(
                        () -> {
                            managerFactory.sessionManager().getSession(sessionId).ifPresent(session -> {
                                UUID userId = session.getUserId();
                                managerFactory.gameManager().quitMatch(gameId, userId);
                            });
                        }
                );
            } catch (Exception ex) {
                handleException(ex);
            }
        });
    }

    @Override
    public void quitTournament(final UUID tournamentId, final String sessionId) {
        execute("quitTournament", sessionId, () -> {
            try {
                callExecutor.execute(
                        () -> {
                            managerFactory.sessionManager().getSession(sessionId).ifPresent(session -> {
                                UUID userId = session.getUserId();

                                managerFactory.tournamentManager().quit(tournamentId, userId);
                            });
                        }
                );
            } catch (Exception ex) {
                handleException(ex);
            }
        });
    }

    @Override
    public void quitDraft(final UUID draftId, final String sessionId) {
        execute("quitDraft", sessionId, () -> {
                try {
                    callExecutor.execute(
                            () -> {
                                managerFactory.sessionManager().getSession(sessionId).ifPresent(
                                        session -> {
                                            UUID userId = session.getUserId();
                                            UUID tableId = managerFactory.draftManager().getControllerByDraftId(draftId).getTableId();
                                            Table table = managerFactory.tableManager().getTable(tableId);
                                            if (table.isTournament()) {
                                                UUID tournamentId = table.getTournament().getId();
                                                managerFactory.tournamentManager().quit(tournamentId, userId);
                                            }
                                        });
                            }
                    );
                } catch (Exception ex) {
                    handleException(ex);
                }
            }
        );
    }

    @Override
    public void sendPlayerAction(final PlayerAction playerAction, final UUID gameId, final String sessionId, final Serializable data) {
        execute("sendPlayerAction", sessionId, () -> {
            managerFactory.sessionManager().getSession(sessionId).ifPresent(session -> {
                UUID userId = session.getUserId();
                managerFactory.gameManager().sendPlayerAction(playerAction, gameId, userId, data);
            });
        });
    }

    @Override
    public void watchTable(final String sessionId, final UUID roomId, final UUID tableId) {
        execute("watchTable", sessionId, () -> {
            UUID userId = managerFactory.sessionManager().getSession(sessionId).get().getUserId();
            managerFactory.gamesRoomManager().getRoom(roomId).get().watchTable(userId, tableId);
        });
    }

    @Override
    public void watchTournamentTable(final String sessionId, final UUID tableId) {
        execute("watchTournamentTable", sessionId, () -> {
            UUID userId = managerFactory.sessionManager().getSession(sessionId).get().getUserId();
            managerFactory.tableManager().watchTable(userId, tableId);
        });
    }

    @Override
    public boolean watchGame(final UUID gameId, final String sessionId) {
        return executeWithResult("watchGame", sessionId, new ActionWithResult<Boolean>() {
            @Override
            public Boolean execute() {
                return managerFactory.sessionManager().getSession(sessionId)
                        .map(session -> {
                            UUID userId = session.getUserId();
                            if (managerFactory.gameManager().watchGame(gameId, userId)!=null){
                                return true;
                            } else {
                                return false;
                            }
                        }).orElse(false);
            }

            @Override
            public Boolean negativeResult() {
                return false;
            }
        });
    }

    @Override
    public void stopWatching(final UUID gameId, final String sessionId) {
        execute("stopWatching", sessionId, () -> {
            managerFactory.sessionManager().getSession(sessionId).ifPresent(session -> {
                UUID userId = session.getUserId();
                managerFactory.userManager().getUser(userId).ifPresent(user -> {
                    managerFactory.gameManager().stopWatching(gameId, userId);
                    user.removeGameWatchInfo(gameId);
                });
            });
        });
    }

    @Override
    public void replayGame(final UUID gameId, final String sessionId) {
        execute("replayGame", sessionId, () -> {
            managerFactory.sessionManager().getSession(sessionId).ifPresent(session -> {
                UUID userId = session.getUserId();
                managerFactory.replayManager().replayGame(gameId, userId);
            });
        });
    }

    @Override
    public void startReplay(final UUID gameId, final String sessionId) {
        execute("startReplay", sessionId, () -> {
            managerFactory.sessionManager().getSession(sessionId).ifPresent(session -> {
                UUID userId = session.getUserId();
                managerFactory.replayManager().startReplay(gameId, userId);
            });
        });
    }

    @Override
    public void stopReplay(final UUID gameId, final String sessionId) {
        execute("stopReplay", sessionId, () -> {
            Optional<Session> session = managerFactory.sessionManager().getSession(sessionId);
            if (!session.isPresent()) {
                logger.error("Session not found : " + sessionId);
            } else {
                UUID userId = session.get().getUserId();
                managerFactory.replayManager().stopReplay(gameId, userId);
            }
        });
    }

    @Override
    public void nextPlay(final UUID gameId, final String sessionId) {
        execute("nextPlay", sessionId, () -> {
            Optional<Session> session = managerFactory.sessionManager().getSession(sessionId);
            if (!session.isPresent()) {
                logger.error("Session not found : " + sessionId);
            } else {
                UUID userId = session.get().getUserId();
                managerFactory.replayManager().nextPlay(gameId, userId);
            }
        });
    }

    @Override
    public void previousPlay(final UUID gameId, final String sessionId) {
        execute("previousPlay", sessionId, () -> {
            Optional<Session> session = managerFactory.sessionManager().getSession(sessionId);
            if (!session.isPresent()) {
                logger.error("Session not found : " + sessionId);
            } else {
                UUID userId = session.get().getUserId();
                managerFactory.replayManager().previousPlay(gameId, userId);
            }
        });
    }

    @Override
    public void skipForward(final UUID gameId, final String sessionId, final int moves) {
        execute("skipForward", sessionId, () -> {
            Optional<Session> session = managerFactory.sessionManager().getSession(sessionId);
            if (!session.isPresent()) {
                logger.error("Session not found : " + sessionId);
            } else {
                UUID userId = session.get().getUserId();
                managerFactory.replayManager().skipForward(gameId, userId, moves);
            }
        });
    }

    @Override
    public ServerState getServerState() {
        return state;
    }

    @Override
    public void cheat(final UUID gameId, final String sessionId, final UUID playerId, final DeckCardLists deckList) {
        execute("cheat", sessionId, () -> {
            if (testMode) {
                managerFactory.sessionManager().getSession(sessionId).ifPresent(session -> {
                    UUID userId = session.getUserId();
                    managerFactory.gameManager().cheat(gameId, userId, playerId, deckList);
                });
            }
        });
    }

    private void handleException(Exception ex) {
        if (!ex.getMessage().equals("No message")) {
            logger.fatal("", ex);
        }
    }

    /**
     * Get user data for admin console
     *
     * @param sessionId
     * @return
     */
    @Override
    public List<UserView> getUsers(String sessionId) {
        return executeWithResult("getUsers", sessionId, new ListActionWithNullNegativeResult(), true);
    }

    @Override
    public void disconnectUser(final String sessionId, final String userSessionId) {
        execute("disconnectUser", sessionId, () -> managerFactory.sessionManager().disconnectUser(sessionId, userSessionId));
    }
    
    @Override
    public void muteUser(final String sessionId, final String userName, final long durationMinutes) {
        execute("muteUser", sessionId, () -> {
            managerFactory.userManager().getUserByName(userName).ifPresent(user -> {
                Date muteUntil = new Date(Calendar.getInstance().getTimeInMillis() + (durationMinutes * Timer.ONE_MINUTE));
                user.showUserMessage("Admin info", "You were muted for chat messages until " + SystemUtil.dateFormat.format(muteUntil) + '.');
                user.setChatLockedUntil(muteUntil);
            });
        });
    }

    @Override
    public void lockUser(final String sessionId, final String userName, final long durationMinutes) {
        execute("lockUser", sessionId, () -> {
            managerFactory.userManager().getUserByName(userName).ifPresent(user -> {
                Date lockUntil = new Date(Calendar.getInstance().getTimeInMillis() + (durationMinutes * Timer.ONE_MINUTE));
                user.showUserMessage("Admin info", "Your user profile was locked until " + SystemUtil.dateFormat.format(lockUntil) + '.');
                user.setLockedUntil(lockUntil);
                if (user.isConnected()) {
                    managerFactory.sessionManager().disconnectUser(sessionId, user.getSessionId());
                }
            });
        });
    }

    @Override
    public void setActivation(final String sessionId, final String userName, boolean active)  {
        execute("setActivation", sessionId, () -> {
            AuthorizedUser authorizedUser = AuthorizedUserRepository.getInstance().getByName(userName);
            Optional<User> u = managerFactory.userManager().getUserByName(userName);
            if (u.isPresent()) {
                User user = u.get();
                user.setActive(active);
                if (!user.isActive() && user.isConnected()) {
                    managerFactory.sessionManager().disconnectUser(sessionId, user.getSessionId());
                }
            } else if (authorizedUser != null) {
                User theUser = new User(managerFactory, userName, "localhost", authorizedUser);
                theUser.setActive(active);
            }
        });
    }

    @Override
    public void toggleActivation(final String sessionId, final String userName) {
        execute("toggleActivation", sessionId, ()
                -> managerFactory.userManager().getUserByName(userName).ifPresent(user
                -> {
            user.setActive(!user.isActive());
            if (!user.isActive() && user.isConnected()) {
                managerFactory.sessionManager().disconnectUser(sessionId, user.getSessionId());
            }
        }));
    }

    @Override
    public void endUserSession(final String sessionId, final String userSessionId) {
        execute("endUserSession", sessionId, () -> managerFactory.sessionManager().endUserSession(sessionId, userSessionId));
    }

    /**
     * Admin console - Remove table
     *
     * @param sessionId
     * @param tableId
     */
    @Override
    public void removeTable(final String sessionId, final UUID tableId) {
        execute("removeTable", sessionId, () -> {
            managerFactory.sessionManager().getSession(sessionId).ifPresent(session -> {
                UUID userId = session.getUserId();
                managerFactory.tableManager().removeTable(userId, tableId);
            });
        });
    }

    @Override
    public Object getServerMessages(String sessionId) {
        return executeWithResult("getGameView", sessionId, new MyActionWithNullNegativeResult());
    }

    @Override
    public void sendFeedbackMessage(final String sessionId, final String title, final String type, final String message, final String email) {
        execute("sendFeedbackMessage", sessionId, () -> {
            managerFactory.sessionManager().getSession(sessionId).ifPresent(session -> {
                UUID userId = session.getUserId();
                managerFactory.userManager().getUser(userId).ifPresent(user -> {
                    FeedbackServiceImpl.instance.feedback(user.getName(), title, type, message, email, session.getHost());
                });
            });
        });
    }

    @Override
    public void sendBroadcastMessage(final String sessionId, final String message) {
        if (message != null) {
            execute("sendBroadcastMessage", sessionId, () -> {
                for (User user : managerFactory.userManager().getUsers()) {
                    if (message.toLowerCase(Locale.ENGLISH).startsWith("warn")) {
                        try{
                            managerFactory.chatManager().broadcast(user.getId(), message, MessageColor.RED);
                        } catch (Exception ex) {
                            handleException(ex);
                        }
                    } else {
                        try{
                            managerFactory.chatManager().broadcast(user.getId(), message, MessageColor.BLUE);
                        } catch (Exception ex) {
                            handleException(ex);
                        }
                    }
                }
            }, true);
        }
    }

    protected void execute(final String actionName, final String sessionId, final Action action, boolean checkAdminRights) {
        if (checkAdminRights) {
            if (!managerFactory.sessionManager().isAdmin(sessionId)) {
                return;
            }
        }
        execute(actionName, sessionId, action);
    }

    protected void execute(final String actionName, final String sessionId, final Action action) {
        if (managerFactory.sessionManager().isValidSession(sessionId)) {
            try {
                callExecutor.execute(
                        () -> {
                            if (managerFactory.sessionManager().isValidSession(sessionId)) {
                                try {
                                    action.execute();
                                } catch (MageException me) {
                                    throw new RuntimeException(me);
                                }
                            }
                        }
                );
            } catch (Exception ex) {
                handleException(ex);
            }
        }
    }

    protected <T> T executeWithResult(String actionName, final String sessionId, final ActionWithResult<T> action, boolean checkAdminRights) {
        if (checkAdminRights) {
            if (!managerFactory.sessionManager().isAdmin(sessionId)) {
                return action.negativeResult();
            }
        }
        return executeWithResult(actionName, sessionId, action);
    }

    //TODO: also run in threads with future task
    protected <T> T executeWithResult(String actionName, final String sessionId, final ActionWithResult<T> action) {
        if (managerFactory.sessionManager().isValidSession(sessionId)) {
            try {
                return action.execute();
            } catch (Exception ex) {
                handleException(ex);
            }
        }
        return action.negativeResult();
    }

    @Override
    public List<ExpansionInfo> getMissingExpansionData(List<String> setCodes) {
        List<ExpansionInfo> result = new ArrayList<>();
        for (ExpansionInfo expansionInfo : ExpansionRepository.instance.getAll()) {
            if (!setCodes.contains(expansionInfo.getCode())) {
                result.add(expansionInfo);
            }
        }
        logger.info("Missing exp downloaded: " + result.size());
        return result;
    }

    @Override
    public List<CardInfo> getMissingCardData(List<String> cards) {
        List<CardInfo> res = CardRepository.instance.getMissingCards(cards);
        logger.info("Missing cards downloaded: " + res.size());
        return res;
    }
    
    @Override
    public void pingTime(long milliSeconds, String sessionId) {
        managerFactory.sessionManager().getSession(sessionId).ifPresent(session -> {
            session.recordPingTime(milliSeconds);
        });
    }
    
    @Override
    public void disconnect(String sessionId, mage.remote.DisconnectReason reason) {
        execute("disconnectUser", sessionId, () -> managerFactory.sessionManager().disconnect(sessionId, reason));
    }
    
    @Override
    public RoomView getRoom(UUID roomId) {
        GamesRoom room = managerFactory.gamesRoomManager().getRoom(roomId).get();
        if (room != null) {
            return room.getRoomView();
        } else {
            return null;
        }
    }
    
    @Override
    public List<String> getCards() {
        return CardRepository.instance.getClassNames();
    }
    
    @Override
    public void receiveBroadcastMessage(String title, String message, String sessionId) {
        execute("receiveBroadcastMessage", sessionId, () -> {
            if (managerFactory.sessionManager().isAdmin(sessionId)) {
                if (message.toLowerCase(Locale.ENGLISH).startsWith("warn")) {
                    Main.getClientCallback().informClients(title, message, MessageType.WARNING);
                } else {
                    Main.getClientCallback().informClients(title, message, MessageType.INFORMATION);
                }
            }
        });
    }
    
    @Override
    public void receiveChatMessage(final UUID chatId, final String sessionId, final String message) {
        execute("receiveChatMessage", sessionId, () -> {
            managerFactory.sessionManager().getUser(sessionId).ifPresent(user -> {
                managerFactory.chatManager().broadcast(chatId, user, StringEscapeUtils.escapeHtml4(message), MessageColor.BLUE, true, null, mage.view.ChatMessage.MessageType.TALK, null);
            });
        });
    }

    private static class MyActionWithNullNegativeResult extends ActionWithNullNegativeResult<Object> {
        @Override
        public Object execute() {
            return CompressUtil.compress(ServerMessagesUtil.instance.getMessages());
        }
    }

    private class ListActionWithNullNegativeResult extends ActionWithNullNegativeResult<List<UserView>> {
        @Override
        public List<UserView> execute() {
            return managerFactory.userManager().getUserInfoList();
        }
    }

    private class MyActionWithTableViewResult extends ActionWithTableViewResult {

        private final String sessionId;
        private final MatchOptions options;
        private final UUID roomId;

        public MyActionWithTableViewResult(String sessionId, MatchOptions options, UUID roomId) {
            this.sessionId = sessionId;
            this.options = options;
            this.roomId = roomId;
        }

        @Override
        public TableView execute() throws MageException {
            Optional<Session> session = managerFactory.sessionManager().getSession(sessionId);
            if (!session.isPresent()) {
                return null;
            }
            UUID userId = session.get().getUserId();
            Optional<User> _user = managerFactory.userManager().getUser(userId);
            if (!_user.isPresent()) {
                logger.error("User for session not found. session = " + sessionId);
                return null;
            }
            User user = _user.get();
            // check if user can create another table
            int notStartedTables = user.getNumberOfNotStartedTables();
            if (notStartedTables > 1) {
                user.showUserMessage("Create table", "You have already " + notStartedTables + " not started tables. You can't create another.");
                throw new MageException("No message");
            }
            // check if the user itself satisfies the quitRatio requirement.
            int quitRatio = options.getQuitRatio();
            if (quitRatio < user.getMatchQuitRatio()) {
                user.showUserMessage("Create table", "Your quit ratio " + user.getMatchQuitRatio() + "% is higher than the table requirement " + quitRatio + '%');
                throw new MageException("No message");
            }
            // check if the user satisfies the minimumRating requirement.
            int minimumRating = options.getMinimumRating();
            int userRating;
            if (options.isLimited()) {
                userRating = user.getUserData().getLimitedRating();
            } else {
                userRating = user.getUserData().getConstructedRating();
            }
            if (userRating < minimumRating) {
                String message = new StringBuilder("Your rating ").append(userRating).append(" is lower than the table requirement ").append(minimumRating).toString();
                user.showUserMessage("Create table", message);
                throw new MageException("No message");
            }
            Optional<GamesRoom> room = managerFactory.gamesRoomManager().getRoom(roomId);
            if (room.isPresent()) {
                TableView table = room.get().createTable(userId, options);
                if (logger.isDebugEnabled()) {
                    logger.debug("TABLE created - tableId: " + table.getTableId() + ' ' + table.getTableName());
                    logger.debug("- " + user.getName() + " userId: " + user.getId());
                    logger.debug("- chatId: " + managerFactory.tableManager().getChatId(table.getTableId()));
                }
                return table;
            } else {
                return null;
            }
        }
    }

}
