

package mage.tournament;

import mage.game.tournament.TournamentType;

/**
 *
 * @author spjspj
 */
public class RichManCubeDraftEliminationTournamentType extends TournamentType {

    public RichManCubeDraftEliminationTournamentType() {
        this.name = "Booster Draft Elimination (Rich Man Cube)";
        this.maxPlayers = 16;
        this.minPlayers = 2;
        this.numBoosters = 1;
        this.draft = true;
        this.limited = true;
        this.cubeBooster = true;
        this.elimination = true;
    }

}
