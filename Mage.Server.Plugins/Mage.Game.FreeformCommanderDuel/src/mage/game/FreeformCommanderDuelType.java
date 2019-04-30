package mage.game;

import mage.game.match.MatchType;

/**
 * @author JayDi85
 */
public class FreeformCommanderDuelType extends MatchType {

    public FreeformCommanderDuelType() {
        this.name = "Freeform Commander Two Player Duel";
        this.maxPlayers = 2;
        this.minPlayers = 2;
        this.numTeams = 0;
        this.useAttackOption = false;
        this.useRange = false;
        this.sideboardingAllowed = false;
    }

    protected FreeformCommanderDuelType(final FreeformCommanderDuelType matchType) {
        super(matchType);
    }

    @Override
    public FreeformCommanderDuelType copy() {
        return new FreeformCommanderDuelType(this);
    }

}
