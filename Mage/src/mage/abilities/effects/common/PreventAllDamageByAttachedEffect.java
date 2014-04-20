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
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */

public class PreventAllDamageByAttachedEffect extends PreventionEffectImpl<PreventAllDamageByAttachedEffect> {

    private final String attachedDescription;
    private final boolean combatOnly;

    public PreventAllDamageByAttachedEffect(Duration duration, String attachedDescription, boolean combatOnly) {
        super(duration);
        this.attachedDescription = attachedDescription;
        this.combatOnly = combatOnly;
        staticText = setText();
    }

    public PreventAllDamageByAttachedEffect(final PreventAllDamageByAttachedEffect effect) {
        super(effect);
        this.attachedDescription = effect.attachedDescription;
        this.combatOnly = effect.combatOnly;
    }

    @Override
    public PreventAllDamageByAttachedEffect copy() {
        return new PreventAllDamageByAttachedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            Permanent attachment = game.getPermanent(source.getSourceId());
            if (attachment != null && attachment.getAttachedTo() != null) {
                if (event.getSourceId().equals(attachment.getAttachedTo())) {
                    return true;
                }
            }
        }
        return false;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("prevent all ");
        if (combatOnly) {
            sb.append("combat ");
        }
        sb.append("damage that would be dealt by ");
        sb.append(attachedDescription);
        return sb.toString();
    }
}
