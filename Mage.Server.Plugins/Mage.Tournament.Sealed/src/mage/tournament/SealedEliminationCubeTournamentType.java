

package mage.tournament;

import mage.game.tournament.TournamentType;

/**
 *
 * @author LevelX2
 */
public class SealedEliminationCubeTournamentType extends TournamentType {

    public SealedEliminationCubeTournamentType() {
        this.name = "Sealed Elimination (Cube)";
        this.maxPlayers = 16;
        this.minPlayers = 2;
        this.numBoosters = 6;
        this.draft = false;
        this.limited = true;
        this.cubeBooster = true;
        this.elimination = true;
    }

}
