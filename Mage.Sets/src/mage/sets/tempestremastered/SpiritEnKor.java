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
package mage.sets.tempestremastered;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public class SpiritEnKor extends CardImpl {

    public SpiritEnKor(UUID ownerId) {
        super(ownerId, 34, "Spirit en-Kor", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.expansionSetCode = "TPR";
        this.subtype.add("Kor");
        this.subtype.add("Spirit");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // {0}: The next 1 damage that would be dealt to Spirit en-Kor this turn is dealt to target creature you control instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SpiritEnKorPreventionEffect(), new GenericManaCost(0));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    public SpiritEnKor(final SpiritEnKor card) {
        super(card);
    }

    @Override
    public SpiritEnKor copy() {
        return new SpiritEnKor(this);
    }
}

class SpiritEnKorPreventionEffect extends PreventionEffectImpl {
    
    SpiritEnKorPreventionEffect() {
        super(Duration.EndOfTurn, 1, false);
        staticText = "The next 1 damage that would be dealt to {this} this turn is dealt to target creature you control instead.";
    }
    
    SpiritEnKorPreventionEffect(final SpiritEnKorPreventionEffect effect) {
        super(effect);
    }
    
    @Override
    public SpiritEnKorPreventionEffect copy() {
        return new SpiritEnKorPreventionEffect(this);
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