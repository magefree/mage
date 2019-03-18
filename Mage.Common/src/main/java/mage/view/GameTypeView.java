

package mage.view;

import java.io.Serializable;
import mage.game.match.MatchType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GameTypeView extends Object implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String name;
    private final int minPlayers;
    private final int maxPlayers;
    private final int numTeams;
    private final int playersPerTeam;
    private final boolean useRange;
    private final boolean useAttackOption;

    public GameTypeView(MatchType gameType) {
        this.name = gameType.getName();
        this.minPlayers = gameType.getMinPlayers();
        this.maxPlayers = gameType.getMaxPlayers();
        this.numTeams = gameType.getNumTeams();
        this.playersPerTeam = gameType.getPlayersPerTeam();
        this.useAttackOption = gameType.isUseAttackOption();
        this.useRange = gameType.isUseRange();
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


}
