
package mage.game;

import java.util.UUID;
import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.match.MatchType;
import mage.game.mulligan.Mulligan;

public class FreeformUnlimitedCommander extends GameCommanderImpl {

    private int numPlayers;

    public FreeformUnlimitedCommander(MultiplayerAttackOption attackOption, RangeOfInfluence range, Mulligan mulligan, int startLife) {
        super(attackOption, range, mulligan, startLife, 60);
    }

    public FreeformUnlimitedCommander(final FreeformUnlimitedCommander game) {
        super(game);
        this.numPlayers = game.numPlayers;
    }

    @Override
    protected void init(UUID choosingPlayerId) {
        if (state.getPlayerList().size() > 2) {
            startingPlayerSkipsDraw = false;
        }
        super.init(choosingPlayerId); 
    }

    @Override
    public MatchType getGameType() {
        return new FreeformUnlimitedCommanderType();
    }

    @Override
    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    @Override
    public FreeformUnlimitedCommander copy() {
        return new FreeformUnlimitedCommander(this);
    }
}
