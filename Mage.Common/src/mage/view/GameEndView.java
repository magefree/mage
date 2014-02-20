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
package mage.view;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import mage.game.Game;
import mage.game.GameState;
import mage.game.match.Match;
import mage.game.match.MatchPlayer;
import mage.players.Player;

/**
 * @author LevelX2
 */
public class GameEndView implements Serializable {

    private PlayerView clientPlayer = null;
    private List<PlayerView> players = new ArrayList<PlayerView>();
    private Date startTime;
    private Date endTime;
    private String resultMessage;
    private boolean won;
    private MatchView matchView;
    private int wins;
    private int loses;
    private int winsNeeded;
    private String nameMatchWinner = null;

    public GameEndView(GameState state, Game game, UUID playerId, Match match) {
        startTime = game.getStartTime();
        endTime = game.getEndTime();

        // set result message
        int winner = 0;
        Player you = null;
        for (Player player: state.getPlayers().values()) {
            PlayerView playerView  = new PlayerView(player, state, game);
            if (playerView.getPlayerId().equals(playerId)) {
                clientPlayer = playerView;
                you = player;
            }
            players.add(playerView);
            if (player.hasWon()) {
                winner++;
            }
        }
        if (you != null) {
            won = you.hasWon();
            if (you.hasWon()) {
                resultMessage = new StringBuilder("You won the game on turn ").append(game.getTurnNum()).append(".").toString();
            } else if (winner > 0) {
                resultMessage = new StringBuilder("You lost the game on turn ").append(game.getTurnNum()).append(".").toString();
            } else {
                resultMessage = new StringBuilder("Game is a draw on Turn ").append(game.getTurnNum()).append(".").toString();
            }
        }
        matchView = new MatchView(match);
        
        winsNeeded = match.getOptions().getWinsNeeded();
        for (MatchPlayer mPlayer: match.getPlayers()) {
            if (mPlayer.getPlayer().equals(you)) {
                wins = mPlayer.getWins();
                loses = mPlayer.getLoses();

            }
            if (mPlayer.getWins() == winsNeeded) {
                nameMatchWinner = mPlayer.getName();
            }
        }

    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public List<PlayerView> getPlayers() {
        return players;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public boolean hasWon() {
        return won;
    }

    public MatchView getMatchView() {
        return matchView;
    }

    public int getWins() {
        return wins;
    }

    public int getLoses() {
        return loses;
    }

    public int getWinsNeeded() {
        return winsNeeded;
    }

    public String getNameMatchWinner() {
        return nameMatchWinner;
    }

    public PlayerView getClientPlayer() {
        return clientPlayer;
    }

}
