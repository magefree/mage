/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import mage.cards.decks.Deck;
import mage.choices.Choice;
import mage.constants.ManaType;
import mage.game.Table;
import mage.game.tournament.TournamentPlayer;
import mage.players.net.UserData;
import mage.remote.DisconnectReason;
import mage.server.draft.DraftSession;
import mage.server.game.GameManager;
import mage.server.game.GameSessionPlayer;
import mage.server.tournament.TournamentController;
import mage.server.tournament.TournamentManager;
import mage.server.tournament.TournamentSession;
import mage.server.util.SystemUtil;
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
import org.apache.log4j.Logger;
import org.mage.network.messages.MessageType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class User {

    private static final Logger logger = Logger.getLogger(User.class);

    public enum UserState {

        Created, Connected, Disconnected, Reconnected, Expired;
    }

    private final UUID userId;
    private final String userName;
    private final String host;
    private final Date connectionTime;
    private final Map<UUID, Table> tables;
    private final ArrayList<UUID> tablesToDelete;
    private final Map<UUID, GameSessionPlayer> gameSessions;
    private final Map<UUID, DraftSession> draftSessions;
    private final Map<UUID, UUID> userTournaments; // playerId, tournamentId
    private final Map<UUID, TournamentSession> constructing;
    private final Map<UUID, Deck> sideboarding;
    private final List<UUID> watchedGames;
    private String sessionId;
    private String info = "";
    private String pingInfo = "";
    private Date disconnectionTime;
    private UserState userState;
    private UserData userData;

    public User(String userName, String host) {
        this.userId = UUID.randomUUID();
        this.userName = userName;
        this.host = host;
        this.userState = UserState.Created;

        this.connectionTime = new Date();

        this.tables = new ConcurrentHashMap<>();
        this.gameSessions = new ConcurrentHashMap<>();
        this.draftSessions = new ConcurrentHashMap<>();
        this.userTournaments = new ConcurrentHashMap<>();
        this.constructing = new ConcurrentHashMap<>();
        this.sideboarding = new ConcurrentHashMap<>();
        this.watchedGames = new ArrayList<>();
        this.tablesToDelete = new ArrayList<>();
        this.sessionId = "";
    }

    public String getName() {
        return userName;
    }

    public UUID getId() {
        return userId;
    }

    public String getHost() {
        return host;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
        if (sessionId.isEmpty()) {
            userState = UserState.Disconnected;
            lostConnection();
            logger.trace("USER - lost connection: " + userName + " id: " + userId);

        } else if (userState == UserState.Created) {
            userState = UserState.Connected;
            logger.trace("USER - created: " + userName + " id: " + userId);
        } else {
            userState = UserState.Reconnected;
            reconnect();
            logger.trace("USER - reconnected: " + userName + " id: " + userId);
        }
    }

    public void lostConnection() {
        // Because watched games don't get restored after reconnection call stop watching
        for (Iterator<UUID> iterator = watchedGames.iterator(); iterator.hasNext();) {
            UUID gameId = iterator.next();
            GameManager.getInstance().stopWatching(gameId, userId);
            iterator.remove();
        }
    }

    public boolean isConnected() {
        return userState.equals(UserState.Connected) || userState.equals(UserState.Reconnected);
    }

    private String getDisconnectDuration() {
        long secondsDisconnected = getSecondsDisconnected();
        long secondsLeft;
        String sign = "";
        if (secondsDisconnected > (3 * 60)) {
            sign = "-";
            secondsLeft = secondsDisconnected - (3 * 60);
        } else {
            secondsLeft = (3 * 60) - secondsDisconnected;
        }

        int minutes = (int) secondsLeft / 60;
        int seconds = (int) secondsLeft % 60;
        return new StringBuilder(sign).append(Integer.toString(minutes)).append(":").append(seconds > 9 ? seconds : "0" + Integer.toString(seconds)).toString();
    }

    public long getSecondsDisconnected() {
        return SystemUtil.getDateDiff(disconnectionTime, new Date(), TimeUnit.SECONDS);
    }

    public Date getConnectionTime() {
        return connectionTime;
    }

    public void joinedTable(final UUID roomId, final UUID tableId, final UUID chatId, boolean owner, boolean tournament) {
        ServerMain.getInstance().joinedTable(sessionId, roomId, tableId, chatId, owner, tournament);
    }

    public void gameStarted(final UUID gameId, final UUID playerId) {
        ServerMain.getInstance().gameStarted(sessionId, gameId, playerId);
    }

    public void initGame(UUID gameId, GameView gameView) {
        ServerMain.getInstance().initGame(sessionId, gameId, gameView);
    }

    public void gameAsk(UUID gameId, GameView gameView, String question, Map<String, Serializable> options) {
        ServerMain.getInstance().gameAsk(sessionId, gameId, gameView, question, options);
    }

    public void gameTarget(UUID gameId, GameView gameView, String question, CardsView cardView, Set<UUID> targets, boolean required, Map<String, Serializable> options) {
        ServerMain.getInstance().gameTarget(sessionId, gameId, gameView, question, cardView, targets, required, options);
    }

    public void gameSelect(UUID gameId, GameView gameView, String message, Map<String, Serializable> options) {
        ServerMain.getInstance().gameSelect(sessionId, gameId, gameView, message, options);
    }

    public void gameChooseAbility(UUID gameId, AbilityPickerView abilities) {
        ServerMain.getInstance().gameChooseAbility(sessionId, gameId, abilities);
    }

    public void gameChoosePile(UUID gameId, String message, CardsView pile1, CardsView pile2) {
        ServerMain.getInstance().gameChoosePile(sessionId, gameId, message, pile1, pile2);
    }

    public void gameChooseChoice(UUID gameId, Choice choice) {
        ServerMain.getInstance().gameChooseChoice(sessionId, gameId, choice);
    }

    public void gamePlayMana(UUID gameId, GameView gameView, String message, Map<String, Serializable> options) {
        ServerMain.getInstance().gamePlayMana(sessionId, gameId, gameView, message, options);
    }

    public void gamePlayXMana(UUID gameId, GameView gameView, String message) {
        ServerMain.getInstance().gamePlayXMana(sessionId, gameId, gameView, message);
    }

    public void gameSelectAmount(UUID gameId, String message, int min, int max) {
        ServerMain.getInstance().gameSelectAmount(sessionId, gameId, message, min, max);
    }

    public void endGameInfo(UUID gameId, GameEndView view) {
        ServerMain.getInstance().endGameInfo(sessionId, gameId, view);
    }
    
    public void userRequestDialog (UUID gameId, UserRequestMessage userRequestMessage) {
        ServerMain.getInstance().userRequestDialog(sessionId, gameId, userRequestMessage);
    }

    public void gameUpdate(UUID gameId, GameView view) {
        ServerMain.getInstance().gameUpdate(sessionId, gameId, view);
    }

    public void gameInform(UUID gameId, GameClientMessage message) {
        ServerMain.getInstance().gameInform(sessionId, gameId, message);
    }

    public void gameInformPersonal(UUID gameId, GameClientMessage message) {
        ServerMain.getInstance().gameInformPersonal(sessionId, gameId, message);
    }

    public void gameOver(UUID gameId, String message) {
        ServerMain.getInstance().gameOver(sessionId, gameId, message);
    }

    public void gameError(UUID gameId, String message) {
        ServerMain.getInstance().gameError(sessionId, gameId, message);
    }

    public void sideboard(final Deck deck, final UUID tableId, final int time, boolean limited) {
        ServerMain.getInstance().sideboard(sessionId, tableId, new DeckView(deck), time, limited);
        sideboarding.put(tableId, deck);
    }

    public void construct(final Deck deck, final UUID tableId, final int time) {
        ServerMain.getInstance().construct(sessionId, tableId, new DeckView(deck), time);
    }

    public void tournamentStarted(final UUID tournamentId, final UUID playerId) {
        ServerMain.getInstance().tournamentStarted(sessionId, tournamentId, playerId);
    }

    public void showTournament(final UUID tournamentId) {
        ServerMain.getInstance().showTournament(sessionId, tournamentId);
    }

    public void draftStarted(final UUID draftId, final UUID playerId) {
        ServerMain.getInstance().startDraft(sessionId, draftId, playerId);
    }

    public void draftInit(UUID draftId, DraftPickView view) {
        ServerMain.getInstance().draftInit(sessionId, draftId, view);
    }

    public void draftUpdate(UUID draftId, DraftView draftView) {
        ServerMain.getInstance().draftUpdate(sessionId, draftId, draftView);
    }

    public void draftOver(UUID draftId) {
        ServerMain.getInstance().draftOver(sessionId, draftId);
    }

    public void draftPick(UUID draftId, DraftPickView view) {
        ServerMain.getInstance().draftPick(sessionId, draftId, view);
    }

    public void chatMessage(UUID chatId, ChatMessage chatMessage) {
        ServerMain.getInstance().sendChatMessage(sessionId, chatId, chatMessage);
    }

    public void showUserMessage(final String title,  String message) {
        ServerMain.getInstance().informClient(sessionId, title, message, MessageType.INFORMATION);
    }

    public void showUserError(final String title,  String message) {
        ServerMain.getInstance().informClient(sessionId, title, message, MessageType.ERROR);
    }

    public void watchGame(final UUID gameId, UUID chatId) {
        GameView game = GameManager.getInstance().watchGame(gameId, userId);
        if (game != null)
            ServerMain.getInstance().watchGame(sessionId, gameId, chatId, game);
    }

    public void replayGame(final UUID gameId) {
        ServerMain.getInstance().replayGame(sessionId, gameId);
    }

    public void replayInit(UUID gameId, GameView gameView) {
        ServerMain.getInstance().replayInit(sessionId, gameId, gameView);
    }

    public void replayDone(UUID gameId, String result) {
        ServerMain.getInstance().replayDone(sessionId, gameId, result);
    }

    public void replayUpdate(UUID gameId, GameView gameView) {
        ServerMain.getInstance().replayUpdate(sessionId, gameId, gameView);
    }

    public void sendPlayerUUID(final UUID gameId, final UUID data) {
        GameManager.getInstance().sendPlayerUUID(gameId, userId, data);
    }

    public void sendPlayerString(final UUID gameId, final String data) {
        GameManager.getInstance().sendPlayerString(gameId, userId, data);
    }

    public void sendPlayerManaType(final UUID gameId, final UUID playerId, final ManaType data) {
        GameManager.getInstance().sendPlayerManaType(gameId, playerId, userId, data);
    }

    public void sendPlayerBoolean(final UUID gameId, final Boolean data) {
        GameManager.getInstance().sendPlayerBoolean(gameId, userId, data);
    }

    public void sendPlayerInteger(final UUID gameId, final Integer data) {
        GameManager.getInstance().sendPlayerInteger(gameId, userId, data);
    }

    private void reconnect() {
        for (Entry<UUID, Table> entry : tables.entrySet()) {
            Table t = entry.getValue();
            joinedTable(t.getRoomId(), t.getId(), TableManager.getInstance().getChatId(t.getId()), TableManager.getInstance().isTableOwner(t.getId(), userId), t.isTournament());
        }
        for (Entry<UUID, UUID> entry : userTournaments.entrySet()) {
            TournamentController tournamentController = TournamentManager.getInstance().getTournamentController(entry.getValue());
            if (tournamentController != null) {
                tournamentStarted(entry.getValue(), entry.getKey());
                tournamentController.rejoin(entry.getKey());
            }
        }

        for (Entry<UUID, GameSessionPlayer> entry : gameSessions.entrySet()) {
            gameStarted(entry.getValue().getGameId(), entry.getKey());
            entry.getValue().init();
            GameManager.getInstance().sendPlayerString(entry.getValue().getGameId(), userId, "");
        }

        for (Entry<UUID, DraftSession> entry : draftSessions.entrySet()) {
            draftStarted(entry.getValue().getDraftId(), entry.getKey());
            entry.getValue().init();
            entry.getValue().update();
        }

        for (Entry<UUID, TournamentSession> entry : constructing.entrySet()) {
            entry.getValue().construct(0); // TODO: Check if this is correct
        }
        for (Entry<UUID, Deck> entry : sideboarding.entrySet()) {
            TableController controller = TableManager.getInstance().getController(entry.getKey());
            sideboard(entry.getValue(), entry.getKey(), controller.getRemainingTime(), controller.getOptions().isLimited());
        }
    }

    public void addGame(UUID playerId, GameSessionPlayer gameSession) {
        gameSessions.put(playerId, gameSession);
    }

    public void removeGame(UUID playerId) {
        gameSessions.remove(playerId);
    }

    public void addDraft(UUID playerId, DraftSession draftSession) {
        draftSessions.put(playerId, draftSession);
    }

    public void removeDraft(UUID playerId) {
        draftSessions.remove(playerId);
    }

    public void addTournament(UUID playerId, UUID tournamentId) {
        userTournaments.put(playerId, tournamentId);
    }

    public void removeTournament(UUID playerId) {
        userTournaments.remove(playerId);
    }

    public void addTable(UUID playerId, Table table) {
        tables.put(playerId, table);
    }

    public void removeTable(UUID playerId) {
        tables.remove(playerId);
    }

    public void addConstructing(UUID playerId, TournamentSession tournamentSession) {
        constructing.put(playerId, tournamentSession);
    }

    public void removeConstructing(UUID playerId) {
        constructing.remove(playerId);
    }

    public void removeSideboarding(UUID tableId) {
        sideboarding.remove(tableId);
    }

    public void remove(DisconnectReason reason) {
        logger.trace("REMOVE " + getName() + " Draft sessions " + draftSessions.size());
        for (DraftSession draftSession : draftSessions.values()) {
            draftSession.setKilled();
        }
        draftSessions.clear();
        logger.trace("REMOVE " + getName() + " Tournament sessions " + userTournaments.size());
        for (UUID tournamentId : userTournaments.values()) {
            TournamentManager.getInstance().quit(tournamentId, getId());
        }
        userTournaments.clear();
        logger.trace("REMOVE " + getName() + " Tables " + tables.size());
        for (Entry<UUID, Table> entry : tables.entrySet()) {
            logger.debug("-- leave tableId: " + entry.getValue().getId());
            TableManager.getInstance().leaveTable(userId, entry.getValue().getId());
        }
        tables.clear();
        logger.trace("REMOVE " + getName() + " Game sessions: " + gameSessions.size());
        for (GameSessionPlayer gameSessionPlayer : gameSessions.values()) {
            logger.debug("-- kill game session of gameId: " + gameSessionPlayer.getGameId());
            GameManager.getInstance().quitMatch(gameSessionPlayer.getGameId(), userId);
            gameSessionPlayer.quitGame();
        }
        gameSessions.clear();
        logger.trace("REMOVE " + getName() + " watched Games " + watchedGames.size());
        for (UUID gameId : watchedGames) {
            GameManager.getInstance().stopWatching(gameId, userId);
        }
        watchedGames.clear();
        logger.trace("REMOVE " + getName() + " Chats ");
        ChatManager.getInstance().removeUser(userId, reason);
    }

    public void setUserData(UserData userData) {
        if (this.userData != null) {
            this.userData.update(userData);
        } else {
            this.userData = userData;
        }
    }

    public UserData getUserData() {
        if (userData == null) {// default these to avaiod NPE -> will be updated from client short after
            return UserData.getDefaultUserDataView();
        }
        return this.userData;
    }

    public String getGameInfo() {
        StringBuilder sb = new StringBuilder();

        int draft = 0, match = 0, sideboard = 0, tournament = 0, construct = 0, waiting = 0;

        for (Map.Entry<UUID, Table> tableEntry : tables.entrySet()) {
            if (tableEntry != null) {
                Table table = tableEntry.getValue();
                if (table != null) {
                    if (table.isTournament()) {
                        if (tableEntry.getKey() != null) {
                            TournamentPlayer tournamentPlayer = table.getTournament().getPlayer(tableEntry.getKey());
                            if (tournamentPlayer != null) {
                                if (!tournamentPlayer.isEliminated()) {
                                    switch (table.getState()) {
                                        case WAITING:
                                        case STARTING:
                                        case READY_TO_START:
                                            waiting++;
                                            break;
                                        case CONSTRUCTING:
                                            construct++;
                                            break;
                                        case DRAFTING:
                                            draft++;
                                            break;
                                        case DUELING:
                                            tournament++;
                                            break;
                                    }

                                    if (!isConnected()) {
                                        tournamentPlayer.setDisconnectInfo(" (discon. " + getDisconnectDuration() + ")");
                                    } else {
                                        tournamentPlayer.setDisconnectInfo("");
                                    }
                                }
                            } else {
                                // can happen if tournamet has just ended
                                logger.debug(getName() + " tournament player missing - tableId:" + table.getId(), null);
                                tablesToDelete.add(tableEntry.getKey());
                            }
                        } else {
                            logger.error(getName() + " tournament key missing - tableId: " + table.getId(), null);
                        }
                    } else {
                        switch (table.getState()) {
                            case WAITING:
                            case STARTING:
                            case READY_TO_START:
                                waiting++;
                                break;
                            case SIDEBOARDING:
                                sideboard++;
                                break;
                            case DUELING:
                                match++;
                                break;
                        }
                    }
                }
            }
        }
        if (!tablesToDelete.isEmpty()) {
            for (UUID keyId : tablesToDelete) {
                removeTable(keyId);
            }
            tablesToDelete.clear();
        }
        if (waiting > 0) {
            sb.append("Wait: ").append(waiting).append(" ");
        }
        if (match > 0) {
            sb.append("Match: ").append(match).append(" ");
        }
        if (sideboard > 0) {
            sb.append("Sideb: ").append(sideboard).append(" ");
        }
        if (draft > 0) {
            sb.append("Draft: ").append(draft).append(" ");
        }
        if (construct > 0) {
            sb.append("Const: ").append(construct).append(" ");
        }
        if (tournament > 0) {
            sb.append("Tourn: ").append(tournament).append(" ");
        }
        if (watchedGames.size() > 0) {
            sb.append("Watch: ").append(watchedGames.size()).append(" ");
        }
        return sb.toString();
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String Info) {
        this.info = Info;
    }

    public void addGameWatchInfo(UUID gameId) {
        watchedGames.add(gameId);
    }

    public void removeGameWatchInfo(UUID gameId) {
        watchedGames.remove(gameId);
    }

    public UserState getUserState() {
        return userState;
    }

}
