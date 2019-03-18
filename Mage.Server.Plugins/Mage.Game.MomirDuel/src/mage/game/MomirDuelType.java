
package mage.game;

import mage.game.match.MatchType;

/**
 *
 * @author nigelzor
 */
public class MomirDuelType extends MatchType {

    public MomirDuelType() {
        this.name = "Momir Basic Two Player Duel";
        this.maxPlayers = 2;
        this.minPlayers = 2;
        this.numTeams = 0;
        this.useAttackOption = false;
        this.useRange = false;
        this.sideboardingAllowed = true;
    }

    protected MomirDuelType(final MomirDuelType matchType){
        super(matchType);
    }

    @Override
    public MomirDuelType copy() {
        return new MomirDuelType(this);
    }

}
