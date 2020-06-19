

package mage.tournament;

import mage.game.tournament.TournamentType;

public class JumpstartEliminationTournamentType extends TournamentType {

    public JumpstartEliminationTournamentType() {
        this.name = "Jumpstart Elimination";
        this.maxPlayers = 16;
        this.minPlayers = 2;
        this.numBoosters = 0;
        this.isJumpstart = true;
        this.elimination = true;
        this.limited = true;
    }

}
