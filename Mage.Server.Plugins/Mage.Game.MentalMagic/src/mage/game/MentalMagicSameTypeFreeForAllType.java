package mage.game;

import mage.game.match.MatchType;

/**
 * @author xico7
 */
public class MentalMagicSameTypeFreeForAllType extends MatchType {

    public MentalMagicSameTypeFreeForAllType() {
        this.name = "Mental Magic (Same Type) Free For All";
        this.maxPlayers = 10;
        this.minPlayers = 3;
        this.numTeams = 0;
        this.useAttackOption = true;
        this.useRange = true;
        this.sideboardingAllowed = true;
    }

    protected MentalMagicSameTypeFreeForAllType(final MentalMagicSameTypeFreeForAllType matchType) {
        super(matchType);
    }

    @Override
    public MentalMagicSameTypeFreeForAllType copy() {
        return new MentalMagicSameTypeFreeForAllType(this);
    }

}
