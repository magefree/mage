

package mage.game.tournament;

import mage.constants.MultiplayerAttackOption;
import mage.game.events.TableEvent;

import java.util.Map;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class TournamentSingleElimination extends TournamentImpl {

    public TournamentSingleElimination(TournamentOptions options) {
        super(options);
    }

    @Override
    protected void runTournament() {
        for (Map.Entry<UUID, TournamentPlayer> entry : players.entrySet()) {
            if (entry.getValue().getPlayer().autoLoseGame()) {
                entry.getValue().setEliminated();
                entry.getValue().setResults("Auto Eliminated");
            }
        }
        if (options.matchOptions.isSingleGameTourney()) {
            // one game with all players
            options.matchOptions.setAttackOption(MultiplayerAttackOption.MULTIPLE);
            MultiplayerRound round = new MultiplayerRound(0, this);
            for (TournamentPlayer player : getActivePlayers()) {
                round.addPlayer(player);
            }
            playMultiplayerRound(round);
        } else {
            // split to multiple rounds/games
            while (this.getActivePlayers().size() > 1) {
                // check if some player got killed / disconnected meanwhile and update their state
                tableEventSource.fireTableEvent(TableEvent.EventType.CHECK_STATE_PLAYERS);
                Round round = createRoundRandom();
                playRound(round);
                eliminatePlayers(round);
            }
        }

        nextStep();
    }

    private void eliminatePlayers(Round round) {
        for (TournamentPairing pair : round.getPairs()) {
            pair.eliminatePlayers();
        }
    }


}
