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

import mage.cards.decks.Deck;
import mage.game.Game;
import mage.game.GameException;
import mage.game.events.Listener;
import mage.game.events.TableEvent;
import mage.game.events.TableEvent.EventType;
import mage.game.events.TableEventSource;
import mage.players.Player;

import java.util.*;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class MatchImpl implements Match {

    protected UUID id = UUID.randomUUID();
    protected List<MatchPlayer> players = new ArrayList<MatchPlayer>();
    protected List<Game> games = new ArrayList<Game>();
    protected MatchOptions options;

    protected TableEventSource tableEventSource = new TableEventSource();

    public MatchImpl(MatchOptions options) {
        this.options = options;
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
        MatchPlayer mPlayer = new MatchPlayer(player, deck);
        players.add(mPlayer);
    }

    @Override
    public boolean leave(UUID playerId) {
        MatchPlayer mPlayer = getPlayer(playerId);
        if (mPlayer != null) {
            return players.remove(mPlayer);
        }
        return false;
    }

    @Override
    public void startMatch() throws GameException {

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
    public boolean isMatchOver() {
        for (MatchPlayer player: players) {
            if (player.getWins() >= options.getWinsNeeded()) {
                return true;
            }
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
    public int getNumGames() {
        return games.size();
    }

    @Override
    public int getWinsNeeded() {
        return options.getWinsNeeded();
    }

    protected void initGame(Game game) throws GameException {
        shufflePlayers();
        for (MatchPlayer matchPlayer: this.players) {
            matchPlayer.getPlayer().init(game);
            game.loadCards(matchPlayer.getDeck().getCards(), matchPlayer.getPlayer().getId());
            game.loadCards(matchPlayer.getDeck().getSideboard(), matchPlayer.getPlayer().getId());
            game.addPlayer(matchPlayer.getPlayer(), matchPlayer.getDeck());
        }
    }

    protected void shufflePlayers() {
        Collections.shuffle(this.players, new Random());
    }

    @Override
    public void endGame() {
        Game game = getGame();
        for (MatchPlayer player: this.players) {
            Player p = game.getPlayer(player.getPlayer().getId());
            if (p != null) {
                if (p.hasWon()) {
                    player.addWin();
                }
                if (p.hasLost()) {
                    player.addLose();
                }
            }
        }
    }

    @Override
    public UUID getChooser() {
        UUID loserId = null;
        Game game = getGame();
        for (MatchPlayer player: this.players) {
            Player p = game.getPlayer(player.getPlayer().getId());
            if (p != null && p.hasLost()) {
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
            player.setSideboarding();
            player.getPlayer().sideboard(this, player.getDeck());
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
            if (!player.isDoneSideboarding()) {
                return false;
            }
        }
        return true;
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
        sb.append("\nMatch score:\n");
        for (MatchPlayer mp :this.getPlayers()) {
            sb.append("- ").append(mp.getPlayer().getName());
            sb.append(" (").append(mp.getWins()).append(" wins / ");
            sb.append(mp.getLoses()).append(" losses)\n");
        }
        sb.append("\n").append(this.getWinsNeeded()).append(" wins needed to win the match\n");
        sb.append("\nGame has started\n");
        return sb.toString();
    }

}
