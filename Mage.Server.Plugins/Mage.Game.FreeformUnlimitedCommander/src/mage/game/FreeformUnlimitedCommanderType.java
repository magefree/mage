package mage.game;

import mage.game.match.MatchType;

public class FreeformUnlimitedCommanderType extends MatchType {

    public FreeformUnlimitedCommanderType() {
        this.name = "Freeform Unlimited Commander";
        this.maxPlayers = 10;
        this.minPlayers = 2;
        this.numTeams = 0;
        this.playersPerTeam = 0;
        this.useAttackOption = true;
        this.useRange = true;
        this.sideboardingAllowed = true;
    }

    protected FreeformUnlimitedCommanderType(final FreeformUnlimitedCommanderType matchType) {
        super(matchType);
    }

    @Override
    public FreeformUnlimitedCommanderType copy() {
        return new FreeformUnlimitedCommanderType(this);
    }
}
