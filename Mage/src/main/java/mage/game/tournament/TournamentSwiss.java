/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.game.tournament;

import java.util.List;
import java.util.Map;
import java.util.UUID;

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
        for (Map.Entry<UUID, TournamentPlayer> entry : players.entrySet()) {
            if (entry.getValue().getPlayer().autoLoseGame()) {
                entry.getValue().setEliminated();
                entry.getValue().setResults("Auto Eliminated");
            }
        }

        while (this.getActivePlayers().size() > 1 && this.getNumberRounds() > this.getRounds().size()) {
            // check if some player got killed / disconnected meanwhile and update their state
            tableEventSource.fireTableEvent(TableEvent.EventType.CHECK_STATE_PLAYERS);
            // Swiss pairing
            Round round = createRoundSwiss();
            playRound(round);
        }
        nextStep();
    }

    protected Round createRoundSwiss() {
        List<TournamentPlayer> roundPlayers = getActivePlayers();
        boolean isLastRound = (rounds.size() + 1 == getNumberRounds());

        RoundPairings roundPairings;
        if (roundPlayers.size() <= 16) {
            SwissPairingMinimalWeightMatching swissPairing = new SwissPairingMinimalWeightMatching(roundPlayers, rounds, isLastRound);
            roundPairings = swissPairing.getRoundPairings();
        } else {
            SwissPairingSimple swissPairing = new SwissPairingSimple(roundPlayers, rounds);
            roundPairings = swissPairing.getRoundPairings();
        }

        Round round = new Round(rounds.size() + 1, this);
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
