
package mage.game.tournament;

import mage.constants.MultiplayerAttackOption;
import mage.constants.TournamentPlayerState;
import mage.game.events.TableEvent;
import mage.game.tournament.pairing.RoundPairings;
import mage.game.tournament.pairing.SwissPairingMinimalWeightMatching;
import mage.game.tournament.pairing.SwissPairingSimple;

import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

/**
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

        if (options.matchOptions.isSingleGameTourney()) {
            // one game with all players (it's same as normal tourney's mode)
            options.matchOptions.setAttackOption(MultiplayerAttackOption.MULTIPLE);
            MultiplayerRound round = new MultiplayerRound(0, this);
            for (TournamentPlayer player : getActivePlayers()) {
                round.addPlayer(player);
            }
            playMultiplayerRound(round);
        } else {
            // split to multiple rounds/games
            while (this.getActivePlayers().size() > 1 && this.getNumberRounds() > this.getRounds().size()) {
                // check if some player got killed / disconnected meanwhile and update their state
                tableEventSource.fireTableEvent(TableEvent.EventType.CHECK_STATE_PLAYERS);
                Round round = createRoundSwiss();
                playRound(round);
            }
        }

        nextStep();
    }

    protected Round createRoundSwiss() {
        // multiple rounds, can be called multiple times per tourney

        List<TournamentPlayer> roundPlayers = getActivePlayers();
        boolean isLastRound = (rounds.size() + 1 == getNumberRounds());
        if (options.matchOptions.isSingleGameTourney()) {
            throw new IllegalStateException("Wrong code usage: multi rounds for non single game tourneys only (e.g. with two seats)");
        }

        Round round = null;

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

        return round;
    }
}
