/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.shardsofalara;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.DragonToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SarkhanVol extends CardImpl {

    private static DragonToken dragonToken = new DragonToken();

    public SarkhanVol(UUID ownerId) {
        super(ownerId, 191, "Sarkhan Vol", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{G}");
        this.expansionSetCode = "ALA";
        this.subtype.add("Sarkhan");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(4));

        // +1: Creatures you control get +1/+1 and gain haste until end of turn.
        Effects effects1 = new Effects();
        effects1.add(new BoostControlledEffect(1, 1, Duration.EndOfTurn));
        effects1.add(new GainAbilityControlledEffect(HasteAbility.getInstance(), Duration.EndOfTurn, new FilterCreaturePermanent()));
        this.addAbility(new LoyaltyAbility(effects1, 1));

        // -2: Gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn.
        Effects effects2 = new Effects();
        effects2.add(new GainControlTargetEffect(Duration.EndOfTurn));
        effects2.add(new UntapTargetEffect());
        effects2.add(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn));
        LoyaltyAbility ability = new LoyaltyAbility(effects2, -2);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // -6: Put five 4/4 red Dragon creature tokens with flying onto the battlefield.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(dragonToken, 5), -6));
    }

    public SarkhanVol(final SarkhanVol card) {
        super(card);
    }

    @Override
    public SarkhanVol copy() {
        return new SarkhanVol(this);
    }
}
