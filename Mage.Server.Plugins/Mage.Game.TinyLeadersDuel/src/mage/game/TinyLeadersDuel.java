

package mage.game;

import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.match.MatchType;

/**
 *
 * @author JRHerlehy
 */
public class TinyLeadersDuel extends GameTinyLeadersImpl {

    public TinyLeadersDuel(MultiplayerAttackOption attackOption, RangeOfInfluence range, int freeMulligans, int startLife) {
        super(attackOption, range, freeMulligans, startLife);
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
