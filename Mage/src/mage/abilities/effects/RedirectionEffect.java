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

package mage.abilities.effects;

import java.util.UUID;
import mage.Constants.Duration;
import mage.Constants.EffectType;
import mage.Constants.Outcome;
import mage.abilities.Ability;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class RedirectionEffect<T extends RedirectionEffect<T>> extends ReplacementEffectImpl<T> {

    protected TargetPermanent redirectTarget;

    public RedirectionEffect(Duration duration) {
        super(duration, Outcome.RedirectDamage);
        this.effectType = EffectType.REDIRECTION;
    }

    public RedirectionEffect(final RedirectionEffect effect) {
        super(effect);
        this.redirectTarget = effect.redirectTarget;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamageEvent damageEvent = (DamageEvent)event;
        Permanent permanent = game.getPermanent(redirectTarget.getFirstTarget());
        Ability damageSource = getSource(damageEvent.getSourceId(), game);
        if (permanent != null && damageSource != null) {
            permanent.damage(damageEvent.getAmount(), damageSource.getId(), game, damageEvent.isPreventable(), damageEvent.isCombatDamage());
            return true;
        }
        Player player = game.getPlayer(redirectTarget.getFirstTarget());
        if (player != null) {
            player.damage(damageEvent.getAmount(), damageSource.getId(), game, damageEvent.isCombatDamage(), damageEvent.isPreventable());
            return true;
        }
        return false;
    }

    protected Ability getSource(UUID sourceId, Game game) {
        StackObject source = game.getStack().getStackObject(sourceId);
        if (source != null) {
            if (source instanceof StackAbility)
                return (StackAbility)source;
            if (source instanceof Spell)
                return ((Spell)source).getSpellAbility();
        }
        return null;
    }

}
