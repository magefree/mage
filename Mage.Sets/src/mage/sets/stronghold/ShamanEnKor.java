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
package mage.sets.stronghold;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetSource;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public class ShamanEnKor extends CardImpl {

    public ShamanEnKor(UUID ownerId) {
        super(ownerId, 115, "Shaman en-Kor", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.expansionSetCode = "STH";
        this.subtype.add("Kor");
        this.subtype.add("Cleric");
        this.subtype.add("Shaman");

        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {0}: The next 1 damage that would be dealt to Shaman en-Kor this turn is dealt to target creature you control instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShamanEnKorPreventionEffect(), new GenericManaCost(0));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
        
        // {1}{W}: The next time a source of your choice would deal damage to target creature this turn, that damage is dealt to Shaman en-Kor instead.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShamanEnKorReplacementEffect(), new ManaCostsImpl("{1}{W}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public ShamanEnKor(final ShamanEnKor card) {
        super(card);
    }

    @Override
    public ShamanEnKor copy() {
        return new ShamanEnKor(this);
    }
}

class ShamanEnKorPreventionEffect extends PreventionEffectImpl {
    
    ShamanEnKorPreventionEffect() {
        super(Duration.EndOfTurn, 1, false);
        staticText = "The next 1 damage that would be dealt to {this} this turn is dealt to target creature you control instead.";
    }
    
    ShamanEnKorPreventionEffect(final ShamanEnKorPreventionEffect effect) {
        super(effect);
    }
    
    @Override
    public ShamanEnKorPreventionEffect copy() {
        return new ShamanEnKorPreventionEffect(this);
    }
    
    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionResult = preventDamageAction(event, source, game);
        if (preventionResult.getPreventedDamage() > 0) {
            Permanent redirectTo = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (redirectTo != null) {
                game.informPlayers("Dealing " + preventionResult.getPreventedDamage() + " to " + redirectTo.getName() + " instead.");
                DamageEvent damageEvent = (DamageEvent) event;
                redirectTo.damage(preventionResult.getPreventedDamage(), event.getSourceId(), game, damageEvent.isCombatDamage(), damageEvent.isPreventable(), event.getAppliedEffects());
            }
        }
        return false;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && super.applies(event, source, game)) {
            if (event.getTargetId().equals(source.getSourceId())) {
                return game.getPermanent(getTargetPointer().getFirst(game, source)) != null;
            }
        }
        return false;
    }
}

class ShamanEnKorReplacementEffect extends ReplacementEffectImpl {
    
    protected TargetSource targetSource;

    ShamanEnKorReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.RedirectDamage);
        staticText = "The next time a source of your choice would deal damage to target creature this turn, that damage is dealt to {this} instead";
    }

    ShamanEnKorReplacementEffect(final ShamanEnKorReplacementEffect effect) {
        super(effect);
        targetSource = effect.targetSource;
    }

    @Override
    public void init(Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        TargetSource target = new TargetSource();
        target.setNotTarget(true);
        if (player != null) {
            target.choose(Outcome.PreventDamage, player.getId(), source.getSourceId(), game);
            this.targetSource = target;
        }
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used) {
            if (event.getType().equals(GameEvent.EventType.DAMAGE_CREATURE) && targetSource != null) {
                if (event.getSourceId().equals(targetSource.getFirstTarget())) {
                    // check source
                    MageObject object = game.getObject(event.getSourceId());
                    if (object == null) {
                        game.informPlayers("Couldn't find source of damage");
                        return false;
                    }
                    else {
                        if (event.getTargetId().equals(source.getFirstTarget())) {
                            Permanent permanent = game.getPermanent(source.getFirstTarget());
                            if (permanent != null) {
                                return true;
                            }
                        }
                    }
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
                    message.append(targetPlayer.getLogName());
                }
                else {
                    message.append("unknown");
                }
            }
            game.informPlayers(message.toString());
            // redirect damage
            this.used = true;
            sourcePermanent.damage(damageEvent.getAmount(), damageEvent.getSourceId(), game, damageEvent.isCombatDamage(), damageEvent.isPreventable(), event.getAppliedEffects());
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ShamanEnKorReplacementEffect copy() {
        return new ShamanEnKorReplacementEffect(this);
    }
}