
package mage.tournament;

import mage.game.tournament.TournamentType;

/**
 *
 * @author spjspj
 */
public class RichManDraftEliminationTournamentType extends TournamentType {

    public RichManDraftEliminationTournamentType() {
        this.name = "Booster Draft Elimination (Rich Man)";
        this.maxPlayers = 16;
        this.minPlayers = 2;
        this.numBoosters = 1;
        this.draft = true;
        this.limited = true;
        this.cubeBooster = false;
        this.elimination = true;
        this.isRandom = false;
        this.isRichMan = true;
    }

} 
