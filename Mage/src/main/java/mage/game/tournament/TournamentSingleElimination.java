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
