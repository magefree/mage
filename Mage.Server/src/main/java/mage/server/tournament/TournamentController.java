/*
 * Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.server.tournament;

import mage.MageException;
import mage.cards.decks.Deck;
import mage.constants.TableState;
import mage.constants.TournamentPlayerState;
import mage.game.GameException;
import mage.game.Table;
import mage.game.draft.Draft;
import mage.game.events.Listener;
import mage.game.events.PlayerQueryEvent;
import mage.game.events.TableEvent;
import mage.game.match.MatchOptions;
import mage.game.result.ResultProtos.TourneyQuitStatus;
import mage.game.tournament.MultiplayerRound;
import mage.game.tournament.Tournament;
import mage.game.tournament.TournamentPairing;
import mage.game.tournament.TournamentPlayer;
import mage.players.PlayerType;
import mage.server.ChatManager;
import mage.server.TableManager;
import mage.server.User;
import mage.server.UserManager;
import mage.server.draft.DraftController;
import mage.server.draft.DraftManager;
import mage.server.game.GamesRoomManager;
import mage.server.util.ThreadExecutor;
import mage.view.ChatMessage.MessageColor;
import mage.view.ChatMessage.MessageType;
import mage.view.ChatMessage.SoundToPlay;
import mage.view.TournamentView;
import org.apache.log4j.Logger;

import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TournamentController {

    private static final Logger logger = Logger.getLogger(TournamentController.class);

    private final UUID chatId;
    private final UUID tableId;
    private boolean started = false;
    private final Tournament tournament;
    private ConcurrentHashMap<UUID, UUID> userPlayerMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, TournamentSession> tournamentSessions = new ConcurrentHashMap<>();

    public TournamentController(Tournament tournament, ConcurrentHashMap<UUID, UUID> userPlayerMap, UUID tableId) {
        this.userPlayerMap = userPlayerMap;
        chatId = ChatManager.instance.createChatSession("Tournament " + tournament.getId());
        this.tournament = tournament;
        this.tableId = tableId;
        init();
    }

    private void init() {
        tournament.addTableEventListener(
                (Listener<TableEvent>) event -> {
                    switch (event.getEventType()) {
                        case CHECK_STATE_PLAYERS:
                            checkPlayersState();
                            break;
                        case INFO:
                            ChatManager.instance.broadcast(chatId, "", event.getMessage(), MessageColor.BLACK, true, MessageType.STATUS, null);
                            logger.debug(tournament.getId() + " " + event.getMessage());
                            break;
                        case START_DRAFT:
                            startDraft(event.getDraft());
                            break;
                        case CONSTRUCT:
                            if (!isAbort()) {
                                construct();
                            } else {
                                endTournament();
                            }
                            break;
                        case START_MATCH:
                            if (!isAbort()) {
                                initTournament(); // set state
                                startMatch(event.getPair(), event.getMatchOptions());
                            }
                            break;
                        case START_MULTIPLAYER_MATCH:
                            if (!isAbort()) {
                                initTournament(); // set state
                                MatchOptions matchOptions = event.getMatchOptions();
                                if (matchOptions != null && event.getMultiplayerRound() != null) {
                                    for (TournamentPlayer player : event.getMultiplayerRound().getAllPlayers()) {
                                        matchOptions.getPlayerTypes().add(player.getPlayerType());
                                    }
                                }

                                startMultiplayerMatch(event.getMultiplayerRound(), event.getMatchOptions());
                            }
                            break;
                        case END:
                            endTournament();
                            break;
                    }
                }
        );
        tournament.addPlayerQueryEventListener(
                (Listener<PlayerQueryEvent>) event -> {
                    try {
                        switch (event.getQueryType()) {
                            case CONSTRUCT:
                                construct(event.getPlayerId(), event.getMax());
                                break;
                        }
                    } catch (MageException ex) {
                        logger.fatal("Player event listener error", ex);
                    }
                }
        );
        for (TournamentPlayer player : tournament.getPlayers()) {
            if (!player.getPlayer().isHuman()) {
                player.setJoined();
                logger.debug("player " + player.getPlayer().getId() + " has joined tournament " + tournament.getId());
                ChatManager.instance.broadcast(chatId, "", player.getPlayer().getLogName() + " has joined the tournament", MessageColor.BLACK, true, MessageType.STATUS, null);
            }
        }
        checkStart();
    }

    public synchronized void join(UUID userId) {
        UUID playerId = userPlayerMap.get(userId);
        if (playerId == null) {
            if (logger.isDebugEnabled()) {
                UserManager.instance.getUser(userId).ifPresent(user ->
                        logger.debug(user.getName() + " shows tournament panel  tournamentId: " + tournament.getId()));

            }
            return;
        }
        if (tournamentSessions.containsKey(playerId)) {
            logger.debug("player reopened tournament panel   userId: " + userId + " tournamentId: " + tournament.getId());
            return;
        }
        // first join of player
        TournamentSession tournamentSession = new TournamentSession(tournament, userId, tableId, playerId);
        tournamentSessions.put(playerId, tournamentSession);
        Optional<User> _user = UserManager.instance.getUser(userId);
        if (_user.isPresent()) {
            User user = _user.get();
            user.addTournament(playerId, tournament.getId());
            TournamentPlayer player = tournament.getPlayer(playerId);
            player.setJoined();
            logger.debug("player " + player.getPlayer().getName() + " - client has joined tournament " + tournament.getId());
            ChatManager.instance.broadcast(chatId, "", player.getPlayer().getLogName() + " has joined the tournament", MessageColor.BLACK, true, MessageType.STATUS, null);
            checkStart();
        } else {
            logger.error("User not found  userId: " + userId + "   tournamentId: " + tournament.getId());
        }
    }

    public void rejoin(UUID playerId) {
        TournamentSession tournamentSession = tournamentSessions.get(playerId);
        if (tournamentSession == null) {
            logger.fatal("Tournament session not found - playerId:" + playerId + "  tournamentId " + tournament.getId());
            return;
        }
        if (!tournamentSession.init()) {
            logger.fatal("Unable to initialize client userId: " + tournamentSession.userId + "  tournamentId " + tournament.getId());
            return;
        }
        tournamentSession.update();
    }

    private void checkStart() {
        if (!started && allJoined()) {
            ThreadExecutor.instance.getCallExecutor().execute(this::startTournament);
        }
    }

    private boolean allJoined() {
        if (!tournament.allJoined()) {
            return false;
        }
        for (TournamentPlayer player : tournament.getPlayers()) {
            if (player.getPlayer().isHuman() && tournamentSessions.get(player.getPlayer().getId()) == null) {
                return false;
            }
        }
        return true;
    }

    private synchronized void startTournament() {
        for (final TournamentSession tournamentSession : tournamentSessions.values()) {
            if (!tournamentSession.init()) {
                logger.fatal("Unable to initialize client userId: " + tournamentSession.userId + "  tournamentId " + tournament.getId());
                //TODO: generate client error message
                return;
            }
        }
        started = true;
        logger.debug("Tournament starts (all players joined): " + tournament.getId() + " - " + tournament.getTournamentType().toString());
        tournament.nextStep();
    }

    private void endTournament() {
        for (TournamentPlayer player : tournament.getPlayers()) {
            player.setStateAtTournamentEnd();
        }
        for (final TournamentSession tournamentSession : tournamentSessions.values()) {
            tournamentSession.tournamentOver();
        }
        this.tournamentSessions.clear();
        TableManager.instance.endTournament(tableId, tournament);
        tournament.cleanUpOnTournamentEnd();

    }

    private void startMatch(TournamentPairing pair, MatchOptions matchOptions) {
        try {
            TableManager tableManager = TableManager.instance;
            Table table = tableManager.createTable(GamesRoomManager.instance.getMainRoomId(), matchOptions);
            table.setTournamentSubTable(true);
            table.setTournament(tournament);
            table.setState(TableState.WAITING);
            TournamentPlayer player1 = pair.getPlayer1();
            TournamentPlayer player2 = pair.getPlayer2();
            Optional<UUID> user1Id = getPlayerUserId(player1.getPlayer().getId());
            Optional<UUID> user2Id = getPlayerUserId(player2.getPlayer().getId());
            if (user1Id.isPresent() && user2Id.isPresent()) {
                tableManager.addPlayer(getPlayerUserId(player1.getPlayer().getId()).get(), table.getId(), player1);
                tableManager.addPlayer(getPlayerUserId(player2.getPlayer().getId()).get(), table.getId(), player2);
                table.setState(TableState.STARTING);
                tableManager.startTournamentSubMatch(null, table.getId());
                tableManager.getMatch(table.getId()).ifPresent(match -> {
                    match.setTableId(tableId);
                    pair.setMatch(match);
                    pair.setTableId(table.getId());
                    player1.setState(TournamentPlayerState.DUELING);
                    player2.setState(TournamentPlayerState.DUELING);
                });
            }
        } catch (GameException ex) {
            logger.fatal("TournamentController startMatch error", ex);
        }
    }

    private void startMultiplayerMatch(MultiplayerRound round, MatchOptions matchOptions) {
        try {
            TableManager tableManager = TableManager.instance;
            Table table = tableManager.createTable(GamesRoomManager.instance.getMainRoomId(), matchOptions);
            table.setTournamentSubTable(true);
            table.setTournament(tournament);
            table.setState(TableState.WAITING);
            if (round.getAllPlayers().stream().allMatch(tournamentPlayer -> getPlayerUserId(tournamentPlayer.getPlayer().getId()).isPresent())) {
                for (TournamentPlayer player : round.getAllPlayers()) {
                    tableManager.addPlayer(getPlayerUserId(player.getPlayer().getId()).get(), table.getId(), player);
                }
                table.setState(TableState.STARTING);
                tableManager.startTournamentSubMatch(null, table.getId());
                tableManager.getMatch(table.getId()).ifPresent(match -> {
                    match.setTableId(tableId);
                    round.setMatch(match);
                    round.setTableId(table.getId());
                    for (TournamentPlayer player : round.getAllPlayers()) {
                        player.setState(TournamentPlayerState.DUELING);
                    }
                });
            }
        } catch (GameException ex) {
            logger.fatal("TournamentController startMatch error", ex);
        }
    }

    private void startDraft(Draft draft) {
        TableManager.instance.startDraft(tableId, draft);
    }

    private void construct() {
        TableManager.instance.construct(tableId);
    }

    private void initTournament() {
        if (TableManager.instance.getTable(tableId).getState() != TableState.DUELING) {
            TableManager.instance.initTournament(tableId);
        }
    }

    private void construct(UUID playerId, int timeout) throws MageException {
        if (tournamentSessions.containsKey(playerId)) {
            TournamentSession tournamentSession = tournamentSessions.get(playerId);
            tournamentSession.construct(timeout);
            getPlayerUserId(playerId).ifPresent(userId -> {
                UserManager.instance.getUser(userId).ifPresent(user -> {
                    user.addConstructing(playerId, tournamentSession);
                    TournamentPlayer player = tournament.getPlayer(playerId);
                    player.setState(TournamentPlayerState.CONSTRUCTING);
                });
            });
        }
    }

    public void submitDeck(UUID playerId, Deck deck) {
        if (tournamentSessions.containsKey(playerId)) {
            TournamentPlayer player = tournament.getPlayer(playerId);
            if (player != null && !player.hasQuit()) {
                tournamentSessions.get(playerId).submitDeck(deck);
                ChatManager.instance.broadcast(chatId, "", player.getPlayer().getLogName() + " has submitted his or her tournament deck", MessageColor.BLACK, true, MessageType.STATUS, SoundToPlay.PlayerSubmittedDeck);
            }
        }
    }

    public void updateDeck(UUID playerId, Deck deck) {
        if (tournamentSessions.containsKey(playerId)) {
            tournamentSessions.get(playerId).updateDeck(deck);
        }
    }

    public void timeout(UUID userId) {
        if (userPlayerMap.containsKey(userId)) {
            TournamentPlayer tournamentPlayer = tournament.getPlayer(userPlayerMap.get(userId));
            if (tournamentPlayer.getDeck() != null) {
                tournament.autoSubmit(userPlayerMap.get(userId), tournamentPlayer.generateDeck());
            } else {
                StringBuilder sb = new StringBuilder();
                UserManager.instance.getUser(userId).ifPresent(user ->
                        sb.append(user.getName()));

                sb.append(" - no deck found for auto submit");
                logger.fatal(sb);
                tournamentPlayer.setEliminated();
                tournamentPlayer.setStateInfo("No deck for auto submit");
            }
        }
    }

    public UUID getChatId() {
        return chatId;
    }

    public void quit(UUID userId) {
        UUID playerId = userPlayerMap.get(userId);
        if (playerId == null) {
            logger.debug("Player not found userId:" + userId + " tournId: " + tournament.getId());
            return;
        }
        TournamentPlayer tournamentPlayer = tournament.getPlayer(playerId);
        if (tournamentPlayer == null) {
            logger.debug("TournamentPlayer not found userId: " + userId + " tournId: " + tournament.getId());
            return;
        }
        if (!started) {
            tournament.leave(playerId);
            return;
        }
        TournamentSession tournamentSession = tournamentSessions.get(playerId);
        if (tournamentSession == null) {
            logger.debug("TournamentSession not found userId: " + userId + " tournId: " + tournament.getId());
            return;
        }
        tournamentSession.setKilled();
        if (tournamentPlayer.isInTournament()) {
            String info;
            TourneyQuitStatus status;
            if (tournament.isDoneConstructing()) {
                info = new StringBuilder("during round ").append(tournament.getRounds().size()).toString();
                // quit active matches of that tournament
                TableManager.instance.userQuitTournamentSubTables(tournament.getId(), userId);
                status = TourneyQuitStatus.DURING_ROUND;
            } else if (tournamentPlayer.getState() == TournamentPlayerState.DRAFTING) {
                info = "during Draft phase";
                if (!checkToReplaceDraftPlayerByAi(userId, tournamentPlayer)) {
                    this.abortDraftTournament();
                } else {
                    DraftManager.instance.getController(tableId).ifPresent(draftController -> {
                        draftController.getDraftSession(playerId).ifPresent(draftSession ->
                                DraftManager.instance.kill(draftSession.getDraftId(), userId));

                    });
                }
                status = TourneyQuitStatus.DURING_DRAFTING;
            } else if (tournamentPlayer.getState() == TournamentPlayerState.CONSTRUCTING) {
                info = "during Construction phase";
                status = TourneyQuitStatus.DURING_CONSTRUCTION;
            } else {
                info = "";
                status = TourneyQuitStatus.NO_TOURNEY_QUIT;
            }
            tournamentPlayer.setQuit(info, status);
            tournament.quit(playerId);
            tournamentSession.quit();
            ChatManager.instance.broadcast(chatId, "", tournamentPlayer.getPlayer().getLogName() + " has quit the tournament", MessageColor.BLACK, true, MessageType.STATUS, SoundToPlay.PlayerQuitTournament);
        }
    }

    private boolean checkToReplaceDraftPlayerByAi(UUID userId, TournamentPlayer leavingPlayer) {

        int humans = 0;
        for (TournamentPlayer tPlayer : tournament.getPlayers()) {
            if (tPlayer.getPlayer().isHuman()) {
                humans++;
            }
        }
        // replace player that quits with draft bot
        if (humans > 1) {
            Optional<User> user = UserManager.instance.getUser(userId);
            TableManager.instance.getController(tableId).ifPresent(tableController -> {

                String replacePlayerName = "Draftbot";
                if (user.isPresent()) {
                    replacePlayerName = "Draftbot (" + user.get().getName() + ')';
                }
                tableController.replaceDraftPlayer(leavingPlayer.getPlayer(), replacePlayerName, PlayerType.COMPUTER_DRAFT_BOT, 5);
                if (user.isPresent()) {
                    user.get().removeDraft(leavingPlayer.getPlayer().getId());
                    user.get().removeTable(leavingPlayer.getPlayer().getId());
                    user.get().removeTournament(leavingPlayer.getPlayer().getId());
                }
                ChatManager.instance.broadcast(chatId, "", leavingPlayer.getPlayer().getLogName() + " was replaced by draftbot", MessageColor.BLACK, true, MessageType.STATUS, null);
            });
            return true;
        }
        return false;
    }

    private Optional<UUID> getPlayerUserId(UUID playerId) {
        return userPlayerMap.entrySet().stream().filter(entry -> entry.getValue().equals(playerId)).map(Entry::getKey).findFirst();
    }

    public TournamentView getTournamentView() {
        return new TournamentView(tournament);
    }

    private void abortDraftTournament() {
        tournament.setAbort(true);
        DraftManager.instance.getController(tableId).ifPresent(DraftController::abortDraft);
    }

    public boolean isAbort() {
        return tournament.isAbort();
    }

    private void checkPlayersState() {
        for (TournamentPlayer tournamentPlayer : tournament.getPlayers()) {
            if (!tournamentPlayer.isEliminated() && tournamentPlayer.getPlayer().isHuman()) {
                if (tournamentSessions.containsKey(tournamentPlayer.getPlayer().getId())) {
                    if (tournamentSessions.get(tournamentPlayer.getPlayer().getId()).isKilled()) {
                        tournamentPlayer.setEliminated();
                        tournamentPlayer.setStateInfo("disconnected");
                    }
                } else {
                    tournamentPlayer.setEliminated();
                    tournamentPlayer.setStateInfo("no tournament session");
                }
            }
        }

    }

    public void cleanUpOnRemoveTournament() {
        ChatManager.instance.destroyChatSession(chatId);
    }

    /**
     * Check tournaments that are not already finished, if they are in a still
     * valid state
     *
     * @param tableState state of the tournament table
     * @return true - if tournament is valid false - if tournament is not valid
     * and should be removed
     */
    public boolean isTournamentStillValid(TableState tableState) {
        int activePlayers = 0;
        for (Entry<UUID, UUID> entry : userPlayerMap.entrySet()) {
            TournamentPlayer tournamentPlayer = tournament.getPlayer(entry.getValue());
            if (tournamentPlayer != null) {
                if (!tournamentPlayer.hasQuit()) {
                    if (tournamentPlayer.getPlayer().isHuman()) {
                        Optional<User> user = UserManager.instance.getUser(entry.getKey());
                        if (!user.isPresent()) {
                            logger.debug("Tournament user is missing but player active -> start quit - tournamentId: " + tournament.getId() + " state: " + tableState.toString());
                            // active tournament player but the user is no longer online
                            quit(entry.getKey());
                        } else {
                            activePlayers++;
                        }
                    }
                }
            } else {
                // tournament player is missing
                logger.debug("Tournament player is missing - tournamentId: " + tournament.getId() + " state: " + tableState.toString());
            }
        }
        for (TournamentPlayer tournamentPlayer : tournament.getPlayers()) {
            if (!tournamentPlayer.getPlayer().isHuman()) {
                if (!tournamentPlayer.hasQuit()) {
                    activePlayers++;
                }
            }
        }
        if (activePlayers < 2 && tableState != TableState.WAITING) {
            logger.debug("Tournament has less than 2 active players - tournamentId: " + tournament.getId() + " state: " + tableState.toString());
            return false;
        }
        return true;
    }
}
