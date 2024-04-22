package mage.game;

import mage.game.match.MatchType;

/**
 * @author Susucr
 */
public class CustomPillarOfTheParunsDuelType extends MatchType {

    public CustomPillarOfTheParunsDuelType() {
        this.name = "Custom Pillar of the Paruns Two Player Duel";
        this.maxPlayers = 2;
        this.minPlayers = 2;
        this.numTeams = 0;
        this.useAttackOption = false;
        this.useRange = false;
        this.sideboardingAllowed = true;
    }

    protected CustomPillarOfTheParunsDuelType(final CustomPillarOfTheParunsDuelType matchType) {
        super(matchType);
    }

    @Override
    public CustomPillarOfTheParunsDuelType copy() {
        return new CustomPillarOfTheParunsDuelType(this);
    }

}
