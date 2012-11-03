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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.BushidoAbility;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetSource;

/**
 * @author LevelX2
 */
public class OpalEyeKondasYojimbo extends CardImpl<OpalEyeKondasYojimbo> {

    public OpalEyeKondasYojimbo(UUID ownerId) {
        super(ownerId, 17, "Opal-Eye, Konda's Yojimbo", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.expansionSetCode = "BOK";
        this.supertype.add("Legendary");
        this.subtype.add("Fox");
        this.subtype.add("Samurai");
        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Defender (This creature can't attack.)
        this.addAbility(DefenderAbility.getInstance());

        // Bushido 1 (When this blocks or becomes blocked, it gets +1/+1 until end of turn.)
        this.addAbility(new BushidoAbility(1));

        // {T}: The next time a source of your choice would deal damage this turn, that damage is dealt to Opal-Eye, Konda's Yojimbo instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new OpalEyeKondasYojimboRedirectionEffect(), new TapSourceCost());
        ability.addTarget(new TargetSource());
        this.addAbility(ability);

        // {1}{W}: Prevent the next 1 damage that would be dealt to Opal-Eye this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new OpalEyeKondasYojimboPreventEffect(), new ManaCostsImpl("{1}{W}")));

    }

    public OpalEyeKondasYojimbo(final OpalEyeKondasYojimbo card) {
        super(card);
    }

    @Override
    public OpalEyeKondasYojimbo copy() {
        return new OpalEyeKondasYojimbo(this);
    }
}

class OpalEyeKondasYojimboRedirectionEffect extends ReplacementEffectImpl<OpalEyeKondasYojimboRedirectionEffect> {

    OpalEyeKondasYojimboRedirectionEffect() {
        super(Constants.Duration.EndOfTurn, Outcome.RedirectDamage);
        staticText = "The next time a source of your choice would deal damage this turn, that damage is dealt to {this} instead";
    }

    OpalEyeKondasYojimboRedirectionEffect(final OpalEyeKondasYojimboRedirectionEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used) {
            if (event.getType().equals(GameEvent.EventType.DAMAGE_CREATURE ) ||
                event.getType().equals(GameEvent.EventType.DAMAGE_PLANESWALKER ) ||
                event.getType().equals(GameEvent.EventType.DAMAGE_PLAYER )    ) {
                if (event.getSourceId().equals(targetPointer.getFirst(game, source))) {
                    // check source
                    MageObject object = game.getObject(event.getSourceId());
                    if (object == null) {
                        game.informPlayers("Couldn't find source of damage");
                        return false;
                    }
                    return true;
                }
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
            this.used = true;
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
    public OpalEyeKondasYojimboRedirectionEffect copy() {
        return new OpalEyeKondasYojimboRedirectionEffect(this);
    }
}

class OpalEyeKondasYojimboPreventEffect extends PreventionEffectImpl<OpalEyeKondasYojimboPreventEffect> {

    public OpalEyeKondasYojimboPreventEffect() {
        super(Constants.Duration.EndOfTurn);
        staticText = "Prevent the next 1 damage that would be dealt to {this} this turn";
    }

    public OpalEyeKondasYojimboPreventEffect(final OpalEyeKondasYojimboPreventEffect effect) {
        super(effect);
    }

    @Override
    public OpalEyeKondasYojimboPreventEffect copy() {
        return new OpalEyeKondasYojimboPreventEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), event.getAmount(), false);
        if (!game.replaceEvent(preventEvent)) {
            if (event.getAmount() >= 1) {
                int damage = 1;
                event.setAmount(event.getAmount() - 1);
                this.used = true;
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), damage));
            }
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && super.applies(event, source, game) && event.getTargetId().equals(source.getSourceId())) {
            return true;
        }
        return false;
    }
}
