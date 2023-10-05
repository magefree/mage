

package mage.game;

import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.match.MatchType;
import mage.game.mulligan.Mulligan;

/**
 * @author JRHerlehy
 */
public class TinyLeadersDuel extends GameTinyLeadersImpl {

    public TinyLeadersDuel(MultiplayerAttackOption attackOption, RangeOfInfluence range,
                           Mulligan mulligan, int startLife, int startHandSize) {
        super(attackOption, range, mulligan, startLife, startHandSize);
    }

    public TinyLeadersDuel(final TinyLeadersDuel game) {
        super(game);
    }

    @Override
    public MatchType getGameType() {
        return new TinyLeadersDuelType();
    }

    @Override
    public int getNumPlayers() {
        return 2;
    }

    @Override
    public TinyLeadersDuel copy() {
        return new TinyLeadersDuel(this);
    }

}
