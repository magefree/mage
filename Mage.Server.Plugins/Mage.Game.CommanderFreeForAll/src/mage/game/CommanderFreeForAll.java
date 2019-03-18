

package mage.game;

import java.util.UUID;
import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.match.MatchType;

/**
 *
 * @author LevelX2
 */
public class CommanderFreeForAll extends GameCommanderImpl {

    private int numPlayers;

    public CommanderFreeForAll(MultiplayerAttackOption attackOption, RangeOfInfluence range, int freeMulligans, int startLife) {
        super(attackOption, range, freeMulligans, startLife);
    }

    public CommanderFreeForAll(final CommanderFreeForAll game) {
        super(game);
        this.numPlayers = game.numPlayers;
    }

    @Override
    protected void init(UUID choosingPlayerId) {
        startingPlayerSkipsDraw = false;
        super.init(choosingPlayerId); 
    }

    @Override
    public MatchType getGameType() {
        return new CommanderFreeForAllType();
    }

    @Override
    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    @Override
    public CommanderFreeForAll copy() {
        return new CommanderFreeForAll(this);
    }

}
