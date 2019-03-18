

package mage.tournament;

import mage.game.tournament.TournamentType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SealedSwissCubeTournamentType extends TournamentType {

    public SealedSwissCubeTournamentType() {
        this.name = "Sealed Swiss (Cube)";
        this.maxPlayers = 16;
        this.minPlayers = 2;
        this.numBoosters = 6;
        this.draft = false;
        this.limited = true;
        this.cubeBooster = true;
        this.elimination = false;
    }

}
