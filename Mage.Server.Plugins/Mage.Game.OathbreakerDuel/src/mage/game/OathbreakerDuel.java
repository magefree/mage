package mage.game;

import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.match.MatchType;
import mage.game.mulligan.Mulligan;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class OathbreakerDuel extends OathbreakerFreeForAll {

    public OathbreakerDuel(MultiplayerAttackOption attackOption, RangeOfInfluence range, Mulligan mulligan, int startLife) {
        super(attackOption, range, mulligan, startLife);
        this.startingPlayerSkipsDraw = true;
    }

    public OathbreakerDuel(final OathbreakerDuel game) {
        super(game);
    }

    @Override
    public MatchType getGameType() {
        return new OathbreakerDuelType();
    }

    @Override
    public int getNumPlayers() {
        return 2;
    }

    @Override
    public OathbreakerDuel copy() {
        return new OathbreakerDuel(this);
    }

    @Override
    protected void init(UUID choosingPlayerId) {
        super.init(choosingPlayerId);

        startingPlayerSkipsDraw = false;
    }

}
