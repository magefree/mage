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
package mage.sets.avacynrestored;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetSource;
import mage.target.common.TargetCreatureOrPlayer;

import java.util.UUID;

/**
 * @author noxx
 */
public class DivineDeflection extends CardImpl<DivineDeflection> {

    public DivineDeflection(UUID ownerId) {
        super(ownerId, 18, "Divine Deflection", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{X}{W}");
        this.expansionSetCode = "AVR";

        this.color.setWhite(true);

        // Prevent the next X damage that would be dealt to you and/or permanents you control this turn. If damage is prevented this way, Divine Deflection deals that much damage to target creature or player.
        this.getSpellAbility().addEffect(new DivineDeflectionPreventDamageTargetEffect(Constants.Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetSource());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
    }

    public DivineDeflection(final DivineDeflection card) {
        super(card);
    }

    @Override
    public DivineDeflection copy() {
        return new DivineDeflection(this);
    }
}

class DivineDeflectionPreventDamageTargetEffect extends PreventionEffectImpl<DivineDeflectionPreventDamageTargetEffect> {

    private int amount = -1;
    
    public DivineDeflectionPreventDamageTargetEffect(Constants.Duration duration) {
        super(duration);
        staticText = "Prevent the next X damage that would be dealt to you and/or permanents you control this turn. If damage is prevented this way, {this} deals that much damage to target creature or player";
    }

    public DivineDeflectionPreventDamageTargetEffect(final DivineDeflectionPreventDamageTargetEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public DivineDeflectionPreventDamageTargetEffect copy() {
        return new DivineDeflectionPreventDamageTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), event.getAmount(), false);
        if (!game.replaceEvent(preventEvent)) {
            if (amount == -1) {
                // define once
                amount = source.getManaCostsToPay().getX();
            }
            int prevented = 0;
            if (event.getAmount() >= this.amount) {
                int damage = amount;
                event.setAmount(event.getAmount() - amount);
                this.used = true;
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), damage));
                prevented = damage;
            } else {
                int damage = event.getAmount();
                event.setAmount(0);
                amount -= damage;
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), damage));
                prevented = damage;
            }

            // deal damage now
            if (prevented > 0) {
                UUID redirectTo = source.getTargets().get(1).getFirstTarget();
                Permanent permanent = game.getPermanent(redirectTo);
                if (permanent != null) {
                    game.informPlayers("Dealing " + prevented + " to " + permanent.getName() + " instead");
                    // keep the original source id as it is redirecting
                    permanent.damage(prevented, event.getSourceId(), game, true, false);
                }
                Player player = game.getPlayer(redirectTo);
                if (player != null) {
                    game.informPlayers("Dealing " + prevented + " to " + player.getName() + " instead");
                    // keep the original source id as it is redirecting
                    player.damage(prevented, event.getSourceId(), game, true, false);
                }
            }
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && super.applies(event, source, game)) {

            // check source
            MageObject object = game.getObject(event.getSourceId());
            if (object == null) {
                game.informPlayers("Couldn't find source of damage");
                return false;
            }

            if (!object.getId().equals(source.getFirstTarget())) {
                return false;
            }

            // check target
            //   check permanent first
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null) {
                if (permanent.getControllerId().equals(source.getControllerId())) {
                    // it's your permanent
                    return true;
                }
            }
            //   check player
            Player player = game.getPlayer(event.getTargetId());
            if (player != null) {
                if (player.getId().equals(source.getControllerId())) {
                    // it is you
                    return true;
                }
            }
        }
        return false;
    }

}
