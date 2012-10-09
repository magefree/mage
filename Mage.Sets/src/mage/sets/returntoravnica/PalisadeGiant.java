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
package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author LevelX2
 */

/*
 * Applying this redirection effect doesn't change whether the damage is combat damage.
 *
 * If noncombat damage would be dealt to a planeswalker you control, the planeswalker
 * redirection effect and Palisade Giant's redirection effect apply in whichever
 * order you choose. No matter which order you choose to apply them in, that damage
 * will be dealt to Palisade Giant instead.
 *
 * If you control more than one Palisade Giant, you choose which redirection effect
 * to apply. You can't divide damage dealt by one source. For example, if an attacking
 * creature would deal 8 damage to you and you control two Palisade Giants, you may
 * have that damage dealt to either of the Palisade Giants. You can't have 4 damage
 * dealt to each Giant or choose to have the 8 damage dealt to you.
 */



public class PalisadeGiant extends CardImpl<PalisadeGiant> {

    public PalisadeGiant(UUID ownerId) {
        super(ownerId, 15, "Palisade Giant", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        this.expansionSetCode = "RTR";
        this.subtype.add("Giant");
        this.subtype.add("Soldier");

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(7);

        // All damage that would be dealt to you or another permanent you control is dealt to Palisade Giant instead.
         this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PalisadeGiantReplacementEffect()));
    }

    public PalisadeGiant(final PalisadeGiant card) {
        super(card);
    }

    @Override
    public PalisadeGiant copy() {
        return new PalisadeGiant(this);
    }
}

class PalisadeGiantReplacementEffect extends ReplacementEffectImpl<PalisadeGiantReplacementEffect> {

    PalisadeGiantReplacementEffect() {
        super(Constants.Duration.WhileOnBattlefield, Outcome.RedirectDamage);
        staticText = "All damage that would be dealt to you or another permanent you control is dealt to Palisade Giant instead";
    }

    PalisadeGiantReplacementEffect(final PalisadeGiantReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGE_PLAYER && event.getPlayerId().equals(source.getControllerId())
                && !event.getAppliedEffects().contains(getId()))
        {

           return true;
        }
        if (event.getType() == GameEvent.EventType.DAMAGE_CREATURE || event.getType() == GameEvent.EventType.DAMAGE_PLANESWALKER)
        {
            Permanent targetPermanent = game.getPermanent(event.getTargetId());
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (targetPermanent != null && 
                    targetPermanent.getControllerId().equals(source.getControllerId()) &&
                    !targetPermanent.getName().equals(sourcePermanent.getName()) && // no redirection from or to other Palisade Giants
                    !event.getAppliedEffects().contains(getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamageEvent damageEvent = (DamageEvent)event;
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            // get name of old target
            Permanent targetPermanent = game.getPermanent(event.getTargetId());
            StringBuilder message = new StringBuilder();
            message.append(sourcePermanent.getName()).append(": gets ");
            message.append(damageEvent.getAmount()).append(" damage redirected from ");
            if (targetPermanent != null) {
                message.append(targetPermanent.getName());
            }
            else {
                Player targetPlayer = game.getPlayer(event.getTargetId());
                if (targetPlayer != null) {
                    message.append(targetPlayer.getName());
                }
                else {
                    message.append("unknown");
                }

            }
            game.informPlayers(message.toString());
            // remember redirection effect (614.5)
            event.getAppliedEffects().add(getId());
            // redirect damage
            sourcePermanent.damage(damageEvent.getAmount(), damageEvent.getSourceId(), game, damageEvent.isPreventable(), damageEvent.isCombatDamage(), event.getAppliedEffects());
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public PalisadeGiantReplacementEffect copy() {
        return new PalisadeGiantReplacementEffect(this);
    }
}
