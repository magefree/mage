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

package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
 *
 * @author LevelX2
 */

public class CantCastMoreThanOneSpellEffect extends ContinuousRuleModifyingEffectImpl {

    private final TargetController targetController;
    
    public CantCastMoreThanOneSpellEffect(TargetController targetController) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.targetController = targetController;        
    }

    public CantCastMoreThanOneSpellEffect(final CantCastMoreThanOneSpellEffect effect) {
        super(effect);
        this.targetController = effect.targetController;
    }

    @Override
    public CantCastMoreThanOneSpellEffect copy() {
        return new CantCastMoreThanOneSpellEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        switch (targetController) {
            case YOU:
                if (!event.getPlayerId().equals(source.getControllerId())) {
                    return false;
                }
                break;
            case NOT_YOU:
                if (event.getPlayerId().equals(source.getControllerId())) {
                    return false;
                }
                break;
            case OPPONENT:
                if (!game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
                    return false;
                }
                break;
            case CONTROLLER_ATTACHED_TO:
                Permanent attachment = game.getPermanent(source.getSourceId());
                if (attachment == null || !attachment.getAttachedTo().equals(event.getPlayerId())) {
                    return false;
                }                    
        }
        CastSpellLastTurnWatcher watcher = (CastSpellLastTurnWatcher) game.getState().getWatchers().get("CastSpellLastTurnWatcher");
        if (watcher != null && watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(event.getPlayerId())> 0) {
            return true;
        }
        return false;
    }
    
    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        switch(targetController) {
            case YOU:
                sb.append("You");
                break;
            case NOT_YOU:
                sb.append("Each other player");
                break;
            case OPPONENT:
                sb.append("Each opponent");
                break;
            case ANY:
                sb.append("Each player");
                break;
            case CONTROLLER_ATTACHED_TO:
                sb.append("Enchanted player");
                break;
            default:
                throw new UnsupportedOperationException("TargetController = " + targetController.toString() + " not supported");
        }
        sb.append(" can't cast more than one spell each turn");
        return sb.toString();
    }
}
