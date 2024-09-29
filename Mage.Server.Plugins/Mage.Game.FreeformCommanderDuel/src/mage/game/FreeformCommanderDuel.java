package mage.game;

import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.match.MatchType;
import mage.game.mulligan.Mulligan;

/**
 * @author JayDi85
 */
public class FreeformCommanderDuel extends GameCommanderImpl {

    public FreeformCommanderDuel(MultiplayerAttackOption attackOption, RangeOfInfluence range,
                                 Mulligan mulligan, int startLife, int startHandSize) {
        super(attackOption, range, mulligan, 60, startLife, startHandSize);
    }

    public FreeformCommanderDuel(final FreeformCommanderDuel game) {
        super(game);
    }

    @Override
    public MatchType getGameType() {
        return new FreeformCommanderDuelType();
    }

    @Override
    public int getNumPlayers() {
        return 2;
    }

    @Override
    public FreeformCommanderDuel copy() {
        return new FreeformCommanderDuel(this);
    }

}
