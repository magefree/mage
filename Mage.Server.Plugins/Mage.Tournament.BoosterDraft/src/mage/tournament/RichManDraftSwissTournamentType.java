package mage.tournament;

import mage.game.tournament.TournamentType;

/**
 *
 * @author glee-
 */
public class RichManDraftSwissTournamentType extends TournamentType {

    public RichManDraftSwissTournamentType() {
        this.name = "Booster Draft Swiss (Rich Man)";
        this.maxPlayers = 16;
        this.minPlayers = 2;
        this.numBoosters = 1;
        this.draft = true;
        this.limited = true;
        this.cubeBooster = false;
        this.elimination = false;
        this.isRandom = false;
        this.isRichMan = true;
    }

}
