

package mage.game;

import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.match.MatchType;
import mage.game.mulligan.Mulligan;

public class CanadianHighlanderDuel extends GameCanadianHighlanderImpl {

    public CanadianHighlanderDuel(MultiplayerAttackOption attackOption, RangeOfInfluence range, Mulligan mulligan, int startLife, int startHandSize) {
        super(attackOption, range, mulligan, startLife, startHandSize);
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
