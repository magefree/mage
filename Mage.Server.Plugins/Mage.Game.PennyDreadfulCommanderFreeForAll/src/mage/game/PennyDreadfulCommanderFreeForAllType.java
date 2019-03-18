

package mage.game;

import mage.game.match.MatchType;


/**
 *
 * @author spjspj
 */
public class PennyDreadfulCommanderFreeForAllType extends MatchType {

    public PennyDreadfulCommanderFreeForAllType() {
        this.name = "Penny Dreadful Commander Free For All";
        this.maxPlayers = 10;
        this.minPlayers = 3;
        this.numTeams = 0;
        this.useAttackOption = true;
        this.useRange = true;
        this.sideboardingAllowed = false;
    }

    protected PennyDreadfulCommanderFreeForAllType(final PennyDreadfulCommanderFreeForAllType matchType) {
        super(matchType);
    }

    @Override
    public PennyDreadfulCommanderFreeForAllType copy() {
        return new PennyDreadfulCommanderFreeForAllType(this);
    }
}
