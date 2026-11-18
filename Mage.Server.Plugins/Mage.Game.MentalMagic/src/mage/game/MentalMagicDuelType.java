package mage.game;

import mage.game.match.MatchType;

/**
 * @author xico7
 */
public class MentalMagicDuelType extends MatchType {

    public MentalMagicDuelType() {
        this.name = "Mental Magic Two Player Duel";
        this.maxPlayers = 2;
        this.minPlayers = 2;
        this.numTeams = 0;
        this.useAttackOption = false;
        this.useRange = false;
        this.sideboardingAllowed = true;
    }

    protected MentalMagicDuelType(final MentalMagicDuelType matchType) {
        super(matchType);
    }

    @Override
    public MentalMagicDuelType copy() {
        return new MentalMagicDuelType(this);
    }

}
