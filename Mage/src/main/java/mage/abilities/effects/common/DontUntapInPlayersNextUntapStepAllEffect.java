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

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author okuRaku
 */
public class DontUntapInPlayersNextUntapStepAllEffect extends ContinuousRuleModifyingEffectImpl {

    private int validForTurnNum;
    //private String targetName;
    FilterPermanent filter;

    /**
     * Attention: This effect won't work with targets controlled by different
     * controllers If this is needed, the validForTurnNum has to be saved per
     * controller.
     *
     * @param filter
     */
    public DontUntapInPlayersNextUntapStepAllEffect(FilterPermanent filter) {
        super(Duration.Custom, Outcome.Detriment, false, true);
        this.filter = filter;
    }

    public DontUntapInPlayersNextUntapStepAllEffect(final DontUntapInPlayersNextUntapStepAllEffect effect) {
        super(effect);
        this.validForTurnNum = effect.validForTurnNum;
        this.filter = effect.filter;

    }

    @Override
    public DontUntapInPlayersNextUntapStepAllEffect copy() {
        return new DontUntapInPlayersNextUntapStepAllEffect(this);
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
            return permanentToUntap.getLogName() + " doesn't untap (" + mageObject.getLogName() + ')';
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
        if (event.getType() == EventType.UNTAP_STEP) {
            if (game.getActivePlayerId().equals(getTargetPointer().getFirst(game, source))) {
                if (validForTurnNum == game.getTurnNum()) { // the turn has a second untap step but the effect is already related to the first untap step
                    discard();
                    return false;
                }
                validForTurnNum = game.getTurnNum();
            }
        }

        if (game.getTurn().getStepType() == PhaseStep.UNTAP && event.getType() == EventType.UNTAP) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null) {
                Player controller = game.getPlayer(source.getControllerId());
                if (!permanent.getControllerId().equals(getTargetPointer().getFirst(game, source))) {
                    return false;
                }
                if (controller != null && !game.isOpponent(controller, permanent.getControllerId())) {
                    return false;
                }
                if (game.getActivePlayerId().equals(permanent.getControllerId())
                        && // controller's untap step
                        filter.match(permanent, source.getSourceId(), source.getControllerId(), game)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (!staticText.isEmpty()) {
            return staticText;
        }
        return filter.getMessage() + " target opponent controls don't untap during his or her next untap step.";
    }
}
