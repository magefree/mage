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
package mage.abilities.effects.common;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DontUntapInControllersNextUntapStepTargetEffect extends ContinuousRuleModifyingEffectImpl {

    private int validForTurnNum;
    private String targetName;

    /**
     * Attention: This effect won't work with targets controlled by different
     * controllers If this is needed, the validForTurnNum has to be saved per
     * controller.
     *
     */
    public DontUntapInControllersNextUntapStepTargetEffect() {
        super(Duration.Custom, Outcome.Detriment, false, true);
    }

    public DontUntapInControllersNextUntapStepTargetEffect(String targetName) {
        this();
        this.targetName = targetName;
    }

    public DontUntapInControllersNextUntapStepTargetEffect(final DontUntapInControllersNextUntapStepTargetEffect effect) {
        super(effect);
        this.validForTurnNum = effect.validForTurnNum;
        this.targetName = effect.targetName;

    }

    @Override
    public DontUntapInControllersNextUntapStepTargetEffect copy() {
        return new DontUntapInControllersNextUntapStepTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source.getSourceId());
        Permanent permanentToUntap = game.getPermanent((event.getTargetId()));
        if (permanentToUntap != null && mageObject != null) {
            return permanentToUntap.getLogName() + " doesn't untap (" + mageObject.getLogName() + ")";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.UNTAP_STEP || event.getType() == EventType.UNTAP;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // the check for turn number is needed if multiple effects are added to prevent untap in next untap step of controller
        // if we don't check for turn number, every untap step of a turn only one effect would be used instead of correctly only one time
        // to skip the untap effect.

        // Discard effect if it's related to a previous turn
        if (validForTurnNum > 0 && validForTurnNum < game.getTurnNum()) {
            discard();
            return false;
        }
        // remember the turn of the untap step the effect has to be applied
        if (GameEvent.EventType.UNTAP_STEP.equals(event.getType())) {
            UUID controllerId = null;
            for (UUID targetId : getTargetPointer().getTargets(game, source)) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null) {
                    controllerId = permanent.getControllerId();
                }
            }
            if (controllerId == null) { // no more targets on the battlefield, effect can be discarded
                discard();
                return false;
            }

            if (game.getActivePlayerId().equals(controllerId)) {
                if (validForTurnNum == game.getTurnNum()) { // the turn has a second untap step but the effect is already related to the first untap step
                    discard();
                    return false;
                }
                validForTurnNum = game.getTurnNum();
            }
        }

        if (game.getTurn().getStepType() == PhaseStep.UNTAP && event.getType() == EventType.UNTAP) {
            if (targetPointer.getTargets(game, source).contains(event.getTargetId())) {
                Permanent permanent = game.getPermanent(event.getTargetId());
                if (permanent != null && game.getActivePlayerId().equals(permanent.getControllerId())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        if (targetName != null && targetName.length() > 0) {
            return targetName + " doesn't untap during its controller's next untap step";
        } else {
            return "Target " + (mode == null ? "creature" : mode.getTargets().get(0).getTargetName()) + " doesn't untap during its controller's next untap step";
        }
    }

}
