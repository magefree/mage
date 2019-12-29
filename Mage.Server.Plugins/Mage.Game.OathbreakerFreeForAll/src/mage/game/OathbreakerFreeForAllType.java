package mage.game;

import mage.game.match.MatchType;


/**
 * @author JayDi85
 */
public class OathbreakerFreeForAllType extends MatchType {

    public OathbreakerFreeForAllType() {
        this.name = "Oathbreaker Free For All";
        this.maxPlayers = 10;
        this.minPlayers = 3;
        this.numTeams = 0;
        this.useAttackOption = true;
        this.useRange = true;
        this.sideboardingAllowed = false;
    }

    protected OathbreakerFreeForAllType(final OathbreakerFreeForAllType matchType) {
        super(matchType);
    }

    @Override
    public OathbreakerFreeForAllType copy() {
        return new OathbreakerFreeForAllType(this);
    }
}
