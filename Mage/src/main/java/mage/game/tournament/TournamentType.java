
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
    protected boolean draft;        // else Sealed
    protected boolean limited;      // else Constructed
    protected boolean elimination;  // else Swiss
    protected boolean isRandom;     // chaos draft
    protected boolean isRemixed;    // boosters generated containing cards from multiple sets
    protected boolean isRichMan;    // new boosters generated for each pick
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

    public boolean isRemixed() {
        return this.isRemixed;
    }

    public boolean isRichMan() {
        return this.isRichMan;
    }

    public boolean isJumpstart() {
        return this.isJumpstart;
    }

} 
