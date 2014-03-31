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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mage.constants.TournamentPlayerState;
import mage.game.events.TableEvent;

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
        for (Map.Entry<UUID, TournamentPlayer> entry: players.entrySet()) {
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
        Round round = new Round(rounds.size() + 1, this);
        rounds.add(round);
        List<TournamentPlayer> roundPlayers = getActivePlayers();
        // sort players by tournament points
        Collections.sort(roundPlayers, new Comparator<TournamentPlayer>() {
            @Override public int compare(TournamentPlayer p1, TournamentPlayer p2) {
                return  p2.getPoints() - p1.getPoints();
            }

        });
        // create pairings
        while (roundPlayers.size() > 0) {
            TournamentPlayer player1 = roundPlayers.get(0);
            roundPlayers.remove(0);
            TournamentPlayer playerForPossibleSecondPairing = null;
            for (TournamentPlayer player2: roundPlayers) {
                if (alreadyPaired(player1, player2)) {
                    // if laready paired but equal ponts -> remember if second pairing is needed
                    if (playerForPossibleSecondPairing == null) {
                        playerForPossibleSecondPairing = player2;
                    }
                } else {
                    if (player2.getPoints() < player1.getPoints() && playerForPossibleSecondPairing != null) {
                        // pair again with a player
                        round.addPairing(new TournamentPairing(player1, playerForPossibleSecondPairing));
                        roundPlayers.remove(playerForPossibleSecondPairing);
                        player1 = null;
                        break;
                    } else {
                        // pair agains the next not paired before
                        round.addPairing(new TournamentPairing(player1, player2));
                        roundPlayers.remove(player2);
                        player1 = null;
                        break;
                    }
                }
            }
            if (player1 != null) {
                // no pairing done yet
                if (playerForPossibleSecondPairing != null) {
                    // pair again with a player
                    round.addPairing(new TournamentPairing(player1, playerForPossibleSecondPairing));
                    roundPlayers.remove(playerForPossibleSecondPairing);
                } else {
                    // player free round - add to bye players of this round
                    round.getPlayerByes().add(player1);
                    player1.setState(TournamentPlayerState.WAITING);
                    player1.setStateInfo("Round Bye");
                }
            }
        }
        return round;
    }

    protected boolean alreadyPaired(TournamentPlayer player1, TournamentPlayer player2) {
        for (Round round : rounds) {
            for (TournamentPairing pairing: round.getPairs()) {
                if (pairing.getPlayer1().equals(player1) || pairing.getPlayer2().equals(player1)) {
                    if (pairing.getPlayer1().equals(player2) || pairing.getPlayer2().equals(player2)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
