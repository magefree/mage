

package mage.game.tournament;

import java.util.Map;
import java.util.UUID;
import mage.constants.MultiplayerAttackOption;
import mage.game.events.TableEvent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class TournamentSingleElimination extends TournamentImpl {

    public TournamentSingleElimination(TournamentOptions options) {
        super(options);
    }

    @Override
    protected void runTournament() {
        for (Map.Entry<UUID, TournamentPlayer> entry: players.entrySet()) {
            if (entry.getValue().getPlayer().autoLoseGame()) {
                entry.getValue().setEliminated();
                entry.getValue().setResults("Auto Eliminated");
            }
        }        
        if (options.matchOptions.getNumSeats() == 2) {
            while (this.getActivePlayers().size() > 1) {
                // check if some player got killed / disconnected meanwhile and update their state
                tableEventSource.fireTableEvent(TableEvent.EventType.CHECK_STATE_PLAYERS);
                Round round = createRoundRandom();
                playRound(round);
                eliminatePlayers(round);
            }        
        } else {
            options.matchOptions.setAttackOption(MultiplayerAttackOption.MULTIPLE);
            MultiplayerRound round = new MultiplayerRound(0, this, options.matchOptions.getNumSeats());
            for (TournamentPlayer player : getActivePlayers()) {
                round.addPlayer(player);
            }
            playMultiplayerRound(round);
        }
        
        nextStep();
    }
    
    private void eliminatePlayers(Round round) {
        for (TournamentPairing pair: round.getPairs()) {
            pair.eliminatePlayers();
        }
    }



}
