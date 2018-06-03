

package mage.game;

import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.match.MatchType;

public class CanadianHighlanderDuel extends GameCanadianHighlanderImpl {
   
    public CanadianHighlanderDuel(MultiplayerAttackOption attackOption, RangeOfInfluence range, int freeMulligans, int startLife) {
        super(attackOption, range, freeMulligans, startLife);
    }

    public CanadianHighlanderDuel(final CanadianHighlanderDuel game) {
        super(game);
    }

    @Override
    public MatchType getGameType() {
        return new CanadianHighlanderDuelType();
    }

    @Override
    public int getNumPlayers() {
        return 2;
    }

    @Override
    public CanadianHighlanderDuel copy() {
        return new CanadianHighlanderDuel(this);
    }

}
