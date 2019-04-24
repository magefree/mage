

package mage.game;

import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.match.MatchType;

public class BrawlDuel extends GameCommanderImpl {
   
    public BrawlDuel(MultiplayerAttackOption attackOption, RangeOfInfluence range, int freeMulligans, int startLife) {
        super(attackOption, range, freeMulligans, startLife);
    }

    public BrawlDuel(final BrawlDuel game) {
        super(game);
    }

    @Override
    public MatchType getGameType() {
        return new BrawlDuelType();
    }

    @Override
    public int getNumPlayers() {
        return 2;
    }

    @Override
    public BrawlDuel copy() {
        return new BrawlDuel(this);
    }

}
