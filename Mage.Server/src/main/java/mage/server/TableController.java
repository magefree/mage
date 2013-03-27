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

import mage.Constants.RangeOfInfluence;
import mage.Constants.TableState;
import mage.MageException;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckCardLists;
import mage.cards.decks.InvalidDeckException;
import mage.game.GameException;
import mage.game.GameOptions;
import mage.game.Seat;
import mage.game.Table;
import mage.game.draft.Draft;
import mage.game.draft.DraftPlayer;
import mage.game.events.Listener;
import mage.game.events.TableEvent;
import mage.game.match.Match;
import mage.game.match.MatchOptions;
import mage.game.match.MatchPlayer;
import mage.game.tournament.Tournament;
import mage.game.tournament.TournamentOptions;
import mage.players.Player;
import mage.server.challenge.ChallengeManager;
import mage.server.draft.DraftManager;
import mage.server.game.*;
import mage.server.services.LogKeys;
import mage.server.services.impl.LogServiceImpl;
import mage.server.tournament.TournamentFactory;
import mage.server.tournament.TournamentManager;
import mage.server.util.ServerMessagesUtil;
import mage.server.util.ThreadExecutor;
import org.apache.log4j.Logger;

import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TableController {

    private static final Logger logger = Logger.getLogger(TableController.class);

    private UUID userId;
    private UUID chatId;
    private String controllerName;
    private Table table;
    private Match match;
    private MatchOptions options;
    private Tournament tournament;
    private ConcurrentHashMap<UUID, UUID> userPlayerMap = new ConcurrentHashMap<UUID, UUID>();

    private ScheduledFuture<?> futureTimeout;
    protected static ScheduledExecutorService timeoutExecutor = ThreadExecutor.getInstance().getTimeoutExecutor();

    public TableController(UUID roomId, UUID userId, MatchOptions options) {
        this.userId = userId;
        chatId = ChatManager.getInstance().createChatSession();
        this.options = options;
        match = GameFactory.getInstance().createMatch(options.getGameType(), options);
        if (userId != null) {
            User user = UserManager.getInstance().getUser(userId);
            controllerName = user.getName();
        } 
        else
            controllerName = "System";
        table = new Table(roomId, options.getGameType(), options.getName(), controllerName, DeckValidatorFactory.getInstance().createDeckValidator(options.getDeckType()), options.getPlayerTypes(), match);
        init();
    }

    public TableController(UUID roomId, UUID userId, TournamentOptions options) {
        this.userId = userId;
        chatId = ChatManager.getInstance().createChatSession();
        tournament = TournamentFactory.getInstance().createTournament(options.getTournamentType(), options);
        if (userId != null) {
            User user = UserManager.getInstance().getUser(userId);
            controllerName = user.getName();
        } 
        else
            controllerName = "System";
        table = new Table(roomId, options.getTournamentType(), options.getName(), controllerName, DeckValidatorFactory.getInstance().createDeckValidator(options.getMatchOptions().getDeckType()), options.getPlayerTypes(), tournament);
    }

    private void init() {
        match.addTableEventListener(
            new Listener<TableEvent> () {
                @Override
                public void event(TableEvent event) {
                    try {
                        switch (event.getEventType()) {
                            case SIDEBOARD:
                                sideboard(event.getPlayerId(), event.getDeck());
                                break;
                        }
                    } catch (MageException ex) {
                        logger.fatal("Table event listener error", ex);
                    }
                }
            }
        );
    }

    public synchronized boolean joinTournament(UUID userId, String name, String playerType, int skill) throws GameException {
        if (table.getState() != TableState.WAITING) {
            return false;
        }
        Seat seat = table.getNextAvailableSeat(playerType);
        if (seat == null) {
            throw new GameException("No available seats.");
        }
        Player player = createPlayer(name, seat.getPlayerType(), skill);
        tournament.addPlayer(player, seat.getPlayerType());
        table.joinTable(player, seat);
        User user = UserManager.getInstance().getUser(userId);
        user.addTable(player.getId(), table);
        logger.info("player joined " + player.getId());
        //only inform human players and add them to sessionPlayerMap
        if (seat.getPlayer().isHuman()) {
            user.joinedTable(table.getRoomId(), table.getId(), true);
            userPlayerMap.put(userId, player.getId());
        }

        return true;
    }

    public synchronized boolean joinTable(UUID userId, String name, String playerType, int skill, DeckCardLists deckList) throws MageException {
        if (table.getState() != TableState.WAITING) {
            return false;
        }
        Seat seat = table.getNextAvailableSeat(playerType);
        if (seat == null) {
            throw new GameException("No available seats.");
        }
        Deck deck = Deck.load(deckList);
        if (!Main.isTestMode() && !table.getValidator().validate(deck)) {
            throw new InvalidDeckException(name + " has an invalid deck for this format", table.getValidator().getInvalid());
        }

        Player player = createPlayer(name, seat.getPlayerType(), skill);
        match.addPlayer(player, deck);
        table.joinTable(player, seat);
        User user = UserManager.getInstance().getUser(userId);
        user.addTable(player.getId(), table);
        logger.info("player joined " + player.getId());
        //only inform human players and add them to sessionPlayerMap
        if (seat.getPlayer().isHuman()) {
            user.joinedTable(table.getRoomId(), table.getId(), false);
            userPlayerMap.put(userId, player.getId());
        }

        return true;
    }

    public void addPlayer(UUID userId, Player player, String playerType, Deck deck) throws GameException  {
        if (table.getState() != TableState.WAITING) {
            return;
        }
        Seat seat = table.getNextAvailableSeat(playerType);
        if (seat == null) {
            throw new GameException("No available seats.");
        }
        match.addPlayer(player, deck);
        table.joinTable(player, seat);
        if (player.isHuman()) {
            userPlayerMap.put(userId, player.getId());
        }
    }

    public synchronized boolean submitDeck(UUID userId, DeckCardLists deckList) throws MageException {
        UUID playerId = userPlayerMap.get(userId);
        if (table.getState() != TableState.SIDEBOARDING && table.getState() != TableState.CONSTRUCTING) {
            return false;
        }
        Deck deck = Deck.load(deckList);
        if (!Main.isTestMode() && !table.getValidator().validate(deck)) {
            throw new InvalidDeckException("Invalid deck for this format", table.getValidator().getInvalid());
        }
        submitDeck(userId, playerId, deck);
        return true;
    }

    public void updateDeck(UUID userId, DeckCardLists deckList) throws MageException {
        UUID playerId = userPlayerMap.get(userId);
        if (table.getState() != TableState.SIDEBOARDING && table.getState() != TableState.CONSTRUCTING) {
            return;
        }
        Deck deck = Deck.load(deckList);
        updateDeck(userId, playerId, deck);
    }

    private void submitDeck(UUID userId, UUID playerId, Deck deck) {
        if (table.getState() == TableState.SIDEBOARDING) {
            match.submitDeck(playerId, deck);
            UserManager.getInstance().getUser(userId).removeSideboarding(table.getId());
        }
        else {
            TournamentManager.getInstance().submitDeck(tournament.getId(), playerId, deck);
            UserManager.getInstance().getUser(userId).removeConstructing(table.getId());
        }
    }

    private void updateDeck(UUID userId, UUID playerId, Deck deck) {
        if (table.getState() == TableState.SIDEBOARDING) {
            match.updateDeck(playerId, deck);
        }
        else {
            TournamentManager.getInstance().updateDeck(tournament.getId(), playerId, deck);
        }
    }

    public boolean watchTable(UUID userId) {
        if (table.getState() != TableState.DUELING) {
            return false;
        }
        UserManager.getInstance().getUser(userId).watchGame(match.getGame().getId());
        return true;
    }

    public boolean replayTable(UUID userId) {
        if (table.getState() != TableState.FINISHED) {
            return false;
        }
        ReplayManager.getInstance().replayGame(table.getId(), userId);
        return true;
    }

    private Player createPlayer(String name, String playerType, int skill) {
        Player player;
        if (options == null) {
            player = PlayerFactory.getInstance().createPlayer(playerType, name, RangeOfInfluence.ALL, skill);
        }
        else {
            player = PlayerFactory.getInstance().createPlayer(playerType, name, options.getRange(), skill);
        }
        if (player != null)
            logger.info("Player created " + player.getId());
        return player;
    }

    public void kill(UUID userId) {
        leaveTable(userId);
        userPlayerMap.remove(userId);
    }

    public synchronized void leaveTable(UUID userId) {
        if (table.getState() == TableState.WAITING || table.getState() == TableState.STARTING)
            table.leaveTable(userPlayerMap.get(userId));
    }

    public synchronized void startMatch(UUID userId) {
        if (userId.equals(this.userId)) {
            startMatch();
        }
    }

    public synchronized void startChallenge(UUID userId, UUID challengeId) {
        if (userId.equals(this.userId)) {
            try {
                match.startMatch();
                match.startGame();
                table.initGame();
                GameOptions options = new GameOptions();
                options.testMode = true;
//                match.getGame().setGameOptions(options);
                GameManager.getInstance().createGameSession(match.getGame(), userPlayerMap, table.getId(), null);
                ChallengeManager.getInstance().prepareChallenge(getPlayerId(), match);
                for (Entry<UUID, UUID> entry: userPlayerMap.entrySet()) {
                    UserManager.getInstance().getUser(entry.getKey()).gameStarted(match.getGame().getId(), entry.getValue());
                }
            } catch (GameException ex) {
                logger.fatal(null, ex);
            }
        }
    }

    private UUID getPlayerId() throws GameException {
        UUID playerId = null;
        for (Entry<UUID, UUID> entry : userPlayerMap.entrySet()) {
            playerId = entry.getValue();
            break;
        }
        if (playerId == null)
            throw new GameException("Couldn't find a player in challenge mode.");
        return playerId;
    }

    public synchronized void startMatch() {
        if (table.getState() == TableState.STARTING) {
            try {
                match.startMatch();
                startGame(null);
            } catch (GameException ex) {
                logger.fatal("Error starting match ", ex);
                match.endGame();
            }
        }
    }

    private void startGame(UUID choosingPlayerId) throws GameException {
        try {
            match.startGame();
            table.initGame();
            GameManager.getInstance().createGameSession(match.getGame(), userPlayerMap, table.getId(), choosingPlayerId);
            String creator = null;
            String opponent = null;
            for (Entry<UUID, UUID> entry: userPlayerMap.entrySet()) {
                User user = UserManager.getInstance().getUser(entry.getKey());
                if (user != null) {
                    user.gameStarted(match.getGame().getId(), entry.getValue());
                    if (creator == null) {
                        creator = user.getName();
                    } else {
                        if (opponent == null) {
                            opponent = user.getName();
                        }
                    }
                }
                else {
                    TableManager.getInstance().removeTable(table.getId());
                    GameManager.getInstance().removeGame(match.getGame().getId());
                    logger.warn("Unable to find player " + entry.getKey());
                    break;
                }
            }
            ServerMessagesUtil.getInstance().incGamesStarted();

            // log about game started
            LogServiceImpl.instance.log(LogKeys.KEY_GAME_STARTED, String.valueOf(userPlayerMap.size()), creator, opponent);
        }
        catch (Exception ex) {
            logger.fatal("Error starting game", ex);
            TableManager.getInstance().removeTable(table.getId());
            GameManager.getInstance().removeGame(match.getGame().getId());
        }
    }

    public synchronized void startTournament(UUID userId) {
        try {
            if (userId.equals(this.userId) && table.getState() == TableState.STARTING) {
                TournamentManager.getInstance().createTournamentSession(tournament, userPlayerMap, table.getId());
                for (Entry<UUID, UUID> entry: userPlayerMap.entrySet()) {
                    User user = UserManager.getInstance().getUser(entry.getKey());
                    user.tournamentStarted(tournament.getId(), entry.getValue());
                }
            }
        }
        catch (Exception ex) {
            logger.fatal("Error starting tournament", ex);
            TableManager.getInstance().removeTable(table.getId());
            TournamentManager.getInstance().kill(tournament.getId(), userId);
        }
    }

    public void startDraft(Draft draft) {
        table.initDraft();
        DraftManager.getInstance().createDraftSession(draft, userPlayerMap, table.getId());
        for (Entry<UUID, UUID> entry: userPlayerMap.entrySet()) {
            UserManager.getInstance().getUser(entry.getKey()).draftStarted(draft.getId(), entry.getValue());
        }
    }

    private void sideboard(UUID playerId, Deck deck) throws MageException {
        for (Entry<UUID, UUID> entry: userPlayerMap.entrySet()) {
            if (entry.getValue().equals(playerId)) {
                User user = UserManager.getInstance().getUser(entry.getKey());
                int remaining = (int) futureTimeout.getDelay(TimeUnit.SECONDS);
                if (user != null)
                    user.sideboard(deck, table.getId(), remaining, options.isLimited());
                break;
            }
        }
    }

    public int getRemainingTime() {
        return (int) futureTimeout.getDelay(TimeUnit.SECONDS);
    }    

    public void construct() {
        table.construct();
    }

    public MatchOptions getOptions() {
        return options;
    }

    public void endGame() {
        UUID choosingPlayerId = match.getChooser();
        match.endGame();
        table.endGame();
// Saving of games caused memory leaks - so save is deactivated
//        if (!match.getGame().isSimulation()) {
//            GameManager.getInstance().saveGame(match.getGame().getId());
//        }
        GameManager.getInstance().removeGame(match.getGame().getId());
        try {
            if (!match.isMatchOver()) {
                table.sideboard();
                setupTimeout(Match.SIDEBOARD_TIME);
                match.sideboard();
                cancelTimeout();
                startGame(choosingPlayerId);
            }
            else {
                match.getGames().clear();
            }
        } catch (GameException ex) {
            logger.fatal(null, ex);
        }
    }

    private synchronized void setupTimeout(int seconds) {
        cancelTimeout();
        if (seconds > 0) {
            futureTimeout = timeoutExecutor.schedule(
                new Runnable() {
                    @Override
                    public void run() {
                        autoSideboard();
                    }
                },
                seconds, TimeUnit.SECONDS
            );
        }
    }

    private synchronized void cancelTimeout() {
        if (futureTimeout != null) {
            futureTimeout.cancel(false);
        }
    }

    private void autoSideboard() {
        for (MatchPlayer player: match.getPlayers()) {
            if (!player.isDoneSideboarding())
                match.submitDeck(player.getPlayer().getId(), player.generateDeck());
        }
    }

    public void endDraft(Draft draft) {
        for (DraftPlayer player: draft.getPlayers()) {
            tournament.getPlayer(player.getPlayer().getId()).setDeck(player.getDeck());
        }
        tournament.nextStep();
    }

    public void endTournament(Tournament tournament) {
        //TODO: implement this
    }

    public void swapSeats(int seatNum1, int seatNum2) {
        if (table.getState() == TableState.STARTING) {
            if (seatNum1 >= 0 && seatNum2 >= 0 && seatNum1 < table.getSeats().length && seatNum2 < table.getSeats().length) {
                Player swapPlayer = table.getSeats()[seatNum1].getPlayer();
                String swapType = table.getSeats()[seatNum1].getPlayerType();
                table.getSeats()[seatNum1].setPlayer(table.getSeats()[seatNum2].getPlayer());
                table.getSeats()[seatNum1].setPlayerType(table.getSeats()[seatNum2].getPlayerType());
                table.getSeats()[seatNum2].setPlayer(swapPlayer);
                table.getSeats()[seatNum2].setPlayerType(swapType);
            }
        }
    }

    public boolean isOwner(UUID userId) {
        if (userId == null)
            return false;
        return userId.equals(this.userId);
    }

    public Table getTable() {
        return table;
    }

    public UUID getChatId() {
        return chatId;
    }

    public Match getMatch() {
        return match;
    }

}
