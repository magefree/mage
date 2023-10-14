

package mage.game;

import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.match.MatchType;
import mage.game.mulligan.Mulligan;

public class BrawlDuel extends GameCommanderImpl {

    public BrawlDuel(MultiplayerAttackOption attackOption, RangeOfInfluence range, Mulligan mulligan, int startLife, int startHandSize) {
        super(attackOption, range, mulligan, 60, startLife, startHandSize);
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
