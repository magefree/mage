
package mage.game;

import mage.game.match.MatchType;

/**
 *
 * @author nigelzor
 */
public class MomirFreeForAllType extends MatchType {

    public MomirFreeForAllType() {
        this.name = "Momir Basic Free For All";
        this.maxPlayers = 10;
        this.minPlayers = 3;
        this.numTeams = 0;
        this.useAttackOption = true;
        this.useRange = true;
        this.sideboardingAllowed = false;
    }

    protected MomirFreeForAllType(final MomirFreeForAllType matchType) {
        super(matchType);
    }

    @Override
    public MomirFreeForAllType copy() {
        return new MomirFreeForAllType(this);
    }

}
