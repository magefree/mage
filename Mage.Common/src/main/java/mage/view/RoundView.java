

package mage.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mage.game.Game;
import mage.game.GameInfo;
import mage.game.tournament.Round;
import mage.game.tournament.TournamentPairing;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class RoundView implements Serializable {
    private static final long serialVersionUID = 1L;

    List<TournamentGameView> games = new ArrayList<>();

    public RoundView(Round round) {
        try {
            for (TournamentPairing pair : round.getPairs()) {
                // get info of finished games from match
                if (pair.getMatch() != null) {
                    for (GameInfo gameInfo : pair.getMatch().getGamesInfo()) {
                        games.add(new TournamentGameView(round.getRoundNumber(), gameInfo.getMatchId(), gameInfo.getGameId(), gameInfo.getState(), gameInfo.getResult(), gameInfo.getPlayers(), gameInfo.getTableId()));
                    }
                    if (!pair.getMatch().hasEnded()) {
                        int numberSavedGames = pair.getMatch().getGamesInfo().size();
                        if (pair.getMatch() != null) {
                            int gameCount = 0;
                            for (Game game : pair.getMatch().getGames()) {
                                gameCount++;
                                if (gameCount > numberSavedGames) {
                                    // only unfinished game info directly from game
                                    games.add(new TournamentGameView(round.getRoundNumber(), pair, game));
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<TournamentGameView> getGames() {
        return games;
    }

}
