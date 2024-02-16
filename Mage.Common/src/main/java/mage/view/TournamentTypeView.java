
package mage.view;

import java.io.Serializable;
import mage.game.tournament.TournamentType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TournamentTypeView implements Serializable {

    private static final long serialVersionUID = 2L;

    private final String name;
    private final int minPlayers;
    private final int maxPlayers;
    private final int numBoosters;
    private final boolean draft;
    private final boolean limited;
    private final boolean cubeBooster;
    private final boolean elimination;
    private final boolean random;
    private final boolean remixed;
    private final boolean richMan;
    private final boolean jumpstart;

    public TournamentTypeView(TournamentType tournamentType) {
        this.name = tournamentType.getName();
        this.minPlayers = tournamentType.getMinPlayers();
        this.maxPlayers = tournamentType.getMaxPlayers();
        this.numBoosters = tournamentType.getNumBoosters();
        this.draft = tournamentType.isDraft();
        this.limited = tournamentType.isLimited();
        this.cubeBooster = tournamentType.isCubeBooster();
        this.elimination = tournamentType.isElimination();
        this.random = tournamentType.isRandom();
        this.remixed = tournamentType.isRemixed();
        this.richMan = tournamentType.isRichMan();
        this.jumpstart = tournamentType.isJumpstart();
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getNumBoosters() {
        return numBoosters;
    }

    public boolean isDraft() {
        return draft;
    }

    public boolean isLimited() {
        return limited;
    }

    public boolean isCubeBooster() {
        return cubeBooster;
    }

    public boolean isElimination() {
        return elimination;
    }

    public boolean isRandom() {
        return random;
    }

    public boolean isRemixed() {
        return remixed;
    }

    public boolean isRichMan() {
        return richMan;
    }

    public boolean isJumpstart() {
        return jumpstart;
    }

} 
