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
import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.turn.Step;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */


//
//    701.26. Detain
//
//    701.26a Certain spells and abilities can detain a permanent. Until the next
//    turn of the controller of that spell or ability, that permanent can’t attack
//    or block and its activated abilities can’t be activated.
//

public class DetainTargetEffect extends OneShotEffect<DetainTargetEffect> {

    public DetainTargetEffect() {
        super(Constants.Outcome.LoseAbility);
    }

    public DetainTargetEffect(String ruleText) {
        super(Constants.Outcome.LoseAbility);
        staticText = ruleText;
    }

    public DetainTargetEffect(final DetainTargetEffect effect) {
        super(effect);
    }

    @Override
    public DetainTargetEffect copy() {
        return new DetainTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID target: this.getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(target);
            if (permanent != null) {
                game.informPlayers("Detained creature: " + permanent.getName());
            }
        }
        game.getContinuousEffects().addEffect(new DetainReplacementEffect(), source);
        game.getContinuousEffects().addEffect(new DetainRestrictionEffect(), source);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        Target target = mode.getTargets().get(0);
        
        if (target.getMaxNumberOfTargets() == target.getNumberOfTargets()) {
            if (target.getMaxNumberOfTargets() == 1) {
                sb.append("detain target ").append(target.getTargetName());
            }
            else {
                sb.append("detain ").append(target.getMaxNumberOfTargets()).append(" target ").append(target.getTargetName());
            }
        } else {
                sb.append("detain up to ").append(target.getMaxNumberOfTargets()).append(" target ").append(target.getTargetName());
        }
        sb.append(". <i>(Until your next turn, ");


        if (target instanceof TargetCreaturePermanent) {
            sb.append(target.getMaxNumberOfTargets() == 1 ? "that creature": "those creatures");
        } 
        else  {
            sb.append(target.getMaxNumberOfTargets() == 1 ? "that permanent": "those permanents");
        }
        sb.append(" can't attack or block and ");
        sb.append(target.getMaxNumberOfTargets() == 1 ? "its": "their");
        sb.append(" activated abilities can't be activated)</i>");
        return sb.toString();
    }
}


class DetainReplacementEffect extends ReplacementEffectImpl<DetainReplacementEffect> {
 
    public DetainReplacementEffect() {
        super(Constants.Duration.Custom, Constants.Outcome.LoseAbility);
        staticText = "";
    }
 
    public DetainReplacementEffect(final DetainReplacementEffect effect) {
        super(effect);
    }
 
    @Override
    public DetainReplacementEffect copy() {
        return new DetainReplacementEffect(this);
    }
 
    @Override
    public boolean isInactive(Ability source, Game game) {
        if (game.getPhase().getStep().getType() == Constants.PhaseStep.UNTAP && game.getStep().getStepPart() == Step.StepPart.PRE)
        {
            if (game.getActivePlayerId().equals(source.getControllerId())) {
                return true;
            }
        }
        return false;
    }
 
    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
 
    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }
 
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.ACTIVATE_ABILITY) {
            if (this.targetPointer.getTargets(game, source).contains(event.getSourceId())) {
                return true;
            }            
        }
        return false;
    }
}
 
class DetainRestrictionEffect extends RestrictionEffect<DetainRestrictionEffect> {
 
    public DetainRestrictionEffect() {
        super(Constants.Duration.Custom);
        staticText = "";
    }
 
    public DetainRestrictionEffect(final DetainRestrictionEffect effect) {
        super(effect);
    }
 
    @Override
    public boolean isInactive(Ability source, Game game) {
        if (game.getPhase().getStep().getType() == Constants.PhaseStep.UNTAP && game.getStep().getStepPart() == Step.StepPart.PRE)
        {
            if (game.getActivePlayerId().equals(source.getControllerId())) {
                return true;
            }
        }
        return false;
    }
 
    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (this.targetPointer.getTargets(game, source).contains(permanent.getId())) {
            return true;
        }
        return false;
    }
 
    @Override
    public boolean canAttack(Game game) {
        return false;
    }
 
    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return false;
    }
 
    @Override
    public DetainRestrictionEffect copy() {
        return new DetainRestrictionEffect(this);
    }
 
}