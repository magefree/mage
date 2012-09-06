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
package mage.abilities.effects.common;

import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.Target;

/**
 *
 * @author North
 */
public class IndestructibleTargetEffect extends ReplacementEffectImpl<IndestructibleTargetEffect> {

    public IndestructibleTargetEffect(Duration duration) {
        super(duration, Outcome.Benefit);
    }

    public IndestructibleTargetEffect(IndestructibleTargetEffect effect) {
        super(effect);
    }

    @Override
    public IndestructibleTargetEffect copy() {
        return new IndestructibleTargetEffect(this);
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
        return event.getType().equals(GameEvent.EventType.DESTROY_PERMANENT)
                && this.targetPointer.getTargets(game, source).contains(event.getTargetId());
    }

    @Override
    public String getText(Mode mode) {
        if (mode.getTargets().isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        Target target = mode.getTargets().get(0);
        if (target.getMaxNumberOfTargets() > 1) {
            if (target.getMaxNumberOfTargets() != target.getNumberOfTargets()) {
                sb.append("up to ");
            }
            sb.append(target.getMaxNumberOfTargets()).append(" ");
        }
        sb.append("target ").append(mode.getTargets().get(0).getTargetName());
        if (target.getMaxNumberOfTargets() > 1) {
            sb.append("s are indestructible");
        } else {
            sb.append(" is indestructible");
        }

        if (Duration.EndOfTurn.equals(this.duration)) {
            sb.append(" this turn");
        }

        return sb.toString();
    }
}
