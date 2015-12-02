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
package mage.abilities.common.delayed;

import mage.constants.TargetController;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class AtTheBeginOfMainPhaseDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public enum PhaseSelection {

        NEXT_PRECOMBAT_MAIN("next precombat"),
        NEXT_POSTCOMAT_MAIN("next postcombat"),
        NEXT_MAIN("next");

        private final String text;

        PhaseSelection(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    private final TargetController targetController;
    private final PhaseSelection phaseSelection;


    public AtTheBeginOfMainPhaseDelayedTriggeredAbility(Effect effect, boolean optional, TargetController targetController, PhaseSelection phaseSelection) {
        super(effect, Duration.EndOfGame, true, optional);
        this.targetController = targetController;
        this.phaseSelection = phaseSelection;

    }

    public AtTheBeginOfMainPhaseDelayedTriggeredAbility(final AtTheBeginOfMainPhaseDelayedTriggeredAbility ability) {
        super(ability);
        this.targetController = ability.targetController;
        this.phaseSelection = ability.phaseSelection;
    }

    @Override
    public AtTheBeginOfMainPhaseDelayedTriggeredAbility copy() {
        return new AtTheBeginOfMainPhaseDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return checkPhase(event.getType());
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (targetController) {
            case ANY:
                return true;
            case YOU:
                boolean yours = event.getPlayerId().equals(this.controllerId);
                return yours;
            case OPPONENT:
                if (game.getPlayer(this.getControllerId()).hasOpponent(event.getPlayerId(), game)) {
                    return true;
                }
                break;

            case CONTROLLER_ATTACHED_TO:
                Permanent attachment = game.getPermanent(sourceId);
                if (attachment != null && attachment.getAttachedTo() != null) {
                    Permanent attachedTo = game.getPermanent(attachment.getAttachedTo());
                    if (attachedTo != null && attachedTo.getControllerId().equals(event.getPlayerId())) {
                        return true;
                    }
                }
        }
        return false;
    }

    private boolean checkPhase(EventType eventType) {
        switch (phaseSelection) {
            case NEXT_MAIN:
                return EventType.PRECOMBAT_MAIN_PHASE_PRE.equals(eventType) || EventType.POSTCOMBAT_MAIN_PHASE_PRE.equals(eventType);
            case NEXT_POSTCOMAT_MAIN:
                return EventType.POSTCOMBAT_MAIN_PHASE_PRE.equals(eventType);
            case NEXT_PRECOMBAT_MAIN:
                return EventType.PRECOMBAT_MAIN_PHASE_PRE.equals(eventType);
            default:
                return false;
        }
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder();
        switch (targetController) {
            case YOU:
                sb.append("At the beginning of your ").append(phaseSelection.toString()).append(" main phase, ");
                break;
            case OPPONENT:
                sb.append("At the beginning of an opponent's ").append(phaseSelection.toString()).append(" main phase, ");
                break;
            case ANY:
                sb.append("At the beginning of the ").append(phaseSelection.toString()).append(" main phase, ");
                break;
            case CONTROLLER_ATTACHED_TO:
                sb.append("At the beginning of the ").append(phaseSelection.toString()).append(" main phase of enchanted creature's controller, ");
                break;
        }
        sb.append(getEffects().getText(modes.getMode()));
        return sb.toString();
    }
}
