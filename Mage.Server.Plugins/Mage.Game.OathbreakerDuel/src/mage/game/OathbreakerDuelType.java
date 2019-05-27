package mage.game;

import mage.game.match.MatchType;

/**
 * @author JayDi85
 */
public class OathbreakerDuelType extends MatchType {

    public OathbreakerDuelType() {
        this.name = "Oathbreaker Two Player Duel";
        this.maxPlayers = 2;
        this.minPlayers = 2;
        this.numTeams = 0;
        this.useAttackOption = false;
        this.useRange = false;
        this.sideboardingAllowed = false;
    }

    protected OathbreakerDuelType(final OathbreakerDuelType matchType) {
        super(matchType);
    }

    @Override
    public OathbreakerDuelType copy() {
        return new OathbreakerDuelType(this);
    }

}
