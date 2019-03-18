
package mage.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import mage.game.Game;
import mage.game.GameState;
import mage.game.Table;
import mage.game.match.Match;
import mage.game.match.MatchPlayer;
import mage.players.Player;

/**
 * @author LevelX2
 */
public class GameEndView implements Serializable {

    private PlayerView clientPlayer = null;
    private final List<PlayerView> players = new ArrayList<>();
    private final Date startTime;
    private final Date endTime;
    private String gameInfo;
    private final String matchInfo;
    private final String additionalInfo;
    private boolean won;
    private final MatchView matchView;
    private int wins;
    private int loses;
    private final int winsNeeded;

    public GameEndView(GameState state, Game game, UUID playerId, Table table) {
        startTime = game.getStartTime();
        endTime = game.getEndTime();

        // set result message
        int winner = 0;
        Player you = null;
        for (Player player : state.getPlayers().values()) {
            PlayerView playerView = new PlayerView(player, state, game, playerId, null);
            if (playerView.getPlayerId().equals(playerId)) {
                clientPlayer = playerView;
                you = player;
                won = you.hasWon(); // needed to control image
            }
            players.add(playerView);
            if (player.hasWon()) {
                winner++;
            }
        }
        if (you != null) {
            if (you.hasWon()) {
                gameInfo = "You won the game on turn " + game.getTurnNum() + ".";
            } else if (winner > 0) {
                gameInfo = "You lost the game on turn " + game.getTurnNum() + ".";
            } else {
                gameInfo = "Game is a draw on Turn " + game.getTurnNum() + ".";
            }
        }
        matchView = new MatchView(table);

        Match match = table.getMatch();
        MatchPlayer matchWinner = null;
        winsNeeded = match.getOptions().getWinsNeeded();
        StringBuilder additonalText = new StringBuilder();
        for (MatchPlayer matchPlayer : match.getPlayers()) {
            if (matchPlayer.getPlayer().equals(you)) {
                wins = matchPlayer.getWins();
            }
            if (matchPlayer.isMatchWinner()) {
                matchWinner = matchPlayer;
            }
            if (matchPlayer.getPlayer().hasTimerTimeout()) {
                if (matchPlayer.getPlayer().equals(you)) {
                    additonalText.append("You run out of time. ");
                } else {
                    additonalText.append(matchPlayer.getName()).append(" runs out of time. ");
                }
            } else if (matchPlayer.getPlayer().hasIdleTimeout()) {
                if (matchPlayer.getPlayer().equals(you)) {
                    additonalText.append("You lost the match for being idle. ");
                } else {
                    additonalText.append(matchPlayer.getName()).append(" lost for being idle. ");
                }
            } else if (matchPlayer.hasQuit()) {
                if (matchPlayer.getPlayer().equals(you)) {
                    additonalText.append("You have quit the match. ");
                } else {
                    additonalText.append(matchPlayer.getName()).append(" has quit the match. ");
                }
            }
        }

        if (matchWinner != null) {
            if (matchWinner.getPlayer().equals(you)) {
                matchInfo = "You won the match!";
            } else {
                matchInfo = new StringBuilder(matchWinner.getName()).append(" won the match!").toString();
            }
        } else {
            matchInfo = new StringBuilder("You need ").append(winsNeeded - wins == 1 ? "one more win " : winsNeeded - wins + " more wins ").append("to win the match.").toString();
        }
        additionalInfo = additonalText.toString();

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

    public String getGameInfo() {
        return gameInfo;
    }

    public String getMatchInfo() {
        return matchInfo;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
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

    public PlayerView getClientPlayer() {
        return clientPlayer;
    }

}
