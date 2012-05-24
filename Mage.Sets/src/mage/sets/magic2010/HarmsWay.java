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
package mage.sets.magic2010;

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
public class HarmsWay extends CardImpl<HarmsWay> {

    public HarmsWay(UUID ownerId) {
        super(ownerId, 14, "Harm's Way", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{W}");
        this.expansionSetCode = "M10";

        this.color.setWhite(true);

        // The next 2 damage that a source of your choice would deal to you and/or permanents you control this turn is dealt to target creature or player instead.
        this.getSpellAbility().addEffect(new HarmsWayPreventDamageTargetEffect(Constants.Duration.EndOfTurn, 2));
        this.getSpellAbility().addTarget(new TargetSource());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
    }

    public HarmsWay(final HarmsWay card) {
        super(card);
    }

    @Override
    public HarmsWay copy() {
        return new HarmsWay(this);
    }
}

class HarmsWayPreventDamageTargetEffect extends PreventionEffectImpl<HarmsWayPreventDamageTargetEffect> {

    private int amount;

    public HarmsWayPreventDamageTargetEffect(Constants.Duration duration, int amount) {
        super(duration);
        this.amount = amount;
        staticText = "The next " + amount + " damage that a source of your choice would deal to you and/or permanents you control this turn is dealt to target creature or player instead";
    }

    public HarmsWayPreventDamageTargetEffect(final HarmsWayPreventDamageTargetEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public HarmsWayPreventDamageTargetEffect copy() {
        return new HarmsWayPreventDamageTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), event.getAmount(), false);
        if (!game.replaceEvent(preventEvent)) {
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
                    game.informPlayers("Dealing " + event.getAmount() + " to " + permanent.getName() + " instead");
                    // keep the original source id as it is redirecting
                    permanent.damage(prevented, event.getSourceId(), game, true, false);
                }
                Player player = game.getPlayer(redirectTo);
                if (player != null) {
                    game.informPlayers("Dealing " + event.getAmount() + " to " + player.getName() + " instead");
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