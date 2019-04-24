

package mage.game;

import java.util.UUID;
import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.match.MatchType;

/**
 *
 * @author spjspj
 */
public class BrawlFreeForAll extends GameCommanderImpl {

    private int numPlayers;

    public BrawlFreeForAll(MultiplayerAttackOption attackOption, RangeOfInfluence range, int freeMulligans, int startLife) {
        super(attackOption, range, freeMulligans, startLife);
    }

    public BrawlFreeForAll(final BrawlFreeForAll game) {
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
        return new BrawlFreeForAllType();
    }

    @Override
    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    @Override
    public BrawlFreeForAll copy() {
        return new BrawlFreeForAll(this);
    }
}
