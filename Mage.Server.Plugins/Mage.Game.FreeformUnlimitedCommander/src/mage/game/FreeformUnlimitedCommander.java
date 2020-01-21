package mage.game;

import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.mulligan.Mulligan;

public class FreeformUnlimitedCommander extends FreeformCommanderFreeForAll {

    protected int numPlayers;

    public FreeformUnlimitedCommander(MultiplayerAttackOption attackOption, RangeOfInfluence range, Mulligan mulligan, int startLife) {
        super(attackOption, range, mulligan, startLife);
    }

    public FreeformUnlimitedCommander(final FreeformUnlimitedCommander game) {
        super(game);
        this.numPlayers = game.numPlayers;
    }
}
