
package mage.tournament;

import mage.game.tournament.TournamentType;

/**
 *
 * @author LevelX2
 */

public class ConstructedSwissTournamentType extends TournamentType {

    public ConstructedSwissTournamentType() {
        this.name = "Constructed Swiss";
        this.maxPlayers = 16;
        this.minPlayers = 4;
        this.numBoosters = 0;
        this.draft = false;
        this.limited = false;
        this.elimination = false;
    }
}