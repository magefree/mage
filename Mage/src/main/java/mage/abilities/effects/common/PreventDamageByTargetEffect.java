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

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.Target;
import mage.target.TargetSpell;

/**
 * @author nantuko
 */
public class PreventDamageByTargetEffect extends PreventionEffectImpl {

    public PreventDamageByTargetEffect(Duration duration) {
        this(duration, false);
    }

    public PreventDamageByTargetEffect(Duration duration, int amount) {
        this(duration, amount, false);
    }

    public PreventDamageByTargetEffect(Duration duration, boolean onlyCombat) {
        this(duration, Integer.MAX_VALUE, onlyCombat);
    }

    public PreventDamageByTargetEffect(Duration duration, int amount, boolean onlyCombat) {
        super(duration, amount, onlyCombat);
    }

    public PreventDamageByTargetEffect(final PreventDamageByTargetEffect effect) {
        super(effect);
    }

    @Override
    public PreventDamageByTargetEffect copy() {
        return new PreventDamageByTargetEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && super.applies(event, source, game)) {
            MageObject mageObject = game.getObject(event.getSourceId());
            if (mageObject != null
                    && (mageObject.isInstant() || mageObject.isSorcery())) {
                for (Target target : source.getTargets()) {
                    if (target instanceof TargetSpell) {
                        if (((TargetSpell) target).getSourceIds().contains(event.getSourceId())) {
                            return true;
                        }
                    }
                }
            }
            return this.getTargetPointer().getTargets(game, source).contains(event.getSourceId());
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        if (amountToPrevent == Integer.MAX_VALUE) {
            StringBuilder sb = new StringBuilder();
            sb.append("Prevent all");
            if (onlyCombat) {
                sb.append(" combat ");
            }
            sb.append(" damage target ");
            sb.append(mode.getTargets().get(0).getTargetName()).append(" would deal ").append(duration.toString());
            return sb.toString();
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Prevent the next ").append(amountToPrevent);
            if (onlyCombat) {
                sb.append("combat ");
            }
            sb.append(" damage that ");
            sb.append(mode.getTargets().get(0).getTargetName()).append(" would deal ").append(duration.toString());
            return sb.toString();
        }

    }

}
