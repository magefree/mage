/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
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

package mage.game.match;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import mage.cards.decks.Deck;
import mage.game.Game;
import mage.game.GameException;
import mage.game.GameInfo;
import mage.game.events.Listener;
import mage.game.events.TableEvent;
import mage.game.events.TableEvent.EventType;
import mage.game.events.TableEventSource;
import mage.players.Player;
import mage.util.DateFormat;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class MatchImpl implements Match {

    private static final Logger logger = Logger.getLogger(MatchImpl.class);
    
    protected UUID id = UUID.randomUUID();
    protected List<MatchPlayer> players = new ArrayList<>();
    protected List<Game> games = new ArrayList<>();
    protected List<GameInfo> gamesInfo = new ArrayList<>();
    protected UUID tableId;

    protected MatchOptions options;

    protected TableEventSource tableEventSource = new TableEventSource();

    protected Date startTime;
    protected Date endTime;

    protected int draws;
    protected int startedGames;

    protected boolean replayAvailable;

    public MatchImpl(MatchOptions options) {
        this.options = options;
        this.startTime = new Date(); // to avaoid null pointer exceptions
        replayAvailable = false;
        draws = 0;
    }

    @Override
    public List<MatchPlayer> getPlayers() {
        return players;
    }

    @Override
    public MatchPlayer getPlayer(UUID playerId) {
        for (MatchPlayer player: players) {
            if (player.getPlayer().getId().equals(playerId)) {
                return player;
            }
        }
        return null;
    }

    @Override
    public void addPlayer(Player player, Deck deck) {
        MatchPlayer matchPlayer = new MatchPlayer(player, deck);
        player.setMatchPlayer(matchPlayer);
        players.add(matchPlayer);
    }

    @Override
    public boolean quitMatch(UUID playerId) {
        MatchPlayer mPlayer = getPlayer(playerId);
        if (mPlayer != null) {
            if (!hasStarted()) {
                return players.remove(mPlayer);
            }
            mPlayer.setQuit(true);
            synchronized (this) {
                this.notifyAll();
            }
            checkIfMatchEnds();
            return true;
        }
        return false;
    }

    @Override
    public void startMatch() throws GameException {
        this.startTime = new Date();
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return options.getName();
    }

    @Override
    public MatchOptions getOptions() {
        return options;
    }

    @Override
    public boolean hasEnded() {
        // Some workarounds to end match if for unknown reason the match was not ended regularly
        if (getGame() == null && isDoneSideboarding()) {
            checkIfMatchEnds();
        }
        if (getGame() != null && getGame().hasEnded()) {            
            for (MatchPlayer matchPlayer:players) {
                if (matchPlayer.getPlayer().hasQuit() && !matchPlayer.hasQuit()) {
                    logger.warn("MatchPlayer was not set to quit matchId " + this.getId()+ " - " + matchPlayer.getName());
                    matchPlayer.setQuit(true);
                }
            }
            checkIfMatchEnds();
        }
        return endTime != null;
    }

    @Override
    public boolean hasStarted() {
        return startedGames > 0;
    }

    @Override
    public boolean checkIfMatchEnds() {
        int activePlayers = 0;
        MatchPlayer matchWinner = null;
        for (MatchPlayer matchPlayer: players) {
            if (!matchPlayer.hasQuit()) {
                activePlayers++;
                matchWinner = matchPlayer;
            }
            if (matchPlayer.getWins() >= options.getWinsNeeded()) {
                matchPlayer.setMatchWinner(true);
                endTime = new Date();
                return true;
            }
        }
        if (activePlayers < 2) {
            if (matchWinner != null) {
                matchWinner.setMatchWinner(true);
            }
            endTime = new Date();
            return true;
        }
        return false;
    }

    @Override
    public Game getGame() {
        if (games.isEmpty()) {
            return null;
        }
        return games.get(games.size() -1);
    }

    @Override
    public List<Game> getGames() {
        return games;
    }

    @Override
    public void addGame() {
        startedGames++;
    }

    @Override
    public int getNumGames() {
        return startedGames;
    }

    @Override
    public int getWinsNeeded() {
        return options.getWinsNeeded();
    }

    @Override
    public int getFreeMulligans() {
        return options.getFreeMulligans();
    }

    protected void initGame(Game game) throws GameException {
        addGame(); // raises only the number
        shufflePlayers();        
        for (MatchPlayer matchPlayer: this.players) {
            if (!matchPlayer.hasQuit()) {
                matchPlayer.getPlayer().init(game);
                game.loadCards(matchPlayer.getDeck().getCards(), matchPlayer.getPlayer().getId());
                game.loadCards(matchPlayer.getDeck().getSideboard(), matchPlayer.getPlayer().getId());
                game.addPlayer(matchPlayer.getPlayer(), matchPlayer.getDeck());
                // set the priority time left for the match
                if (games.isEmpty()) { // first game full time 
                       matchPlayer.getPlayer().setPriorityTimeLeft(options.getPriorityTime());
                } else {
                    if (matchPlayer.getPriorityTimeLeft() > 0) {
                        matchPlayer.getPlayer().setPriorityTimeLeft(matchPlayer.getPriorityTimeLeft());
                    }
                }
            }
        }
        game.setPriorityTime(options.getPriorityTime());
    }

    protected void shufflePlayers() {
        Collections.shuffle(this.players, new Random());
    }

    @Override
    public void endGame() {
        Game game = getGame();
        for (MatchPlayer matchPlayer: this.players) {
            Player player = game.getPlayer(matchPlayer.getPlayer().getId());
            if (player != null) {
                // get the left time from player priority timer
                if (game.getPriorityTime() > 0) {
                    matchPlayer.setPriorityTimeLeft(player.getPriorityTimeLeft());
                }
                if (player.hasQuit()) {
                    matchPlayer.setQuit(true);
                }
                if (player.hasWon()) {
                    matchPlayer.addWin();
                }
            }
        }
        if (game.isADraw()) {
            addDraw();
        }
        checkIfMatchEnds();
        game.fireGameEndInfo();
        gamesInfo.add(createGameInfo(game));
    }

    @Override
    public GameInfo createGameInfo(Game game) {
        StringBuilder playersInfo = new StringBuilder();
        int counter = 0;

        for (MatchPlayer matchPlayer: getPlayers()) {
            if (counter > 0) {
                playersInfo.append(" - ");
            }
            playersInfo.append(matchPlayer.getName());
            counter++;
        }

        String state;
        String result;
        String duelingTime = "";
        if (game.hasEnded()) {
            if (game.getEndTime() != null) {
                duelingTime = " (" + DateFormat.getDuration((game.getEndTime().getTime() - game.getStartTime().getTime())/1000) + ")";
            }
            state = "Finished" + duelingTime;
            result = game.getWinner();
        }
        else {
            if (game.getStartTime() != null) {
                duelingTime = " (" + DateFormat.getDuration((new Date().getTime() - game.getStartTime().getTime())/1000) + ")";
            }
            state = "Dueling" + duelingTime;
            result = "";
        }
        return new GameInfo(0, this.getId(), game.getId(), state, result, playersInfo.toString(), tableId);
    }

    @Override
    public List<GameInfo> getGamesInfo() {
        return gamesInfo;
    }

    @Override
    public void setTableId(UUID tableId) {
        this.tableId = tableId;
    }

    @Override
    public void setTournamentRound(int round) {
        for (GameInfo gameInfo: gamesInfo) {
            gameInfo.setRoundNum(round);
        }
    }

    @Override
    public UUID getChooser() {
        UUID loserId = null;
        Game game = getGame();
        for (MatchPlayer player: this.players) {
            Player p = game.getPlayer(player.getPlayer().getId());
            if (p != null && p.hasLost() && !p.hasQuit()) {
                loserId = p.getId();
            }
        }
        return loserId;
    }

    @Override
    public void addTableEventListener(Listener<TableEvent> listener) {
        tableEventSource.addListener(listener);
    }

    @Override
    public void sideboard() {
        for (MatchPlayer player: this.players) {
            if (!player.hasQuit()) {
                if (player.getDeck() != null) {
                    player.setSideboarding();
                    player.getPlayer().sideboard(this, player.getDeck());
                } else {
                    logger.error("Player " + player.getName() + " has no deck: " + player.getPlayer().getId());
                }
            }
        }
        synchronized(this) {
            while (!isDoneSideboarding()) {
                try {
                    this.wait();
                } catch (InterruptedException ex) { }
            }
        }
    }

    @Override
    public boolean isDoneSideboarding() {
        for (MatchPlayer player: this.players) {
            if (!player.hasQuit() && !player.isDoneSideboarding()) {
                return false;
            }
        }
        return true;

    }

    //@Override
    public boolean areAllDoneSideboarding() {
        int count = 0;
        for (MatchPlayer player: this.players) {
            if (!player.hasQuit() && player.isDoneSideboarding()) {
                return true;
            }
            if (player.hasQuit()) {
                count++;
            }
        }
        return count < this.players.size();
    }

    @Override
    public void fireSideboardEvent(UUID playerId, Deck deck) {
        MatchPlayer player = getPlayer(playerId);
        if (player != null) {
            tableEventSource.fireTableEvent(EventType.SIDEBOARD, playerId, deck, SIDEBOARD_TIME);
        }
    }

    @Override
    public void submitDeck(UUID playerId, Deck deck) {
        MatchPlayer player = getPlayer(playerId);
        if (player != null) {
            // make sure the deck name (needed for Tiny Leaders) won't get lost by sideboarding
            deck.setName(player.getDeck().getName());
            player.submitDeck(deck);
        }
        synchronized (this) {
            this.notifyAll();
        }
    }

    @Override
    public void updateDeck(UUID playerId, Deck deck) {
        MatchPlayer player = getPlayer(playerId);
        if (player != null) {
            player.updateDeck(deck);
        }
    }

    protected String createGameStartMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("<br/><b>Match score:</b><br/>");
        for (MatchPlayer mp :this.getPlayers()) {
            sb.append("   ").append(mp.getName());
            sb.append(" - ").append(mp.getWins()).append(mp.getWins()==1?" win":" wins");
            if (mp.hasQuit()) {
                sb.append(" QUITTED");
            }
            sb.append("<br/>");
        }
        if (getDraws() > 0) {
            sb.append("   Draws: ").append(getDraws()).append("<br/>");
        }
        sb.append("<br/>").append("You have to win ").append(this.getWinsNeeded()).append(this.getWinsNeeded() == 1 ? " game":" games").append(" to win the complete match<br/>");
        sb.append("<br/>Game has started<br/><br/>");
        return sb.toString();
    }

    @Override
    public Date getStartTime() {
        if (startTime != null) {
            return new Date(startTime.getTime());
        }
        return null;
    }

    @Override
    public Date getEndTime() {
        if (endTime != null) {
            return new Date(endTime.getTime());
        }
        return null;
    }

    @Override
    public boolean isReplayAvailable() {
        return replayAvailable;
    }

    @Override
    public void setReplayAvailable(boolean replayAvailable) {
        this.replayAvailable = replayAvailable;
    }

    @Override
    public void cleanUpOnMatchEnd(boolean isSaveGameActivated, boolean isTournament) {
        for (MatchPlayer matchPlayer: players) {
            matchPlayer.cleanUpOnMatchEnd();
        }
        if ((!isSaveGameActivated && !isTournament) || this.getGame().isSimulation()) {
            this.getGames().clear();
        }         
    }    

    @Override
    public void addDraw() {
        ++draws;
    }

    @Override
    public int getDraws() {
        return draws;
    }


    @Override
    public void cleanUp() {
        for (MatchPlayer matchPlayer: players) {
            matchPlayer.cleanUpOnMatchEnd();
        }
        this.getGames().clear();
    }

}
