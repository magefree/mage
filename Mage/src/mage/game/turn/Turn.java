/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.game.turn;

import mage.Constants.PhaseStep;
import mage.Constants.TurnPhase;
import mage.game.Game;
import mage.players.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Turn implements Serializable {

    private Phase currentPhase;
    private UUID activePlayerId;
    private List<Phase> phases = new ArrayList<Phase>();

    public Turn() {
        phases.add(new BeginningPhase());
        phases.add(new PreCombatMainPhase());
        phases.add(new CombatPhase());
        phases.add(new PostCombatMainPhase());
        phases.add(new EndPhase());
    }

    public Turn(final Turn turn) {
        if (turn.currentPhase != null)
            this.currentPhase = turn.currentPhase.copy();
        this.activePlayerId = turn.activePlayerId;
        for (Phase phase: turn.phases) {
            this.phases.add(phase.copy());
        }

    }

    public TurnPhase getPhaseType() {
        if (currentPhase != null)
            return currentPhase.getType();
        return null;
    }

    public Phase getPhase() {
        return currentPhase;
    }

    public Phase getPhase(TurnPhase turnPhase) {
        for (Phase phase: phases) {
            if (phase.getType() == turnPhase) {
                return phase;
            }
        }
        return null;
    }

    public void setPhase(Phase phase) {
        this.currentPhase = phase;
    }

    public Step getStep() {
        if (currentPhase != null)
            return currentPhase.getStep();
        return null;
    }

    public PhaseStep getStepType() {
        if (currentPhase != null && currentPhase.getStep() != null)
            return currentPhase.getStep().getType();
        return null;
    }

    public void play(Game game, UUID activePlayerId) {
        if (game.isPaused() || game.isGameOver())
            return;

        if (game.getState().getTurnMods().skipTurn(activePlayerId))
            return;

        checkTurnIsControlledByOtherPlayer(game, activePlayerId);

        this.activePlayerId = activePlayerId;
        resetCounts();
        game.getPlayer(activePlayerId).beginTurn(game);
        for (Phase phase: phases) {
            if (game.isPaused() || game.isGameOver())
                return;
            currentPhase = phase;
            if (!game.getState().getTurnMods().skipPhase(activePlayerId, currentPhase.getType())) {
                if (phase.play(game, activePlayerId)) {
                    //20091005 - 500.4/703.4n
                    game.emptyManaPools();

                    //game.saveState();

                    //20091005 - 500.8
                    playExtraPhases(game, phase.getType());
                }
            }
            if (!currentPhase.equals(phase)) // phase was changed from the card
                break;
        }

    }

    public void resumePlay(Game game, boolean wasPaused) {
        activePlayerId = game.getActivePlayerId();
        UUID priorityPlayerId = game.getPriorityPlayerId();
        TurnPhase phaseType = game.getPhase().getType();
        PhaseStep stepType = game.getStep().getType();

        Iterator<Phase> it = phases.iterator();
        Phase phase;
        do {
            phase = it.next();
            currentPhase = phase;
        } while (phase.type != phaseType);
        if (phase.resumePlay(game, stepType, wasPaused)) {
            //20091005 - 500.4/703.4n
            game.emptyManaPools();
            game.saveState();
            //20091005 - 500.8
            playExtraPhases(game, phase.getType());
        }
        while (it.hasNext()) {
            phase = it.next();
            if (game.isPaused() || game.isGameOver())
                return;
            currentPhase = phase;
            if (!game.getState().getTurnMods().skipPhase(activePlayerId, currentPhase.getType())) {
                if (phase.play(game, activePlayerId)) {
                    //20091005 - 500.4/703.4n
                    game.emptyManaPools();
                    game.saveState();
                    //20091005 - 500.8
                    playExtraPhases(game, phase.getType());
                }
            }
            if (!currentPhase.equals(phase)) // phase was changed from the card
                break;
        }
    }

    private void checkTurnIsControlledByOtherPlayer(Game game, UUID activePlayerId) {
        UUID newControllerId = game.getState().getTurnMods().controlsTurn(activePlayerId);
        if (newControllerId != null && !newControllerId.equals(activePlayerId)) {
            game.getPlayer(newControllerId).controlPlayersTurn(game, activePlayerId);
        }
    }

    private void resetCounts() {
        for (Phase phase: phases) {
            phase.resetCount();
        }
    }

    private void playExtraPhases(Game game, TurnPhase afterPhase) {
        TurnPhase extraPhase = game.getState().getTurnMods().extraPhase(activePlayerId, afterPhase);
        if (extraPhase == null)
            return;
        Phase phase;
        switch(extraPhase) {
            case BEGINNING:
                phase = new BeginningPhase();
                break;
            case PRECOMBAT_MAIN:
                phase = new PreCombatMainPhase();
                break;
            case COMBAT:
                phase = new CombatPhase();
                break;
            case POSTCOMBAT_MAIN:
                phase = new PostCombatMainPhase();
                break;
            default:
                phase = new EndPhase();
        }
        currentPhase = phase;
        phase.play(game, activePlayerId);
    }

    /*protected void playExtraTurns(Game game) {
        while (game.getState().getTurnMods().extraTurn(activePlayerId)) {
            this.play(game, activePlayerId);
        }
    }*/

    public void endTurn(Game game, UUID activePlayerId) {
        // Exile all spells and abilities on the stack
        game.getStack().clear();

        // Discard down to your maximum hand size.
        Player activePlayer = game.getPlayer(activePlayerId);
        game.getState().setPriorityPlayerId(activePlayer.getId());
        //20091005 - 514.1
        if (!activePlayer.hasLeft() && !activePlayer.hasLost()) {
            activePlayer.discardToMax(game);
            activePlayer.setGameUnderYourControl(true);
        }

        // Damage wears off.
        //20100423 - 514.2
        game.getBattlefield().endOfTurn(activePlayerId, game);
        game.getState().removeEotEffects(game);

        Phase phase = new EndPhase();
        phase.setStep(new CleanupStep());
        currentPhase = phase;
        //phase.play(game, activePlayerId);
    }

    public Turn copy() {
        return new Turn(this);
    }
}
