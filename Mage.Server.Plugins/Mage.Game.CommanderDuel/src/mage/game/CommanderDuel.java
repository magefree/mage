

package mage.game;

import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.match.MatchType;
import mage.game.mulligan.Mulligan;

public class CommanderDuel extends GameCommanderImpl {

    public CommanderDuel(MultiplayerAttackOption attackOption, RangeOfInfluence range, Mulligan mulligan, int startLife, int startHandSize) {
        super(attackOption, range, mulligan, 100, startLife, startHandSize);
    }

    public CommanderDuel(final CommanderDuel game) {
        super(game);
    }

    @Override
    public MatchType getGameType() {
        return new CommanderDuelType();
    }

    @Override
    public int getNumPlayers() {
        return 2;
    }

    @Override
    public CommanderDuel copy() {
        return new CommanderDuel(this);
    }

}
