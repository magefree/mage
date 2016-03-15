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

import java.util.HashMap;
import java.util.Map;
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

    private UUID onlyIfControlledByPlayer;
    private String targetName;
    // holds the info what target was already handled in Untap of its controller
    private final Map<UUID, Boolean> handledTargetsDuringTurn = new HashMap<>();

    /**
     * Attention: This effect won't work with targets controlled by different
     * controllers If this is needed, the validForTurnNum has to be saved per
     * controller.
     *
     */
    public DontUntapInControllersNextUntapStepTargetEffect() {
        this("");
    }

    public DontUntapInControllersNextUntapStepTargetEffect(String targetName) {
        this(targetName, null);
    }

    /**
     *
     * @param targetName used as target text for the generated rule text
     * @param onlyIfControlledByPlayer the effect only works if the permanent is
     * controlled by that controller, null = it works for all players
     */
    public DontUntapInControllersNextUntapStepTargetEffect(String targetName, UUID onlyIfControlledByPlayer) {
        super(Duration.Custom, Outcome.Detriment, false, true);
        this.targetName = targetName;
        this.onlyIfControlledByPlayer = onlyIfControlledByPlayer;
    }

    public DontUntapInControllersNextUntapStepTargetEffect(final DontUntapInControllersNextUntapStepTargetEffect effect) {
        super(effect);
        this.targetName = effect.targetName;
        this.handledTargetsDuringTurn.putAll(effect.handledTargetsDuringTurn);
        this.onlyIfControlledByPlayer = effect.onlyIfControlledByPlayer;
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
        // the check if a permanent untap pahse is already handled is needed if multiple effects are added to prevent untap in next untap step of controller
        // if we don't check it for every untap step of a turn only one effect would be consumed instead of all be valid for the next untap step
        if (GameEvent.EventType.UNTAP_STEP.equals(event.getType())) {
            boolean allHandled = true;
            for (UUID targetId : getTargetPointer().getTargets(game, source)) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null) {
                    if (game.getActivePlayerId().equals(permanent.getControllerId())
                            || (game.getActivePlayerId().equals(onlyIfControlledByPlayer))) { // if effect works only for specific player, all permanents have to be set to handled in that players untap step
                        if (!handledTargetsDuringTurn.containsKey(targetId)) {
                            // it's the untep step of the current controller and the effect was not handled for this target yet, so do it now
                            handledTargetsDuringTurn.put(targetId, false);
                            allHandled = false;
                        } else if (!handledTargetsDuringTurn.get(targetId)) {
                            // if it was already ready to be handled on an previous Untap step set it to done if not already so
                            handledTargetsDuringTurn.put(targetId, true);
                        }
                    } else {
                        allHandled = false;
                    }
                }
            }
            if (allHandled) {
                discard();
            }
        }

        if (game.getTurn().getStepType() == PhaseStep.UNTAP && event.getType() == EventType.UNTAP) {
            if (handledTargetsDuringTurn.containsKey(event.getTargetId())
                    && !handledTargetsDuringTurn.get(event.getTargetId())
                    && getTargetPointer().getTargets(game, source).contains(event.getTargetId())) {
                Permanent permanent = game.getPermanent(event.getTargetId());
                if (permanent != null && game.getActivePlayerId().equals(permanent.getControllerId())) {
                    handledTargetsDuringTurn.put(event.getTargetId(), true);
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
            return "target " + (mode == null ? "creature" : mode.getTargets().get(0).getTargetName()) + " doesn't untap during its controller's next untap step";
        }
    }

}
