package mage.tournament;

import mage.game.tournament.TournamentType;

/**
 *
 * @author glee-
 */
public class RichManCubeDraftSwissTournamentType extends TournamentType {

    public RichManCubeDraftSwissTournamentType() {
        this.name = "Booster Draft Swiss (Rich Man Cube)";
        this.maxPlayers = 16;
        this.minPlayers = 2;
        this.numBoosters = 1;
        this.draft = true;
        this.limited = true;
        this.cubeBooster = true;
        this.elimination = false;
    }

}
