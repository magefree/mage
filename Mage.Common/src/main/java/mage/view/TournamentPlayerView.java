
package mage.view;

import java.io.Serializable;
import mage.game.tournament.TournamentPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TournamentPlayerView implements Serializable, Comparable {

    private static final long serialVersionUID = 1L;

    private final String flagName;
    private final String name;
    private final String state;
    private final String results;
    private final String history;
    private final int points;
    private final boolean quit;

    TournamentPlayerView(TournamentPlayer tournamentPlayer) {
        this.name = tournamentPlayer.getPlayer().getName();
        StringBuilder sb = new StringBuilder(tournamentPlayer.getState().toString());
        String stateInfo = tournamentPlayer.getStateInfo();
        if (!stateInfo.isEmpty()) {
            sb.append(" (").append(stateInfo).append(')');
        }
        sb.append(tournamentPlayer.getDisconnectInfo());
        this.state = sb.toString();
        this.points = tournamentPlayer.getPoints();
        this.results = tournamentPlayer.getResults();
        this.quit = !tournamentPlayer.isInTournament();
        this.history = tournamentPlayer.getPlayer().getUserData().getHistory();
        this.flagName = tournamentPlayer.getPlayer().getUserData().getFlagName();
    }

    public String getName() {
        return this.name;
    }

    public String getState() {
        return state;
    }

    public int getPoints() {
        return this.points;
    }

    public String getResults() {
        return results;
    }

    public boolean hasQuit() {
        return quit;
    }

    @Override
    public int compareTo(Object t) {
        return ((TournamentPlayerView) t).getPoints() - this.getPoints();
    }

    public String getFlagName() {
        return flagName;
    }

    public String getHistory() {
        return history;
    }

}
