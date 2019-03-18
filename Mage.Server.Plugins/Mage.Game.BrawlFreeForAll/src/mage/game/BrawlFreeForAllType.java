

package mage.game;

import mage.game.match.MatchType;


/**
 *
 * @author spjspj
 */
public class BrawlFreeForAllType extends MatchType {

    public BrawlFreeForAllType() {
        this.name = "Brawl Free For All";
        this.maxPlayers = 10;
        this.minPlayers = 3;
        this.numTeams = 0;
        this.useAttackOption = true;
        this.useRange = true;
        this.sideboardingAllowed = false;
    }

    protected BrawlFreeForAllType(final BrawlFreeForAllType matchType) {
        super(matchType);
    }

    @Override
    public BrawlFreeForAllType copy() {
        return new BrawlFreeForAllType(this);
    }
}
