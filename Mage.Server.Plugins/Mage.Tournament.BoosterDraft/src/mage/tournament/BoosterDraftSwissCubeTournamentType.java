

package mage.tournament;

import mage.game.tournament.TournamentType;

/**
 *
 * @author LevelX2
 */
public class BoosterDraftSwissCubeTournamentType extends TournamentType {

    public BoosterDraftSwissCubeTournamentType() {
        this.name = "Booster Draft Swiss (Cube)";
        this.maxPlayers = 16;
        this.minPlayers = 4;
        this.numBoosters = 3;
        this.draft = true;
        this.limited = true;
        this.cubeBooster = true;
        this.elimination = false;
    }

}