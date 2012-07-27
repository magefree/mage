/*
 *  
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
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
 * 
 */
package mage.abilities.effects.common.continious;

import mage.Constants;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;

/**
 * @author LevelX
 */
public class SetCardColorTargetEffect extends ContinuousEffectImpl<SetCardColorTargetEffect> {

    private ObjectColor setColor;

    public SetCardColorTargetEffect(ObjectColor setColor, Constants.Duration duration, String text) {
        super(duration, Constants.Layer.ColorChangingEffects_5, Constants.SubLayer.NA, Constants.Outcome.Benefit);
        this.setColor = setColor;
        staticText = text;
    }

    public SetCardColorTargetEffect(ObjectColor setColor, Constants.Duration duration) {
        super(duration, Constants.Layer.ColorChangingEffects_5, Constants.SubLayer.NA, Constants.Outcome.Benefit);
        this.setColor = setColor;
    }

    public SetCardColorTargetEffect(final SetCardColorTargetEffect effect) {
        super(effect);
        this.setColor = effect.setColor;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject o = game.getObject(targetPointer.getFirst(game, source));
        if (o != null) {
            if (o instanceof Permanent || o instanceof StackObject) {
                o.getColor().setColor(setColor);
            }
        }

        return false;
    }

    @Override
    public SetCardColorTargetEffect copy() {
        return new SetCardColorTargetEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        sb.append("Target ").append(mode.getTargets().get(0).getTargetName());
        sb.append(" becomes ").append(setColor.getDescription());
        sb.append(" ").append(duration.toString());
        return sb.toString();
    }
}