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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import mage.constants.PhaseStep;
import mage.constants.TurnPhase;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.util.ThreadLocalStringBuilder;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Turn implements Serializable {

    private static final transient ThreadLocalStringBuilder threadLocalBuilder = new ThreadLocalStringBuilder(50);

    private Phase currentPhase;
    private UUID activePlayerId;
    private final List<Phase> phases = new ArrayList<>();
    private boolean declareAttackersStepStarted = false;
    private boolean endTurn; // indicates that an end turn effect has resolved.
    
    public Turn() {
        endTurn = false;
        phases.add(new BeginningPhase());
        phases.add(new PreCombatMainPhase());
        phases.add(new CombatPhase());
        phases.add(new PostCombatMainPhase());
        phases.add(new EndPhase());
    }

    public Turn(final Turn turn) {
        if (turn.currentPhase != null) {
            this.currentPhase = turn.currentPhase.copy();
        }
        this.activePlayerId = turn.activePlayerId;
        for (Phase phase: turn.phases) {
            this.phases.add(phase.copy());
        }
        this.declareAttackersStepStarted = turn.declareAttackersStepStarted;
        this.endTurn = turn.endTurn;

    }

    public TurnPhase getPhaseType() {
        if (currentPhase != null) {
            return currentPhase.getType();
        }
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
        if (currentPhase != null) {
            return currentPhase.getStep();
        }
        return null;
    }

    public PhaseStep getStepType() {
        if (currentPhase != null && currentPhase.getStep() != null) {
            return currentPhase.getStep().getType();
        }
        return null;
    }

    public void play(Game game, UUID activePlayerId) {
        this.setDeclareAttackersStepStarted(false);
        if (game.isPaused() || game.gameOver(null)) {
            return;
        }

        if (game.getState().getTurnMods().skipTurn(activePlayerId)) {
            return;
        }

        checkTurnIsControlledByOtherPlayer(game, activePlayerId);

        this.activePlayerId = activePlayerId;
        resetCounts();
        game.getPlayer(activePlayerId).beginTurn(game);
        for (Phase phase: phases) {
            if (game.isPaused() || game.gameOver(null)) {
                return;
            }
            if (!isEndTurnRequested() || phase.getType().equals(TurnPhase.END)) {
                currentPhase = phase;
                game.fireEvent(new GameEvent(GameEvent.EventType.PHASE_CHANGED, activePlayerId, null, activePlayerId));
                if (!game.getState().getTurnMods().skipPhase(activePlayerId, currentPhase.getType())) {
                    if (phase.play(game, activePlayerId)) {
                        //20091005 - 500.4/703.4n
                        game.emptyManaPools();                        
                        game.saveState(false);

                        //20091005 - 500.8
                        while (playExtraPhases(game, phase.getType())) {
                        }
                    }
                }
            }
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
            //game.saveState();
            //20091005 - 500.8
            playExtraPhases(game, phase.getType());
        }
        while (it.hasNext()) {
            phase = it.next();
            if (game.isPaused() || game.gameOver(null)) {
                return;
            }
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
            if (!currentPhase.equals(phase)) { // phase was changed from the card
                game.fireEvent(new GameEvent(GameEvent.EventType.PHASE_CHANGED, activePlayerId, null, activePlayerId));
                break;
            }
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

    private boolean playExtraPhases(Game game, TurnPhase afterPhase) {
        TurnMod extraPhaseTurnMod = game.getState().getTurnMods().extraPhase(activePlayerId, afterPhase);
        if (extraPhaseTurnMod == null) {
            return false;
        }
        TurnPhase extraPhase = extraPhaseTurnMod.getExtraPhase();
        if (extraPhase == null) {
            return false;
        }
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
        game.fireEvent(new GameEvent(GameEvent.EventType.PHASE_CHANGED, activePlayerId, extraPhaseTurnMod.getId(), activePlayerId));
        Player activePlayer = game.getPlayer(activePlayerId);
        if (activePlayer != null && !game.isSimulation()) {
            game.informPlayers(activePlayer.getLogName() + " starts an additional " + phase.getType().toString() + " phase");
        }
        phase.play(game, activePlayerId);

        return true;
    }

    /*protected void playExtraTurns(Game game) {
        while (game.getState().getTurnMods().extraTurn(activePlayerId)) {
            this.play(game, activePlayerId);
        }
    }*/
/**
 * Used for some spells with end turn effect (e.g. Time Stop).
 *
 * @param game
 * @param activePlayerId
 */
    public void endTurn(Game game, UUID activePlayerId) {
        // Ending the turn this way (Time Stop) means the following things happen in order: 
       
        setEndTurnRequested(true);
        
        // 1) All spells and abilities on the stack are exiled. This includes Time Stop, though it will continue to resolve. 
        // It also includes spells and abilities that can't be countered. 
        while (!game.getStack().isEmpty()) {
            StackObject stackObject = game.getStack().removeLast();
            if (stackObject instanceof Spell) {
                ((Spell) stackObject).moveToExile(null, "", null, game);
            }
        }
        // 2) All attacking and blocking creatures are removed from combat. 
        for (UUID attackerId: game.getCombat().getAttackers()) {
            Permanent permanent = game.getPermanent(attackerId);
            if (permanent != null) {
                permanent.removeFromCombat(game);
            }
            game.getCombat().removeAttacker(attackerId, game);
        }
        for (UUID blockerId: game.getCombat().getBlockers()) {
            Permanent permanent = game.getPermanent(blockerId);
            if (permanent != null) {
                permanent.removeFromCombat(game);
            }
        }
        
        // 3) State-based actions are checked. No player gets priority, and no triggered abilities are put onto the stack.         
        
        game.checkStateAndTriggered(); // triggered effects don't go to stack because check of endTurnRequested
        
        // 4) The current phase and/or step ends. 
        // The game skips straight to the cleanup step. The cleanup step happens in its entirety.
        // this is caused by the endTurnRequest state
        
    }

    public boolean isDeclareAttackersStepStarted() {
        return declareAttackersStepStarted;
    }

    public void setDeclareAttackersStepStarted(boolean declareAttackersStepStarted) {
        this.declareAttackersStepStarted = declareAttackersStepStarted;
    }

    public void setEndTurnRequested(boolean endTurn) {
        this.endTurn = endTurn;
    }
    
    public boolean isEndTurnRequested() {
        return endTurn;
    }
    
    public Turn copy() {
        return new Turn(this);
    }
    
    public String getValue(int turnNum) {
        StringBuilder sb = threadLocalBuilder.get();
        sb.append("[").append(turnNum)
            .append(":").append(currentPhase.getType())
            .append(":").append(currentPhase.getStep().getType())
            .append("]");

        return sb.toString();
    }
    
}
