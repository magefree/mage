

package mage.player.ai;

import mage.constants.RangeOfInfluence;
import mage.game.Game;
import mage.game.Table;
import mage.game.tournament.TournamentType;
import mage.players.Player;

/**
 *
 * @author nantuko
 */
public class ComputerDraftPlayer extends ComputerPlayer implements Player {

    public ComputerDraftPlayer(String name, RangeOfInfluence range) {
        super(name, range);
    }

    public ComputerDraftPlayer(String name, RangeOfInfluence range, int skill) {
        super(name, range);
    }

    public ComputerDraftPlayer(final ComputerDraftPlayer player) {
        super(player);
    }

    @Override
    public ComputerDraftPlayer copy() {
        return new ComputerDraftPlayer(this);
    }

    @Override
    public boolean autoLoseGame() {
        return true;
    }

    @Override
    public boolean priority(Game game) {
        game.concede(playerId);
        return true;
    }

    @Override
    public boolean canJoinTable(Table table) {
        if (table.isTournament()) {
            TournamentType tournamentType = table.getTournament().getTournamentType();
            if(tournamentType != null && tournamentType.isDraft()) {
                return true;
            }
        }
        return false;
    }
}
