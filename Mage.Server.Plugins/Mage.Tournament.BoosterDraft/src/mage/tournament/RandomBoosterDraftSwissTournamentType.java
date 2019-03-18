

package mage.tournament;

import mage.game.tournament.TournamentType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class RandomBoosterDraftSwissTournamentType extends TournamentType {

    public RandomBoosterDraftSwissTournamentType() {
        this.name = "Booster Draft Swiss (Random)";
        this.maxPlayers = 16;
        this.minPlayers = 4;
        this.numBoosters = 3;
        this.draft = true;
        this.limited = true;
        this.cubeBooster = false;
        this.elimination = false;
        this.isRandom = true;
    }

}
