package mage.tournament;

import mage.game.tournament.TournamentType;

public class JumpstartCustomEliminationTournamentType extends TournamentType {

    public JumpstartCustomEliminationTournamentType() {
        this.name = "Jumpstart Elimination (Custom)";
        this.maxPlayers = 16;
        this.minPlayers = 2;
        this.numBoosters = 0;
        this.isJumpstart = true;
        this.elimination = true;
        this.limited = true;
    }

}
