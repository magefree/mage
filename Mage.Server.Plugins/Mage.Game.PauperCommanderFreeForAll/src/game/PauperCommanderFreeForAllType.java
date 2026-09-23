

package mage.game;

import mage.game.match.MatchType;


/**
 * @author ArcadesSaboth
 */
public class PauperCommanderFreeForAllType extends MatchType {

    public PauperCommanderFreeForAllType() {
        this.name = "Pauper Commander Free For All";
        this.maxPlayers = 10;
        this.minPlayers = 3;
        this.numTeams = 0;
        this.useAttackOption = true;
        this.useRange = true;
        this.sideboardingAllowed = false;
    }

    protected PauperCommanderFreeForAllType(final PauperCommanderFreeForAllType matchType) {
        super(matchType);
    }

    @Override
    public PauperCommanderFreeForAllType copy() {
        return new PauperCommanderFreeForAllType(this);
    }
}
