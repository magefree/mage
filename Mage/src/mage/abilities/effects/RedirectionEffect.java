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

import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.Duration;
import mage.constants.EffectType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class RedirectionEffect extends ReplacementEffectImpl {

    protected Target redirectTarget;
    protected int amountToRedirect;
    protected boolean oneUsage;

    public RedirectionEffect(Duration duration) {
        this(duration, Integer.MAX_VALUE, false);
    }

    public RedirectionEffect(Duration duration, int amountToRedirect, boolean oneUsage) {
        super(duration, Outcome.RedirectDamage);
        this.effectType = EffectType.REDIRECTION;
        this.amountToRedirect = amountToRedirect;
        this.oneUsage = oneUsage;
    }

    public RedirectionEffect(final RedirectionEffect effect) {
        super(effect);
        this.redirectTarget = effect.redirectTarget;
        this.amountToRedirect = effect.amountToRedirect;
        this.oneUsage = effect.oneUsage;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_CREATURE:
            case DAMAGE_PLAYER:
            case DAMAGE_PLANESWALKER:
                return true;
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        MageObject sourceObject = game.getObject(source.getSourceId());
        DamageEvent damageEvent = (DamageEvent) event;
        int restDamage = 0;
        int damageToRedirect = event.getAmount();
        if (damageEvent.getAmount() > amountToRedirect) {
            restDamage = damageEvent.getAmount() - amountToRedirect;
            damageToRedirect = amountToRedirect;
        }
        if (damageToRedirect > 0 && oneUsage) {
            this.discard();
        }
        Permanent permanent = game.getPermanent(redirectTarget.getFirstTarget());
        if (permanent != null) {
            permanent.damage(damageToRedirect, event.getSourceId(), game, damageEvent.isCombatDamage(), damageEvent.isPreventable(), event.getAppliedEffects());
            game.informPlayers(sourceObject.getLogName() + ": Redirected " + damageToRedirect + " damage to " + permanent.getLogName());
        } else {
            Player player = game.getPlayer(redirectTarget.getFirstTarget());
            if (player != null) {
                player.damage(damageToRedirect, event.getSourceId(), game, damageEvent.isCombatDamage(), damageEvent.isPreventable(), event.getAppliedEffects());
                game.informPlayers(sourceObject.getLogName() + ": Redirected " + damageToRedirect + " damage to " + player.getLogName());
            }
        }
        if (restDamage > 0) {
            damageEvent.setAmount(restDamage);
            return false;
        }
        return true;
    }

}
