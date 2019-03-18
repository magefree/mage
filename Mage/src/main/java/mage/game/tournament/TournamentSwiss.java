
package mage.game.tournament;

import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import mage.constants.MultiplayerAttackOption;

import mage.constants.TournamentPlayerState;
import mage.game.events.TableEvent;
import mage.game.tournament.pairing.RoundPairings;
import mage.game.tournament.pairing.SwissPairingMinimalWeightMatching;
import mage.game.tournament.pairing.SwissPairingSimple;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class TournamentSwiss extends TournamentImpl {

    public TournamentSwiss(TournamentOptions options) {
        super(options);
    }

    @Override
    protected void runTournament() {
        for (Entry<UUID, TournamentPlayer> entry : players.entrySet()) {
            if (entry.getValue().getPlayer().autoLoseGame()) {
                entry.getValue().setEliminated();
                entry.getValue().setResults("Auto Eliminated");
            }
        }

        if (options.matchOptions.getNumSeats() == 2) {
            while (this.getActivePlayers().size() > 1 && this.getNumberRounds() > this.getRounds().size()) {
                // check if some player got killed / disconnected meanwhile and update their state
                tableEventSource.fireTableEvent(TableEvent.EventType.CHECK_STATE_PLAYERS);
                // Swiss pairing
                Round round = createRoundSwiss();
                playRound(round);
            }
        } else {
            options.matchOptions.setAttackOption(MultiplayerAttackOption.MULTIPLE);
            MultiplayerRound round = createMultiplayerRound();
            playMultiplayerRound(round);
        }
        
        nextStep();
    }

    protected Round createRoundSwiss() {
        List<TournamentPlayer> roundPlayers = getActivePlayers();
        boolean isLastRound = (rounds.size() + 1 == getNumberRounds());

        Round round = null;
        if (options.matchOptions.getNumSeats() == 2) {
            RoundPairings roundPairings;
            if (roundPlayers.size() <= 16) {
                SwissPairingMinimalWeightMatching swissPairing = new SwissPairingMinimalWeightMatching(roundPlayers, rounds, isLastRound);
                roundPairings = swissPairing.getRoundPairings();
            } else {
                SwissPairingSimple swissPairing = new SwissPairingSimple(roundPlayers, rounds);
                roundPairings = swissPairing.getRoundPairings();
            }

            round = new Round(rounds.size() + 1, this);
            rounds.add(round);
            for (TournamentPairing pairing : roundPairings.getPairings()) {
                round.addPairing(pairing);
            }
            for (TournamentPlayer playerBye : roundPairings.getPlayerByes()) {
                // player free round - add to bye players of this round
                round.getPlayerByes().add(playerBye);
                if (isLastRound) {
                    playerBye.setState(TournamentPlayerState.FINISHED);
                } else {
                    playerBye.setState(TournamentPlayerState.WAITING);
                }
                playerBye.setStateInfo("Round Bye");
                updateResults();
            }
        }

        return round;
    }

    public MultiplayerRound createMultiplayerRound() {
        List<TournamentPlayer> roundPlayers = getActivePlayers();
        boolean isLastRound = (rounds.size() + 1 == getNumberRounds());

        MultiplayerRound round = null;
        if (options.matchOptions.getNumSeats() > 2) {
            options.matchOptions.setAttackOption(MultiplayerAttackOption.MULTIPLE);
            RoundPairings roundPairings;
            if (roundPlayers.size() <= 16) {
                SwissPairingMinimalWeightMatching swissPairing = new SwissPairingMinimalWeightMatching(roundPlayers, rounds, isLastRound);
                roundPairings = swissPairing.getRoundPairings();
            } else {
                SwissPairingSimple swissPairing = new SwissPairingSimple(roundPlayers, rounds);
                roundPairings = swissPairing.getRoundPairings();
            }

            round = new MultiplayerRound(rounds.size() + 1, this, options.matchOptions.getNumSeats());
            for (TournamentPairing pairing : roundPairings.getPairings()) {
                round.addPairing(pairing);
            }
            
        }
        return round;

    }
}
