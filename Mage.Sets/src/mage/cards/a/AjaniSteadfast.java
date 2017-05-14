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
package mage.cards.a;

import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.command.emblems.AjaniSteadfastEmblem;
import mage.target.common.TargetCreaturePermanent;
import java.util.UUID;

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

    public AjaniSteadfast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{W}");
        this.subtype.add("Ajani");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(4));

        // +1: Until end of turn, up to one target creature gets +1/+1 and gains first strike, vigilance, and lifelink.
        Effect effect = new BoostTargetEffect(1, 1, Duration.EndOfTurn);
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
        ability.addTarget(new TargetCreaturePermanent(0, 1));
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
