
package mage.game;

import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.match.MatchType;
import mage.game.mulligan.Mulligan;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FreeForAll extends GameImpl {

    private int numPlayers;

    public FreeForAll(MultiplayerAttackOption attackOption, RangeOfInfluence range, Mulligan mulligan, int startLife) {
        super(attackOption, range, mulligan, startLife, 60, 7);
    }

    public FreeForAll(final FreeForAll game) {
        super(game);
        this.numPlayers = game.numPlayers;
    }

    @Override
    public MatchType getGameType() {
        return new FreeForAllType();
    }

    @Override
    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    @Override
    public FreeForAll copy() {
        return new FreeForAll(this);
    }

}
