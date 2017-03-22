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

import mage.constants.Duration;
import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.Target;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public class RegenerateTargetEffect  extends ReplacementEffectImpl {

    public RegenerateTargetEffect ( ) {
        super(Duration.EndOfTurn, Outcome.Regenerate);
    }

    public RegenerateTargetEffect(final RegenerateTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        //20110204 - 701.11
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent != null && permanent.regenerate(this.getId(), game)) {
            this.used = true;
            return true;
        }
        return false;
    }

    @Override
    public RegenerateTargetEffect copy() {
        return new RegenerateTargetEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return apply(game, source);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return EventType.DESTROY_PERMANENT == event.getType();
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        //20110204 - 701.11c - event.getAmount() is used to signal if regeneration is allowed
        if (event.getAmount() == 0 && event.getTargetId().equals(targetPointer.getFirst(game, source)) && !this.used) {
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
        sb.append("Regenerate ");
        Target target = mode.getTargets().get(0);
        if (target != null) {
            if (!target.getTargetName().toLowerCase().startsWith("another")) {
                sb.append("target ");
            }
            sb.append(target.getTargetName());
        }
        return sb.toString();
    }

}
