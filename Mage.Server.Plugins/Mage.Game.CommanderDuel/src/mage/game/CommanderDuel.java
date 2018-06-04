

package mage.game;

import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.match.MatchType;

public class CommanderDuel extends GameCommanderImpl {
   
    public CommanderDuel(MultiplayerAttackOption attackOption, RangeOfInfluence range, int freeMulligans, int startLife) {
        super(attackOption, range, freeMulligans, startLife);
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
