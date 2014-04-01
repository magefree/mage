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

package mage.game.tournament;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.decks.Deck;
import mage.constants.TournamentPlayerState;
import mage.game.draft.DraftCube;
import mage.game.events.Listener;
import mage.game.events.PlayerQueryEvent;
import mage.game.events.PlayerQueryEventSource;
import mage.game.events.TableEvent;
import mage.game.events.TableEvent.EventType;
import mage.game.events.TableEventSource;
import mage.game.match.Match;
import mage.game.match.MatchPlayer;
import mage.players.Player;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class TournamentImpl implements Tournament {

    protected UUID id = UUID.randomUUID();
    protected List<Round> rounds = new CopyOnWriteArrayList<>();
    protected Map<UUID, TournamentPlayer> players = new HashMap<>();
    protected static Random rnd = new Random();
    protected String matchName;
    protected TournamentOptions options;
    protected TournamentType tournamentType;
    protected List<ExpansionSet> sets = new ArrayList<>();
    protected String setsInfoShort;

    protected TableEventSource tableEventSource = new TableEventSource();
    protected PlayerQueryEventSource playerQueryEventSource = new PlayerQueryEventSource();

    protected Date startTime;
    protected Date endTime;
    protected boolean abort;

    public TournamentImpl(TournamentOptions options) {
        this.options = options;
        startTime = new Date();
        abort = false;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void addPlayer(Player player, String playerType) {
        players.put(player.getId(), new TournamentPlayer(player, playerType));
    }

    @Override
    public void removePlayer(UUID playerId) {
        players.remove(playerId);
    }

    @Override
    public TournamentPlayer getPlayer(UUID playerId) {
        return players.get(playerId);
    }

    @Override
    public void autoSubmit(UUID playerId, Deck deck) {
        if (players.containsKey(playerId)) {
            players.get(playerId).submitDeck(deck);
        }
    }

    @Override
    public TournamentOptions getOptions() {
        return options;
    }

    @Override
    public Collection<TournamentPlayer> getPlayers() {
        return players.values();
    }

    @Override
    public int getNumberRounds() {
        return options.getNumberRounds();
    }

    @Override
    public Collection<Round> getRounds() {
        return rounds;
    }

    @Override
    public List<ExpansionSet> getSets() {
        return sets;
    }

    @Override
    public void setBoosterInfo(String setsInfoShort) {
        this.setsInfoShort = setsInfoShort;
    }

    @Override
    public String getBoosterInfo() {
        return setsInfoShort;
    }

    @Override
    public void quit(UUID playerId) {
        synchronized (this) {
            this.notifyAll();
        }
    }

    // can only be used, if tournament did not start yet?
    @Override
    public void leave(UUID playerId) {
        if (players.containsKey(playerId)) {
            players.remove(playerId);
        }
    }

    @Override
    public void submitDeck(UUID playerId, Deck deck) {
        if (players.containsKey(playerId)) {
            players.get(playerId).submitDeck(deck);
        }
        synchronized (this) {
            this.notifyAll();
        }
    }

    @Override
    public void updateDeck(UUID playerId, Deck deck) {
        if (players.containsKey(playerId)) {
            players.get(playerId).updateDeck(deck);
        }
    }

    protected Round createRoundRandom() {
        Round round = new Round(rounds.size() + 1, this);
        rounds.add(round);
        List<TournamentPlayer> roundPlayers = getActivePlayers();
        while (roundPlayers.size() > 1) {
            int i = rnd.nextInt(roundPlayers.size());
            TournamentPlayer player1 = roundPlayers.get(i);
            roundPlayers.remove(i);
            i = rnd.nextInt(roundPlayers.size());
            TournamentPlayer player2 = roundPlayers.get(i);
            roundPlayers.remove(i);
            round.addPairing(new TournamentPairing(player1, player2));
        }
        if (roundPlayers.size() > 0) {
            // player free round - add to bye players of this round
            TournamentPlayer player1 = roundPlayers.get(0);
            round.getPlayerByes().add(player1);
            player1.setState(TournamentPlayerState.WAITING);
            player1.setStateInfo("Round Bye");
        }
        return round;
    }

    protected void playRound(Round round) {
        for (TournamentPairing pair: round.getPairs()) {
            playMatch(pair);
        }
        updateResults(); // show points from byes
        while (!round.isRoundOver()) {
            try {
                //TODO: improve this
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TournamentImpl.class).warn("TournamentImpl playRound error ", ex);
                break;
            }
        }
        updateResults();
    }

    protected List<TournamentPlayer> getActivePlayers() {
        List<TournamentPlayer> activePlayers = new ArrayList<>();
        for (TournamentPlayer player: players.values()) {
            if (!player.getEliminated()) {
                activePlayers.add(player);
            }
        }
        return activePlayers;
    }

    /**
     *
     */
    @Override
    public void updateResults() {
        for (TournamentPlayer player: players.values()) {
            player.setResults("");
            player.setPoints(0);
            player.setStateInfo("");
        }
        for (Round round: rounds) {
            for (TournamentPairing pair: round.getPairs()) {
                Match match = pair.getMatch();
                if (match != null && match.isMatchOver()) {
                    TournamentPlayer tp1 = pair.getPlayer1();
                    TournamentPlayer tp2 = pair.getPlayer2();
                    MatchPlayer mp1 = match.getPlayer(pair.getPlayer1().getPlayer().getId());
                    MatchPlayer mp2 = match.getPlayer(pair.getPlayer2().getPlayer().getId());
                    // set player satte
                    if (round.getRoundNumber() == rounds.size()) {
                        if (tp1.getState().equals(TournamentPlayerState.DUELING)) {
                            tp1.setState(TournamentPlayerState.WAITING);
                        }
                        if (tp2.getState().equals(TournamentPlayerState.DUELING)) {
                            tp2.setState(TournamentPlayerState.WAITING);
                        }
                    }
                    // Add round result
                    tp1.setResults(addRoundResult(round.getRoundNumber(), pair, tp1, tp2));
                    tp2.setResults(addRoundResult(round.getRoundNumber(), pair, tp2, tp1));

                    // Add points
                    if (mp2.hasQuit() || mp1.getWins() > mp2.getWins()) {
                        tp1.setPoints(tp1.getPoints() + 3);
                    } else if (mp1.hasQuit() || mp1.getWins() < mp2.getWins()) {
                        tp2.setPoints(tp2.getPoints() + 3);
                    } else {
                        tp1.setPoints(tp1.getPoints() + 1);
                        tp2.setPoints(tp2.getPoints() + 1);
                    }
                }
            }
            for (TournamentPlayer tp : round.getPlayerByes()) {
                tp.setResults(new StringBuilder(tp.getResults()).append("R").append(round.getRoundNumber()).append(" ").append("Bye ").toString());
                tp.setPoints(tp.getPoints() + 3);
            }
        }
    }

    private static String addRoundResult(int round, TournamentPairing pair, TournamentPlayer tournamentPlayer, TournamentPlayer opponentPlayer) {
        StringBuilder playerResult = new StringBuilder(tournamentPlayer.getResults());
        playerResult.append("R").append(round).append(" ");
        playerResult.append(getMatchResultString(tournamentPlayer, opponentPlayer, pair.getMatch()));
        return playerResult.toString();
    }
    
    private static String getMatchResultString(TournamentPlayer p1, TournamentPlayer p2, Match match) {
        MatchPlayer mp1 = match.getPlayer(p1.getPlayer().getId());
        MatchPlayer mp2 = match.getPlayer(p2.getPlayer().getId());
        StringBuilder matchResult = new StringBuilder();
        matchResult.append(p2.getPlayer().getName());
        matchResult.append(" (").append(mp1.getWins());
        if (mp1.hasQuit()) {
            matchResult.append("Q");
        }
        matchResult.append("-").append(mp2.getWins());
        if (mp2.hasQuit()) {
            matchResult.append("Q");
        }                
        matchResult.append(") ");        
        return matchResult.toString();
    }
    
    @Override
    public boolean isDoneConstructing() {
        for (TournamentPlayer player: this.players.values()) {
            if (!player.isDoneConstructing()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean allJoined() {
        for (TournamentPlayer player: this.players.values()) {
            if (!player.isJoined()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void addTableEventListener(Listener<TableEvent> listener) {
        tableEventSource.addListener(listener);
    }

    @Override
    public void addPlayerQueryEventListener(Listener<PlayerQueryEvent> listener) {
        playerQueryEventSource.addListener(listener);
    }

    @Override
    public void fireConstructEvent(UUID playerId) {
        playerQueryEventSource.construct(playerId, "Construct", getOptions().getLimitedOptions().getConstructionTime());
    }

    public void construct() {
        tableEventSource.fireTableEvent(EventType.CONSTRUCT);
        if (!isAbort()) {
            for (final TournamentPlayer player: players.values()) {

                player.setConstructing();
                new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            player.getPlayer().construct(TournamentImpl.this, player.getDeck());
                        }
                    }
                ).start();
            }
            synchronized(this) {
                while (!isDoneConstructing()) {
                    try {
                        this.wait();
                    } catch (InterruptedException ex) { }
                }
            }
        }
        nextStep();
    }

    protected void openBoosters() {
        for (TournamentPlayer player: this.players.values()) {
            player.setDeck(new Deck());
            if (options.getLimitedOptions().getDraftCube() != null) {
                DraftCube cube = options.getLimitedOptions().getDraftCube();
                for (int i = 0; i < options.getLimitedOptions().getNumberBoosters(); i++) {
                    List<Card> booster = cube.createBooster();
                    for (Card card: booster) {
                        player.getDeck().getSideboard().add(card);
                    }
                }
            } else {
                for (ExpansionSet set: sets) {
                    List<Card> booster = set.createBooster();
                    for (Card card: booster) {
                        player.getDeck().getSideboard().add(card);
                    }
                }
            }
        }
        nextStep();
    }

    public void playMatch(TournamentPairing pair) {
        options.getMatchOptions().getPlayerTypes().clear();
        options.getMatchOptions().getPlayerTypes().add(pair.getPlayer1().getPlayerType());
        options.getMatchOptions().getPlayerTypes().add(pair.getPlayer2().getPlayerType());
        tableEventSource.fireTableEvent(EventType.START_MATCH, pair, options.getMatchOptions());
    }

    public void end() {
        endTime = new Date();
        tableEventSource.fireTableEvent(EventType.END);
    }

    protected abstract void runTournament();

    @Override
    public Date getStartTime() {
        return new Date(startTime.getTime());
    }

    @Override
    public Date getEndTime() {
        if (endTime == null) {
            return null;
        }
        return new Date(endTime.getTime());
    }

    @Override
    public TournamentType getTournamentType() {
        return tournamentType;
    }

    @Override
    public void setTournamentType(TournamentType tournamentType) {
        this.tournamentType = tournamentType;
    }

    protected void winners() {
        // TODO: Generate StateInfo for Swiss pairing (1st, 2nd, ...)
        for(TournamentPlayer winner: this.getActivePlayers()) {
            winner.setState(TournamentPlayerState.FINISHED);
            if (options.getNumberRounds() == 0) { // if no swiss, last active is the winner
                if (isAbort()) {
                    winner.setStateInfo("Tournament canceled");
                } else {
                    winner.setStateInfo("Winner");
                }
            }
        }
    }

    @Override
    public void cleanUpOnTournamentEnd() {
        for(TournamentPlayer tournamentPlayer: players.values()) {
            tournamentPlayer.CleanUpOnTournamentEnd();
        }
    }

    @Override
    public boolean isAbort() {
        return abort;
    }

    @Override
    public void setAbort(boolean abort) {
        this.abort = abort;
    }

}
