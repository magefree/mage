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
package mage.sets.magic2015;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class AjaniSteadfast extends CardImpl {

    private static final FilterPlaneswalkerPermanent filter = new FilterPlaneswalkerPermanent("other planeswalker you control");
    
    static {
        filter.add(new AnotherPredicate());
        filter.add(new ControllerPredicate(TargetController.YOU));
    }
    
    public AjaniSteadfast(UUID ownerId) {
        super(ownerId, 1, "Ajani Steadfast", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{3}{W}");
        this.expansionSetCode = "M15";
        this.subtype.add("Ajani");

        
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(4)), false));

        // +1: Until end of turn, up to one target creature gets +1/+1 and gains first strike, vigilance, and lifelink.
        Effect effect = new BoostTargetEffect(1,1, Duration.EndOfTurn);
        effect.setText("Until end of turn, up to one target creature gets +1/+1");
        LoyaltyAbility ability = new LoyaltyAbility(effect, 1);
        effect = new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains first strike");
        ability.addEffect(effect);
        effect = new GainAbilityTargetEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn);
        effect.setText(", vigilance");
        ability.addEffect(effect);
        effect = new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn);
        effect.setText(", and lifelink");
        ability.addEffect(effect);
        ability.addTarget(new TargetCreaturePermanent(0,1));
        this.addAbility(ability);
        
        // -2: Put a +1/+1 counter on each creature you control and a loyalty counter on each other planeswalker you control.
        ability = new LoyaltyAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), new FilterControlledCreaturePermanent()), -2);
        effect = new AddCountersAllEffect(CounterType.LOYALTY.createInstance(), filter);
        effect.setText("and a loyalty counter on each other planeswalker you control");
        ability.addEffect(effect);
        this.addAbility(ability);
        
        // -7: You get an emblem with "If a source would deal damage to you or a planeswalker you control, prevent all but 1 of that damage."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new AjaniSteadfastEmblem()), -7));
    }

    public AjaniSteadfast(final AjaniSteadfast card) {
        super(card);
    }

    @Override
    public AjaniSteadfast copy() {
        return new AjaniSteadfast(this);
    }
}

class AjaniSteadfastEmblem extends Emblem {

    public AjaniSteadfastEmblem() {
        setName("EMBLEM: Ajani Steadfast");
        this.getAbilities().add(new SimpleStaticAbility(Zone.COMMAND, new AjaniSteadfastPreventEffect()));        
        this.setExpansionSetCodeForImage("M15");
    }
}

class AjaniSteadfastPreventEffect extends PreventionEffectImpl {

    public AjaniSteadfastPreventEffect() {
        super(Duration.EndOfGame);
        this.staticText = "If a source would deal damage to you or a planeswalker you control, prevent all but 1 of that damage";
        consumable = false;
    }

    public AjaniSteadfastPreventEffect(AjaniSteadfastPreventEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int damage = event.getAmount();
        if (damage > 1) {
            amountToPrevent = damage - 1;
            preventDamageAction(event, source, game);        
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType().equals(GameEvent.EventType.DAMAGE_PLAYER)
                && event.getTargetId().equals(source.getControllerId())) {
            return super.applies(event, source, game);
        }

        if (event.getType().equals(GameEvent.EventType.DAMAGE_PLANESWALKER)) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.getControllerId().equals(source.getControllerId())) {
                return super.applies(event, source, game);
            }
        }
        return false;
    }

    @Override
    public AjaniSteadfastPreventEffect copy() {
        return new AjaniSteadfastPreventEffect(this);
    }
}
