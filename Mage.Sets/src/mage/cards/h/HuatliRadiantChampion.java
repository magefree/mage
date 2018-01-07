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
package mage.cards.h;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.command.emblems.HuatliRadiantChampionEmblem;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class HuatliRadiantChampion extends CardImpl {

    public HuatliRadiantChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{G}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUATLI);
        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(3));

        // +1: Put a loyalty counter on Huatli, Radiant Champion for each creature you control.
        this.addAbility(new LoyaltyAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(0),
                new PermanentsOnBattlefieldCount(StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED), true), 1));

        // -1: Target creature gets +X/+X until end of turn, where X is the number of creatures you control.
        PermanentsOnBattlefieldCount amount = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED);
        LoyaltyAbility ability2 = new LoyaltyAbility(new BoostTargetEffect(amount, amount, Duration.EndOfTurn, true)
                .setText("Target creature gets +X/+X until end of turn, where X is the number of creatures you control"), -1);
        ability2.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability2);

        // -8: You get an emblem with "Whenever a creature enters the battlefield under your control, you may draw a card."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new HuatliRadiantChampionEmblem()), -8));
    }

    public HuatliRadiantChampion(final HuatliRadiantChampion card) {
        super(card);
    }

    @Override
    public HuatliRadiantChampion copy() {
        return new HuatliRadiantChampion(this);
    }
}
