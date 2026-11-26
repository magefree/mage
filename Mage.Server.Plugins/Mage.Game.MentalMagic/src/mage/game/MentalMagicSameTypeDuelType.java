package mage.game;

import mage.game.match.MatchType;

/**
 * @author xico7
 */
public class MentalMagicSameTypeDuelType extends MatchType {

    public MentalMagicSameTypeDuelType() {
        this.name = "Mental Magic (Same Type) Two Player Duel";
        this.maxPlayers = 2;
        this.minPlayers = 2;
        this.numTeams = 0;
        this.useAttackOption = false;
        this.useRange = false;
        this.sideboardingAllowed = true;
    }

    protected MentalMagicSameTypeDuelType(final MentalMagicSameTypeDuelType matchType) {
        super(matchType);
    }

    @Override
    public MentalMagicSameTypeDuelType copy() {
        return new MentalMagicSameTypeDuelType(this);
    }

}
