/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
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

package mage.game.turn;

import java.io.Serializable;
import java.util.UUID;
import mage.constants.PhaseStep;
import mage.constants.TurnPhase;


/**
 * stores extra turns, phases or steps
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TurnMod implements Serializable {

    private final UUID id;
    private final UUID playerId;
    private UUID newControllerId;
    private boolean extraTurn;
    private boolean skipTurn;
    private TurnPhase extraPhase;
    private TurnPhase skipPhase;
    private Step extraStep;
    private PhaseStep skipStep;
    private TurnPhase afterPhase;
    private PhaseStep afterStep;

    // Turn mod that should be applied after current turn mod.
    // Implemented only for control player turn mod!
    // Added for Emrakul, the Promised End.
    private TurnMod subsequentTurnMod;

    /**
     * Used to define if a player skips the next turn or gets an extra turn.
     *
     * @param playerId
     * @param skip - true = skips next turn, false = player gets extra turn
     */
    public TurnMod(UUID playerId, boolean skip) {
        this.id = UUID.randomUUID();
        this.playerId = playerId;
        if (skip) {
            this.skipTurn = true;
        }
        else {
            this.extraTurn = true;
        }
    }

    /**
     * Used to define that a player controlls the next turn of another player.
     *
     * @param playerId - id of the player whose next turn is controlled by newControllerId
     * @param newControllerId - id of the player that controlls playerId's next turn
     */
    public TurnMod(UUID playerId, UUID newControllerId) {
        this.id = UUID.randomUUID();
        this.playerId = playerId;
        this.newControllerId = newControllerId;
    }

    /**
     * Used to define if and when a player gets an extra phase.
     *
     * @param playerId
     * @param phase
     * @param afterPhase - set to null if extraPhase is after the next phase
     * @param skip
     */
    public TurnMod(UUID playerId, TurnPhase phase, TurnPhase afterPhase, boolean skip) {
        this.id = UUID.randomUUID();
        this.playerId = playerId;
        if (skip) {
            this.skipPhase = phase;
        }
        else {
            this.extraPhase = phase;
        }
        this.afterPhase = afterPhase;
    }

    /**
     * Used to define if and when a player gets an extra step.
     *
     * @param playerId
     * @param step - extra step the player gets
     * @param afterStep - set to null if extraStep is after the next step
     */
    public TurnMod(UUID playerId, Step step, PhaseStep afterStep) {
        this.id = UUID.randomUUID();
        this.playerId = playerId;
        this.extraStep = step;
        this.afterStep = afterStep;
    }

    /**
     * Used to define that a player skips the next time the specified step
     *
     * @param playerId
     * @param step - step to skip the next time
     */
    public TurnMod(UUID playerId, PhaseStep step) {
        this.id = UUID.randomUUID();
        this.playerId = playerId;
        this.skipStep = step;
    }

    public TurnMod(final TurnMod mod) {
        this.id = mod.id;
        this.playerId = mod.playerId;
        this.newControllerId = mod.newControllerId;
        this.extraTurn = mod.extraTurn;
        this.skipTurn = mod.skipTurn;
        this.extraPhase = mod.extraPhase;
        this.skipPhase = mod.skipPhase;
        if (mod.extraStep != null) {
            this.extraStep = mod.extraStep.copy();
        }
        this.skipStep = mod.skipStep;
        this.afterPhase = mod.afterPhase;
        this.afterStep = mod.afterStep;
        if (mod.subsequentTurnMod != null) {
            this.subsequentTurnMod = mod.subsequentTurnMod.copy();
        }
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public boolean isExtraTurn() {
        return extraTurn;
    }

    public boolean isSkipTurn() {
        return skipTurn;
    }

    public TurnPhase getExtraPhase() {
        return extraPhase;
    }

    public Step getExtraStep() {
        return extraStep;
    }

    public TurnPhase getSkipPhase() {
        return skipPhase;
    }

    public PhaseStep getSkipStep() {
        return skipStep;
    }

    public TurnPhase getAfterPhase() {
        return afterPhase;
    }

    public PhaseStep getAfterStep() {
        return afterStep;
    }

    public UUID getNewControllerId() {
        return newControllerId;
    }

    public TurnMod copy() {
        return new TurnMod(this);
    }

    public UUID getId() {
        return id;
    }

    public TurnMod getSubsequentTurnMod() {
        return subsequentTurnMod;
    }

    public void setSubsequentTurnMod(TurnMod subsequentTurnMod) {
        this.subsequentTurnMod = subsequentTurnMod;
    }
}
