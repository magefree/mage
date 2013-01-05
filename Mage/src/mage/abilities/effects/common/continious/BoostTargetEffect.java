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
package mage.abilities.effects.common.continious;

import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.SubLayer;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class BoostTargetEffect extends ContinuousEffectImpl<BoostTargetEffect> {

    private DynamicValue power;
    private DynamicValue toughness;
    private boolean lockedIn;

    public BoostTargetEffect(int power, int toughness, Duration duration) {
        this(new StaticValue(power), new StaticValue(toughness), duration, false);
    }

    public BoostTargetEffect(DynamicValue power, DynamicValue toughness, Duration duration) {
        this(power, toughness, duration, false);
    }

    /**
     * @param lockedIn if true, power and toughness will be calculated only once, when the ability resolves
     */
    public BoostTargetEffect(DynamicValue power, DynamicValue toughness, Duration duration, boolean lockedIn) {
        super(duration, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, isCanKill(toughness) ? Outcome.UnboostCreature : Outcome.BoostCreature);
        this.power = power;
        this.toughness = toughness;
        this.lockedIn = lockedIn;
    }

    public BoostTargetEffect(final BoostTargetEffect effect) {
        super(effect);
        this.power = effect.power.copy();
        this.toughness = effect.toughness.copy();
        this.lockedIn = effect.lockedIn;
    }

    @Override
    public BoostTargetEffect copy() {
        return new BoostTargetEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (lockedIn) {
            power = new StaticValue(power.calculate(game, source));
            toughness = new StaticValue(toughness.calculate(game, source));
        }
        targetPointer.init(game, source);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int affectedTargets = 0;
        for (UUID permanentId : targetPointer.getTargets(game, source)) {
            Permanent target = (Permanent) game.getPermanent(permanentId);
            if (target != null) {
                target.addPower(power.calculate(game, source));
                target.addToughness(toughness.calculate(game, source));
                affectedTargets++;
            }
        }
        return affectedTargets > 0;
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        Target target = mode.getTargets().get(0);
        if(target.getNumberOfTargets() > 1){
            sb.append(target.getNumberOfTargets()).append(" target ").append(target.getTargetName()).append(" get ");
        } else {
            sb.append("Target ").append(target.getTargetName()).append(" gets ");
        }
        String p = power.toString();
        if(!p.startsWith("-")) {
            sb.append("+");
        }
        sb.append(p).append("/");
        String t = toughness.toString();
        if(!t.startsWith("-")){
            if(p.startsWith("-")) {
                sb.append("-");
            }
            else {
                sb.append("+");
            }
        }
        sb.append(t);
        if (duration != Duration.WhileOnBattlefield) {
            sb.append(" ").append(duration.toString());
        }
        String message = power.getMessage();
        if (message.length() > 0) {
            sb.append(" for each ");
        }
        sb.append(message);
        return sb.toString();
    }
}
