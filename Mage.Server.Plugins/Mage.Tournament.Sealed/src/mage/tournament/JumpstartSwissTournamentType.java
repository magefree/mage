

package mage.tournament;

import mage.game.tournament.TournamentType;

public class JumpstartSwissTournamentType extends TournamentType {

    public JumpstartSwissTournamentType() {
        this.name = "Jumpstart Swiss";
        this.maxPlayers = 16;
        this.minPlayers = 2;
        this.numBoosters = 0;
        this.isJumpstart = true;
        this.limited = true;
    }

}
