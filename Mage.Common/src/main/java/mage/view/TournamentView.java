

package mage.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import mage.game.tournament.Round;
import mage.game.tournament.Tournament;
import mage.game.tournament.TournamentPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TournamentView implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String tournamentName;
    private final String tournamentType;
    private final String tournamentState;
    
    private final Date startTime;
    private final Date endTime;
    private final Date stepStartTime;
    private final Date serverTime;
    private final int constructionTime;
    private final boolean watchingAllowed;

    private final List<RoundView> rounds = new ArrayList<>();
    private final List<TournamentPlayerView> players = new ArrayList<>();
    private final String runningInfo;
    
    public TournamentView(Tournament tournament) {

        tournamentName = tournament.getOptions().getName();
        StringBuilder typeText = new StringBuilder(tournament.getOptions().getTournamentType());
        if (!tournament.getTournamentType().isLimited()) {
            typeText.append(" / ").append(tournament.getOptions().getMatchOptions().getDeckType());
        }
        if (tournament.getNumberRounds() > 0) {
            typeText.append(' ').append(tournament.getNumberRounds()).append(" rounds");
        } 
        tournamentType = typeText.toString();
        startTime = tournament.getStartTime();
        endTime = tournament.getEndTime();
        stepStartTime = tournament.getStepStartTime();
        constructionTime = tournament.getOptions().getLimitedOptions().getConstructionTime();
        watchingAllowed = tournament.getOptions().isWatchingAllowed();
        serverTime = new Date();
        tournamentState = tournament.getTournamentState();

        if (tournament.getTournamentState().equals("Drafting") && tournament.getDraft() != null) {
            runningInfo = "booster/card: " + tournament.getDraft().getBoosterNum() + '/' + (tournament.getDraft().getCardNum());
        } else {
            runningInfo = "";
        }
        for (TournamentPlayer player: tournament.getPlayers()) {
            players.add(new TournamentPlayerView(player));
        }
        Collections.sort(players);
        for (Round round: tournament.getRounds()) {
            rounds.add(new RoundView(round));
        }
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public String getTournamentType() {
        return tournamentType;
    }

    public Date getStartTime() {
        return new Date(startTime.getTime());
    }

    public Date getEndTime() {
        if (endTime == null) {
            return null;
        }
        return new Date(endTime.getTime());
    }

    public boolean isWatchingAllowed() {
        return watchingAllowed;
    }

    public List<TournamentPlayerView> getPlayers() {
        return players;
    }

    public List<RoundView> getRounds() {
        return rounds;
    }

    public String getTournamentState() {
        return tournamentState;
    }

    public Date getStepStartTime() {
        return stepStartTime;
    }

    public int getConstructionTime() {
        return constructionTime;
    }

    public Date getServerTime() {
        return serverTime;
    }

    public String getRunningInfo() {
        return runningInfo;
    }
    
}
