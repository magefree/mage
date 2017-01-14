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

import mage.MageException;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.cards.repository.ExpansionInfo;
import mage.cards.repository.ExpansionRepository;
import mage.constants.ManaType;
import mage.constants.PlayerAction;
import mage.constants.TableState;
import mage.game.GameException;
import mage.game.Table;
import mage.game.match.MatchOptions;
import mage.game.tournament.TournamentOptions;
import mage.interfaces.Action;
import mage.interfaces.ActionWithResult;
import mage.interfaces.MageServer;
import mage.interfaces.ServerState;
import mage.interfaces.callback.ClientCallback;
import mage.players.net.UserData;
import mage.remote.MageVersionException;
import mage.server.draft.CubeFactory;
import mage.server.draft.DraftManager;
import mage.server.game.*;
import mage.server.services.impl.FeedbackServiceImpl;
import mage.server.tournament.TournamentFactory;
import mage.server.tournament.TournamentManager;
import mage.server.util.ConfigSettings;
import mage.server.util.ServerMessagesUtil;
import mage.server.util.SystemUtil;
import mage.server.util.ThreadExecutor;
import mage.utils.*;
import mage.view.*;
import mage.view.ChatMessage.MessageColor;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;

import javax.management.timer.Timer;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.ExecutorService;

/**
 * @author BetaSteward_at_googlemail.com, noxx
 */
public class MageServerImpl implements MageServer {

    private static final Logger logger = Logger.getLogger(MageServerImpl.class);
    private static final ExecutorService callExecutor = ThreadExecutor.getInstance().getCallExecutor();
    private static final SecureRandom RANDOM = new SecureRandom();

    private final String adminPassword;
    private final boolean testMode;
    private final LinkedHashMap<String, String> activeAuthTokens = new LinkedHashMap<String, String>() {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
            // Keep the latest 1024 auth tokens in memory.
            return size() > 1024;
        }
    };

    public MageServerImpl(String adminPassword, boolean testMode) {
        this.adminPassword = adminPassword;
        this.testMode = testMode;
        ServerMessagesUtil.getInstance().getMessages();
    }

    @Override
    public boolean registerUser(String sessionId, String userName, String password, String email) throws MageException {
        return SessionManager.getInstance().registerUser(sessionId, userName, password, email);
    }

    // generateAuthToken returns a uniformly distributed 6-digits string.
    static private String generateAuthToken() {
        return String.format("%06d", RANDOM.nextInt(1000000));
    }

    @Override
    public boolean emailAuthToken(String sessionId, String email) throws MageException {
        if (!ConfigSettings.getInstance().isAuthenticationActivated()) {
            sendErrorMessageToClient(sessionId, "Registration is disabled by the server config");
            return false;
        }
        AuthorizedUser authorizedUser = AuthorizedUserRepository.instance.getByEmail(email);
        if (authorizedUser == null) {
            sendErrorMessageToClient(sessionId, "No user was found with the email address " + email);
            logger.info("Auth token is requested for " + email + " but there's no such user in DB");
            return false;
        }
        String authToken = generateAuthToken();
        activeAuthTokens.put(email, authToken);
        String subject = "XMage Password Reset Auth Token";
        String text = "Use this auth token to reset " + authorizedUser.name + "'s password: " + authToken + "\n"
                + "It's valid until the next server restart.";
        boolean success;
        if (!ConfigSettings.getInstance().getMailUser().isEmpty()) {
            success = MailClient.sendMessage(email, subject, text);
        } else {
            success = MailgunClient.sendMessage(email, subject, text);
        }
        if (!success) {
            sendErrorMessageToClient(sessionId, "There was an error inside the server while emailing an auth token");
            return false;
        }
        return true;
    }

    @Override
    public boolean resetPassword(String sessionId, String email, String authToken, String password) throws MageException {
        if (!ConfigSettings.getInstance().isAuthenticationActivated()) {
            sendErrorMessageToClient(sessionId, "Registration is disabled by the server config");
            return false;
        }
        String storedAuthToken = activeAuthTokens.get(email);
        if (storedAuthToken == null || !storedAuthToken.equals(authToken)) {
            sendErrorMessageToClient(sessionId, "Invalid auth token");
            logger.info("Invalid auth token " + authToken + " is sent for " + email);
            return false;
        }
        AuthorizedUser authorizedUser = AuthorizedUserRepository.instance.getByEmail(email);
        if (authorizedUser == null) {
            sendErrorMessageToClient(sessionId, "The user is no longer in the DB");
            logger.info("Auth token is valid, but the user with email address " + email + " is no longer in the DB");
            return false;
        }
        AuthorizedUserRepository.instance.remove(authorizedUser.getName());
        AuthorizedUserRepository.instance.add(authorizedUser.getName(), password, email);
        activeAuthTokens.remove(email);
        return true;
    }

    @Override
    public boolean connectUser(String userName, String password, String sessionId, MageVersion version) throws MageException {
        try {
            if (version.compareTo(Main.getVersion()) != 0) {
                logger.info("MageVersionException: userName=" + userName + ", version=" + version);
                throw new MageVersionException(version, Main.getVersion());
            }
            return SessionManager.getInstance().connectUser(sessionId, userName, password);
        } catch (MageException ex) {
            if (ex instanceof MageVersionException) {
                throw (MageVersionException) ex;
            }
            handleException(ex);
        }
        return false;
    }

    @Override
    public boolean setUserData(final String userName, final String sessionId, final UserData userData, final String clientVersion) throws MageException {
        return executeWithResult("setUserData", sessionId, new ActionWithBooleanResult() {
            @Override
            public Boolean execute() throws MageException {
                return SessionManager.getInstance().setUserData(userName, sessionId, userData, clientVersion);
            }
        });
    }

    @Override
    public boolean connectAdmin(String adminPassword, String sessionId, MageVersion version) throws MageException {
        try {
            if (version.compareTo(Main.getVersion()) != 0) {
                throw new MageException("Wrong client version " + version + ", expecting version " + Main.getVersion());
            }
            if (!adminPassword.equals(this.adminPassword)) {
                throw new MageException("Wrong password");
            }
            return SessionManager.getInstance().connectAdmin(sessionId);
        } catch (Exception ex) {
            handleException(ex);
        }
        return false;
    }

    @Override
    public TableView createTable(final String sessionId, final UUID roomId, final MatchOptions options) throws MageException {
        return executeWithResult("createTable", sessionId, new ActionWithTableViewResult() {
            @Override
            public TableView execute() throws MageException {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                Optional<User> _user = UserManager.getInstance().getUser(userId);
                if (!_user.isPresent()) {
                    logger.error("User for session not found. session = " + sessionId);
                    return null;
                }
                User user = _user.get();
                // check if user can create another table
                int notStartedTables = user.getNumberOfNotStartedTables();
                if (notStartedTables > 1) {
                    user.showUserMessage("Create table", "You have already " + notStartedTables + " not started table" + (notStartedTables == 1 ? "" : "s") + ". You can't create another.");
                    throw new MageException("No message");
                }
                // check if the user itself satisfies the quitRatio requirement.
                int quitRatio = options.getQuitRatio();
                if (quitRatio < user.getMatchQuitRatio()) {
                    user.showUserMessage("Create table", "Your quit ratio " + user.getMatchQuitRatio() + "% is higher than the table requirement " + quitRatio + "%");
                    throw new MageException("No message");
                }

                TableView table = GamesRoomManager.getInstance().getRoom(roomId).createTable(userId, options);
                if (logger.isDebugEnabled()) {
                    logger.debug("TABLE created - tableId: " + table.getTableId() + " " + table.getTableName());
                    logger.debug("- " + user.getName() + " userId: " + user.getId());
                    logger.debug("- chatId: " + TableManager.getInstance().getChatId(table.getTableId()));
                }
                return table;
            }
        });
    }

    @Override
    public TableView createTournamentTable(final String sessionId, final UUID roomId, final TournamentOptions options) throws MageException {
        return executeWithResult("createTournamentTable", sessionId, new ActionWithTableViewResult() {
            @Override
            public TableView execute() throws MageException {
                try {
                    UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                    Optional<User> _user = UserManager.getInstance().getUser(userId);
                    if (!_user.isPresent()) {
                        logger.error("User for session not found. session = " + sessionId);
                        return null;
                    }
                    User user = _user.get();
                    // check if user can create another table
                    int notStartedTables = user.getNumberOfNotStartedTables();
                    if (notStartedTables > 1) {
                        user.showUserMessage("Create table", "You have already " + notStartedTables + " not started table" + (notStartedTables == 1 ? "" : "s") + ". You can't create another.");
                        throw new MageException("No message");
                    }
                    // check AI players max
                    String maxAiOpponents = ConfigSettings.getInstance().getMaxAiOpponents();
                    if (maxAiOpponents != null) {
                        int max = Integer.parseInt(maxAiOpponents);
                        int aiPlayers = 0;
                        for (String playerType : options.getPlayerTypes()) {
                            if (!playerType.equals("Human")) {
                                aiPlayers++;
                            }
                        }
                        if (aiPlayers > max) {
                            user.showUserMessage("Create tournament", "It's only allowed to use a maximum of " + max + " AI players.");
                            throw new MageException("No message");
                        }
                    }
                    // check if the user satisfies the quitRatio requirement.
                    int quitRatio = options.getQuitRatio();
                    if (quitRatio < user.getTourneyQuitRatio()) {
                        String message = new StringBuilder("Your quit ratio ").append(user.getTourneyQuitRatio())
                                .append("% is higher than the table requirement ").append(quitRatio).append("%").toString();
                        user.showUserMessage("Create tournament", message);
                        throw new MageException("No message");
                    }
                    TableView table = GamesRoomManager.getInstance().getRoom(roomId).createTournamentTable(userId, options);
                    logger.debug("Tournament table " + table.getTableId() + " created");
                    return table;
                } catch (Exception ex) {
                    handleException(ex);
                }
                return null;
            }
        });
    }

    @Override
    public void removeTable(final String sessionId, final UUID roomId, final UUID tableId) throws MageException {
        execute("removeTable", sessionId, () -> {
            UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
            TableManager.getInstance().removeTable(userId, tableId);
        });
    }

    @Override
    public boolean joinTable(final String sessionId, final UUID roomId, final UUID tableId, final String name, final String playerType, final int skill, final DeckCardLists deckList, final String password) throws MageException, GameException {
        return executeWithResult("joinTable", sessionId, new ActionWithBooleanResult() {
            @Override
            public Boolean execute() throws MageException {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                logger.debug(name + " joins tableId: " + tableId);
                if (userId == null) {
                    logger.fatal("Got no userId from sessionId" + sessionId + " tableId" + tableId);
                    return false;
                }
                return GamesRoomManager.getInstance().getRoom(roomId).joinTable(userId, tableId, name, playerType, skill, deckList, password);

            }
        });
    }

    @Override
    public boolean joinTournamentTable(final String sessionId, final UUID roomId, final UUID tableId, final String name, final String playerType, final int skill, final DeckCardLists deckList, final String password) throws MageException, GameException {
        return executeWithResult("joinTournamentTable", sessionId, new ActionWithBooleanResult() {
            @Override
            public Boolean execute() throws MageException {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                if (logger.isTraceEnabled()) {
                    Optional<User> user = UserManager.getInstance().getUser(userId);
                    if (user.isPresent()) {
                        logger.trace("join tourn. tableId: " + tableId + " " + name);
                    }
                }
                if (userId == null) {
                    logger.fatal("Got no userId from sessionId" + sessionId + " tableId" + tableId);
                    return false;
                }
                return GamesRoomManager.getInstance().getRoom(roomId).joinTournamentTable(userId, tableId, name, playerType, skill, deckList, password);

            }
        });
    }

    @Override
    public boolean submitDeck(final String sessionId, final UUID tableId, final DeckCardLists deckList) throws MageException, GameException {
        return executeWithResult("submitDeck", sessionId, new ActionWithBooleanResult() {
            @Override
            public Boolean execute() throws MageException {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                boolean ret = TableManager.getInstance().submitDeck(userId, tableId, deckList);
                logger.debug("Session " + sessionId + " submitted deck");
                return ret;
            }
        });
    }

    @Override
    public void updateDeck(final String sessionId, final UUID tableId, final DeckCardLists deckList) throws MageException, GameException {
        execute("updateDeck", sessionId, () -> {
            UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
            TableManager.getInstance().updateDeck(userId, tableId, deckList);
            logger.trace("Session " + sessionId + " updated deck");
        });
    }

    @Override
    //FIXME: why no sessionId here???
    public List<TableView> getTables(UUID roomId) throws MageException {
        try {
            GamesRoom room = GamesRoomManager.getInstance().getRoom(roomId);
            if (room != null) {
                return room.getTables();
            } else {
                return null;
            }
        } catch (Exception ex) {
            handleException(ex);
        }
        return null;
    }

    @Override
    //FIXME: why no sessionId here???
    public List<MatchView> getFinishedMatches(UUID roomId) throws MageException {
        try {
            GamesRoom room = GamesRoomManager.getInstance().getRoom(roomId);
            if (room != null) {
                return room.getFinished();
            } else {
                return null;
            }
        } catch (Exception ex) {
            handleException(ex);
        }
        return null;
    }

    @Override
    public List<RoomUsersView> getRoomUsers(UUID roomId) throws MageException {
        try {
            GamesRoom room = GamesRoomManager.getInstance().getRoom(roomId);
            if (room != null) {
                return room.getRoomUsersInfo();
            } else {
                return null;
            }
        } catch (Exception ex) {
            handleException(ex);
        }
        return null;
    }

    @Override
    //FIXME: why no sessionId here???
    public TableView getTable(UUID roomId, UUID tableId) throws MageException {
        try {
            GamesRoom room = GamesRoomManager.getInstance().getRoom(roomId);
            if (room != null) {
                return room.getTable(tableId);
            } else {
                return null;
            }
        } catch (Exception ex) {
            handleException(ex);
        }
        return null;
    }

    @Override
    public boolean ping(String sessionId, String pingInfo) {
        return SessionManager.getInstance().extendUserSession(sessionId, pingInfo);
    }

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
    @Override
    public boolean startMatch(final String sessionId, final UUID roomId, final UUID tableId) throws MageException {
        if (!TableManager.getInstance().getController(tableId).changeTableStateToStarting()) {
            return false;
        }
        execute("startMatch", sessionId, () -> {
            UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
            TableManager.getInstance().startMatch(userId, roomId, tableId);
        });
        return true;
    }

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
    @Override
    public boolean startTournament(final String sessionId, final UUID roomId, final UUID tableId) throws MageException {
        if (!TableManager.getInstance().getController(tableId).changeTableStateToStarting()) {
            return false;
        }
        execute("startTournament", sessionId, () -> {
            UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
            TableManager.getInstance().startTournament(userId, roomId, tableId);
        });
        return true;
    }

    @Override
    //FIXME: why no sessionId here???
    public TournamentView getTournament(UUID tournamentId) throws MageException {
        try {
            return TournamentManager.getInstance().getTournamentView(tournamentId);
        } catch (Exception ex) {
            handleException(ex);
        }
        return null;
    }

    @Override
    //FIXME: why no sessionId here???
    public void sendChatMessage(final UUID chatId, final String userName, final String message) throws MageException {
        try {
            callExecutor.execute(
                    () -> ChatManager.getInstance().broadcast(chatId, userName, StringEscapeUtils.escapeHtml4(message), MessageColor.BLUE, true, ChatMessage.MessageType.TALK, null)
            );
        } catch (Exception ex) {
            handleException(ex);
        }
    }

    @Override
    public void joinChat(final UUID chatId, final String sessionId, final String userName) throws MageException {
        execute("joinChat", sessionId, () -> {
            UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
            ChatManager.getInstance().joinChat(chatId, userId);
        });
    }

    @Override
    public void leaveChat(final UUID chatId, final String sessionId) throws MageException {
        execute("leaveChat", sessionId, () -> {
            if (chatId != null) {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                ChatManager.getInstance().leaveChat(chatId, userId);
            } else {
                logger.warn("The chatId is null.  sessionId = " + sessionId);
            }
        });
    }

    @Override
    //FIXME: why no sessionId here???
    public UUID getMainRoomId() throws MageException {
        try {
            return GamesRoomManager.getInstance().getMainRoomId();
        } catch (Exception ex) {
            handleException(ex);
        }
        return null;
    }

    @Override
    //FIXME: why no sessionId here???
    public UUID getRoomChatId(UUID roomId) throws MageException {
        try {
            return GamesRoomManager.getInstance().getRoom(roomId).getChatId();
        } catch (Exception ex) {
            handleException(ex);
        }
        return null;
    }

    @Override
    public boolean isTableOwner(final String sessionId, UUID roomId, final UUID tableId) throws MageException {
        return executeWithResult("isTableOwner", sessionId, new ActionWithBooleanResult() {
            @Override
            public Boolean execute() {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                return TableManager.getInstance().isTableOwner(tableId, userId);
            }
        });
    }

    @Override
    public void swapSeats(final String sessionId, final UUID roomId, final UUID tableId, final int seatNum1, final int seatNum2) throws MageException {
        execute("swapSeats", sessionId, () -> {
            UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
            TableManager.getInstance().swapSeats(tableId, userId, seatNum1, seatNum2);
        });
    }

    @Override
    public boolean leaveTable(final String sessionId, final UUID roomId, final UUID tableId) throws MageException {
        TableState tableState = TableManager.getInstance().getController(tableId).getTableState();
        if (tableState != TableState.WAITING && tableState != TableState.READY_TO_START) {
            // table was already started, so player can't leave anymore now
            return false;
        }
        execute("leaveTable", sessionId, () -> {
            UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
            GamesRoomManager.getInstance().getRoom(roomId).leaveTable(userId, tableId);
        });
        return true;
    }

    @Override
    //FIXME: why no sessionId here???
    public UUID getTableChatId(UUID tableId) throws MageException {
        try {
            return TableManager.getInstance().getChatId(tableId);
        } catch (Exception ex) {
            handleException(ex);
        }
        return null;
    }

    @Override
    public void joinGame(final UUID gameId, final String sessionId) throws MageException {
        execute("joinGame", sessionId, () -> {
            UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
            GameManager.getInstance().joinGame(gameId, userId);
        });
    }

    @Override
    public void joinDraft(final UUID draftId, final String sessionId) throws MageException {
        execute("joinDraft", sessionId, () -> {
            UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
            DraftManager.getInstance().joinDraft(draftId, userId);
        });
    }

    @Override
    public void joinTournament(final UUID tournamentId, final String sessionId) throws MageException {
        execute("joinTournament", sessionId, () -> {
            UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
            TournamentManager.getInstance().joinTournament(tournamentId, userId);
        });
    }

    @Override
    //FIXME: why no sessionId here???
    public UUID getGameChatId(UUID gameId) throws MageException {
        try {
            return GameManager.getInstance().getChatId(gameId);
        } catch (Exception ex) {
            handleException(ex);
        }
        return null;
    }

    @Override
    //FIXME: why no sessionId here???
    public UUID getTournamentChatId(UUID tournamentId) throws MageException {
        try {
            return TournamentManager.getInstance().getChatId(tournamentId);
        } catch (Exception ex) {
            handleException(ex);
        }
        return null;
    }

    @Override
    public void sendPlayerUUID(final UUID gameId, final String sessionId, final UUID data) throws MageException {
        execute("sendPlayerUUID", sessionId, () -> {
            Optional<User> user = SessionManager.getInstance().getUser(sessionId);
            if (user.isPresent()) {
//                    logger.warn("sendPlayerUUID gameId=" + gameId + " sessionId=" + sessionId + " username=" + user.getName());
                user.get().sendPlayerUUID(gameId, data);
            } else {
                logger.warn("Your session expired: gameId=" + gameId + ", sessionId=" + sessionId);
            }
        });
    }

    @Override
    public void sendPlayerString(final UUID gameId, final String sessionId, final String data) throws MageException {
        execute("sendPlayerString", sessionId, () -> {
            Optional<User> user = SessionManager.getInstance().getUser(sessionId);
            if (user.isPresent()) {
                user.get().sendPlayerString(gameId, data);
            } else {
                logger.warn("Your session expired: gameId=" + gameId + ", sessionId=" + sessionId);
            }
        });
    }

    @Override
    public void sendPlayerManaType(final UUID gameId, final UUID playerId, final String sessionId, final ManaType data) throws MageException {
        execute("sendPlayerManaType", sessionId, () -> {
            Optional<User> user = SessionManager.getInstance().getUser(sessionId);
            if (user.isPresent()) {
                user.get().sendPlayerManaType(gameId, playerId, data);
            } else {
                logger.warn("Your session expired: gameId=" + gameId + ", sessionId=" + sessionId);
            }
        });
    }

    @Override
    public void sendPlayerBoolean(final UUID gameId, final String sessionId, final Boolean data) throws MageException {
        execute("sendPlayerBoolean", sessionId, () -> {
            Optional<User> user = SessionManager.getInstance().getUser(sessionId);
            if (user.isPresent()) {
                user.get().sendPlayerBoolean(gameId, data);
            } else {
                logger.warn("Your session expired: gameId=" + gameId + ", sessionId=" + sessionId);
            }
        });
    }

    @Override
    public void sendPlayerInteger(final UUID gameId, final String sessionId, final Integer data) throws MageException {
        execute("sendPlayerInteger", sessionId, () -> {
            Optional<User> user = SessionManager.getInstance().getUser(sessionId);
            if (user.isPresent()) {
                user.get().sendPlayerInteger(gameId, data);
            } else {
                logger.warn("Your session expired: gameId=" + gameId + ", sessionId=" + sessionId);
            }
        });
    }

    @Override
    public DraftPickView sendCardPick(final UUID draftId, final String sessionId, final UUID cardPick, final Set<UUID> hiddenCards) throws MageException {
        return executeWithResult("sendCardPick", sessionId, new ActionWithNullNegativeResult<DraftPickView>() {
            @Override
            public DraftPickView execute() {
                Session session = SessionManager.getInstance().getSession(sessionId);
                if (session != null) {
                    return DraftManager.getInstance().sendCardPick(draftId, session.getUserId(), cardPick, hiddenCards);
                } else {
                    logger.error("Session not found sessionId: " + sessionId + "  draftId:" + draftId);
                }
                return null;
            }
        });
    }

    @Override
    public void sendCardMark(final UUID draftId, final String sessionId, final UUID cardPick) throws MageException {
        execute("sendCardMark", sessionId, () -> {
            Session session = SessionManager.getInstance().getSession(sessionId);
            if (session != null) {
                DraftManager.getInstance().sendCardMark(draftId, session.getUserId(), cardPick);
            } else {
                logger.error("Session not found sessionId: " + sessionId + "  draftId:" + draftId);
            }
        });
    }

    @Override
    public void quitMatch(final UUID gameId, final String sessionId) throws MageException {
        execute("quitMatch", sessionId, () -> {
            Session session = SessionManager.getInstance().getSession(sessionId);
            if (session != null) {
                GameManager.getInstance().quitMatch(gameId, session.getUserId());
            } else {
                logger.error("Session not found sessionId: " + sessionId + "  gameId:" + gameId);
            }
        });
    }

    @Override
    public void quitTournament(final UUID tournamentId, final String sessionId) throws MageException {
        execute("quitTournament", sessionId, () -> {
            Session session = SessionManager.getInstance().getSession(sessionId);
            if (session != null) {
                TournamentManager.getInstance().quit(tournamentId, session.getUserId());
            } else {
                logger.error("Session not found sessionId: " + sessionId + "  tournamentId:" + tournamentId);
            }
        });
    }

    @Override
    public void quitDraft(final UUID draftId, final String sessionId) throws MageException {
        execute("quitDraft", sessionId, () -> {
            Session session = SessionManager.getInstance().getSession(sessionId);
            if (session == null) {
                logger.error("Session not found sessionId: " + sessionId + "  draftId:" + draftId);
                return;
            }
            UUID tableId = DraftManager.getInstance().getControllerByDraftId(draftId).getTableId();
            Table table = TableManager.getInstance().getTable(tableId);
            if (table.isTournament()) {
                UUID tournamentId = table.getTournament().getId();
                TournamentManager.getInstance().quit(tournamentId, session.getUserId());
            }
        });
    }

    @Override
    public void sendPlayerAction(final PlayerAction playerAction, final UUID gameId, final String sessionId, final Object data) throws MageException {
        execute("sendPlayerAction", sessionId, () -> {
            Session session = SessionManager.getInstance().getSession(sessionId);
            if (session == null) {
                logger.error("Session not found sessionId: " + sessionId + "  gameId:" + gameId);
                return;
            }
            GameManager.getInstance().sendPlayerAction(playerAction, gameId, session.getUserId(), data);
        });
    }

    @Override
    public boolean watchTable(final String sessionId, final UUID roomId, final UUID tableId) throws MageException {
        return executeWithResult("setUserData", sessionId, new ActionWithBooleanResult() {
            @Override
            public Boolean execute() throws MageException {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                return GamesRoomManager.getInstance().getRoom(roomId).watchTable(userId, tableId);
            }
        });
    }

    @Override
    public boolean watchTournamentTable(final String sessionId, final UUID tableId) throws MageException {
        return executeWithResult("setUserData", sessionId, new ActionWithBooleanResult() {
            @Override
            public Boolean execute() throws MageException {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                return TableManager.getInstance().watchTable(userId, tableId);
            }
        });
    }

    @Override
    public void watchGame(final UUID gameId, final String sessionId) throws MageException {
        execute("watchGame", sessionId, () -> {
            UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
            GameManager.getInstance().watchGame(gameId, userId);
        });
    }

    @Override
    public void stopWatching(final UUID gameId, final String sessionId) throws MageException {
        execute("stopWatching", sessionId, () -> {
            UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
            UserManager.getInstance().getUser(userId).ifPresent(user -> {
                GameManager.getInstance().stopWatching(gameId, userId);
                user.removeGameWatchInfo(gameId);
            });

        });
    }

    @Override
    public void replayGame(final UUID gameId, final String sessionId) throws MageException {
        execute("replayGame", sessionId, () -> {
            UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
            ReplayManager.getInstance().replayGame(gameId, userId);
        });
    }

    @Override
    public void startReplay(final UUID gameId, final String sessionId) throws MageException {
        execute("startReplay", sessionId, () -> {
            UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
            ReplayManager.getInstance().startReplay(gameId, userId);
        });
    }

    @Override
    public void stopReplay(final UUID gameId, final String sessionId) throws MageException {
        execute("stopReplay", sessionId, () -> {
            UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
            ReplayManager.getInstance().stopReplay(gameId, userId);
        });
    }

    @Override
    public void nextPlay(final UUID gameId, final String sessionId) throws MageException {
        execute("nextPlay", sessionId, () -> {
            UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
            ReplayManager.getInstance().nextPlay(gameId, userId);
        });
    }

    @Override
    public void previousPlay(final UUID gameId, final String sessionId) throws MageException {
        execute("previousPlay", sessionId, () -> {
            UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
            ReplayManager.getInstance().previousPlay(gameId, userId);
        });
    }

    @Override
    public void skipForward(final UUID gameId, final String sessionId, final int moves) throws MageException {
        execute("skipForward", sessionId, () -> {
            UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
            ReplayManager.getInstance().skipForward(gameId, userId, moves);
        });
    }

    @Override
    //TODO: check how often it is used
    public ServerState getServerState() throws MageException {
        try {
            return new ServerState(
                    GameFactory.getInstance().getGameTypes(),
                    TournamentFactory.getInstance().getTournamentTypes(),
                    PlayerFactory.getInstance().getPlayerTypes().toArray(new String[PlayerFactory.getInstance().getPlayerTypes().size()]),
                    DeckValidatorFactory.getInstance().getDeckTypes().toArray(new String[DeckValidatorFactory.getInstance().getDeckTypes().size()]),
                    CubeFactory.getInstance().getDraftCubes().toArray(new String[CubeFactory.getInstance().getDraftCubes().size()]),
                    testMode,
                    Main.getVersion(),
                    CardRepository.instance.getContentVersionConstant(),
                    ExpansionRepository.instance.getContentVersionConstant()
            );
        } catch (Exception ex) {
            handleException(ex);
        }
        return null;
    }

    @Override
    public void cheat(final UUID gameId, final String sessionId, final UUID playerId, final DeckCardLists deckList) throws MageException {
        execute("cheat", sessionId, () -> {
            if (testMode) {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                GameManager.getInstance().cheat(gameId, userId, playerId, deckList);
            }
        });
    }

    @Override
    public boolean cheat(final UUID gameId, final String sessionId, final UUID playerId, final String cardName) throws MageException {
        return executeWithResult("cheatOne", sessionId, new ActionWithBooleanResult() {
            @Override
            public Boolean execute() {
                if (testMode) {
                    UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                    return GameManager.getInstance().cheat(gameId, userId, playerId, cardName);
                }
                return false;
            }
        });
    }

    public void handleException(Exception ex) throws MageException {
        if (!ex.getMessage().equals("No message")) {
            logger.fatal("", ex);
            throw new MageException("Server error: " + ex.getMessage());
        }
    }

    @Override
    public GameView getGameView(final UUID gameId, final String sessionId, final UUID playerId) throws MageException {
        return executeWithResult("getGameView", sessionId, new ActionWithNullNegativeResult<GameView>() {
            @Override
            public GameView execute() throws MageException {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                return GameManager.getInstance().getGameView(gameId, userId, playerId);
            }
        });
    }

    /**
     * Get user data for admin console
     *
     * @param sessionId
     * @return
     * @throws MageException
     */
    @Override
    public List<UserView> getUsers(String sessionId) throws MageException {
        return executeWithResult("getUsers", sessionId, new ActionWithNullNegativeResult<List<UserView>>() {
            @Override
            public List<UserView> execute() throws MageException {
                List<UserView> users = new ArrayList<>();
                for (User user : UserManager.getInstance().getUsers()) {
                    users.add(new UserView(
                            user.getName(),
                            user.getHost(),
                            user.getSessionId(),
                            user.getConnectionTime(),
                            user.getGameInfo(),
                            user.getUserState().toString(),
                            user.getChatLockedUntil(),
                            user.getClientVersion()
                    ));
                }
                return users;
            }
        }, true);
    }

    @Override
    public void disconnectUser(final String sessionId, final String userSessionId) throws MageException {
        execute("disconnectUser", sessionId, () -> SessionManager.getInstance().disconnectUser(sessionId, userSessionId));
    }

    @Override
    public void muteUser(final String sessionId, final String userName, final long durationMinutes) throws MageException {
        execute("muteUser", sessionId, () -> {
            User user = UserManager.getInstance().getUserByName(userName);
            if (user != null) {
                Date muteUntil = new Date(Calendar.getInstance().getTimeInMillis() + (durationMinutes * Timer.ONE_MINUTE));
                user.showUserMessage("Admin info", "You were muted for chat messages until " + SystemUtil.dateFormat.format(muteUntil) + ".");
                user.setChatLockedUntil(muteUntil);
            }

        });
    }

    @Override
    public void lockUser(final String sessionId, final String userName, final long durationMinutes) throws MageException {
        execute("lockUser", sessionId, () -> {
            User user = UserManager.getInstance().getUserByName(userName);
            if (user != null) {
                Date lockUntil = new Date(Calendar.getInstance().getTimeInMillis() + (durationMinutes * Timer.ONE_MINUTE));
                user.showUserMessage("Admin info", "Your user profile was locked until " + SystemUtil.dateFormat.format(lockUntil) + ".");
                user.setLockedUntil(lockUntil);
                if (user.isConnected()) {
                    SessionManager.getInstance().disconnectUser(sessionId, user.getSessionId());
                }
            }

        });
    }

    @Override
    public void setActivation(final String sessionId, final String userName, boolean active) throws MageException {
        execute("setActivation", sessionId, () -> {
            AuthorizedUser authorizedUser = AuthorizedUserRepository.instance.getByName(userName);
            User user = UserManager.getInstance().getUserByName(userName);
            if (user != null) {
                user.setActive(active);
                if (!user.isActive() && user.isConnected()) {
                    SessionManager.getInstance().disconnectUser(sessionId, user.getSessionId());
                }
            } else if (authorizedUser != null) {
                User theUser = new User(userName, "localhost", authorizedUser);
                theUser.setActive(active);
            }

        });
    }

    @Override
    public void toggleActivation(final String sessionId, final String userName) throws MageException {
        execute("toggleActivation", sessionId, () -> {
            User user = UserManager.getInstance().getUserByName(userName);
            if (user != null) {
                user.setActive(!user.isActive());
                if (!user.isActive() && user.isConnected()) {
                    SessionManager.getInstance().disconnectUser(sessionId, user.getSessionId());
                }
            }

        });
    }

    @Override
    public void endUserSession(final String sessionId, final String userSessionId) throws MageException {
        execute("endUserSession", sessionId, () -> SessionManager.getInstance().endUserSession(sessionId, userSessionId));
    }

    /**
     * Admin console - Remove table
     *
     * @param sessionId
     * @param tableId
     * @throws MageException
     */
    @Override
    public void removeTable(final String sessionId, final UUID tableId) throws MageException {
        execute("removeTable", sessionId, () -> {
            UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
            TableManager.getInstance().removeTable(userId, tableId);
        });
    }

    @Override
    public Object getServerMessagesCompressed(String sessionId) throws MageException {
        return executeWithResult("getGameView", sessionId, new ActionWithNullNegativeResult<Object>() {
            @Override
            public Object execute() throws MageException {
                return CompressUtil.compress(ServerMessagesUtil.getInstance().getMessages());
            }
        });
    }

    @Override
    public void sendFeedbackMessage(final String sessionId, final String username, final String title, final String type, final String message, final String email) throws MageException {
        if (title != null && message != null) {
            execute("sendFeedbackMessage", sessionId, () -> {
                String host = SessionManager.getInstance().getSession(sessionId).getHost();
                FeedbackServiceImpl.instance.feedback(username, title, type, message, email, host);
            });
        }
    }

    @Override
    public void sendBroadcastMessage(final String sessionId, final String message) throws MageException {
        if (message != null) {
            execute("sendBroadcastMessage", sessionId, () -> {
                for (User user : UserManager.getInstance().getUsers()) {
                    if (message.toLowerCase(Locale.ENGLISH).startsWith("warn")) {
                        user.fireCallback(new ClientCallback("serverMessage", null, new ChatMessage("SERVER", message, null, MessageColor.RED)));
                    } else {
                        user.fireCallback(new ClientCallback("serverMessage", null, new ChatMessage("SERVER", message, null, MessageColor.BLUE)));
                    }
                }
            }, true);
        }
    }

    private void sendErrorMessageToClient(final String sessionId, final String message) throws MageException {
        execute("sendErrorMessageToClient", sessionId, () -> SessionManager.getInstance().sendErrorMessageToClient(sessionId, message));
    }

    protected void execute(final String actionName, final String sessionId, final Action action, boolean checkAdminRights) throws MageException {
        if (checkAdminRights) {
            if (!SessionManager.getInstance().isAdmin(sessionId)) {
                return;
            }
        }
        execute(actionName, sessionId, action);
    }

    protected void execute(final String actionName, final String sessionId, final Action action) throws MageException {
        if (SessionManager.getInstance().isValidSession(sessionId)) {
            try {
                callExecutor.execute(
                        () -> {
                            if (SessionManager.getInstance().isValidSession(sessionId)) {
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

    protected <T> T executeWithResult(String actionName, final String sessionId, final ActionWithResult<T> action, boolean checkAdminRights) throws MageException {
        if (checkAdminRights) {
            if (!SessionManager.getInstance().isAdmin(sessionId)) {
                return action.negativeResult();
            }
        }
        return executeWithResult(actionName, sessionId, action);
    }

    //TODO: also run in threads with future task
    protected <T> T executeWithResult(String actionName, final String sessionId, final ActionWithResult<T> action) throws MageException {
        if (SessionManager.getInstance().isValidSession(sessionId)) {
            try {
                return action.execute();
            } catch (Exception ex) {
                handleException(ex);
            }
        }
        return action.negativeResult();
    }

    @Override
    public List<ExpansionInfo> getMissingExpansionData(List<String> codes) {
        List<ExpansionInfo> result = new ArrayList<>();
        for (ExpansionInfo expansionInfo : ExpansionRepository.instance.getAll()) {
            if (!codes.contains(expansionInfo.getCode())) {
                result.add(expansionInfo);
            }
        }
        return result;
    }

    @Override
    public List<CardInfo> getMissingCardsData(List<String> classNames) {
        return CardRepository.instance.getMissingCards(classNames);
    }
}
