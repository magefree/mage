package mage.game;

import mage.game.match.MatchType;

/**
 * @author Susucr
 */
public class PillarOfTheParunsDuelType extends MatchType {

    public PillarOfTheParunsDuelType() {
        this.name = "Freeform Pillar of the Paruns Two Player Duel";
        this.maxPlayers = 2;
        this.minPlayers = 2;
        this.numTeams = 0;
        this.useAttackOption = false;
        this.useRange = false;
        this.sideboardingAllowed = true;
    }

    protected PillarOfTheParunsDuelType(final PillarOfTheParunsDuelType matchType) {
        super(matchType);
    }

    @Override
    public PillarOfTheParunsDuelType copy() {
        return new PillarOfTheParunsDuelType(this);
    }

}
