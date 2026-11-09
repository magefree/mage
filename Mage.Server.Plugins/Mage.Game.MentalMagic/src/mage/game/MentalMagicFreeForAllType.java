package mage.game;

import mage.game.match.MatchType;

/**
 * @author xico7
 */
public class MentalMagicFreeForAllType extends MatchType {

    public MentalMagicFreeForAllType() {
        this.name = "Mental Magic Free For All";
        this.maxPlayers = 10;
        this.minPlayers = 3;
        this.numTeams = 0;
        this.useAttackOption = true;
        this.useRange = true;
        this.sideboardingAllowed = true;
    }

    protected MentalMagicFreeForAllType(final MentalMagicFreeForAllType matchType) {
        super(matchType);
    }

    @Override
    public MentalMagicFreeForAllType copy() {
        return new MentalMagicFreeForAllType(this);
    }

}
