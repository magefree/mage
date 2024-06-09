

package mage.tournament;

import mage.game.tournament.TournamentType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class RemixedBoosterDraftEliminationTournamentType extends TournamentType {

    public RemixedBoosterDraftEliminationTournamentType() {
        this.name = "Booster Draft Elimination (Remixed)";
        this.maxPlayers = 16;
        this.minPlayers = 4;
        this.numBoosters = 3;
        this.draft = true;
        this.limited = true;
        this.cubeBooster = false;
        this.elimination = true;
        this.isRemixed = true;
    }

}
