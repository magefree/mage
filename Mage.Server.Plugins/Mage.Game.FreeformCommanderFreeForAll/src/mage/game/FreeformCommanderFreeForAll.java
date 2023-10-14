

package mage.game;

import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.match.MatchType;
import mage.game.mulligan.Mulligan;

import java.util.UUID;

/**
 * @author spjspj
 */
public class FreeformCommanderFreeForAll extends GameCommanderImpl {

    private int numPlayers;

    public FreeformCommanderFreeForAll(MultiplayerAttackOption attackOption, RangeOfInfluence range,
                                       Mulligan mulligan, int startLife, int startHandSize) {
        super(attackOption, range, mulligan, 60, startLife, startHandSize);
    }

    public FreeformCommanderFreeForAll(final FreeformCommanderFreeForAll game) {
        super(game);
        this.numPlayers = game.numPlayers;
    }

    @Override
    protected void init(UUID choosingPlayerId) {
        startingPlayerSkipsDraw = false;
        super.init(choosingPlayerId);
    }

    @Override
    public MatchType getGameType() {
        return new FreeformCommanderFreeForAllType();
    }

    @Override
    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    @Override
    public FreeformCommanderFreeForAll copy() {
        return new FreeformCommanderFreeForAll(this);
    }
}
