

package mage.game;

import mage.game.match.MatchType;

/**
 *
 * @author spjspj
 */
public class BrawlDuelType extends MatchType {

    public BrawlDuelType() {
        this.name = "Brawl Two Player Duel";
        this.maxPlayers = 2;
        this.minPlayers = 2;
        this.numTeams = 0;
        this.useAttackOption = false;
        this.useRange = false;
        this.sideboardingAllowed = false;
    }

    protected BrawlDuelType(final BrawlDuelType matchType) {
        super(matchType);
    }

    @Override
    public BrawlDuelType copy() {
        return new BrawlDuelType(this);
    }

}
