

package mage.game;

import mage.game.match.MatchType;

/**
 *
 * @author spjspj
 */
public class CanadianHighlanderDuelType extends MatchType {

    public CanadianHighlanderDuelType() {
        this.name = "Canadian Highlander Two Player Duel";
        this.maxPlayers = 2;
        this.minPlayers = 2;
        this.numTeams = 0;
        this.useAttackOption = false;
        this.useRange = false;
        this.sideboardingAllowed = false;
    }

    protected CanadianHighlanderDuelType(final CanadianHighlanderDuelType matchType) {
        super(matchType);
    }

    @Override
    public CanadianHighlanderDuelType copy() {
        return new CanadianHighlanderDuelType(this);
    }
}
