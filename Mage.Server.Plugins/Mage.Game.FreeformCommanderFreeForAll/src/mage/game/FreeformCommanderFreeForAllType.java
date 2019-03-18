

package mage.game;

import mage.game.match.MatchType;


/**
 *
 * @author spjspj
 */
public class FreeformCommanderFreeForAllType extends MatchType {

    public FreeformCommanderFreeForAllType() {
        this.name = "Freeform Commander Free For All";
        this.maxPlayers = 10;
        this.minPlayers = 3;
        this.numTeams = 0;
        this.useAttackOption = true;
        this.useRange = true;
        this.sideboardingAllowed = false;
    }

    protected FreeformCommanderFreeForAllType(final FreeformCommanderFreeForAllType matchType) {
        super(matchType);
    }

    @Override
    public FreeformCommanderFreeForAllType copy() {
        return new FreeformCommanderFreeForAllType(this);
    }
}
