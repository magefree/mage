

package mage.game.match;

import java.io.Serializable;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class MatchType implements Serializable {

    protected String name;
    protected int minPlayers;
    protected int maxPlayers;
    protected int numTeams;
    protected int playersPerTeam;
    protected boolean useRange;
    protected boolean useAttackOption;
    protected boolean sideboardingAllowed;

    protected MatchType() {}

    protected MatchType(final MatchType matchType) {
        this.name = matchType.name;
        this.maxPlayers = matchType.maxPlayers;
        this.minPlayers = matchType.minPlayers;
        this.numTeams = matchType.numTeams;
        this.playersPerTeam = matchType.playersPerTeam;
        this.useRange = matchType.useRange;
        this.useAttackOption = matchType.useAttackOption;
        this.sideboardingAllowed = matchType.sideboardingAllowed;
    }

    public abstract MatchType copy();

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

    public int getNumTeams() {
        return numTeams;
    }

    public int getPlayersPerTeam() {
        return playersPerTeam;
    }

    public boolean isUseRange() {
        return useRange;
    }

    public boolean isUseAttackOption() {
        return useAttackOption;
    }

    public boolean isSideboardingAllowed() {
        return sideboardingAllowed;
    }

}
