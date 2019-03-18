

package mage.tournament;

import mage.game.tournament.TournamentType;

/**
 *
 * @author LevelX2
 */
public class BoosterDraftEliminationCubeTournamentType extends TournamentType {

    public BoosterDraftEliminationCubeTournamentType() {
        this.name = "Booster Draft Elimination (Cube)";
        this.maxPlayers = 16;
        this.minPlayers = 4;
        this.numBoosters = 3;
        this.draft = true;
        this.limited = true;
        this.cubeBooster = true;
        this.elimination = true;
    }

}