
package mage.game.tournament;

import java.io.Serializable;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TournamentType implements Serializable {

    protected String name;
    protected int minPlayers;
    protected int maxPlayers;
    protected int numBoosters;
    protected boolean cubeBooster;  // boosters are generated from a defined cube
    protected boolean draft;        // or sealed
    protected boolean limited;      // or construced
    protected boolean elimination;  // or Swiss
    protected boolean isRandom;
    protected boolean isRichMan;    // or Rich Man Draft
    protected boolean isJumpstart;

    protected TournamentType() {
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

    public boolean isElimination() {
        return elimination;
    }

    public boolean isCubeBooster() {
        return cubeBooster;
    }

    public boolean isRandom() {
        return this.isRandom;
    }

    public boolean isRichMan() {
        return this.isRichMan;
    }

    public boolean isJumpstart() {
        return this.isJumpstart;
    }

} 
