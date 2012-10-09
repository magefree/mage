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
package mage.sets.tempest;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 *
 */
public class FurnaceOfRath extends CardImpl<FurnaceOfRath> {

    public FurnaceOfRath(UUID ownerId) {
        super(ownerId, 177, "Furnace of Rath", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{R}{R}");
        this.expansionSetCode = "TMP";

        this.color.setRed(true);

        // If a source would deal damage to a creature or player, it deals double that damage to that creature or player instead.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new FurnaceOfRathEffect()));
    }

    public FurnaceOfRath(final FurnaceOfRath card) {
        super(card);
    }

    @Override
    public FurnaceOfRath copy() {
        return new FurnaceOfRath(this);
    }
}

class FurnaceOfRathEffect extends ReplacementEffectImpl<FurnaceOfRathEffect> {

    public FurnaceOfRathEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Damage);
        staticText = "If a source would deal damage to a creature or player, that source deals double that damage to that creature or player instead";
    }

    public FurnaceOfRathEffect(final FurnaceOfRathEffect effect) {
        super(effect);
    }

    @Override
    public FurnaceOfRathEffect copy() {
        return new FurnaceOfRathEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!event.getAppliedEffects().contains(this.getId())) {
            switch (event.getType()) {
                case DAMAGE_PLAYER:
                    return true;
                case DAMAGE_CREATURE:
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
        DamageEvent damageEvent = (DamageEvent)event;
        damageEvent.getAppliedEffects().add(getId());
        if (damageEvent.getType() == EventType.DAMAGE_PLAYER) {
            Player targetPlayer = game.getPlayer(event.getTargetId());
            if (targetPlayer != null) {
                targetPlayer.damage(damageEvent.getAmount()*2, damageEvent.getSourceId(), game, damageEvent.isPreventable(), damageEvent.isCombatDamage(), event.getAppliedEffects());
                return true;
            }
        } else {
            Permanent targetPermanent = game.getPermanent(event.getTargetId());
            if (targetPermanent != null) {
                targetPermanent.damage(damageEvent.getAmount()*2, damageEvent.getSourceId(), game, damageEvent.isPreventable(), damageEvent.isCombatDamage(), event.getAppliedEffects());
                return true;
            }
        }
        return false;
    }
}
