
package mage.game.tournament;

import mage.game.match.MatchOptions;
import mage.players.PlayerType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TournamentOptions implements Serializable {

    protected String name;
    protected String tournamentType;
    protected List<PlayerType> playerTypes = new ArrayList<>();
    protected MatchOptions matchOptions;
    protected LimitedOptions limitedOptions;
    protected boolean watchingAllowed = true;
    protected boolean planeChase = false;
    protected int numberRounds;
    protected String password;
    protected int quitRatio;
    protected int minimumRating;

    public TournamentOptions(String name, String matchType, int numSeats) {
        this.name = name;
        this.matchOptions = new MatchOptions("", matchType, numSeats > 2, numSeats);
    }

    public String getName() {
        return name;
    }

    public String getTournamentType() {
        return tournamentType;
    }

    public void setTournamentType(String tournamentType) {
        this.tournamentType = tournamentType;
    }

    public List<PlayerType> getPlayerTypes() {
        return playerTypes;
    }

    public MatchOptions getMatchOptions() {
        return matchOptions;
    }

    public void setLimitedOptions(LimitedOptions limitedOptions) {
        this.limitedOptions = limitedOptions;
    }

    public LimitedOptions getLimitedOptions() {
        return limitedOptions;
    }

    public boolean isWatchingAllowed() {
        return watchingAllowed;
    }

    public void setWatchingAllowed(boolean watchingAllowed) {
        this.watchingAllowed = watchingAllowed;
    }

    public boolean isPlaneChase() {
        return planeChase;
    }

    public void setPlaneChase(boolean planeChase) {
        this.planeChase = planeChase;
        this.matchOptions.setPlaneChase(planeChase);
    }    

    public int getNumberRounds() {
        return numberRounds;
    }

    public void setNumberRounds(int numberRounds) {
        this.numberRounds = numberRounds;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getQuitRatio() {
        return quitRatio;
    }

    public void setQuitRatio(int quitRatio) {
        this.quitRatio = quitRatio;
    }

    public int getMinimumRating() { return minimumRating; }

    public void setMinimumRating(int minimumRating) { this.minimumRating = minimumRating; }
}
