

package mage.game;

import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.match.MatchType;
import mage.game.mulligan.Mulligan;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class CommanderFreeForAll extends GameCommanderImpl {

    private int numPlayers;

    public CommanderFreeForAll(MultiplayerAttackOption attackOption, RangeOfInfluence range, Mulligan mulligan, int startLife, int startHandSize) {
        super(attackOption, range, mulligan, 100, startLife, startHandSize);
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
